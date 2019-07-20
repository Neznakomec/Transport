package com.sdimdev.nnhackaton.utils.system.router;

import ru.terrakok.cicerone.commands.Command;

public class SingleTopDialog implements Command {
	private String screenKey;
	private Object transitionData;

	public SingleTopDialog(String screenKey, Object transitionData) {
		this.screenKey = screenKey;
		this.transitionData = transitionData;
	}

	public String getScreenKey() {
		return screenKey;
	}

	public void setScreenKey(String screenKey) {
		this.screenKey = screenKey;
	}

	public Object getTransitionData() {
		return transitionData;
	}

	public void setTransitionData(Object transitionData) {
		this.transitionData = transitionData;
	}
}
