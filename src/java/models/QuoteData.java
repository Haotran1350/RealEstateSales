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
public class QuoteData {
    
    private int listingId;
    private BigDecimal listPrice;
    private String listingStatus;
    private String sellerName;
    private String sellerPhone;

    // property
    private int propertyId;
    private String type, city, district, ward, addressLine;
    private BigDecimal areaM2;
    private Integer bedrooms, bathrooms, yearBuilt;
    private String legalStatus;

    // agent
    private Integer agentId;
    private String agentName, agentPhone, agentEmail;

    // valuation latest for listing
    private BigDecimal pLow, pMed, pHigh;
    private String explanationText;
    private Timestamp valuationCreatedAt;

    public QuoteData() {
    }

    public QuoteData(int listingId, BigDecimal listPrice, String listingStatus, String sellerName, String sellerPhone, int propertyId, String type, String city, String district, String ward, String addressLine, BigDecimal areaM2, Integer bedrooms, Integer bathrooms, Integer yearBuilt, String legalStatus, Integer agentId, String agentName, String agentPhone, String agentEmail, BigDecimal pLow, BigDecimal pMed, BigDecimal pHigh, String explanationText, Timestamp valuationCreatedAt) {
        this.listingId = listingId;
        this.listPrice = listPrice;
        this.listingStatus = listingStatus;
        this.sellerName = sellerName;
        this.sellerPhone = sellerPhone;
        this.propertyId = propertyId;
        this.type = type;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.addressLine = addressLine;
        this.areaM2 = areaM2;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.yearBuilt = yearBuilt;
        this.legalStatus = legalStatus;
        this.agentId = agentId;
        this.agentName = agentName;
        this.agentPhone = agentPhone;
        this.agentEmail = agentEmail;
        this.pLow = pLow;
        this.pMed = pMed;
        this.pHigh = pHigh;
        this.explanationText = explanationText;
        this.valuationCreatedAt = valuationCreatedAt;
    }

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public String getListingStatus() {
        return listingStatus;
    }

    public void setListingStatus(String listingStatus) {
        this.listingStatus = listingStatus;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public BigDecimal getAreaM2() {
        return areaM2;
    }

    public void setAreaM2(BigDecimal areaM2) {
        this.areaM2 = areaM2;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Integer getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(Integer yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public String getLegalStatus() {
        return legalStatus;
    }

    public void setLegalStatus(String legalStatus) {
        this.legalStatus = legalStatus;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentPhone() {
        return agentPhone;
    }

    public void setAgentPhone(String agentPhone) {
        this.agentPhone = agentPhone;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }

    public BigDecimal getpLow() {
        return pLow;
    }

    public void setpLow(BigDecimal pLow) {
        this.pLow = pLow;
    }

    public BigDecimal getpMed() {
        return pMed;
    }

    public void setpMed(BigDecimal pMed) {
        this.pMed = pMed;
    }

    public BigDecimal getpHigh() {
        return pHigh;
    }

    public void setpHigh(BigDecimal pHigh) {
        this.pHigh = pHigh;
    }

    public String getExplanationText() {
        return explanationText;
    }

    public void setExplanationText(String explanationText) {
        this.explanationText = explanationText;
    }

    public Timestamp getValuationCreatedAt() {
        return valuationCreatedAt;
    }

    public void setValuationCreatedAt(Timestamp valuationCreatedAt) {
        this.valuationCreatedAt = valuationCreatedAt;
    }

   
}
