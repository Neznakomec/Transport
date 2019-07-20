package com.sdimdev.nnhackaton.model.interactor.app;

import io.reactivex.Completable;

public class AppStartInteractorImpl implements AppStartInteractor {

    public AppStartInteractorImpl() {

    }

    @Override
    public Completable onAppStarted() {
        return Completable.complete();
    }
}
