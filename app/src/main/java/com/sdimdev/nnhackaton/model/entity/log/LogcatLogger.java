package com.sdimdev.nnhackaton.model.entity.log;

import android.util.Log;

import com.sdimdev.nnhackaton.BuildConfig;

public class LogcatLogger implements Logger {
    private String tag;

    public LogcatLogger(String tag) {
        this.tag = tag;
    }

    @Override
    public void log(String message) {
        if (BuildConfig.DEBUG)
            Log.d(tag, message);
    }

    @Override
    public void log(String message, Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.d(tag, message, throwable);
    }
}
