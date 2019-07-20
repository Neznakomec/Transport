package com.sdimdev.nnhackaton.model.repository;

import com.sdimdev.nnhackaton.model.entity.route.RouteResult;

import io.reactivex.Single;

public interface RouteRepository {
    Single<RouteResult> getRoute();
}
