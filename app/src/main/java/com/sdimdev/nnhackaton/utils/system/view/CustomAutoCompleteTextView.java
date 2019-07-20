package com.sdimdev.nnhackaton.utils.system.view;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

public class CustomAutoCompleteTextView extends AppCompatAutoCompleteTextView {

	public CustomAutoCompleteTextView(Context context) {
		super(context);
	}

	public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean onKeyPreIme(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && isPopupShowing()) {
			InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

			if (inputManager.hideSoftInputFromWindow(findFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS)) {
				return true;
			}
		}

		return super.onKeyPreIme(keyCode, event);
	}
	@Override
	public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
		final InputConnection ic = super.onCreateInputConnection(outAttrs);
		if (ic != null && outAttrs.hintText == null) {
			// If we don't have a hint and our parent is a TextInputLayout, use it's hint for the
			// EditorInfo. This allows us to display a hint in 'extract mode'.
			final ViewParent parent = getParent();
			if (parent instanceof TextInputLayout) {
				outAttrs.hintText = ((TextInputLayout) parent).getHint();
			}
		}
		return ic;
	}

}
