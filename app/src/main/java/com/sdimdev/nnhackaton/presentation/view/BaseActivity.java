package com.sdimdev.nnhackaton.presentation.view;

import com.arellomobile.mvp.MvpAppCompatActivity;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

public abstract class BaseActivity extends MvpAppCompatActivity {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Inject
    NavigatorHolder navigatorHolder;

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    protected void connect(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    protected abstract Navigator getNavigator();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(getNavigator());
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }
}
