package com.mygdx.game.sprites.frogs.active;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.animation.Animation;
import com.mygdx.game.assets.AssetController;
import com.mygdx.game.assets.Assets;
import com.mygdx.game.runtime.RuntimeInfo;

/**
 * This class represents a regular-frog.
 * It has no special abilities, and it's profit and penalty values are the default ones:
 *  (+1 and -1 respectively).
 *
 * Created by MichaelBond on 9/8/2016.
 */
public class HealthFrog extends Frog {

    private static final int FROG_SCORE_PROFIT_VALUE = 2;
    private static final int FROG_LIFE_PROFIT_VALUE = 1;

    private Animation animation;


    public HealthFrog() {
    }

    @Override
    public void init(AssetController assetController, RuntimeInfo runtimeInfo, float positionX, float positionY) {
        super.defaultInit(assetController, runtimeInfo, positionX, positionY);
        setAnimation();
        this.frogRectangle = new Rectangle(
                this.position.x-20, this.position.y-35,
                getWidth() + 40, getHeight() + 35);
    }

    private void setAnimation() {
        this.animation = this.assetController.getAnimation(Assets.HEALTH_FROG_ANIMATION);
        Texture frame = getFrogTexture();
        setSize(frame.getWidth(), frame.getHeight());
    }

    @Override
    public void update(float deltaTime) {
        this.animation.update(deltaTime);
        this.lifeTime += deltaTime * this.runtimeInfo.gameSpeed * 1.2f;
    }

    @Override
    public void touched() {
        this.isKilled = true;
    }

    @Override
    public void onDeath() {
        if (isKilled()) {
            this.runtimeInfo.gameScore += FROG_SCORE_PROFIT_VALUE;
            this.runtimeInfo.gameLives += FROG_LIFE_PROFIT_VALUE;
        }
        else {
            Gdx.input.vibrate(500);
        }
    }

    @Override
    public void reset() {
        super.defaultReset();
        this.animation.reset();
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(getFrogTexture(), this.position.x, this.position.y,
                    0, 0,
                    (int)getWidth(), (int)getHeight() -
                            (int)(((FROG_MAX_LIFE_TIME - this.lifeTime)*100)/(FROG_MAX_LIFE_TIME)));
    }

    private Texture getFrogTexture() {
        return this.animation.getFrame();
    }

}
