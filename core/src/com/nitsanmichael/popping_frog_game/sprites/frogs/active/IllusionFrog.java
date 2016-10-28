package com.nitsanmichael.popping_frog_game.sprites.frogs.active;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nitsanmichael.popping_frog_game.animation.Animation;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.sprites.Hole;

/**
 * This class represents a regular-frog.
 * It has no special abilities, and it's profit and penalty values are the default ones:
 *  (+1 and -1 respectively).
 *
 * Created by MichaelBond on 9/8/2016.
 */
public class IllusionFrog extends Frog {

    private static final int FROG_SCORE_PROFIT_VALUE = 3;
    private static final int FROG_LIFE_PENALTY_VALUE = -1;

    private Animation animation;


    public IllusionFrog() {
    }

    @Override
    public void init(AssetController assetController, RuntimeInfo runtimeInfo, Vector2 position) {
        super.defaultInit(assetController, runtimeInfo, position);
        setAnimation();
        this.frogRectangle = new Rectangle(
                this.position.x-20, this.position.y-35,
                getTexture().getWidth() + 40, getTexture().getHeight() + 35);
        for (Hole hole : this.runtimeInfo.holes) {
            hole.shuffleOn();
        }
    }

    private void setAnimation() {
        this.animation = this.assetController.getAnimation(Assets.ILLUSION_FROG_ANIMATION);
        Texture frame = this.animation.getFrame();
        setTexture(frame);
    }

    @Override
    public void touched() {
        this.isKilled = true;
    }

    @Override
    public void onDeath() {
        for (Hole hole : this.runtimeInfo.holes) {
            hole.shuffleOff(false);
        }
        if (isKilled()) {
            this.runtimeInfo.gameScore += FROG_SCORE_PROFIT_VALUE;
        }
        else {
            this.runtimeInfo.gameLives += FROG_LIFE_PENALTY_VALUE;
            Gdx.input.vibrate(500);
        }
    }

    @Override
    public void update(float deltaTime) {
        updateAnimation(deltaTime);
        this.lifeTime += deltaTime * this.runtimeInfo.gameSpeed;
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
