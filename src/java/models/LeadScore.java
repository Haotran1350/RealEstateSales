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
public class LeadScore {
    private int scoreId;
    private int leadId;
    private int horizonDays; // 7/14/30
    private BigDecimal probability; // DECIMAL(5,4)
    private String bucket; // HOT/WARM/COLD
    private String explanationText;
    private String modelVersion;
    private Timestamp createdAt;

    public LeadScore() {}

    public LeadScore(int scoreId, int leadId, int horizonDays, BigDecimal probability,
                     String bucket, String explanationText, String modelVersion, Timestamp createdAt) {
        this.scoreId = scoreId;
        this.leadId = leadId;
        this.horizonDays = horizonDays;
        this.probability = probability;
        this.bucket = bucket;
        this.explanationText = explanationText;
        this.modelVersion = modelVersion;
        this.createdAt = createdAt;
    }

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public int getLeadId() {
        return leadId;
    }

    public void setLeadId(int leadId) {
        this.leadId = leadId;
    }

    public int getHorizonDays() {
        return horizonDays;
    }

    public void setHorizonDays(int horizonDays) {
        this.horizonDays = horizonDays;
    }

    public BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(BigDecimal probability) {
        this.probability = probability;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getExplanationText() {
        return explanationText;
    }

    public void setExplanationText(String explanationText) {
        this.explanationText = explanationText;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    
}
