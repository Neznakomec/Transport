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
import com.sdimdev.nnhackaton.presentation.GlobalMenuController;
import com.sdimdev.nnhackaton.presentation.presenter.search.SearchPresenter;
import com.sdimdev.nnhackaton.presentation.view.BaseFragment;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
public class AccessibilityFragment extends BaseFragment implements SearchView {

    //CustomAutoCompleteTextView address;
    //CustomAutoCompleteTextView speciality;
    //CustomAutoCompleteTextView examinationType;
    Toolbar toolbar;
    //View searchButton;
    View progress;

    CompositeDisposable textChangesDisposable = new CompositeDisposable();
    @Inject
    @InjectPresenter
    SearchPresenter searchPresenter;

    @Inject
    GlobalMenuController globalMenuController;

    public static Fragment getInstance() {
        return new AccessibilityFragment();
    }

    @ProvidePresenter
    public SearchPresenter provideSearchPresenter() {
        return searchPresenter;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_accessibility;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTextChanges();
    }

    private void setTextChanges() {
        textChangesDisposable = new CompositeDisposable();
        /*textChangesDisposable.add(RxTextView.textChanges(address)
                .map(CharSequence::toString)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(any -> address.isFocused())
                .subscribeOn(AndroidSchedulers.mainThread())

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text -> {
                    searchPresenter.onAddressEntered(text);
                    address.setError(null);
                }));
        textChangesDisposable.add(RxTextView.textChanges(speciality)
                .map(CharSequence::toString)
                .debounce(100, TimeUnit.MILLISECONDS)
                .filter(any -> speciality.isFocused())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text -> {
                    searchPresenter.onSpecialityEntered(text);
                    speciality.setError(null);
                }));
        textChangesDisposable.add(RxTextView.textChanges(examinationType)
                .map(CharSequence::toString)
                .debounce(100, TimeUnit.MILLISECONDS)
                .filter(any -> examinationType.isFocused())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text -> {
                    searchPresenter.onExaminationTypeEntered(text);
                    examinationType.setError(null);
                }));
        textChangesDisposable.add(RxView.focusChanges(speciality)
                .filter(any -> any)
                .subscribe(any -> speciality.setText(speciality.getText())));
        textChangesDisposable.add(RxView.focusChanges(examinationType)
                .filter(any -> any)
                .subscribe(any -> examinationType.setText(examinationType.getText())));
                */
    }

    @Override
    public void onPause() {
        super.onPause();
        textChangesDisposable.dispose();
        textChangesDisposable = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*address = view.findViewById(R.id.enterAddress);
        speciality = view.findViewById(R.id.enterSpeciality);
        examinationType = view.findViewById(R.id.enterExaminationType);
        searchButton = view.findViewById(R.id.search_button);*/
        progress = view.findViewById(R.id.progress);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.buy_ticket);
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> globalMenuController.open());
        //toolbar.setNavigationOnClickListener(v -> onBackPressed());
        /*searchButton.setOnClickListener(v -> {
            KeyBoardUtils.hideKeyboard(getActivity());
            searchPresenter.startSearch();
        });*/
    }


    @Override
    public void showProgress() {
        new Handler(Looper.getMainLooper())
                .post(() -> {
                    progress.setVisibility(View.VISIBLE);
                    //searchButton.setEnabled(false);
                });

    }

    @Override
    public void hideProgress() {
        new Handler(Looper.getMainLooper())
                .post(() -> {
                    progress.setVisibility(View.GONE);
                    //searchButton.setEnabled(true);
                });
    }
}
