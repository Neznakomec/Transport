package com.sdimdev.nnhackaton.model.interactor.app;

import com.sdimdev.nnhackaton.model.entity.time.SystemTimeSourceImpl;
import com.sdimdev.nnhackaton.model.entity.time.TimeSource;


public class TimeSourceProviderImpl implements TimeSourceProvider {


    public TimeSourceProviderImpl() {

    }

    @Override
    public TimeSource provideTimeSource() {
        return new SystemTimeSourceImpl();
    }
}
