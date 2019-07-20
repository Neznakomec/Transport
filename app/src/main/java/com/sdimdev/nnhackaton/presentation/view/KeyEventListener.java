package com.sdimdev.nnhackaton.presentation.view;

import android.view.KeyEvent;

public interface KeyEventListener {
	boolean onKeyDown(int keyCode, KeyEvent event);

	boolean onKeyUp(int keyCode, KeyEvent event);
}
