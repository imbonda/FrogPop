package com.nitsanmichael.popping_frog_game.tweens.accessors;

import com.nitsanmichael.popping_frog_game.scenes.panel.Timer;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by MichaelBond on 10/28/2016.
 */
public class TimerAccessor implements TweenAccessor<Timer> {

    public static final int TIME_LEFT_TYPE = 0;


    @Override
    public int getValues(Timer target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case TIME_LEFT_TYPE:
                returnValues[0] = target.getTimeLeft();
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public void setValues(Timer target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case TIME_LEFT_TYPE:
                target.setTimeLeft(newValues[0]);
                break;
            default:
                break;
        }
    }
}
