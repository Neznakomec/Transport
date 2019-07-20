package com.sdimdev.nnhackaton.presentation.presenter.search;

import com.arellomobile.mvp.InjectViewState;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.Relay;
import com.sdimdev.nnhackaton.presentation.presenter.BasePresenter;
import com.sdimdev.nnhackaton.presentation.view.search.SearchView;
import com.sdimdev.nnhackaton.utils.rx.RxSchedulers;
import com.sdimdev.nnhackaton.utils.system.router.FlowRouter;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */

@InjectViewState
public class SearchPresenter extends BasePresenter<SearchView> {
    private String selectedAddress;
    private RxSchedulers rxSchedulers;
    private Relay<String> specialityRelay = BehaviorRelay.create();
    private Relay<String> examinationRelay = BehaviorRelay.create();
    private FlowRouter flowRouter;

    public SearchPresenter(RxSchedulers rxSchedulers, FlowRouter flowRouter) {
        this.rxSchedulers = rxSchedulers;
        this.flowRouter = flowRouter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }


    public boolean onBackPressed() {
        return false;
    }
}
