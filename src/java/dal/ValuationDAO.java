/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import models.Valuation;
import utils.DBUtils;

/**
 *
 * @author Hao
 */
public class ValuationDAO extends DBUtils {

    // Lấy valuation mới nhất theo listing
    public Valuation getLatestByListingId(int listingId) throws Exception {
        String sql = "SELECT TOP 1 val_id, property_id, listing_id, p_low, p_med, p_high, confidence, explanation_text, model_version, created_at "
                + "FROM dbo.valuations WHERE listing_id = ? "
                + "ORDER BY created_at DESC, val_id DESC";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, listingId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Valuation(
                            rs.getInt("val_id"),
                            (Integer) rs.getObject("property_id"),
                            (Integer) rs.getObject("listing_id"),
                            rs.getBigDecimal("p_low"),
                            rs.getBigDecimal("p_med"),
                            rs.getBigDecimal("p_high"),
                            rs.getBigDecimal("confidence"),
                            rs.getString("explanation_text"),
                            rs.getString("model_version"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        }
        return null;
    }

    // Lấy dữ liệu base để demo AI (list_price + area + location)
    public ListingBase getListingBase(int listingId) throws Exception {
        String sql = "SELECT l.listing_id, l.list_price, l.status, "
                + "p.area_m2, p.city, p.district, p.ward, p.type "
                + "FROM dbo.listings l "
                + "JOIN dbo.properties p ON p.property_id = l.property_id "
                + "WHERE l.listing_id = ?";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, listingId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ListingBase b = new ListingBase();
                    b.listingId = rs.getInt("listing_id");
                    b.listPrice = rs.getBigDecimal("list_price");
                    b.status = rs.getString("status");
                    b.areaM2 = rs.getBigDecimal("area_m2");
                    b.city = rs.getString("city");
                    b.district = rs.getString("district");
                    b.ward = rs.getString("ward");
                    b.type = rs.getString("type");
                    return b;
                }
            }
        }
        return null;
    }

    // Insert valuation theo listing (property_id phải NULL để không dính CK_valuations_xor)
    public boolean createForListing(Valuation v) throws Exception {
        String sql = "INSERT INTO dbo.valuations(property_id, listing_id, p_low, p_med, p_high, confidence, explanation_text, model_version) "
                + "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, v.getListingId());
            ps.setBigDecimal(2, v.getPLow());
            ps.setBigDecimal(3, v.getPMed());
            ps.setBigDecimal(4, v.getPHigh());

            if (v.getConfidence() == null) {
                ps.setNull(5, java.sql.Types.DECIMAL);
            } else {
                ps.setBigDecimal(5, v.getConfidence());
            }

            ps.setString(6, v.getExplanationText());
            ps.setString(7, v.getModelVersion()); // có thể null

            return ps.executeUpdate() > 0;
        }
    }

    // Demo “AI”: bấm phát ra 1 valuation mới
    public Valuation generateDemoValuation(int listingId) throws Exception {
        ListingBase b = getListingBase(listingId);
        if (b == null || b.listPrice == null) {
            return null;
        }

        // factor theo area (demo)
        BigDecimal area = (b.areaM2 == null ? BigDecimal.ZERO : b.areaM2);

        // random nhẹ ±2%
        double jitter = (new Random().nextDouble() * 0.04) - 0.02; // [-0.02, +0.02]

        BigDecimal base = b.listPrice;
        BigDecimal pMed = base.multiply(BigDecimal.valueOf(1.0 + jitter));
        BigDecimal pLow = pMed.multiply(BigDecimal.valueOf(0.95));
        BigDecimal pHigh = pMed.multiply(BigDecimal.valueOf(1.05));

        // làm tròn VND (không lẻ)
        pMed = pMed.setScale(0, BigDecimal.ROUND_HALF_UP);
        pLow = pLow.setScale(0, BigDecimal.ROUND_HALF_UP);
        pHigh = pHigh.setScale(0, BigDecimal.ROUND_HALF_UP);

        Valuation v = new Valuation();
        v.setListingId(listingId);
        v.setPMed(pMed);
        v.setPLow(pLow);
        v.setPHigh(pHigh);
        v.setConfidence(new BigDecimal("0.78")); // demo
        v.setModelVersion(null); // hoặc "VAL_DEMO_v1" nếu mày muốn tạo thêm trong model_registry
        v.setExplanationText(
                "Demo AI: based on list_price (" + base.toPlainString() + ")"
                + ", area_m2 (" + area.toPlainString() + ")"
                + ", location (" + safe(b.city) + " " + safe(b.district) + " " + safe(b.ward) + ")"
                + ", type (" + safe(b.type) + ")."
        );
        return v;
    }

    private String safe(String s) {
        return (s == null) ? "" : s.trim();
    }

    // inner class chứa base info
    public static class ListingBase {

        public int listingId;
        public BigDecimal listPrice;
        public String status;
        public BigDecimal areaM2;
        public String city;
        public String district;
        public String ward;
        public String type;
    }
}
