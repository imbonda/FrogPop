package com.mygdx.game.scenes.panel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.assets.AssetController;
import com.mygdx.game.assets.Assets;

/**
 * Created by MichaelBond on 9/6/2016.
 */
public class Timer extends Actor {

    private static final float DEFAULT_COUNTDOWN_TIME = 6;
    private static final Vector2 DEFAULT_TIMER_TEXTURE_POSITION = new Vector2(15, 370);
    private BitmapFont levelUp;
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

    private Texture timerTexture;
    private Texture clockHandTexture;
    private Vector2 timerTexturePosition;
    private  float levelUpShowTime=0;
    private float timeToCountDown;
    private float timeLeftToCountDown;


    public Timer(AssetController assetController) {
        this.timerTexture = assetController.get(Assets.TIMER);
        this.clockHandTexture = assetController.get(Assets.TIMER_HAND);
        this.timerTexturePosition = DEFAULT_TIMER_TEXTURE_POSITION;
        this.timeToCountDown = DEFAULT_COUNTDOWN_TIME;
        this.timeLeftToCountDown = this.timeToCountDown;
        this.timerState = State.VIBRATING;
        this.stateTimer = 0;
        levelUp=new BitmapFont(Gdx.files.internal("font.fnt"));
        levelUp.getData().setScale(0.3f);
        levelUp.setColor(Color.FIREBRICK);

        // TODO (consider using animation for clock vibration on timeout)
//        Texture timerTexture = new Texture("CAnimation/timer.png");
//        Array<TextureRegion> frames = new Array<TextureRegion>();
//        for(int i = 0; i < 6; ++i) {
//            frames.add(new TextureRegion(timerTexture, i * 128, 0, 128, 128));
//        }
//        this.timerVibrate = new CAnimation(0.1f, frames);
//        frames.clear();
    }

    public void update(float deltaTime) {
        levelUpShowTime+=deltaTime;
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

        if(timeToCountDown>6){
        if(timeToCountDown-timeLeftToCountDown<1f){
        levelUp.draw(batch, "Level up ! ",320,440);//}
        }
    }}

    private TextureRegion getFrame() {
        return this.timerVibrate.getKeyFrame(this.stateTimer, true);
    }

    private float getClockHandRotation() {
        return (1 - (1 - this.timeLeftToCountDown / this.timeToCountDown)) * 360;
    }

}
