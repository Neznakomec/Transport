package com.sdimdev.nnhackaton.di.route;

import com.sdimdev.nnhackaton.HackatonApplication;
import com.sdimdev.nnhackaton.data.route.TestRouteRepositoryImpl;
import com.sdimdev.nnhackaton.data.route.parse.Parser;
import com.sdimdev.nnhackaton.di.ScreenScope;
import com.sdimdev.nnhackaton.model.interactor.route.RouteInteractor;
import com.sdimdev.nnhackaton.model.repository.RouteRepository;
import com.sdimdev.nnhackaton.model.repository.SearchRepository;
import com.sdimdev.nnhackaton.presentation.presenter.route.RoutePresenter;
import com.sdimdev.nnhackaton.utils.rx.RxSchedulers;
import com.sdimdev.nnhackaton.utils.system.router.FlowRouter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
@Module
public class RouteModule {
    @ScreenScope
    @Provides
    RoutePresenter provideRoutePresenter(FlowRouter flowRouter, RxSchedulers schedulers,
                                         RouteInteractor routeInteractor) {
        return new RoutePresenter(routeInteractor, flowRouter);
    }

    @ScreenScope
    @Provides
    RouteInteractor provideRouteInteractor(RouteRepository routeRepository) {
        return new RouteInteractor(routeRepository);
    }

    @ScreenScope
    @Provides
    RouteRepository provideRouteRepository(HackatonApplication application) {
        return new TestRouteRepositoryImpl(new Parser(application));
    }
}
