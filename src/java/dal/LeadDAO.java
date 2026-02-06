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
import models.Lead;
import utils.DBUtils;

/**
 *
 * @author Hao
 */
public class LeadDAO extends DBUtils {

    public List<Lead> getAll() throws Exception {
        List<Lead> list = new ArrayList<>();

        String sql = "SELECT lead_id, full_name, phone, email, budget_min, budget_max, purpose, "
                + "location_pref_json, assigned_agent_id, status "
                + "FROM dbo.leads ORDER BY lead_id DESC";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Lead l = new Lead(
                        rs.getInt("lead_id"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getBigDecimal("budget_min"),
                        rs.getBigDecimal("budget_max"),
                        rs.getString("purpose"),
                        rs.getString("location_pref_json"),
                        (Integer) rs.getObject("assigned_agent_id"),
                        rs.getString("status")
                );
                list.add(l);
            }
        }
        return list;
    }

    public boolean create(Lead x) throws Exception {
        // created_at, updated_at có DEFAULT trong DB nên không cần insert
        String sql = "INSERT INTO dbo.leads "
                + "(full_name, phone, email, budget_min, budget_max, purpose, location_pref_json, assigned_agent_id, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, x.getFullName());
            ps.setString(2, x.getPhone());
            ps.setString(3, x.getEmail());

            if (x.getBudgetMin() == null) {
                ps.setNull(4, java.sql.Types.DECIMAL);
            } else {
                ps.setBigDecimal(4, x.getBudgetMin());
            }

            if (x.getBudgetMax() == null) {
                ps.setNull(5, java.sql.Types.DECIMAL);
            } else {
                ps.setBigDecimal(5, x.getBudgetMax());
            }

            ps.setString(6, x.getPurpose()); // "BUY" hoặc "RENT"
            ps.setString(7, x.getLocationPrefJson());

            if (x.getAssignedAgentId() == null) {
                ps.setNull(8, java.sql.Types.INTEGER);
            } else {
                ps.setInt(8, x.getAssignedAgentId());
            }

            // status nếu null thì set mặc định "NEW"
            ps.setString(9, (x.getStatus() == null || x.getStatus().trim().isEmpty()) ? "NEW" : x.getStatus());

            return ps.executeUpdate() > 0;
        }
    }
}
