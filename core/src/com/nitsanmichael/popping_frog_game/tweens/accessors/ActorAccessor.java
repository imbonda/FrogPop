package com.nitsanmichael.popping_frog_game.tweens.accessors;

import com.badlogic.gdx.scenes.scene2d.Actor;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by MichaelBond on 11/6/2016.
 */
public class ActorAccessor implements TweenAccessor<Actor> {

    public static final int ALPHA_TYPE = 0;


    @Override
    public int getValues(Actor target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA_TYPE:
                returnValues[0] = target.getColor().a;
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public void setValues(Actor target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA_TYPE:
                target.getColor().a = newValues[0];
                break;
            default:
                break;
        }
    }
}
