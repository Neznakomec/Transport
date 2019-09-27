package com.sdimdev.nnhackaton.presentation.view.search;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sdimdev.nnhackaton.HackatonApplication;
import com.sdimdev.nnhackaton.R;
import com.sdimdev.nnhackaton.di.DIManager;
import com.sdimdev.nnhackaton.di.coin.search.DaggerCoinComponent;
import com.sdimdev.nnhackaton.di.route.DaggerRouteComponent;
import com.sdimdev.nnhackaton.presentation.GlobalMenuController;
import com.sdimdev.nnhackaton.presentation.presenter.search.SearchPresenter;
import com.sdimdev.nnhackaton.presentation.view.BaseFragment;
import com.sdimdev.nnhackaton.presentation.view.HorizontalNumberPicker;
import com.sdimdev.nnhackaton.presentation.view.game.CoinFlyOptions;
import com.sdimdev.nnhackaton.presentation.view.game.FlyingCoinFragment;
import com.sdimdev.nnhackaton.presentation.view.zxing.ScanActivity;
import com.vanniktech.rxpermission.RxPermission;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
public class CoinsFragment extends BaseFragment implements SearchView {

    //CustomAutoCompleteTextView address;
    //CustomAutoCompleteTextView speciality;
    //CustomAutoCompleteTextView examinationType;
    @Inject
    RxPermission rxPermission;
    Toolbar toolbar;
    //View searchButton;
    View progress;
    CoinsFragmentKt wrapper;

    HorizontalNumberPicker _countPicker;

    CompositeDisposable textChangesDisposable = new CompositeDisposable();
    @Inject
    @InjectPresenter
    SearchPresenter searchPresenter;

    @Inject
    GlobalMenuController globalMenuController;

    public static Fragment getInstance() {
        return new CoinsFragment();
    }

    @ProvidePresenter
    public SearchPresenter provideSearchPresenter() {
        return searchPresenter;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_coin;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        // to inject rx permissions
        DaggerCoinComponent.builder()
                .appApi(DIManager.get().getAppComponent())
                .build().inject(this);


        wrapper = new CoinsFragmentKt(this);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            rxPermission.requestEach(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE})
                    .observeOn(AndroidSchedulers.mainThread())
                    .toList()
                    .subscribe();
            return;
        }
        wrapper.startScan();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTextChanges();
        if (lastQr != null) {
            lastQr = null;
            onCodeChecked(getView());
        }

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
        toolbar.setTitle("");
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        //toolbar.setNavigationOnClickListener(v -> globalMenuController.open());
        //toolbar.setNavigationOnClickListener(v -> onBackPressed());
        /*searchButton.setOnClickListener(v -> {
            KeyBoardUtils.hideKeyboard(getActivity());
            searchPresenter.startSearch();
        });*/

        Button start = view.findViewById(R.id.enterButton);
        start.setOnClickListener(v -> {
            startBarcodeScanning();

        });

        TextView bonusText = view.findViewById(R.id.bonusText);
        bonusText.setOnClickListener(v -> {
            Intent launchIntent = HackatonApplication.app.getPackageManager().getLaunchIntentForPackage("example.com.myapplication");
            if (launchIntent != null) {
                startActivity(launchIntent);//null pointer check in case package name was not found
            }
        });
    }


    public void onCodeChecked(View view) {
        View start1 = view.findViewById(R.id.enterButton);
        View stop = view.findViewById(R.id.exitButton);
        CoinFlyOptions _coinFlyOptions = new CoinFlyOptions(start1, stop);
        FlyingCoinFragment flyingCoinFragment =
                FlyingCoinFragment
                        .newInstance(_coinFlyOptions.getFillBundle());
        FragmentTransaction transaction =
                getFragmentManager().beginTransaction();

        transaction.add(
                R.id.child_fragment_container,
                flyingCoinFragment,
                "COIN_FLY_OVERLAY");
        transaction.commit();

        TextView text = view.findViewById(R.id.balance);
        int balance = text.getText().length() > 0 ? Integer.parseInt(""+text.getText()) : 0;
        balance += 20;
        text.setText(String.valueOf(balance));
        Log.d("BALANCE", "set " + balance);
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

    private void startBarcodeScanning()
    {
        // from MMS
        Intent intentScan = new Intent(getActivity(), ScanActivity.class);
        intentScan.setAction(Intents.Scan.ACTION);
        intentScan.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //turn off pin-code request on activity resume, after scanning
        startActivityForResult(intentScan, IntentIntegrator.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null)
        {
            boolean flag = true;
            /*while (flag) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            String contents = result.getContents();
            if (contents != null)
            {
                if (true/*checkQr.equals(contents)*/) {
                    lastQr = contents;
                    //onCodeChecked(getView());
                    Log.d("BALANCE", "тот код");
                } else {
                    lastQr = null;
                    Toast.makeText(getContext(), "Не тот код!", Toast.LENGTH_LONG).show();
                    Log.d("BALANCE", "не тот код");
                }
            }
        }
    }

    String lastQr;
    String checkQr = "CHECK";
}
