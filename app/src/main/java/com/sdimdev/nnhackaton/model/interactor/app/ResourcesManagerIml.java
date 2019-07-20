package com.sdimdev.nnhackaton.model.interactor.app;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.sdimdev.nnhackaton.HackatonApplication;

public class ResourcesManagerIml implements ResourcesManager {
    private Context appContext;

    public ResourcesManagerIml(HackatonApplication ponyApplication) {
        this.appContext = ponyApplication;
    }

    @Override
    public String getString(@StringRes int stringId) {
        return appContext.getString(stringId);
    }

    @Override
    public int getInt(@IntegerRes int intId) {
        return appContext.getResources().getInteger(intId);
    }

    @Override
    public @ColorInt
    int getColor(@ColorRes int color) {
        return ContextCompat.getColor(appContext, color);
    }
}
