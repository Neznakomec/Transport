package com.sdimdev.nnhackaton.di.search;

import com.sdimdev.nnhackaton.di.FeatureScope;
import com.sdimdev.nnhackaton.di.app.AppApi;
import com.sdimdev.nnhackaton.presentation.view.search.SearchFragment;

import dagger.Component;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
@FeatureScope
@Component(dependencies = {AppApi.class}, modules = {SearchModule.class})
public interface SearchComponent {
    void inject(SearchFragment fragment);
}
