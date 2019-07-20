package com.sdimdev.nnhackaton.utils.navigation;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.sdimdev.nnhackaton.model.entity.log.Logger;
import com.sdimdev.nnhackaton.model.interactor.app.TimeSourceProvider;
import com.sdimdev.nnhackaton.presentation.Screens;
import com.sdimdev.nnhackaton.presentation.view.MessageDialog;
import com.sdimdev.nnhackaton.presentation.view.route.RouteMapFragment;
import com.sdimdev.nnhackaton.presentation.view.search.SearchFragment;
import com.sdimdev.nnhackaton.utils.system.router.DialogCommand;
import com.sdimdev.nnhackaton.utils.system.router.FinishFlow;
import com.sdimdev.nnhackaton.utils.system.router.FlowNavigator;
import com.sdimdev.nnhackaton.utils.system.router.MessageCommand;
import com.sdimdev.nnhackaton.utils.system.router.SingleTopDialog;
import com.sdimdev.nnhackaton.utils.system.router.StartFlow;

import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

public class AppNavigator extends FlowNavigator {
    private IOnApplyCommandListener onApplyCommandListener;
    private Logger logger;
    private TimeSourceProvider timeSourceProvider;

    public AppNavigator(FragmentActivity activity, int containerId, IOnApplyCommandListener onApplyCommandListener,
                        Logger logger, TimeSourceProvider timeSourceProvider) {
        super(activity, logger, containerId);
        this.onApplyCommandListener = onApplyCommandListener;
        this.logger = logger;
        this.timeSourceProvider = timeSourceProvider;
    }

    @Override
    protected void applyCommand(Command command) {
        if (command instanceof Forward)
            logger.log("forward: " + ((Forward) command).getScreenKey());
        else if (command instanceof Back)
            logger.log("back");
        else if (command instanceof BackTo)
            logger.log("backTo: " + ((BackTo) command).getScreenKey());
        else if (command instanceof DialogCommand)
            logger.log("dialog: " + ((DialogCommand) command).getScreenKey());
        else if (command instanceof Replace)
            logger.log("replace: " + ((Replace) command).getScreenKey());
        else if (command instanceof StartFlow)
            logger.log("startflow: " + ((StartFlow) command).getScreenKey());
        else if (command instanceof FinishFlow)
            logger.log("finishflow");
        else if (command instanceof SingleTopDialog)
            logger.log("singleTopDialog");

        super.applyCommand(command);
        if (onApplyCommandListener != null)
            onApplyCommandListener.onApplyCommand(command);
    }

    @Override
    protected Fragment createFragment(String screenKey, Object data) {
        switch (screenKey) {
            case Screens.SEARCH_SCREEN:
                return SearchFragment.getInstance();
            case Screens.ROUTE_MAP:
                return RouteMapFragment.getInstance();
        }
        return null;
    }

    @Override
    protected Intent createFlowIntent(String flowKey, @Nullable Object data) {
        switch (flowKey) {

        }
        return null;
    }

    @Override
    protected void startFlow(String flowKey, Object data) {
        switch (flowKey) {
            default:
                super.startFlow(flowKey, data);
        }

    }

    @Override
    protected DialogFragment createDialog(String screenKey, Object data) {
        switch (screenKey) {
            case Screens.DIALOG_MESSAGE_SCREEN:
                return MessageDialog.newInstance(((MessageCommand) data).getMessage(), ((MessageCommand) data).getTitle());

        }
        return null;
    }


    public interface IOnApplyCommandListener {
        void onApplyCommand(Command command);
    }
}
