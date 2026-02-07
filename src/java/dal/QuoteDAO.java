/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.math.BigDecimal;
import models.QuoteData;
import utils.DBUtils;

/**
 *
 * @author Hao
 */
public class QuoteDAO extends DBUtils {

    public QuoteData getByListingId(int listingId) throws Exception {
        String sql
                = "SELECT "
                + "  l.listing_id, l.property_id, l.list_price, l.status AS listing_status, l.seller_name, l.seller_phone, l.agent_id, "
                + "  p.type, p.city, p.district, p.ward, p.address_line, p.area_m2, p.bedrooms, p.bathrooms, p.legal_status, p.year_built, "
                + "  u.full_name AS agent_name, u.phone AS agent_phone, u.email AS agent_email, "
                + "  v.p_low, v.p_med, v.p_high, v.explanation_text, v.created_at AS val_created_at "
                + "FROM dbo.listings l "
                + "JOIN dbo.properties p ON p.property_id = l.property_id "
                + "LEFT JOIN dbo.[users] u ON u.user_id = l.agent_id "
                + "OUTER APPLY ( "
                + "   SELECT TOP 1 * FROM dbo.valuations vv "
                + "   WHERE vv.listing_id = l.listing_id "
                + "   ORDER BY vv.created_at DESC "
                + ") v "
                + "WHERE l.listing_id = ?";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, listingId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                QuoteData q = new QuoteData();

                q.setListingId(rs.getInt("listing_id"));
                q.setPropertyId(rs.getInt("property_id"));
                q.setListPrice(rs.getBigDecimal("list_price"));
                q.setListingStatus(rs.getString("listing_status"));
                q.setSellerName(rs.getString("seller_name"));
                q.setSellerPhone(rs.getString("seller_phone"));

                q.setAgentId((Integer) rs.getObject("agent_id"));
                q.setAgentName(rs.getString("agent_name"));
                q.setAgentPhone(rs.getString("agent_phone"));
                q.setAgentEmail(rs.getString("agent_email"));

                q.setType(rs.getString("type"));
                q.setCity(rs.getString("city"));
                q.setDistrict(rs.getString("district"));
                q.setWard(rs.getString("ward"));
                q.setAddressLine(rs.getString("address_line"));
                q.setAreaM2(rs.getBigDecimal("area_m2"));
                q.setBedrooms((Integer) rs.getObject("bedrooms"));
                q.setBathrooms((Integer) rs.getObject("bathrooms"));
                q.setLegalStatus(rs.getString("legal_status"));
                q.setYearBuilt((Integer) rs.getObject("year_built"));

                q.setpLow(rs.getBigDecimal("p_low"));
                q.setpMed(rs.getBigDecimal("p_med"));
                q.setpHigh(rs.getBigDecimal("p_high"));
                q.setExplanationText(rs.getString("explanation_text"));
                q.setValuationCreatedAt(rs.getTimestamp("val_created_at"));

                return q;
            }
        }
    }
}
