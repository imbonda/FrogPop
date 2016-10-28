package com.nitsanmichael.popping_frog_game.tweens.accessors;

import com.nitsanmichael.popping_frog_game.sprites.frogs.active.Frog;

import aurelienribon.tweenengine.TweenAccessor;


/**
 * Created by MichaelBond on 10/28/2016.
 */
public class FrogAccessor implements TweenAccessor<Frog> {

    public static final int ALPHA_TYPE = 0;


    @Override
    public int getValues(Frog target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA_TYPE:
                returnValues[0] = target.getColor().a;
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public void setValues(Frog target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA_TYPE:
                target.setAlpha(newValues[0]);
                break;
            default:
                break;
        }
    }

}
