package com.sdimdev.nnhackaton.presentation.view.navigation;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.sdimdev.nnhackaton.R;
import com.sdimdev.nnhackaton.di.DIManager;
import com.sdimdev.nnhackaton.di.navigation.DaggerNavComponent;
import com.sdimdev.nnhackaton.model.entity.log.Logger;
import com.sdimdev.nnhackaton.model.interactor.app.TimeSourceProvider;
import com.sdimdev.nnhackaton.presentation.GlobalMenuController;
import com.sdimdev.nnhackaton.presentation.presenter.NavigationPresenter;
import com.sdimdev.nnhackaton.presentation.view.BaseActivity;
import com.sdimdev.nnhackaton.presentation.view.BaseFragment;
import com.sdimdev.nnhackaton.presentation.view.KeyEventListener;
import com.sdimdev.nnhackaton.presentation.view.drawer.NavigationDrawerFragment;
import com.sdimdev.nnhackaton.utils.navigation.AppNavigator;
import com.sdimdev.nnhackaton.utils.string.ToString;
import com.sdimdev.nnhackaton.utils.system.other.PermissionHelper;
import com.vanniktech.rxpermission.Permission;
import com.vanniktech.rxpermission.RxPermission;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Command;

public class NavigationActivity extends BaseActivity implements NavigationView, AppNavigator.IOnApplyCommandListener {
    public static final String NAVIGATION_MENU = "NAVIGATION_MENU";
    public static final String START_SCREEN = "START_SCREEN";
    public static final String START_DIALOG_SCREEN = "START_DIALOG_SCREEN";
    public static final String START_SINGLE_DIALOG_SCREEN = "START_SINGLE_DIALOG_SCREEN";
    public static final String START_SCREEN_DATA = "START_SCREEN_DATA";
    private final static String[] permissions =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ? new String[]{}
                    : new String[]{};
    protected DrawerLayout drawerLayout;
    @Inject
    GlobalMenuController menuController;
    @Inject
    @InjectPresenter
    NavigationPresenter navigationPresenter;
    Disposable menuStateDisposable;
    @Inject
    Logger logger;
    @Inject
    TimeSourceProvider timeSourceProvider;


    Navigator navigator;

    @Inject
    RxPermission rxPermission;

    @ProvidePresenter
    public NavigationPresenter getNavigationPresenter() {
        return navigationPresenter;
    }

    private void updateNavDrawer() {
        /*getSupportFragmentManager().executePendingTransactions();
        if (getDrawerFragment() != null && getCurrentFragment() != null) {
			if(currentFragment instanceof ...)
			{
				drawerFragment.onScreenChanged(...);
			}
        }*/

    }

    @Override
    protected void onNewIntent(Intent intent) { // Receives the PendingIntent
        super.onNewIntent(intent);
        setIntent(intent);
    }

    protected NavigationDrawerFragment createNavigationFragment() {
        //newInstance
        return new NavigationDrawerFragment();
    }

    @Override
    protected Navigator getNavigator() {
        return navigator;
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        menuStateDisposable = menuController.getState().subscribe(this::openNavDrawer);
        String screen = getIntent().getStringExtra(START_SCREEN);
        if (screen != null && !ToString.EMPTY.equals(screen)) {
            getIntent().putExtra(START_SCREEN, ToString.EMPTY);
            navigationPresenter.onCreatedWithStartScreen(screen, getIntent().getStringExtra(START_SCREEN_DATA));
        }
    }


    @Override
    protected void onPause() {
        menuStateDisposable.dispose();
        super.onPause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        DaggerNavComponent.builder()
                .appApi(DIManager.get().getAppComponent())
                .build()
                .inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigator = new AppNavigator(this, R.id.container, this, logger, timeSourceProvider);
        Intent intent = getIntent();

        if (intent.getStringExtra(START_SCREEN) != null) {
            navigationPresenter.onCreatedWithStartScreen(intent.getExtras().getString(START_SCREEN), intent.getExtras().getString(START_SCREEN_DATA));
        } else if (intent.getStringExtra(START_DIALOG_SCREEN) != null) {
            navigationPresenter.onCreatedWithStartDialogScreen(intent.getExtras().getString(START_DIALOG_SCREEN), intent.getExtras().getString(START_SCREEN_DATA));
        } else if (intent.getStringExtra(START_SINGLE_DIALOG_SCREEN) != null) {
            navigationPresenter.onCreatedWithStartSingleDialogScreen(intent.getExtras().getString(START_SINGLE_DIALOG_SCREEN), intent.getExtras().getString(START_SCREEN_DATA));
        }
        if (savedInstanceState == null && permissions != null && permissions.length > 0) {
            connect(rxPermission.requestEach(permissions)
                    .filter(permission -> permission.state() != Permission.State.GRANTED)
                    .toList()
                    .map(List::isEmpty)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(isEmpty -> {
                        if (isEmpty) {
                            //nothing
                        } else {
                            new AlertDialog.Builder(this)
                                    .setMessage(R.string.permissions_message_request_storage)
                                    .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                                        PermissionHelper.showSettings(NavigationActivity.this);
                                    })
                                    .setNegativeButton(R.string.btn_cancel, (dialogInterface, i) -> {/*nothing*/})
                                    .setCancelable(false)
                                    .show();
                        }
                    })
            );
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            openNavDrawer(false);
        } else {
            BaseFragment baseFragment = getCurrentFragment();
            if (baseFragment == null || !baseFragment.onBackPressed())
                navigationPresenter.onBackPressed();
        }
    }

    private void openNavDrawer(boolean open) {
        if (open) drawerLayout.openDrawer(GravityCompat.START);
        else drawerLayout.closeDrawer(GravityCompat.START);
    }

    private NavigationDrawerFragment getDrawerFragment() {
        return (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
    }

    private BaseFragment getCurrentFragment() {
        return (BaseFragment)
                getSupportFragmentManager().findFragmentById(R.id.container);
    }

    @Override
    public void initNavFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.navigation_drawer, createNavigationFragment(), NAVIGATION_MENU)
                .commitNow();
    }

    @Override
    public void onApplyCommand(Command command) {
        updateNavDrawer();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return super.onKeyDown(keyCode, event);
        } else {
            Object baseFragment = getCurrentFragment();
            if (baseFragment instanceof KeyEventListener) {
                return ((KeyEventListener) baseFragment).onKeyDown(keyCode, event);
            } else
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Object baseFragment = getCurrentFragment();
        if (baseFragment instanceof KeyEventListener) {
            return ((KeyEventListener) baseFragment).onKeyUp(keyCode, event);
        } else
            return super.onKeyUp(keyCode, event);
    }
}
