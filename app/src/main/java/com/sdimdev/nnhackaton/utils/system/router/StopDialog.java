package com.sdimdev.nnhackaton.utils.system.router;

import ru.terrakok.cicerone.commands.Command;

public class StopDialog implements Command {
	private String screenKey;

	public StopDialog(String screenKey) {

		this.screenKey = screenKey;
	}

	public String getScreenKey() {
		return screenKey;
	}
}
