package com.sdimdev.nnhackaton.presentation.view.route;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.sdimdev.nnhackaton.model.entity.route.RouteResult;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
public interface RouteMapView extends MvpView {
    @StateStrategyType(AddToEndStrategy.class)
    void showRoute(RouteResult routeResult);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showProgress();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void hideProgress();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showError();
}
