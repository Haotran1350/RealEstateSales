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
public class Valuation {

    private Integer valId;
    private Integer propertyId; // luôn NULL khi định giá theo listing
    private Integer listingId;
    private BigDecimal pLow;
    private BigDecimal pMed;
    private BigDecimal pHigh;
    private BigDecimal confidence; // DECIMAL(5,4) -> BigDecimal
    private String explanationText;
    private String modelVersion; // có thể null
    private Timestamp createdAt;

    public Valuation() {
    }

    public Valuation(Integer valId, Integer propertyId, Integer listingId,
            BigDecimal pLow, BigDecimal pMed, BigDecimal pHigh,
            BigDecimal confidence, String explanationText, String modelVersion,
            Timestamp createdAt) {
        this.valId = valId;
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

    public Integer getValId() {
        return valId;
    }

    public void setValId(Integer valId) {
        this.valId = valId;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getListingId() {
        return listingId;
    }

    public void setListingId(Integer listingId) {
        this.listingId = listingId;
    }

    public BigDecimal getPLow() {
        return pLow;
    }

    public void setPLow(BigDecimal pLow) {
        this.pLow = pLow;
    }

    public BigDecimal getPMed() {
        return pMed;
    }

    public void setPMed(BigDecimal pMed) {
        this.pMed = pMed;
    }

    public BigDecimal getPHigh() {
        return pHigh;
    }

    public void setPHigh(BigDecimal pHigh) {
        this.pHigh = pHigh;
    }

    public BigDecimal getConfidence() {
        return confidence;
    }

    public void setConfidence(BigDecimal confidence) {
        this.confidence = confidence;
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
