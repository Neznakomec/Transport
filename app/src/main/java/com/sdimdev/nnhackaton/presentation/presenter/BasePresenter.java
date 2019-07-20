package com.sdimdev.nnhackaton.presentation.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends MvpView> extends MvpPresenter<V> {
    private Relay<Boolean> attachRelay = BehaviorRelay.create();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public Completable awaitAttach() {
        return attachRelay.filter(aBoolean -> aBoolean)
                .ignoreElements();
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
        attachRelay.accept(true);
    }

    @Override
    public void detachView(V view) {
        super.detachView(view);
        if (getAttachedViews().isEmpty())
            attachRelay.accept(false);
    }

    protected void connect(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
