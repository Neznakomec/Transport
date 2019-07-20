package com.sdimdev.nnhackaton.model.interactor.navigation;

import io.reactivex.Observable;

public class NavigationDrawerInteractorImpl implements NavigationDrawerInteractor {


    public NavigationDrawerInteractorImpl() {

    }

    @Override
    public Observable<Boolean> isAuthorized() {
        return Observable.just(true);
    }
}
