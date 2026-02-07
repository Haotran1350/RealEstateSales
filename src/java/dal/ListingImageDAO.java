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
import models.ListingImage;
import utils.DBUtils;

/**
 *
 * @author Hao
 */
public class ListingImageDAO extends DBUtils {

    public List<ListingImage> getByListingId(int listingId) throws Exception {
        List<ListingImage> list = new ArrayList<>();

        String sql = "SELECT image_id, listing_id, url_or_path, sort_order, created_at "
                + "FROM dbo.listing_images WHERE listing_id = ? "
                + "ORDER BY sort_order ASC, image_id ASC";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, listingId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ListingImage(
                            rs.getInt("image_id"),
                            rs.getInt("listing_id"),
                            rs.getString("url_or_path"),
                            rs.getInt("sort_order"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        }
        return list;
    }

    public boolean create(int listingId, String urlOrPath, Integer sortOrder) throws Exception {
        String sql = "INSERT INTO dbo.listing_images(listing_id, url_or_path, sort_order) VALUES(?,?,?)";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, listingId);
            ps.setString(2, urlOrPath);
            ps.setInt(3, (sortOrder == null ? 0 : sortOrder));
            return ps.executeUpdate() > 0;
        }
    }

    public String getPathByImageId(int imageId) throws Exception {
        String sql = "SELECT url_or_path FROM dbo.listing_images WHERE image_id = ?";
        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, imageId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        }
        return null;
    }

    public boolean delete(int imageId) throws Exception {
        String sql = "DELETE FROM dbo.listing_images WHERE image_id = ?";
        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, imageId);
            return ps.executeUpdate() > 0;
        }
    }
}
