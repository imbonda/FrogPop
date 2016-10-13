package com.nitsanmichael.popping_frog_game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.nitsanmichael.popping_frog_game.tweens.TweenController;


/**
 * An abstract class representing a screen that has the ability to fade (in, out).
 *
 * Created by MichaelBond on 10/9/2016.
 */
public abstract class FadingScreen implements Screen {

    private static final int TRANSPARENT_WHITE_COLOR = 0xffffff00;

    public Color screenColor;
    protected Batch batch;
    protected TweenController tweenController;


    public FadingScreen(Batch batch, TweenController tweenController) {
        this.screenColor = new Color(TRANSPARENT_WHITE_COLOR);
        this.batch = batch;
        this.batch.setColor(this.screenColor);
        this.tweenController = tweenController;
    }

    public void setScreenColor(float r, float g, float b, float a) {
        this.batch.setColor(r, g, b, a);
        this.screenColor.set(r, g, b, a);
    }

}
