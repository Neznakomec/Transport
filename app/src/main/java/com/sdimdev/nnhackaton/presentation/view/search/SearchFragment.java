package com.sdimdev.nnhackaton.presentation.view.search;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.sdimdev.nnhackaton.R;
import com.sdimdev.nnhackaton.di.DIManager;
import com.sdimdev.nnhackaton.di.search.DaggerSearchComponent;
import com.sdimdev.nnhackaton.presentation.GlobalMenuController;
import com.sdimdev.nnhackaton.presentation.presenter.search.SearchPresenter;
import com.sdimdev.nnhackaton.presentation.view.BaseFragment;
import com.sdimdev.nnhackaton.utils.keyboard.KeyBoardUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
public class SearchFragment extends BaseFragment implements SearchView {

    Toolbar toolbar;
    View searchButton;
    View progress;


    @Inject
    @InjectPresenter
    SearchPresenter searchPresenter;

    @Inject
    GlobalMenuController globalMenuController;

    public static Fragment getInstance() {
        return new SearchFragment();
    }

    @ProvidePresenter
    public SearchPresenter provideSearchPresenter() {
        return searchPresenter;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_search;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DaggerSearchComponent.builder()
                .appApi(DIManager.get().getAppComponent())
                .build()
                .inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*getChildFragmentManager().beginTransaction()
                .replace(R.id.child_fragment_container, BuyTicketFragment.getInstance(), "TICKET")
                .commit();*/

       /* getChildFragmentManager().beginTransaction()
                .replace(R.id.child_fragment_container_2, AccessibilityFragment.getInstance(), "ACCESSIBILITY")
                .commit();*/

       if (getChildFragmentManager().findFragmentByTag("TICKET") == null) {
           getChildFragmentManager().beginTransaction()
                   .replace(R.id.child_fragment_container, CoinsFragment.getInstance(), "TICKET")
                   .commit();
       }
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchButton = view.findViewById(R.id.search_button);
        progress = view.findViewById(R.id.progress);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Поиск");
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> globalMenuController.open());
        //toolbar.setNavigationOnClickListener(v -> onBackPressed());
        searchButton.setOnClickListener(v -> {
            KeyBoardUtils.hideKeyboard(getActivity());

        });
    }


    @Override
    public void showProgress() {
        new Handler(Looper.getMainLooper())
                .post(() -> {
                    progress.setVisibility(View.VISIBLE);
                    searchButton.setEnabled(false);
                });

    }

    @Override
    public void hideProgress() {
        new Handler(Looper.getMainLooper())
                .post(() -> {
                    progress.setVisibility(View.GONE);
                    searchButton.setEnabled(true);
                });
    }
}
