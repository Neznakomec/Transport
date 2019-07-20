package com.sdimdev.nnhackaton.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.sdimdev.nnhackaton.presentation.Screens;
import com.sdimdev.nnhackaton.presentation.view.navigation.NavigationView;
import com.sdimdev.nnhackaton.utils.system.router.FlowRouter;

@InjectViewState
public class NavigationPresenter extends BasePresenter<NavigationView> {
    private FlowRouter flowRouter;

    public NavigationPresenter(FlowRouter flowRouter) {
        this.flowRouter = flowRouter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().initNavFragment();
        flowRouter.newRootScreen(Screens.SEARCH_SCREEN);
    }

    public void onBackPressed() {
        flowRouter.finishChain();
    }

    public void onCreatedWithStartScreen(String screen, String stringExtra) {
        flowRouter.newRootScreen(screen, stringExtra);
    }

    public void onCreatedWithStartDialogScreen(String screen, String data) {
        flowRouter.startDialog(screen, data);
    }

    public void onCreatedWithStartSingleDialogScreen(String screen, String data) {
        flowRouter.singleStartDialog(screen, data);
    }
}
