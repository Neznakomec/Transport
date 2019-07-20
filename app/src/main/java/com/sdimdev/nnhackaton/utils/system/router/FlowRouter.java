package com.sdimdev.nnhackaton.utils.system.router;

import android.support.annotation.Nullable;

import ru.terrakok.cicerone.Router;

public class FlowRouter extends Router {
	public void startFlow(String flowKey, @Nullable Object data) {
		executeCommands(new StartFlow(flowKey, data));
	}

	public void startDialog(String dialogKey, @Nullable Object data) {
		executeCommands(new DialogCommand(dialogKey, data));
	}

	public void singleStartDialog(String dialogKey, @Nullable Object data) {
		executeCommands(new SingleTopDialog(dialogKey, data));
	}

	public void startDialog(String dialogKey) {
		startDialog(dialogKey, null);
	}

	public void showMessage(@Nullable String title, String message) {
		executeCommands(new MessageCommand(title, message));
	}

	public void stopDialog(String dialogKey) {
		executeCommands(new StopDialog(dialogKey));
	}

	public void finishFlow(@Nullable Object data) {
		executeCommands(new FinishFlow(data));
	}

	public void cancelFlow() {
		finishChain();
	}
}
