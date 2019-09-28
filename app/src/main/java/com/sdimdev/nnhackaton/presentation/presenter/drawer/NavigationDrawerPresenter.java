package com.sdimdev.nnhackaton.presentation.presenter.drawer;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.sdimdev.nnhackaton.R;
import com.sdimdev.nnhackaton.data.DatabaseCopyManager;
import com.sdimdev.nnhackaton.presentation.GlobalMenuController;
import com.sdimdev.nnhackaton.presentation.presenter.BasePresenter;
import com.sdimdev.nnhackaton.presentation.view.drawer.NavigationDrawerView;
import com.sdimdev.nnhackaton.utils.system.router.FlowRouter;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class NavigationDrawerPresenter extends BasePresenter<NavigationDrawerView> {
    private GlobalMenuController menuController;
    private NavigationDrawerView.MenuItem currentSelectedItem = null;
    private FlowRouter flowRouter;
    private DatabaseCopyManager databaseCopyManager;

    public NavigationDrawerPresenter(GlobalMenuController menuController, FlowRouter flowRouter, DatabaseCopyManager databaseCopyManager) {
        this.menuController = menuController;
        this.flowRouter = flowRouter;
        this.databaseCopyManager = databaseCopyManager;
    }

    public void onMenuItemClick(NavigationDrawerView.MenuItem menuItem) {
        menuController.close();
        if (menuItem.getMenuId() == R.id.nav_copy_db) {
            connect(databaseCopyManager.copyToRoot()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> flowRouter.showMessage("Успешно", "Запись базы в корень системы успешна"),
                            throwable -> {
                                flowRouter.showMessage("Ошибка", throwable.getMessage());
                                Log.e("NavigationDrawer", "error", throwable);
                            }));
            return;
        }
        if (menuItem != currentSelectedItem) {
            flowRouter.newRootScreen(menuItem.getScreen());
        }
    }

    public void onScreenChanged(NavigationDrawerView.MenuItem item) {
        menuController.close();
        currentSelectedItem = item;
        getViewState().selectMenuItem(item);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().prepareMenu(R.menu.nav_menu);
    }

}
