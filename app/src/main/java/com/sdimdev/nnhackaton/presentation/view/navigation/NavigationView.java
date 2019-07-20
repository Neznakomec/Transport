package com.sdimdev.nnhackaton.presentation.view.navigation;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface NavigationView extends MvpView {
	void initNavFragment();
}
