package com.sdimdev.nnhackaton.di.navigation;

import com.sdimdev.nnhackaton.di.FeatureScope;
import com.sdimdev.nnhackaton.model.entity.log.LogcatLogger;
import com.sdimdev.nnhackaton.model.entity.log.Logger;
import com.sdimdev.nnhackaton.presentation.GlobalMenuController;
import com.sdimdev.nnhackaton.presentation.presenter.NavigationPresenter;
import com.sdimdev.nnhackaton.presentation.presenter.drawer.NavigationDrawerPresenter;
import com.sdimdev.nnhackaton.utils.system.router.FlowRouter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
@Module
public class NavModule {
    @Provides
    @FeatureScope
    NavigationPresenter provideNavigationPresenter(FlowRouter flowRouter){
        return new NavigationPresenter(flowRouter);
    }
    @Provides
    @FeatureScope
    NavigationDrawerPresenter provide(GlobalMenuController controller, FlowRouter flowRouter){
        return new NavigationDrawerPresenter(controller, flowRouter);
    }

    @Provides
    @FeatureScope
    Logger provideLogger(){
        return new LogcatLogger("NavModule");
    }
}
