package com.sdimdev.nnhackaton.presentation.presenter.drawer;

import com.arellomobile.mvp.InjectViewState;
import com.sdimdev.nnhackaton.R;
import com.sdimdev.nnhackaton.presentation.GlobalMenuController;
import com.sdimdev.nnhackaton.presentation.presenter.BasePresenter;
import com.sdimdev.nnhackaton.presentation.view.drawer.NavigationDrawerView;
import com.sdimdev.nnhackaton.utils.system.router.FlowRouter;

@InjectViewState
public class NavigationDrawerPresenter extends BasePresenter<NavigationDrawerView> {
    private GlobalMenuController menuController;
    private NavigationDrawerView.MenuItem currentSelectedItem = null;
    private FlowRouter flowRouter;

    public NavigationDrawerPresenter(GlobalMenuController menuController, FlowRouter flowRouter) {
        this.menuController = menuController;
        this.flowRouter = flowRouter;
    }

    public void onMenuItemClick(NavigationDrawerView.MenuItem menuItem) {
        menuController.close();
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
