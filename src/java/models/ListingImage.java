/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Hao
 */
public class ListingImage {
    private int imageId;
    private int listingId;
    private String imageUrl;
    private Integer sortOrder;

    public ListingImage() {}

    public ListingImage(int imageId, int listingId, String imageUrl, Integer sortOrder) {
        this.imageId = imageId;
        this.listingId = listingId;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
    }

    public int getImageId() { return imageId; }
    public void setImageId(int imageId) { this.imageId = imageId; }

    public int getListingId() { return listingId; }
    public void setListingId(int listingId) { this.listingId = listingId; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
}
