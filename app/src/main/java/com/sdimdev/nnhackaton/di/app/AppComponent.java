package com.sdimdev.nnhackaton.di.app;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
@Component(modules = {AppModule.class, NavigationModule.class, SearchGlobalModule.class})
@Singleton
public interface AppComponent extends AppApi {
}
