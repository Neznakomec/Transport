package com.sdimdev.nnhackaton.model.interactor.app;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;

public interface ResourcesManager {
	String getString(@StringRes int stringId);

	int getInt(@IntegerRes int intId);

	@ColorInt
	int getColor(@ColorRes int color);
}
