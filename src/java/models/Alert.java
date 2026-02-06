/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author Hao
 */
public class Alert {
    private long alertId;
    private int agentId;
    private Integer leadId;       // nullable
    private Integer listingId;    // nullable
    private String severity;      // INFO/WARN/CRIT
    private String status;        // OPEN/ACK/CLOSED
    private String message;
    private Timestamp createdAt;
    private Timestamp resolvedAt; // nullable

    public Alert() {}

    public Alert(long alertId, int agentId, Integer leadId, Integer listingId,
                 String severity, String status, String message,
                 Timestamp createdAt, Timestamp resolvedAt) {
        this.alertId = alertId;
        this.agentId = agentId;
        this.leadId = leadId;
        this.listingId = listingId;
        this.severity = severity;
        this.status = status;
        this.message = message;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
    }

    public long getAlertId() { return alertId; }
    public void setAlertId(long alertId) { this.alertId = alertId; }

    public int getAgentId() { return agentId; }
    public void setAgentId(int agentId) { this.agentId = agentId; }

    public Integer getLeadId() { return leadId; }
    public void setLeadId(Integer leadId) { this.leadId = leadId; }

    public Integer getListingId() { return listingId; }
    public void setListingId(Integer listingId) { this.listingId = listingId; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(Timestamp resolvedAt) { this.resolvedAt = resolvedAt; }
}
