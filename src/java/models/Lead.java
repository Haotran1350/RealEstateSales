/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author Hao
 */
public class Lead {

    private int leadId;
    private String fullName;
    private String phone;
    private String email;
    private BigDecimal budgetMin;
    private BigDecimal budgetMax;
    private String purpose; // BUY/RENT
    private String locationPrefJson;
    private Integer assignedAgentId; // nullable
    private String status; // NEW/CONTACTED/QUALIFIED/VIEWING/NEGOTIATING/WON/LOST

    public Lead() {
    }

    public Lead(int leadId, String fullName, String phone, String email, BigDecimal budgetMin, BigDecimal budgetMax, String purpose, String locationPrefJson, Integer assignedAgentId, String status) {
        this.leadId = leadId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.purpose = purpose;
        this.locationPrefJson = locationPrefJson;
        this.assignedAgentId = assignedAgentId;
        this.status = status;
    }

    public int getLeadId() {
        return leadId;
    }

    public void setLeadId(int leadId) {
        this.leadId = leadId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBudgetMin() {
        return budgetMin;
    }

    public void setBudgetMin(BigDecimal budgetMin) {
        this.budgetMin = budgetMin;
    }

    public BigDecimal getBudgetMax() {
        return budgetMax;
    }

    public void setBudgetMax(BigDecimal budgetMax) {
        this.budgetMax = budgetMax;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getLocationPrefJson() {
        return locationPrefJson;
    }

    public void setLocationPrefJson(String locationPrefJson) {
        this.locationPrefJson = locationPrefJson;
    }

    public Integer getAssignedAgentId() {
        return assignedAgentId;
    }

    public void setAssignedAgentId(Integer assignedAgentId) {
        this.assignedAgentId = assignedAgentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

}
