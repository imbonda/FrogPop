package com.nitsanmichael.frog_pop_game.screens;

import com.badlogic.gdx.Screen;
import com.nitsanmichael.frog_pop_game.FrogPop;

/**
 * Created by nitsa on 05-Oct-16.
 */
public class TransitionController {

    private static final float DEFAULT_FADE_TIME = 1f;

    private float fadeTime=1f;
    private FrogPop game;
    private Screen screen;

    public TransitionController(FrogPop game) {
        this.game = game;
        this.screen = null;
    }

    public void update(float deltaTime) {
        if (null != this.screen) {
            this.fadeTime-=deltaTime;
        }
        this.game.batch.setColor(1, 1 , 1, this.fadeTime);
        if (this.fadeTime <= 0.01f) {
            this.fadeTime = 1f;
            this.game.batch.setColor(1, 1 , 1, this.fadeTime);
            this.game.setScreen(this.screen);
            this.screen = null;
        }
    }

    public void setNextScreen(Screen nextScreen) {
        this.screen = nextScreen;
    }

}
