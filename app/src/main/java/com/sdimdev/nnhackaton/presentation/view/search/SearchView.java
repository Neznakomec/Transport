package com.sdimdev.nnhackaton.presentation.view.search;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
@StateStrategyType(OneExecutionStateStrategy.class)
public interface SearchView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showProgress();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void hideProgress();
}
