package com.sdimdev.nnhackaton.di.coin.search;

import com.sdimdev.nnhackaton.di.FeatureScope;
import com.sdimdev.nnhackaton.di.app.AppApi;
import com.sdimdev.nnhackaton.presentation.view.search.CoinsFragment;
import com.sdimdev.nnhackaton.presentation.view.search.SearchFragment;

import dagger.Component;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
@FeatureScope
@Component(dependencies = {AppApi.class}, modules = {CoinModule.class})
public interface CoinComponent {
    void inject(CoinsFragment fragment);
}
