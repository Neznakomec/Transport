package com.sdimdev.nnhackaton.presentation.presenter.route;

import com.arellomobile.mvp.InjectViewState;
import com.sdimdev.nnhackaton.model.interactor.route.RouteInteractor;
import com.sdimdev.nnhackaton.presentation.presenter.BasePresenter;
import com.sdimdev.nnhackaton.presentation.view.route.RouteMapView;
import com.sdimdev.nnhackaton.utils.rx.RxSchedulers;
import com.sdimdev.nnhackaton.utils.system.router.FlowRouter;

@InjectViewState
public class RoutePresenter extends BasePresenter<RouteMapView> {
    private RouteInteractor routeInteractor;
    private RxSchedulers rxSchedulers;

    public RoutePresenter(RouteInteractor routeInteractor, FlowRouter flowRouter) {
        this.routeInteractor = routeInteractor;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void onMapReady() {
        connect(routeInteractor.getRouteResult()
                .doOnSubscribe(c -> getViewState().showProgress())
                .doFinally(() -> getViewState().hideProgress())
                .subscribe(routeResult -> getViewState().showRoute(routeResult),
                        th -> getViewState().showError()));
    }
}
