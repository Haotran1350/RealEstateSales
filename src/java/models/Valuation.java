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
public class Valuation {
    private long valuationId;
    private Integer propertyId;     // nullable (property_id XOR listing_id)
    private Integer listingId;      // nullable
    private Double pLow;
    private Double pMed;
    private Double pHigh;
    private Double confidence;      // 0..1
    private String explanationText;
    private String modelVersion;    // FK -> model_registry.model_version
    private Timestamp createdAt;

    public Valuation() {}

    public Valuation(long valuationId, Integer propertyId, Integer listingId,
                     Double pLow, Double pMed, Double pHigh, Double confidence,
                     String explanationText, String modelVersion, Timestamp createdAt) {
        this.valuationId = valuationId;
        this.propertyId = propertyId;
        this.listingId = listingId;
        this.pLow = pLow;
        this.pMed = pMed;
        this.pHigh = pHigh;
        this.confidence = confidence;
        this.explanationText = explanationText;
        this.modelVersion = modelVersion;
        this.createdAt = createdAt;
    }

    public long getValuationId() { return valuationId; }
    public void setValuationId(long valuationId) { this.valuationId = valuationId; }

    public Integer getPropertyId() { return propertyId; }
    public void setPropertyId(Integer propertyId) { this.propertyId = propertyId; }

    public Integer getListingId() { return listingId; }
    public void setListingId(Integer listingId) { this.listingId = listingId; }

    public Double getpLow() { return pLow; }
    public void setpLow(Double pLow) { this.pLow = pLow; }

    public Double getpMed() { return pMed; }
    public void setpMed(Double pMed) { this.pMed = pMed; }

    public Double getpHigh() { return pHigh; }
    public void setpHigh(Double pHigh) { this.pHigh = pHigh; }

    public Double getConfidence() { return confidence; }
    public void setConfidence(Double confidence) { this.confidence = confidence; }

    public String getExplanationText() { return explanationText; }
    public void setExplanationText(String explanationText) { this.explanationText = explanationText; }

    public String getModelVersion() { return modelVersion; }
    public void setModelVersion(String modelVersion) { this.modelVersion = modelVersion; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
