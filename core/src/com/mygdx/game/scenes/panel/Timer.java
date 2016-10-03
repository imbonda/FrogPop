package com.mygdx.game.scenes.panel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

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

    private enum State {STATIC, VIBRATING}
    private State timerState;
    private Animation timerVibrate;
    private float stateTimer;

    private TextureRegion timerTexture;
    private TextureRegion clockHandTexture;
    private Vector2 timerTexturePosition;

    private float timeToCountDown;
    private float timeLeftToCountDown;


    public Timer() {
        this.timerTexture = new TextureRegion(new Texture("timer.png"));
        this.clockHandTexture = new TextureRegion(new Texture("0c.png"));
        this.timerTexturePosition = DEFAULT_TIMER_TEXTURE_POSITION;
        this.timeToCountDown = DEFAULT_COUNTDOWN_TIME;
        this.timeLeftToCountDown = this.timeToCountDown;
        this.timerState = State.VIBRATING;
        this.stateTimer = 0;

        // TODO (consider using animation for clock vibration on timeout)
//        Texture timerTexture = new Texture("Animation/timer.png");
//        Array<TextureRegion> frames = new Array<TextureRegion>();
//        for(int i = 0; i < 6; ++i) {
//            frames.add(new TextureRegion(timerTexture, i * 128, 0, 128, 128));
//        }
//        this.timerVibrate = new Animation(0.1f, frames);
//        frames.clear();
    }

    public void update(float deltaTime) {
        if (this.timeLeftToCountDown > 0) {
            this.timeLeftToCountDown -= deltaTime;
        }
    }

    public boolean isTimedOut() {
        return this.timeLeftToCountDown <= 0;
    }

    public void setCountTimeByFactor(float scalingFactor) {
        this.timeToCountDown *= scalingFactor;
        this.timeLeftToCountDown = this.timeToCountDown;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
                    //getFrame(),
                    this.timerTexture,
                    DEFAULT_TIMER_TEXTURE_POSITION.x,
                    DEFAULT_TIMER_TEXTURE_POSITION.y);
        batch.draw(this.clockHandTexture.getTexture(),
                    this.timerTexturePosition.x + this.timerTexture.getRegionWidth() / 2 +
                                TimerHandClockTextureMeta.TEXTURE_OFFSET_X,
                    this.timerTexturePosition.y + this.timerTexture.getRegionHeight() / 2 +
                                TimerHandClockTextureMeta.TEXTURE_OFFSET_Y,
                    this.clockHandTexture.getRegionWidth() / 2,
                    0,
                    this.clockHandTexture.getRegionWidth(),
                    this.clockHandTexture.getRegionHeight() / 2,
                    TimerHandClockTextureMeta.TEXTURE_SCALE_X,
                    TimerHandClockTextureMeta.TEXTURE_SCALE_Y,
                    getClockHandRotation(),
                    TimerHandClockTextureMeta.TEXTURE_SRC_X,
                    TimerHandClockTextureMeta.TEXTURE_SRC_Y,
                    TimerHandClockTextureMeta.TEXTURE_SRC_WIDTH,
                    TimerHandClockTextureMeta.TEXTURE_SRC_HEIGHT,
                    false, false);
          //if((1 - (1 - this.timeLeftToCountDown / this.timeToCountDown)) * 360>310) {
          //    batch.setColor(1f,1f,1f,1-((1 - (1 - this.timeLeftToCountDown / this.timeToCountDown)) * 360f)/360f);}
        // else
      //     {batch.setColor(1f,1f,1f,1f);}
//        if((1 - (1 - this.timeLeftToCountDown / this.timeToCountDown)) * 360<50) {
//        batch.setColor(1f,1f,1f,((1 - (1 - this.timeLeftToCountDown / this.timeToCountDown)) * 360f)/50f);}
//        else
//        {batch.setColor(1f,1f,1f,1f);}
    }

    private TextureRegion getFrame() {
        return this.timerVibrate.getKeyFrame(this.stateTimer, true);
    }

    private float getClockHandRotation() {
        return (1 - (1 - this.timeLeftToCountDown / this.timeToCountDown)) * 360;
    }

}
