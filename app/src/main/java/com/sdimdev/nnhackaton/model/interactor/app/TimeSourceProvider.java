package com.sdimdev.nnhackaton.model.interactor.app;


import com.sdimdev.nnhackaton.model.entity.time.TimeSource;

public interface TimeSourceProvider {
    TimeSource provideTimeSource();
}
