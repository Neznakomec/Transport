package com.sdimdev.nnhackaton.di.app;

import com.sdimdev.nnhackaton.HackatonApplication;
import com.sdimdev.nnhackaton.model.interactor.app.ResourcesManager;
import com.sdimdev.nnhackaton.model.interactor.app.ResourcesManagerIml;
import com.sdimdev.nnhackaton.model.interactor.app.TimeSourceProvider;
import com.sdimdev.nnhackaton.model.interactor.app.TimeSourceProviderImpl;
import com.sdimdev.nnhackaton.utils.rx.DefaultRxSchedulers;
import com.sdimdev.nnhackaton.utils.rx.RxSchedulers;
import com.vanniktech.rxpermission.RealRxPermission;
import com.vanniktech.rxpermission.RxPermission;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
@Module
public class AppModule {
    private HackatonApplication application;

    public AppModule(HackatonApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    TimeSourceProvider provideTimeSourceProvider() {
        return new TimeSourceProviderImpl();
    }

    @Provides
    @Singleton
    ResourcesManager provideResourcesManager() {
        return new ResourcesManagerIml(application);
    }

    @Provides
    @Singleton
    RxPermission provideRxPermission() {
        return RealRxPermission.getInstance(application);
    }

    @Provides
    @Singleton
    RxSchedulers provideRxSchedulers() {
        return new DefaultRxSchedulers();
    }

}
