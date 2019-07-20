package com.sdimdev.nnhackaton.presentation.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.sdimdev.nnhackaton.R;


public class MessageDialog extends BaseDialogFragment {
	private final static String MESSAGE = "MESSAGE";
	private final static String TITLE = "TITLE";
	private TextView titleView;
	private TextView messageView;

	public static MessageDialog newInstance(String message, String title) {
		MessageDialog dialog = new MessageDialog();
		Bundle bundle = new Bundle();
		bundle.putString(MESSAGE, message);
		bundle.putString(TITLE, title);
		dialog.setArguments(bundle);
		return dialog;
	}

	@Override
	public int getLayoutRes() {
		return R.layout.dialog_message;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		view.findViewById(R.id.ok).setOnClickListener(v -> dismiss());
		String message = getArguments().getString(MESSAGE);
		String title = getArguments().getString(TITLE);
		titleView = view.findViewById(R.id.title);
		if (title != null) {
			titleView.setText(title);
		}
		messageView = view.findViewById(R.id.text);
		messageView.setText(message);
	}
}
