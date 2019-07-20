package com.sdimdev.nnhackaton.di.app;

import com.sdimdev.nnhackaton.HackatonApplication;
import com.sdimdev.nnhackaton.model.interactor.app.TimeSourceProvider;
import com.sdimdev.nnhackaton.model.repository.SearchRepository;
import com.sdimdev.nnhackaton.presentation.GlobalMenuController;
import com.sdimdev.nnhackaton.utils.rx.RxSchedulers;
import com.sdimdev.nnhackaton.utils.system.router.FlowRouter;
import com.vanniktech.rxpermission.RxPermission;

import ru.terrakok.cicerone.NavigatorHolder;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
public interface AppApi {
    RxSchedulers provideRxSchedulers();
    TimeSourceProvider provideTimeSourceProvider();
    GlobalMenuController provideGlobalMenuController();
    FlowRouter provideFlowRouter();
    NavigatorHolder provideNavigationHolder();
    RxPermission provideRxPermission();
    SearchRepository provideSearchRepository();
    HackatonApplication provideHackatonApplication();
}
