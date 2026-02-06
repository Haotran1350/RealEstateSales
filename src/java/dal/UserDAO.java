/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;
import utils.DBUtils;

/**
 *
 * @author Hao
 */
public class UserDAO extends DBUtils {

    public User checkLogin(String username, String password) throws SQLException, ClassNotFoundException {
        String sql = "SELECT user_id, username, password, full_name, phone, email, role, region_id "
                + "FROM dbo.[users] WHERE username = ? AND password = ?";
        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("full_name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("role"),
                            (Integer) rs.getObject("region_id")
                    );
                }
            }
        }
        return null;
    }
}
