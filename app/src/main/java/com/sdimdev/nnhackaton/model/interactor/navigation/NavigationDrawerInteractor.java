package com.sdimdev.nnhackaton.model.interactor.navigation;


import io.reactivex.Observable;

public interface NavigationDrawerInteractor {
	Observable<Boolean> isAuthorized();
}
