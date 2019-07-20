package com.sdimdev.nnhackaton.presentation.view.game;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.sdimdev.nnhackaton.R;


/**
 * Created by Shumkin Aleksey on 23.05.2017.
 */

public class CoinFlyOptions {
	private final int MAX_COIN_SIZE = 7;
	private final int DEFAULT_COIN_SIZE = 5;
	private float coinBoxY = 0f;
	private float totalViewY = 0f;
	private float totalViewX = 0f;
	private float coinBoxX = 0f;
	private int initialValue = 0;
	private int newValue = 0;
	private boolean enabled = false;

	public CoinFlyOptions(@NonNull View startView, @NonNull View destinationView, int coinType) {
		int[] location = new int[2];
		startView.getLocationOnScreen(location);
		coinBoxX = location[0];
		coinBoxY = location[1] - ((View) (startView.getParent().getParent())).getHeight() - startView.getHeight();

		View coin = destinationView.findViewById(coinType).findViewById(R.id.coreImage);
		totalViewX = coin.getX() + coin.getWidth() / 2;
		totalViewY = destinationView.getY();
	}

	public void setupTotalValues(int initialValue, int newValue) {
		this.initialValue = initialValue;
		this.newValue = newValue;
		if (this.initialValue != this.newValue) {
			enabled = true;
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int getNumberOfCoins() {
		int coins = Math.abs(newValue - initialValue);
		return (coins < MAX_COIN_SIZE) ? coins : DEFAULT_COIN_SIZE;
	}

	public boolean isAddMode() {
		return (newValue - initialValue) > 0 ? true : false;
	}


	public float getDestY() {

		return (isAddMode()) ? totalViewY : coinBoxY;
	}

	public float getDestX() {
		return (isAddMode()) ? totalViewX : coinBoxX;
	}


	public float getStartX() {
		return (isAddMode()) ? coinBoxX : totalViewX;
	}

	public float getStartY() {
		return (isAddMode()) ? coinBoxY : totalViewY;
	}

	public Bundle getFillBundle() {
		Bundle args = new Bundle();
		args.putFloat(FlyingCoinFragment.KEY_START_POSITION_X, getStartX());
		args.putFloat(FlyingCoinFragment.KEY_START_POSITION_Y, getStartY());
		args.putFloat(FlyingCoinFragment.KEY_DEST_POSITION_X, getDestX());
		args.putFloat(FlyingCoinFragment.KEY_DEST_POSITION_Y, getDestY());
		args.putInt(FlyingCoinFragment.KEY_IMAGE_RESOURCE_ID, R.drawable.g_coin_revolving);
		args.putFloat(FlyingCoinFragment.KEY_NUMBER_OF_COINS, getNumberOfCoins());
		args.putLong(FlyingCoinFragment.KEY_ANIMATION_DURATION, 200);
		return args;
	}
}
