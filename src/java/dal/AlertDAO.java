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
import models.Alert;
import utils.DBUtils;

/**
 *
 * @author Hao
 */
public class AlertDAO extends DBUtils {

    public List<Alert> getAll() throws Exception {
        List<Alert> list = new ArrayList<>();

        String sql
                = "SELECT a.alert_id, a.agent_id, a.lead_id, a.listing_id, a.type, a.severity, a.message, a.status, a.created_at, a.resolved_at, "
                + "       u.full_name AS agent_name "
                + "FROM dbo.alerts a "
                + "LEFT JOIN dbo.[users] u ON u.user_id = a.agent_id "
                + "ORDER BY a.created_at DESC, a.alert_id DESC";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Alert x = map(rs);
                list.add(x);
            }
        }
        return list;
    }

    // AGENT: chỉ xem của mình
    public List<Alert> getByAgentId(int agentId) throws Exception {
        List<Alert> list = new ArrayList<>();

        String sql
                = "SELECT a.alert_id, a.agent_id, a.lead_id, a.listing_id, a.type, a.severity, a.message, a.status, a.created_at, a.resolved_at, "
                + "       u.full_name AS agent_name "
                + "FROM dbo.alerts a "
                + "LEFT JOIN dbo.[users] u ON u.user_id = a.agent_id "
                + "WHERE a.agent_id = ? "
                + "ORDER BY a.created_at DESC, a.alert_id DESC";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, agentId);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Alert x = map(rs);
                    list.add(x);
                }
            }
        }
        return list;
    }

    // OPEN -> ACK (không set resolved_at)
    public boolean ack(int alertId) throws Exception {
        String sql
                = "UPDATE dbo.alerts SET status = 'ACK' "
                + "WHERE alert_id = ? AND status = 'OPEN'";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, alertId);
            return ps.executeUpdate() > 0;
        }
    }

    // DONE: set resolved_at
    public boolean done(int alertId) throws Exception {
        String sql
                = "UPDATE dbo.alerts SET status = 'DONE', resolved_at = SYSDATETIME() "
                + "WHERE alert_id = ? AND status <> 'DONE'";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, alertId);
            return ps.executeUpdate() > 0;
        }
    }

    private Alert map(ResultSet rs) throws Exception {
        Alert x = new Alert();
        x.setAlertId(rs.getInt("alert_id"));
        x.setAgentId((Integer) rs.getObject("agent_id"));
        x.setLeadId((Integer) rs.getObject("lead_id"));
        x.setListingId((Integer) rs.getObject("listing_id"));
        x.setType(rs.getString("type"));
        x.setSeverity(rs.getString("severity"));
        x.setMessage(rs.getString("message"));
        x.setStatus(rs.getString("status"));
        x.setCreatedAt(rs.getTimestamp("created_at"));
        x.setResolvedAt(rs.getTimestamp("resolved_at"));
        x.setAgentName(rs.getString("agent_name"));
        return x;
    }

    public boolean createNBA(int agentId, int leadId, String severity, String message) throws Exception {
        String sql = "INSERT INTO dbo.alerts(agent_id, lead_id, listing_id, type, severity, message, status) "
                + "VALUES(?, ?, NULL, 'NEXT_BEST_ACTION', ?, ?, 'OPEN')";
        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, agentId);
            ps.setInt(2, leadId);
            ps.setString(3, severity);
            ps.setString(4, message);
            return ps.executeUpdate() > 0;
        }
    }
}
