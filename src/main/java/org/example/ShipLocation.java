package org.example;

import java.math.BigDecimal;

public class ShipLocation {
    private BigDecimal longitude;
    private BigDecimal latitude;

    public ShipLocation() {

    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }


}
