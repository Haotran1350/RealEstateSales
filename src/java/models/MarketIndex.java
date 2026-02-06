/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;

/**
 *
 * @author Hao
 */
public class MarketIndex {
    private long marketIndexId;
    private Integer regionId;      // nullable
    private Date ts;               // DATE
    private Double hpi;            // House Price Index
    private Double interestRate;
    private Double cpi;
    private String source;

    public MarketIndex() {}

    public MarketIndex(long marketIndexId, Integer regionId, Date ts, Double hpi,
                       Double interestRate, Double cpi, String source) {
        this.marketIndexId = marketIndexId;
        this.regionId = regionId;
        this.ts = ts;
        this.hpi = hpi;
        this.interestRate = interestRate;
        this.cpi = cpi;
        this.source = source;
    }

    public long getMarketIndexId() { return marketIndexId; }
    public void setMarketIndexId(long marketIndexId) { this.marketIndexId = marketIndexId; }

    public Integer getRegionId() { return regionId; }
    public void setRegionId(Integer regionId) { this.regionId = regionId; }

    public Date getTs() { return ts; }
    public void setTs(Date ts) { this.ts = ts; }

    public Double getHpi() { return hpi; }
    public void setHpi(Double hpi) { this.hpi = hpi; }

    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }

    public Double getCpi() { return cpi; }
    public void setCpi(Double cpi) { this.cpi = cpi; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
