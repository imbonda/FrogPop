package com.nitsanmichael.popping_frog_game.tweens.accessors;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import aurelienribon.tweenengine.TweenAccessor;


/**
 * Created by MichaelBond on 10/12/2016.
 */
public class LabelAccessor implements TweenAccessor<Label> {

    public static final int ALPHA_TYPE = 0;
    public static final int SCALE_TYPE = 1;


    @Override
    public int getValues(Label target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA_TYPE:
                returnValues[0] =target.getStyle().fontColor.a;
                return 1;
            case SCALE_TYPE:
                returnValues[0] = target.getFontScaleX();
                returnValues[1] = target.getFontScaleY();
                return 2;
            default:
                return -1;
        }
    }

    @Override
    public void setValues(Label target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA_TYPE:
                target.getStyle().fontColor.a = newValues[0];
                break;
            case SCALE_TYPE:
                target.setFontScale(newValues[0], newValues[1]);
                break;
            default:
                break;
        }
    }
}
