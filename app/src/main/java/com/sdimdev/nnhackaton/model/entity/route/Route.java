package com.sdimdev.nnhackaton.model.entity.route;

import android.support.annotation.ColorInt;

import java.util.ArrayList;
import java.util.List;

public class Route {
    @ColorInt
    int color;
    List<RoutePoint> routePoints = new ArrayList<>();
    String name;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<RoutePoint> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<RoutePoint> routePoints) {
        this.routePoints = routePoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
