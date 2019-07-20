package com.sdimdev.nnhackaton.presentation.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class SingleChoiceDialog extends AppCompatDialogFragment {
	public final static String SELECTED_POS = "SELECTED_POS";
	private final static String TITLE = "TITLE";
	private final static String POSITIVE_BTN = "POSITIVE_BTN";
	private final static String NEGATIVE_BTN = "NEGATIVE_BTN";
	private final static String VALUES = "VALUES";
	private int selected = -1; //not selected

	public static SingleChoiceDialog newInstance(@StringRes int title, @StringRes int positive, @StringRes int negative, ArrayList<String> values) {
		Bundle args = new Bundle();
		args.putInt(TITLE, title);
		args.putInt(POSITIVE_BTN, positive);
		args.putInt(NEGATIVE_BTN, negative);
		args.putStringArrayList(VALUES, values);
		SingleChoiceDialog fragment = new SingleChoiceDialog();
		fragment.setArguments(args);
		return fragment;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		if (getArguments().getInt(TITLE) != 0)
			builder.setTitle(getArguments().getInt(TITLE));
		if (getArguments().getInt(POSITIVE_BTN) != 0)
			builder.setPositiveButton(getArguments().getInt(POSITIVE_BTN), (dialog, i) -> {
				Intent intent = new Intent();
				intent.putExtra(SELECTED_POS, selected);
				if (getTargetFragment() != null)
					getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
			});
		if (getArguments().getInt(NEGATIVE_BTN) != 0)
			builder.setNegativeButton(getArguments().getInt(NEGATIVE_BTN), (dialog, i) -> {
				Intent intent = new Intent();
				intent.putExtra(SELECTED_POS, selected);
				if (getTargetFragment() != null)
					getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, intent);
			});
		ArrayList<String> list = getArguments().getStringArrayList(VALUES);

		if (list != null) {
			String[] arr = new String[list.size()];
			list.toArray(arr);
			builder.setSingleChoiceItems(arr, selected, (dialogInterface, i) -> {
				selected = i;
			});
		}
		return builder.create();
	}
}
