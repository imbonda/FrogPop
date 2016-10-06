package com.mygdx.game.sprites.frogs.active;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.animation.Animation;
import com.mygdx.game.assets.AssetController;
import com.mygdx.game.assets.Assets;
import com.mygdx.game.runtime.RuntimeInfo;

import java.util.Random;


/**
 * This class represents a regular-frog.
 * It has no special abilities, and it's profit and penalty values are the default ones:
 *  (+1 and -1 respectively).
 *
 * Created by MichaelBond on 9/8/2016.
 */
public class RegularFrog extends Frog {

    private static final int FROG_SCORE_PROFIT_VALUE = 1;
    private static final int FROG_LIFE_PENALTY_VALUE = -1;
    // Animations.
    private static final int TONGUE_ANIMATION = 0;
    private static final int WINK_ANIMATION = 1;


    private Animation animation;


    public RegularFrog() {
    }

    private void generateRandomAnimation() {
        Random rand = new Random();
        if (rand.nextInt(2) == TONGUE_ANIMATION) {
            this.animation = this.assetController.getAnimation(Assets.HERO_REGULAR_TONGUE_ANIMATION);
        }
        else {
            this.animation = this.assetController.getAnimation(Assets.HERO_REGULAR_WINK_ANIMATION);
        }
        Texture frame = this.animation.getFrame();
        setSize(frame.getWidth(), frame.getHeight());
    }

    @Override
    public void dispose() {
    }

    @Override
    public void touched() {
        this.isKilled = true;
    }

    @Override
    public void onDeath() {
        if (isKilled()) {
            this.runtimeInfo.gameScore += FROG_SCORE_PROFIT_VALUE;
        }
        else {
            this.runtimeInfo.gameLives += FROG_LIFE_PENALTY_VALUE;
            Gdx.input.vibrate(500);
        }
    }

    @Override
    public void init(AssetController assetController, RuntimeInfo runtimeInfo, float positionX, float positionY) {
        super.defaultInit(assetController, runtimeInfo, positionX, positionY);
        generateRandomAnimation();
        this.frogRectangle = new Rectangle(
                this.position.x-20, this.position.y-35,
                getWidth() + 40, getHeight() + 35);
    }

    @Override
    public void reset() {
        super.defaultReset();
        this.animation.reset();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.animation.update(deltaTime);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(getFrogTexture(), this.position.x, this.position.y,
                0, 0, (int)getWidth(), (int)getHeight()-(int)(((FROG_MAX_LIFE_TIME - this.lifeTime)*100)/(FROG_MAX_LIFE_TIME)));
    }

    public Texture getFrogTexture() {
        return this.animation.getFrame();
    }

}
