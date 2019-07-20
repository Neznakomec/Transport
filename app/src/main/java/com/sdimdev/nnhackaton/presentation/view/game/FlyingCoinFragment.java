package com.sdimdev.nnhackaton.presentation.view.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.sdimdev.nnhackaton.R;

import pl.droidsonroids.gif.GifImageView;


public class FlyingCoinFragment extends Fragment {
    /**
     * Flying coin image resource id. Default one exists only for safety reasons.
     */
    private int imageResourceId = R.drawable.default_image;


    public static final String KEY_IMAGE_RESOURCE_ID = "image_resource_id";

    public static final String KEY_START_POSITION_X = "start_position_x";
    public static final String KEY_START_POSITION_Y = "start_position_y";
    public static final String KEY_DEST_POSITION_X = "dest_position_x";
    public static final String KEY_DEST_POSITION_Y = "dest_position_y";
    public static final String KEY_NUMBER_OF_COINS = "number_of_coins";

    public static final String KEY_ANIMATION_START_OFFSET = "animation_start_offset";
    public static final String KEY_ANIMATION_DURATION = "animation_duration";

    /**
     * Coordinates of coin departure and arrival. In out case it will be Safe
     * and total coin (distribution, time etc.) at the top of the screen
     */
    private float startPositionX;
    private float startPositionY;
    private float destPositionX;
    private float destPositionY;

    private long animationStartOffset;
    private long animationDuration;

    /**
     * determines how many coins will fly from treasure to total coin container
     */
    private int numberOfCoins;

    private static final int DEFAULT_NUMBER_OF_COINS = 5;
    private static final long DEFAULT_ANIMATION_START_OFFSET = 100;
    private static final long DEFAULT_ANIMATION_DURATION = 100;
    public FlyingCoinFragment() {
        // Required empty public constructor
    }

    public static FlyingCoinFragment newInstance(Bundle args) {
        FlyingCoinFragment fragment = new FlyingCoinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            startPositionX = getArguments().getFloat(KEY_START_POSITION_X, 0f);
            startPositionY = getArguments().getFloat(KEY_START_POSITION_Y, 0f);
            destPositionX = getArguments().getFloat(KEY_DEST_POSITION_X, 0f);
            destPositionY = getArguments().getFloat(KEY_DEST_POSITION_Y, 0f);
            imageResourceId = getArguments().getInt(KEY_IMAGE_RESOURCE_ID);
            animationStartOffset = getArguments().getLong(KEY_ANIMATION_START_OFFSET, DEFAULT_ANIMATION_START_OFFSET);
            animationDuration = getArguments().getLong(KEY_ANIMATION_DURATION, DEFAULT_ANIMATION_DURATION);
            numberOfCoins = getArguments().getInt(KEY_NUMBER_OF_COINS, DEFAULT_NUMBER_OF_COINS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View main = inflater.inflate(R.layout.fragment_flying_coin, container, false);
        final GifImageView image = (GifImageView) main.findViewById(R.id.img);
        image.setImageResource(imageResourceId);
        final TranslateAnimation translateAnimation = new TranslateAnimation(
                startPositionX,
                destPositionX,
                startPositionY,
                destPositionY
        );
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            int counter = 0;
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if (counter < numberOfCoins){
                    counter++;
                    image.startAnimation(translateAnimation);
                } else {
                    image.setX(destPositionX);
                    image.setY(destPositionY);
                    //Exit fragment when animation ends
                    getActivity().getSupportFragmentManager().beginTransaction().remove(FlyingCoinFragment.this).commit();
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        translateAnimation.setStartOffset(animationStartOffset);
        translateAnimation.setDuration(animationDuration);
        image.startAnimation(translateAnimation);
        return main;
    }
}
