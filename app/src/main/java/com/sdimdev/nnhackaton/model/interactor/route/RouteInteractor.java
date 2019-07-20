package com.sdimdev.nnhackaton.model.interactor.route;

import com.sdimdev.nnhackaton.model.entity.route.RouteResult;
import com.sdimdev.nnhackaton.model.repository.RouteRepository;

import io.reactivex.Single;

public class RouteInteractor {
    private RouteRepository routeRepository;

    public RouteInteractor(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Single<RouteResult> getRouteResult() {
        return routeRepository.getRoute();
    }
}
