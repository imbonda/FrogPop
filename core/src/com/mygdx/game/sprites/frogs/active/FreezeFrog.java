package com.mygdx.game.sprites.frogs.active;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.animation.Animation;
import com.mygdx.game.runtime.RuntimeInfo;

/**
 * This class represents a regular-frog.
 * It has no special abilities, and it's profit and penalty values are the default ones:
 *  (+1 and -1 respectively).
 *
 * Created by MichaelBond on 9/8/2016.
 */
public class FreezeFrog extends Frog {

    private static final int FROG_SCORE_PROFIT_VALUE = 1;
    private static final int FROG_LIFE_PENALTY_VALUE = -1;
    private static final float SLOW_DOWN_FACTOR = 0.3f;

    private final Texture freezeFrogAnimationTextures [] = {
        new Texture("Frog/0b.png"),
        new Texture("Frog/1b.png"),
        new Texture("Frog/2b.png"),
        new Texture("Frog/2b.png"),
    };

    private Animation animation;


    public FreezeFrog() {
        this.animation = new Animation(freezeFrogAnimationTextures);
        setSize(freezeFrogAnimationTextures[0].getWidth(), freezeFrogAnimationTextures[0].getHeight());
    }

    @Override
    public void dispose() {
        // TODO (check if we want this behavior).
        for (Texture texture : this.freezeFrogAnimationTextures) {
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
    public void init(RuntimeInfo runtimeInfo, float positionX, float positionY) {
        super.defaultInit(runtimeInfo, positionX, positionY);
        this.frogRectangle = new Rectangle(
                this.position.x-20, this.position.y-35,
                getWidth() + 40, getHeight() + 35);
        initAbility();
    }

    private void initAbility() {
        this.runtimeInfo.gameSpeed *= SLOW_DOWN_FACTOR;
    }

    @Override
    public void reset() {
        super.defaultReset();
        this.animation.reset();
        this.runtimeInfo.gameSpeed *= (1 / SLOW_DOWN_FACTOR);
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
