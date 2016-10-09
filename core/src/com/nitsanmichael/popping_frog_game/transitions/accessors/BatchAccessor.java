package com.nitsanmichael.popping_frog_game.transitions.accessors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by MichaelBond on 10/9/2016.
 */
public class BatchAccessor implements TweenAccessor<SpriteBatch> {

    public static final int ALPHA_TYPE = 0;


    @Override
    public int getValues(SpriteBatch target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA_TYPE:
                returnValues[0] = target.getColor().a;
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public void setValues(SpriteBatch target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA_TYPE:
                Color oldColor = target.getColor();
                target.setColor(oldColor.r, oldColor.g, oldColor.b, newValues[0]);
                break;
            default:
                break;
        }
    }
}
