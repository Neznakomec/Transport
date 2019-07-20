package com.sdimdev.nnhackaton.presentation.view.game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.sdimdev.nnhackaton.R;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Администратор on 13.02.2017.
 */

public class CoinBox extends RelativeLayout implements ICoinBox{
    private static final String TAG = CoinBox.class.getSimpleName();

    /**
     * Image that represents the safe itself
     */
    private GifImageView coreImage;

    public CoinBox(Context context) {
        super(context);
    }

    public CoinBox(final Context context, AttributeSet attrs) {
        super(context, attrs);
        CoinBox coinBox = (CoinBox) LayoutInflater.from(context).inflate(R.layout.coin_box_layout, this);
        coreImage = (GifImageView) coinBox.findViewById(R.id.coreImage);
    }

    public CoinBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean setImage(int resourceId) {
        coreImage.setImageResource(resourceId);
        return true;
    }
}
