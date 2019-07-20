package com.sdimdev.nnhackaton.presentation;

import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.Observable;

public class GlobalMenuController {
	private final PublishRelay<Boolean> relay = PublishRelay.create();

	public Observable<Boolean> getState() {
		return relay;
	}

	public void open() {
		relay.accept(true);
	}

	public void close() {
		relay.accept(false);
	}
}
