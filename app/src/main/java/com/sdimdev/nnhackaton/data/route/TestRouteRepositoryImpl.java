package com.sdimdev.nnhackaton.data.route;

import com.sdimdev.nnhackaton.data.route.parse.Parser;
import com.sdimdev.nnhackaton.model.entity.route.RouteResult;
import com.sdimdev.nnhackaton.model.repository.RouteRepository;

import io.reactivex.Maybe;
import io.reactivex.Single;

public class TestRouteRepositoryImpl implements RouteRepository {
    RouteResult result;
    private String relationId = "269601";
    private Parser parser;

    public TestRouteRepositoryImpl(Parser parser) {
        this.parser = parser;
    }

    @Override
    public Single<RouteResult> getRoute() {
        return Maybe.fromCallable(() -> result)
                .switchIfEmpty(loadNodes()
                        .doOnSuccess(res -> result = res));
    }

    private Single<RouteResult> loadNodes() {
        return Single.fromCallable(() -> parser.getData(relationId));
    }
}
