package com.sdimdev.nnhackaton.di.navigation;

import com.sdimdev.nnhackaton.di.FeatureScope;
import com.sdimdev.nnhackaton.di.app.AppApi;
import com.sdimdev.nnhackaton.presentation.view.drawer.NavigationDrawerFragment;
import com.sdimdev.nnhackaton.presentation.view.navigation.NavigationActivity;

import dagger.Component;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
@FeatureScope
@Component(modules = {NavModule.class}, dependencies = {AppApi.class})
public interface NavComponent extends NavApi {
    void inject(NavigationDrawerFragment fragment);

    void inject(NavigationActivity activity);
}
