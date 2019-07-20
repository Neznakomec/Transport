package com.sdimdev.nnhackaton.di.search;

import com.sdimdev.nnhackaton.di.FeatureScope;
import com.sdimdev.nnhackaton.presentation.presenter.search.SearchPresenter;
import com.sdimdev.nnhackaton.utils.rx.RxSchedulers;
import com.sdimdev.nnhackaton.utils.system.router.FlowRouter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
@Module
public class SearchModule {
    @Provides
    @FeatureScope
    SearchPresenter provideSearchPresenter(RxSchedulers schedulers,
                                           FlowRouter flowRouter) {
        return new SearchPresenter(schedulers, flowRouter);
    }
}
