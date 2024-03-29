package com.nitsanmichael.popping_frog_game.sprites.frogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nitsanmichael.popping_frog_game.animation.Animation;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;

/**
 * This class represents a regular-frog.
 * It has no special abilities, and it's profit and penalty values are the default ones:
 *  (+1 and -1 respectively).
 *
 * Created by MichaelBond on 9/8/2016.
 */
public class EvilFrog extends Frog {

    private static final int FROG_SCORE_PENALTY_VALUE = -5;
    private static final int FROG_LIFE_PENALTY_VALUE = -1;

    private Animation animation;


    public EvilFrog() {
    }

    @Override
    public void init(AssetController assetController, RuntimeInfo runtimeInfo, Vector2 position) {
        super.defaultInit(assetController, runtimeInfo, position);
        setAnimation();
        this.frogRectangle = new Rectangle(
                this.position.x-20, this.position.y-35,
                getTexture().getWidth() + 40, getTexture().getHeight() + 35);
    }

    private void setAnimation() {
        this.animation = this.assetController.getAnimation(Assets.EVIL_FROG_ANIMATION);
        Texture frame = this.animation.getFrame();
        setTexture(frame);
    }

    @Override
    public void touched() {
        this.isKilled = true;
    }

    @Override
    public void onDeath() {
        if (isKilled()) {
            int lives = this.runtimeInfo.gameLives.get();
            this.runtimeInfo.gameLives.set(lives + FROG_LIFE_PENALTY_VALUE);
            int score = this.runtimeInfo.gameScore.get();
            this.runtimeInfo.gameScore.set(score + FROG_SCORE_PENALTY_VALUE);
            Gdx.input.vibrate(new long[] { 0, 200, 200, 200}, -1);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        updateAnimation(deltaTime);
    }

    private void updateAnimation(float deltaTime) {
        this.animation.update(deltaTime);
        Texture frame = this.animation.getFrame();
        setTexture(frame);
    }

    @Override
    public void draw(Batch batch) {
        float fromPeekFraction = Math.abs(FROG_MAX_LIFE_TIME / 2 - this.lifeTime) /
                (FROG_MAX_LIFE_TIME / 2);
        setPosition(this.position.x, this.position.y);
        setSize(getTexture().getWidth(), getTexture().getHeight() * (1 - fromPeekFraction));
        setRegion(0, 0, (int)getWidth(), (int)(getHeight()));
        super.draw(batch);
    }

    @Override
    public void reset() {
        super.defaultReset();
        this.animation.reset();
    }

}
