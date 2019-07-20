package com.sdimdev.nnhackaton.di.route;

import com.sdimdev.nnhackaton.di.ScreenScope;
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
                                         SearchRepository searchRepository) {
        return new RoutePresenter();
    }
}
