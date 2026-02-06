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
public class Property {
    private Integer propertyId;
    private String type;
    private String city;
    private String district;
    private String ward;
    private String addressLine;
    private BigDecimal areaM2;
    private Integer bedrooms;
    private Integer bathrooms;
    private String legalStatus;
    private Integer yearBuilt;
    private String featuresJson;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Property() {}

    public Property(Integer propertyId, String type, String city, String district, String ward,
                    String addressLine, BigDecimal areaM2, Integer bedrooms, Integer bathrooms,
                    String legalStatus, Integer yearBuilt, String featuresJson,
                    Timestamp createdAt, Timestamp updatedAt) {
        this.propertyId = propertyId;
        this.type = type;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.addressLine = addressLine;
        this.areaM2 = areaM2;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.legalStatus = legalStatus;
        this.yearBuilt = yearBuilt;
        this.featuresJson = featuresJson;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getPropertyId() { return propertyId; }
    public void setPropertyId(Integer propertyId) { this.propertyId = propertyId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }

    public String getAddressLine() { return addressLine; }
    public void setAddressLine(String addressLine) { this.addressLine = addressLine; }

    public BigDecimal getAreaM2() { return areaM2; }
    public void setAreaM2(BigDecimal areaM2) { this.areaM2 = areaM2; }

    public Integer getBedrooms() { return bedrooms; }
    public void setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; }

    public Integer getBathrooms() { return bathrooms; }
    public void setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; }

    public String getLegalStatus() { return legalStatus; }
    public void setLegalStatus(String legalStatus) { this.legalStatus = legalStatus; }

    public Integer getYearBuilt() { return yearBuilt; }
    public void setYearBuilt(Integer yearBuilt) { this.yearBuilt = yearBuilt; }

    public String getFeaturesJson() { return featuresJson; }
    public void setFeaturesJson(String featuresJson) { this.featuresJson = featuresJson; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
