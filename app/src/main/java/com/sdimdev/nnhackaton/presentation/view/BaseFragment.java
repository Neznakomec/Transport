package com.sdimdev.nnhackaton.presentation.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;

public abstract class BaseFragment extends MvpAppCompatFragment implements BackPressListener {
	//override this invoiceType
	@Override
	public boolean onBackPressed() {
		return true;
	}

	@LayoutRes
	public abstract int getLayoutRes();

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(getLayoutRes(), container, false);
	}
}
