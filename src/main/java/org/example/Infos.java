package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Infos {
    @JsonProperty("ship-id")
    private String shipId;
    @JsonProperty("ship-name")
    private String shipName;
    private ShipLocation location;
    private Long timestamp;
    @JsonProperty("order-no")
    private Integer orderNo;
    @JsonProperty("log-id")
    private String logId;

    public Infos() {

    }

    public Infos(ShipLocation location, Long timestamp, String shipId, String shipName, Integer orderNo, String logId) {
        this.logId = logId;
        this.orderNo = orderNo;
        this.timestamp = timestamp;
        this.location = location;
        this.shipName = shipName;
        this.shipId = shipId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public ShipLocation getLocation() {
        return location;
    }

    public void setLocation(ShipLocation location) {
        this.location = location;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Infos infos = (Infos) o;
        return Objects.equals(shipId, infos.shipId) && Objects.equals(timestamp, infos.timestamp) && Objects.equals(logId, infos.logId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipId, timestamp, logId);
    }
}


