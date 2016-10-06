package com.mygdx.game.sprites.frogs.active;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.animation.CAnimation;
import com.mygdx.game.assets.AssetController;
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

    private final Texture tongueAnimationTextures [] = {
            new Texture("Frog/0.png"),
            new Texture("Frog/1.png"),
            new Texture("Frog/2.png"),
            new Texture("Frog/3.png"),
    };
    private final Texture winkAnimationTextures [] = {
            new Texture("Frog/0.png"),
            new Texture("Frog/eye2.png"),
            new Texture("Frog/eye3.png"),
            new Texture("Frog/eye4.png")
    };
    private final Texture britishAnimationTextures [] = {
            new Texture("Frog/special/britain1.png"),
            new Texture("Frog/special/britain2.png"),
            new Texture("Frog/special/britain3.png"),
            new Texture("Frog/special/britain2.png"),
    };
    private final Texture turkAnimationTextures [] = {
            new Texture("Frog/special/turk1.png"),
            new Texture("Frog/special/turk2.png"),
            new Texture("Frog/special/turk3.png"),
            new Texture("Frog/special/turk2.png"),
    };
    private final Texture mexicanAnimationTextures [] = {
            new Texture("Frog/special/mex1.png"),
            new Texture("Frog/special/mex2.png"),
            new Texture("Frog/special/mex1.png"),
            new Texture("Frog/special/mex2.png"),
    };

    private CAnimation animation;


    public RegularFrog() {
    }

    private void generateRandomAnimation() {
        Random rand = new Random();
        if (rand.nextInt(2) == TONGUE_ANIMATION) {
            this.animation = new CAnimation(tongueAnimationTextures);
            setSize(tongueAnimationTextures[0].getWidth(), tongueAnimationTextures[0].getHeight());
        }
        else {
            this.animation = new CAnimation(winkAnimationTextures);
            setSize(winkAnimationTextures[0].getWidth(), winkAnimationTextures[0].getHeight());
        }
    }

    @Override
    public void dispose() {
        // TODO (check if we want this behavior).
        for (Texture texture : this.tongueAnimationTextures) {
            texture.dispose();
        }
        for (Texture texture : this.winkAnimationTextures) {
            texture.dispose();
        }
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
                0, 0, 100, 100-(int)(((FROG_MAX_LIFE_TIME - this.lifeTime)*100)/(FROG_MAX_LIFE_TIME)));
    }

    public Texture getFrogTexture() {
        return this.animation.getFrame();
    }

}
