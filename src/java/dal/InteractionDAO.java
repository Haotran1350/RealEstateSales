/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import models.Interaction;
import utils.DBUtils;

/**
 *
 * @author Hao
 */
public class InteractionDAO extends DBUtils {

    public List<Interaction> getByLeadId(int leadId) throws Exception {
        List<Interaction> list = new ArrayList<>();

        String sql = "SELECT interaction_id, lead_id, agent_id, channel, content, ts, outcome, next_action_at "
                + "FROM dbo.interactions WHERE lead_id = ? ORDER BY ts DESC, interaction_id DESC";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, leadId);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Interaction i = new Interaction();
                    i.setInteractionId(rs.getLong("interaction_id"));
                    i.setLeadId(rs.getInt("lead_id"));
                    i.setAgentId(rs.getInt("agent_id"));
                    i.setChannel(rs.getString("channel"));
                    i.setContent(rs.getString("content"));
                    i.setTs(rs.getTimestamp("ts"));
                    i.setOutcome(rs.getString("outcome"));
                    i.setNextActionAt(rs.getTimestamp("next_action_at"));
                    list.add(i);
                }
            }
        }
        return list;
    }

    public boolean create(Interaction x) throws Exception {
        // ts có DEFAULT SYSDATETIME() trong DB thì cho NULL để DB tự set.
        String sql = "INSERT INTO dbo.interactions (lead_id, agent_id, channel, content, ts, outcome, next_action_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, x.getLeadId());
            ps.setInt(2, x.getAgentId());
            ps.setString(3, x.getChannel());
            ps.setString(4, x.getContent());

            Timestamp ts = x.getTs();
            if (ts == null) {
                ps.setNull(5, java.sql.Types.TIMESTAMP);
            } else {
                ps.setTimestamp(5, ts);
            }

            ps.setString(6, x.getOutcome());

            Timestamp next = x.getNextActionAt();
            if (next == null) {
                ps.setNull(7, java.sql.Types.TIMESTAMP);
            } else {
                ps.setTimestamp(7, next);
            }

            return ps.executeUpdate() > 0;
        }
    }
}
