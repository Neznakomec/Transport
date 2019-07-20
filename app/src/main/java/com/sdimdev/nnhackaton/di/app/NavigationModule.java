package com.sdimdev.nnhackaton.di.app;

import com.sdimdev.nnhackaton.presentation.GlobalMenuController;
import com.sdimdev.nnhackaton.utils.system.router.FlowRouter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
@Module
public class NavigationModule {
    private Cicerone cicerone;

    public NavigationModule() {
        cicerone = Cicerone.create(new FlowRouter());
    }

    @Provides
    @Singleton
    FlowRouter provideFlowRouter() {
        return (FlowRouter) cicerone.getRouter();
    }

    @Provides
    @Singleton
    NavigatorHolder provideNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    @Provides
    @Singleton
    GlobalMenuController provideGlobalMenuController() {
        return new GlobalMenuController();
    }
}
