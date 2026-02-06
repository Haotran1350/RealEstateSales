/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Listing;
import utils.DBUtils;

/**
 *
 * @author Hao
 */
public class ListingDAO extends DBUtils {

    public List<Listing> getAll() throws Exception {
        List<Listing> list = new ArrayList<>();

        String sql
                = "SELECT l.listing_id, l.property_id, l.seller_name, l.seller_phone, l.agent_id, "
                + "       l.list_price, l.status, l.created_at, l.updated_at, l.closed_at, "
                + "       p.type AS property_type, p.city, p.district, p.ward, p.address_line "
                + "FROM dbo.listings l "
                + "JOIN dbo.properties p ON p.property_id = l.property_id "
                + "ORDER BY l.listing_id DESC";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Listing x = new Listing();
                x.setListingId(rs.getInt("listing_id"));
                x.setPropertyId(rs.getInt("property_id"));
                x.setSellerName(rs.getString("seller_name"));
                x.setSellerPhone(rs.getString("seller_phone"));
                x.setAgentId((Integer) rs.getObject("agent_id"));
                x.setListPrice(rs.getBigDecimal("list_price"));
                x.setStatus(rs.getString("status"));
                x.setCreatedAt(rs.getTimestamp("created_at"));
                x.setUpdatedAt(rs.getTimestamp("updated_at"));
                x.setClosedAt(rs.getTimestamp("closed_at"));

                x.setPropertyType(rs.getString("property_type"));
                x.setCity(rs.getString("city"));
                x.setDistrict(rs.getString("district"));
                x.setWard(rs.getString("ward"));
                x.setAddressLine(rs.getString("address_line"));

                list.add(x);
            }
        }
        return list;
    }

    public boolean create(Listing x) throws Exception {
        // created_at, updated_at có DEFAULT trong DB
        String sql
                = "INSERT INTO dbo.listings (property_id, seller_name, seller_phone, agent_id, list_price, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, x.getPropertyId());
            ps.setString(2, x.getSellerName());
            ps.setString(3, x.getSellerPhone());

            if (x.getAgentId() == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, x.getAgentId());
            }

            ps.setBigDecimal(5, x.getListPrice());

            String st = (x.getStatus() == null || x.getStatus().trim().isEmpty()) ? "DRAFT" : x.getStatus().trim();
            ps.setString(6, st);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(int listingId, String newStatus) throws Exception {
        // nếu SOLD/RENTED thì set closed_at, còn lại closed_at = NULL
        String sql
                = "UPDATE dbo.listings SET status = ?, "
                + "    updated_at = SYSDATETIME(), "
                + "    closed_at = CASE WHEN ? IN ('SOLD','RENTED') THEN SYSDATETIME() ELSE NULL END "
                + "WHERE listing_id = ?";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setString(2, newStatus);
            ps.setInt(3, listingId);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int listingId) throws Exception {
        String sql = "DELETE FROM dbo.listings WHERE listing_id=?";
        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, listingId);
            return ps.executeUpdate() > 0;
        }
    }
}
