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
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.bonus.BonusActivity;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sdimdev.nnhackaton.HackatonApplication;
import com.sdimdev.nnhackaton.R;
import com.sdimdev.nnhackaton.data.persistence.DataBaseProvider;
import com.sdimdev.nnhackaton.data.service.CollectService;
import com.sdimdev.nnhackaton.di.DIManager;
import com.sdimdev.nnhackaton.di.coin.search.DaggerCoinComponent;
import com.sdimdev.nnhackaton.presentation.GlobalMenuController;
import com.sdimdev.nnhackaton.presentation.presenter.search.SearchPresenter;
import com.sdimdev.nnhackaton.presentation.view.BaseFragment;
import com.sdimdev.nnhackaton.presentation.view.HorizontalNumberPicker;
import com.sdimdev.nnhackaton.presentation.view.game.CoinFlyOptions;
import com.sdimdev.nnhackaton.presentation.view.game.FlyingCoinFragment;
import com.sdimdev.nnhackaton.presentation.view.zxing.ScanActivity;
import com.vanniktech.rxpermission.RxPermission;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

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

    @Inject
    DataBaseProvider dataBaseProvider;
    Toolbar toolbar;
    //View searchButton;
    View progress;
    View enterButton;
    View exitButton;
    TextView durationTextView;
    TextView balanceTextView;
    CoinsFragmentKt wrapper;

    HorizontalNumberPicker _countPicker;

    CompositeDisposable textChangesDisposable = new CompositeDisposable();
    @Inject
    @InjectPresenter
    SearchPresenter searchPresenter;

    @Inject
    GlobalMenuController globalMenuController;
    String lastQr;
    String checkQr = "CHECK";

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


        wrapper = new CoinsFragmentKt(this, dataBaseProvider);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            rxPermission.requestEach(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
                    .observeOn(AndroidSchedulers.mainThread())
                    .toList()
                    .subscribe();
            return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTextChanges();
        if (lastQr != null) {
            lastQr = null;
            //onCodeChecked(getView(), 20);
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

    Timer timer;
    TimerTask timerTask;
    private int time = 0;
    private static int period = 1000;
    public void startTimer() {
        //set a new Timer
        if (timer != null) {
            return;
        }
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 0, period);
    }

    public void stoptimertask(View v) {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        time = 0;
        durationTextView.setText(String.format(getString(R.string.duration_format_str), "00:00:00"));
    }
    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        //get the current timeStamp
                        /*Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
                        final String strDate = simpleDateFormat.format(calendar.getTime());

                        //show the toast
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), strDate, duration);
                        toast.show();*/
                        time += period / DateUtils.SECOND_IN_MILLIS;
                        int hh = time / 3600;
                        int mm = (time % 3600) / 60;
                        int ss = (time % 60);
                        String timeStr = formatTime(time * 1000);//String.format("%d:%d:%d", hh, mm, ss);
                        durationTextView.setText(String.format(getString(R.string.duration_format_str), timeStr));
                    }
                });
            }
        };
    }

    public static final String formatTime(long millis) {
        long secs = millis / 1000;
        return String.format("%02d:%02d:%02d", secs / 3600, (secs % 3600) / 60, secs % 60);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progress = view.findViewById(R.id.progress);
        toolbar = view.findViewById(R.id.toolbar);
        enterButton = view.findViewById(R.id.enterButton);
        exitButton = view.findViewById(R.id.exitButton);
        balanceTextView = view.findViewById(R.id.balance);
        durationTextView = view.findViewById(R.id.duration);
        toolbar.setTitle("");

        enterButton.setOnClickListener(v -> {
            //startBarcodeScanning();
            onCodeChecked(20);
            startTimer();

        });

        exitButton.setOnClickListener(v -> {
            stoptimertask(getView());
        });
        toolbar.setNavigationOnClickListener(v -> {
            globalMenuController.open();
        });

        TextView bonusText = view.findViewById(R.id.bonusText);
        bonusText.setOnClickListener(v -> {
            //Intent launchIntent = HackatonApplication.app.getPackageManager().getLaunchIntentForPackage("example.com.myapplication");
            Intent bonusActivity = new Intent(getContext(), BonusActivity.class);
            if (bonusActivity != null) {
                startActivity(bonusActivity);//null pointer check in case package name was not found
            }
        });
        if(savedInstanceState==null) {
            //wrapper.startScan();
            getContext().startService(new Intent(getContext(), CollectService.class));
        }
    }

    public void onCodeChecked(int valueToAdd) {

        CoinFlyOptions _coinFlyOptions = new CoinFlyOptions(enterButton, exitButton);
        FlyingCoinFragment flyingCoinFragment =
                FlyingCoinFragment
                        .newInstance(_coinFlyOptions.getFillBundle());
        FragmentTransaction transaction =
                getFragmentManager().beginTransaction();

        transaction.replace(
                R.id.child_fragment_container,
                flyingCoinFragment,
                "COIN_FLY_OVERLAY");
        transaction.commit();

        int balance = balanceTextView.getText().length() > 0 ? Integer.parseInt("" + balanceTextView.getText()) : 0;
        balance += valueToAdd;
        balanceTextView.setText(String.valueOf(balance));
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

    private void startBarcodeScanning() {
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
        if (result != null) {
            boolean flag = true;
            /*while (flag) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            String contents = result.getContents();
            if (contents != null) {
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
}
