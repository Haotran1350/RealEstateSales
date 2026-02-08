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
import models.LeadScore;
import utils.DBUtils;

/**
 *
 * @author Hao
 */
public class LeadScoreDAO extends DBUtils {

    public List<LeadScore> getByLeadId(int leadId) throws Exception {
        List<LeadScore> list = new ArrayList<>();
        String sql = "SELECT score_id, lead_id, horizon_days, probability, bucket, explanation_text, model_version, created_at "
                + "FROM dbo.lead_scores WHERE lead_id=? ORDER BY created_at DESC";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, leadId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new LeadScore(
                            rs.getInt("score_id"),
                            rs.getInt("lead_id"),
                            rs.getInt("horizon_days"),
                            rs.getBigDecimal("probability"),
                            rs.getString("bucket"),
                            rs.getString("explanation_text"),
                            rs.getString("model_version"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        }
        return list;
    }

    public boolean create(LeadScore s) throws Exception {
        String sql = "INSERT INTO dbo.lead_scores(lead_id, horizon_days, probability, bucket, explanation_text, model_version) "
                + "VALUES(?,?,?,?,?,?)";
        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, s.getLeadId());
            ps.setInt(2, s.getHorizonDays());
            ps.setBigDecimal(3, s.getProbability());
            ps.setString(4, s.getBucket());
            ps.setString(5, s.getExplanationText());

            String mv = s.getModelVersion();
            if (mv == null || mv.trim().isEmpty()) {
                ps.setNull(6, java.sql.Types.NVARCHAR); // không vướng FK
            } else {
                ps.setString(6, mv.trim()); // QUAN TRỌNG: trim
            }

            return ps.executeUpdate() > 0;
        }
    }

    // Lấy agent được assign cho lead để tạo alert NBA
    public Integer getAssignedAgentId(int leadId) throws Exception {
        String sql = "SELECT assigned_agent_id FROM dbo.leads WHERE lead_id=?";
        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, leadId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return (Integer) rs.getObject(1);
                }
            }
        }
        return null;
    }
    
    public String getLatestModelVersion(String modelType) throws Exception {
    String sql = "SELECT TOP 1 model_version " +
                 "FROM dbo.model_registry " +
                 "WHERE model_type = ? " +
                 "ORDER BY created_at DESC";

    try (Connection cn = DBUtils.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setString(1, modelType);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getString(1) : null;
        }
    }
}
    
}
