package com.sdimdev.nnhackaton.di.route;

import com.sdimdev.nnhackaton.di.ScreenScope;
import com.sdimdev.nnhackaton.di.app.AppApi;
import com.sdimdev.nnhackaton.presentation.view.route.RouteMapFragment;

import dagger.Component;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
@ScreenScope
@Component(dependencies = {AppApi.class}, modules = {RouteModule.class})
public interface RouteComponent {
    void inject(RouteMapFragment fragment);
}
