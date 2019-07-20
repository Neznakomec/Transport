package com.sdimdev.nnhackaton.presentation.view.drawer;

import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.sdimdev.nnhackaton.R;
import com.sdimdev.nnhackaton.di.DIManager;
import com.sdimdev.nnhackaton.di.navigation.DaggerNavComponent;
import com.sdimdev.nnhackaton.presentation.presenter.drawer.NavigationDrawerPresenter;
import com.sdimdev.nnhackaton.presentation.view.BaseFragment;

import javax.inject.Inject;

public class NavigationDrawerFragment extends BaseFragment implements NavigationDrawerView {
    protected NavigationView navigationView;

    @Inject
    @InjectPresenter
    NavigationDrawerPresenter navigationDrawerPresenter;

    @ProvidePresenter
    NavigationDrawerPresenter providePresenter() {
        return navigationDrawerPresenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DaggerNavComponent.builder()
                .appApi(DIManager.get().getAppComponent())
                .build()
                .inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_navigation;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigationView = view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            navigationDrawerPresenter.onMenuItemClick(MenuItem.forId(item.getItemId()));
            return false;
        });
    }

    @Override
    public void prepareMenu(@MenuRes int menuId) {
        navigationView.getMenu()
                .clear();
        navigationView.inflateMenu(menuId);
    }

    @Override
    public void showError(Throwable th) {
        Toast.makeText(getContext(), th.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void selectMenuItem(MenuItem menuItem) {
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            android.view.MenuItem navItem = navigationView.getMenu().getItem(i);
            if (navItem.getItemId() == menuItem.getMenuId()) {
                navItem.setChecked(true);
            } else {
                navItem.setChecked(false);
            }
        }
    }

    public void onScreenChanged(MenuItem item) {
        navigationDrawerPresenter.onScreenChanged(item);
    }
}
