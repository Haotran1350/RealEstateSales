/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Hao
 */
public class Region {
    private int regionId;
    private String name;
    private Integer parentRegionId; // nullable
    private String city;
    private String district;
    private String ward;

    public Region() {}

    public Region(int regionId, String name, Integer parentRegionId, String city, String district, String ward) {
        this.regionId = regionId;
        this.name = name;
        this.parentRegionId = parentRegionId;
        this.city = city;
        this.district = district;
        this.ward = ward;
    }

    public int getRegionId() { return regionId; }
    public void setRegionId(int regionId) { this.regionId = regionId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getParentRegionId() { return parentRegionId; }
    public void setParentRegionId(Integer parentRegionId) { this.parentRegionId = parentRegionId; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }
}
