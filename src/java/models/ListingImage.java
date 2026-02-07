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
public class ListingImage {

    private Integer imageId;
    private Integer listingId;
    private String urlOrPath;
    private Integer sortOrder;
    private Timestamp createdAt;

    public ListingImage() {
    }

    public ListingImage(Integer imageId, Integer listingId, String urlOrPath, Integer sortOrder, Timestamp createdAt) {
        this.imageId = imageId;
        this.listingId = listingId;
        this.urlOrPath = urlOrPath;
        this.sortOrder = sortOrder;
        this.createdAt = createdAt;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getListingId() {
        return listingId;
    }

    public void setListingId(Integer listingId) {
        this.listingId = listingId;
    }

    public String getUrlOrPath() {
        return urlOrPath;
    }

    public void setUrlOrPath(String urlOrPath) {
        this.urlOrPath = urlOrPath;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
