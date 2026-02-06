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
import models.Property;
import utils.DBUtils;

/**
 *
 * @author Hao
 */
public class PropertyDAO extends DBUtils{
    public List<Property> getAll() throws Exception {
        List<Property> list = new ArrayList<>();

        String sql = "SELECT property_id, type, city, district, ward, address_line, area_m2, "
                   + "bedrooms, bathrooms, legal_status, year_built, features_json, created_at, updated_at "
                   + "FROM dbo.properties ORDER BY property_id DESC";

        try (Connection cn = DBUtils.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Property p = new Property(
                        rs.getInt("property_id"),
                        rs.getString("type"),
                        rs.getString("city"),
                        rs.getString("district"),
                        rs.getString("ward"),
                        rs.getString("address_line"),
                        rs.getBigDecimal("area_m2"),
                        (Integer) rs.getObject("bedrooms"),
                        (Integer) rs.getObject("bathrooms"),
                        rs.getString("legal_status"),
                        (Integer) rs.getObject("year_built"),
                        rs.getString("features_json"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                list.add(p);
            }
        }
        return list;
    }

    public boolean create(Property x) throws Exception {
        // created_at, updated_at có DEFAULT trong DB
        String sql = "INSERT INTO dbo.properties "
                   + "(type, city, district, ward, address_line, area_m2, bedrooms, bathrooms, legal_status, year_built, features_json) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection cn = DBUtils.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, x.getType());
            ps.setString(2, x.getCity());
            ps.setString(3, x.getDistrict());
            ps.setString(4, x.getWard());
            ps.setString(5, x.getAddressLine());

            // area_m2 NOT NULL
            ps.setBigDecimal(6, x.getAreaM2());

            if (x.getBedrooms() == null) ps.setNull(7, java.sql.Types.INTEGER);
            else ps.setInt(7, x.getBedrooms());

            if (x.getBathrooms() == null) ps.setNull(8, java.sql.Types.INTEGER);
            else ps.setInt(8, x.getBathrooms());

            ps.setString(9, x.getLegalStatus());

            if (x.getYearBuilt() == null) ps.setNull(10, java.sql.Types.INTEGER);
            else ps.setInt(10, x.getYearBuilt());

            ps.setString(11, x.getFeaturesJson());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(Property x) throws Exception {
        // tự cập nhật updated_at
        String sql = "UPDATE dbo.properties SET "
                   + "type=?, city=?, district=?, ward=?, address_line=?, area_m2=?, bedrooms=?, bathrooms=?, "
                   + "legal_status=?, year_built=?, features_json=?, updated_at = SYSDATETIME() "
                   + "WHERE property_id=?";

        try (Connection cn = DBUtils.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, x.getType());
            ps.setString(2, x.getCity());
            ps.setString(3, x.getDistrict());
            ps.setString(4, x.getWard());
            ps.setString(5, x.getAddressLine());
            ps.setBigDecimal(6, x.getAreaM2());

            if (x.getBedrooms() == null) ps.setNull(7, java.sql.Types.INTEGER);
            else ps.setInt(7, x.getBedrooms());

            if (x.getBathrooms() == null) ps.setNull(8, java.sql.Types.INTEGER);
            else ps.setInt(8, x.getBathrooms());

            ps.setString(9, x.getLegalStatus());

            if (x.getYearBuilt() == null) ps.setNull(10, java.sql.Types.INTEGER);
            else ps.setInt(10, x.getYearBuilt());

            ps.setString(11, x.getFeaturesJson());

            ps.setInt(12, x.getPropertyId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int propertyId) throws Exception {
        String sql = "DELETE FROM dbo.properties WHERE property_id=?";
        try (Connection cn = DBUtils.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, propertyId);
            return ps.executeUpdate() > 0;
        }
    }
}
