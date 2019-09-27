package com.sdimdev.nnhackaton.model.entity.coordinate;

import com.google.android.gms.maps.model.LatLng;

public class CoordTemp {
    int date;
    double latitude;
    double longitude;
    String оператор;
    String уровень_сигнала;
    String networkType;
    String mobile_id;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public LatLng getCoord() {
        return new LatLng(latitude, longitude);
    }

    public void setLatitude(double lat) {
        this.latitude = lat;
    }

    public void setLongitude(double lon) {
        this.longitude = lon;
    }

    public String getNetworkCarrier() {
        return оператор;
    }

    public void setNetworkCarrier(String networkCarrier) {
        оператор = networkCarrier;
    }
}
