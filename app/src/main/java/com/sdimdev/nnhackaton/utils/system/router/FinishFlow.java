package com.sdimdev.nnhackaton.utils.system.router;

import ru.terrakok.cicerone.commands.Command;

public class FinishFlow implements Command {
	private Object transitionData;

	public FinishFlow(Object transitionData) {
		this.transitionData = transitionData;

	}

	public Object getTransitionData() {
		return transitionData;
	}

	public void setTransitionData(Object transitionData) {
		this.transitionData = transitionData;
	}
}
