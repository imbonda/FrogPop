package com.nitsanmichael.popping_frog_game.scenes.panel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.tweens.TweenController;


/**
 * Created by MichaelBond on 9/6/2016.
 */
public class Timer extends Actor {

    private static final float DEFAULT_COUNTDOWN_TIME = 6;
    private static final Vector2 DEFAULT_TIMER_TEXTURE_POSITION = new Vector2(15, 370);

    private static final class TimerHandClockTextureMeta {
        public static int TEXTURE_SRC_X = 0;
        public static int TEXTURE_SRC_Y = 0;
        public static int TEXTURE_SRC_WIDTH = 20;
        public static int TEXTURE_SRC_HEIGHT = 74;
        public static float TEXTURE_SCALE_X = 0.3f;
        public static float TEXTURE_SCALE_Y = 0.8f;
        public static int TEXTURE_OFFSET_X = 0;
        public static int TEXTURE_OFFSET_Y = -3;
    }

    private float stateTimer;
    private Texture timerTexture;
    private Texture clockHandTexture;
    private Vector2 timerTexturePosition;
    private float timeToCountDown;
    private float timeLeftToCountDown;


    public Timer(AssetController assetController, TweenController tweenController) {
        this.timerTexture = assetController.get(Assets.TIMER);
        this.clockHandTexture = assetController.get(Assets.TIMER_HAND);
        this.timerTexturePosition = DEFAULT_TIMER_TEXTURE_POSITION;
        this.timeToCountDown = DEFAULT_COUNTDOWN_TIME;
        this.timeLeftToCountDown = this.timeToCountDown;
        this.stateTimer = 0;
    }

    public void update(float deltaTime) {
        if (this.timeLeftToCountDown > 0) {
            this.timeLeftToCountDown -= deltaTime;
        }
    }

    public boolean isTimedOut() {
        return this.timeLeftToCountDown <= 0;
    }

    public void setCountdownTime(float time) {
        this.timeToCountDown = time;
        this.timeLeftToCountDown = this.timeToCountDown;
    }

    public float getCountdownTime() {
        return this.timeToCountDown;
    }

    public void setTimeLeft(float time) {
        this.timeLeftToCountDown = time;
    }

    public float getTimeLeft() {
        return this.timeLeftToCountDown;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
                    this.timerTexture,
                    DEFAULT_TIMER_TEXTURE_POSITION.x,
                    DEFAULT_TIMER_TEXTURE_POSITION.y);
        batch.draw(
                    this.clockHandTexture,
                    this.timerTexturePosition.x + this.timerTexture.getWidth() / 2 +
                                TimerHandClockTextureMeta.TEXTURE_OFFSET_X,
                    this.timerTexturePosition.y + this.timerTexture.getHeight() / 2 +
                                TimerHandClockTextureMeta.TEXTURE_OFFSET_Y,
                    this.clockHandTexture.getWidth() / 2,
                    0,
                    this.clockHandTexture.getWidth(),
                    this.clockHandTexture.getHeight() / 2,
                    TimerHandClockTextureMeta.TEXTURE_SCALE_X,
                    TimerHandClockTextureMeta.TEXTURE_SCALE_Y,
                    getClockHandRotation(),
                    TimerHandClockTextureMeta.TEXTURE_SRC_X,
                    TimerHandClockTextureMeta.TEXTURE_SRC_Y,
                    TimerHandClockTextureMeta.TEXTURE_SRC_WIDTH,
                    TimerHandClockTextureMeta.TEXTURE_SRC_HEIGHT,
                    false, false);
    }

    private float getClockHandRotation() {
        return (1 - (1 - this.timeLeftToCountDown / this.timeToCountDown)) * 360;
    }

}
