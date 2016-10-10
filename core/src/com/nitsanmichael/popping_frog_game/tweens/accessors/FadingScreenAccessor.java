package com.nitsanmichael.popping_frog_game.tweens.accessors;

import com.badlogic.gdx.graphics.Color;
import com.nitsanmichael.popping_frog_game.screens.FadingScreen;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by MichaelBond on 10/9/2016.
 */
public class FadingScreenAccessor implements TweenAccessor<FadingScreen> {

    public static final int ALPHA_TYPE = 0;


    @Override
    public int getValues(FadingScreen target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA_TYPE:
                returnValues[0] = target.screenColor.a;
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public void setValues(FadingScreen target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA_TYPE:
                Color oldColor = target.screenColor;
                target.setScreenColor(oldColor.r, oldColor.g, oldColor.b, newValues[0]);
                break;
            default:
                break;
        }
    }
}
