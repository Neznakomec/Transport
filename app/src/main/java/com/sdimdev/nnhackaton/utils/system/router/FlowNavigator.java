package com.sdimdev.nnhackaton.utils.system.router;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.sdimdev.nnhackaton.model.entity.log.Logger;
import com.sdimdev.nnhackaton.presentation.Screens;

import ru.terrakok.cicerone.android.SupportFragmentNavigator;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

public abstract class FlowNavigator extends SupportFragmentNavigator {
    protected FragmentActivity activity;
    private Logger logger;

    public FlowNavigator(FragmentActivity activity, Logger logger, int containerId) {
        super(activity.getSupportFragmentManager(), containerId);
        this.activity = activity;
        this.logger = logger;
    }

    @Override
    protected void applyCommand(Command command) {
        try {
            if (command instanceof FinishFlow) {
                finishFlow(((FinishFlow) command).getTransitionData());
            } else if (command instanceof StartFlow) {
                startFlow(((StartFlow) command).getScreenKey(), ((StartFlow) command).getTransitionData());
            } else if (command instanceof DialogCommand) {
                startDialog(((DialogCommand) command).getScreenKey(), ((DialogCommand) command).getTransitionData());
            } else if (command instanceof SingleTopDialog) {
                startSingleDialog(((SingleTopDialog) command).getScreenKey(), ((SingleTopDialog) command).getTransitionData());
            } else if (command instanceof MessageCommand) {
                showMessage(((MessageCommand) command));
            } else if (command instanceof StopDialog) {
                stopDialog(((StopDialog) command).getScreenKey());
            } else {
                super.applyCommand(command);
            }
        } catch (Throwable throwable) {
            logger.log("Can't apply command:" + command.toString(), throwable);
        }
    }

    public void setLaunchScreen(String screenKey, @Nullable Object data) {
        applyCommands(new Command[]{
                new BackTo(null),
                new Replace(screenKey, data)});
    }

    protected void startFlow(String flowKey, Object data) {
        Intent intent =
                createFlowIntent(flowKey, data);
        if (intent != null) {
            activity.startActivityForResult(intent, getRequestCodeForFlow(flowKey));
        }
    }

    protected void startSingleDialog(String screenKey, Object data) {
        if (screenKey == null)
            return;
        {
            Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(screenKey);
            if (fragment != null) {
                if (fragment instanceof IUpdateWithData && fragment.isAdded())
                    ((IUpdateWithData) fragment).updateWithData(data);
            } else {
                startDialog(screenKey, data);
            }

        }
    }

    protected void stopDialog(String screenKey) {
        if (screenKey == null)
            return;
        {
            Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(screenKey);
            if (fragment != null) {
                if (fragment instanceof DialogFragment && fragment.isAdded()) {
                    ((DialogFragment) fragment).dismiss();
                }
            }

        }
    }

    protected void startDialog(String screenKey, Object data) {
        DialogFragment dialogFragment = createDialog(screenKey, data);
        if (dialogFragment != null) {
            dialogFragment.showNow(activity.getSupportFragmentManager(), screenKey);
        }
    }

    protected void showMessage(MessageCommand message) {
        startDialog(Screens.DIALOG_MESSAGE_SCREEN, message);
    }

    protected DialogFragment createDialog(String screenKey, Object data) {
        return null;
    }

    protected int getRequestCodeForFlow(String flowKey) {
        return -1;
    }

    protected Intent createFlowIntent(String flowKey, @Nullable Object data) {
        return null;
    }

    protected void finishFlow(Object transitionData) {
        activity.setResult(Activity.RESULT_OK, createFlowResult(transitionData));
        activity.finish();
    }

    protected Intent createFlowResult(Object transitionData) {
        return null;
    }

    @Override
    protected void showSystemMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void exit() {
        activity.setResult(Activity.RESULT_CANCELED);
        activity.finish();
    }

    @Override
    protected void unknownScreen(Command command) {
        logger.log("Can't apply command:" + command.toString());
        throw new RuntimeException("Can't create a screen for passed screenKey. Command:" + command.toString() + "\n");
    }
}
