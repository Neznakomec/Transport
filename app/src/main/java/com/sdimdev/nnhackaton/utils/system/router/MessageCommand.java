package com.sdimdev.nnhackaton.utils.system.router;

import ru.terrakok.cicerone.commands.Command;

public class MessageCommand implements Command {
	private String message;
	private String title;

	public String getTitle() {
		return title;
	}

	public MessageCommand(String title, String message) {
		this.message = message;
		this.title = title;
	}

	public String getMessage() {
		return message;
	}
}
