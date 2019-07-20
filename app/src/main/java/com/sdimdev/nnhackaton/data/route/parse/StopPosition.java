package com.sdimdev.nnhackaton.data.route.parse;

public class StopPosition {
    private String name;
    private boolean bus;
    private boolean trolleybus;
    private String id;
    private Double lat;
    private Double lon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBus() {
        return bus;
    }

    public void setBus(boolean bus) {
        this.bus = bus;
    }

    public boolean isTrolleybus() {
        return trolleybus;
    }

    public void setTrolleybus(boolean trolleybus) {
        this.trolleybus = trolleybus;
    }
}
