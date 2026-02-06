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
public class AuditLog {
    private long auditId;
    private Integer userId;       // nullable
    private String action;
    private String entityType;
    private String entityId;
    private String detailsJson;
    private Timestamp createdAt;

    public AuditLog() {}

    public AuditLog(long auditId, Integer userId, String action, String entityType,
                    String entityId, String detailsJson, Timestamp createdAt) {
        this.auditId = auditId;
        this.userId = userId;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.detailsJson = detailsJson;
        this.createdAt = createdAt;
    }

    public long getAuditId() { return auditId; }
    public void setAuditId(long auditId) { this.auditId = auditId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public String getEntityId() { return entityId; }
    public void setEntityId(String entityId) { this.entityId = entityId; }

    public String getDetailsJson() { return detailsJson; }
    public void setDetailsJson(String detailsJson) { this.detailsJson = detailsJson; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
