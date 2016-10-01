package com.mygdx.game.sprites.frogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by nitsa on 11-Sep-16.
 */
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.Batch;
    import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.animation.Animation;
import com.mygdx.game.runtime.RuntimeInfo;
import com.mygdx.game.scenes.Hud;

import java.util.Random;

    /**
     * This class represents a regular-frog.
     * It has no special abilities, and it's profit and penalty values are the default ones:
     *  (+1 and -1 respectively).
     *
     * Created by MichaelBond on 9/8/2016.
     */
    public class PoisonFrog extends Frog {

        private static final int FROG_SCORE_PROFIT_VALUE = -1;
        private static final int FROG_LIFE_PROFIT_VALUE = -1;
        private static final int FROG_LIFE_PENALTY_VALUE = 0;

        private final Texture poisonFrogAnimationTextures [] = {
                new Texture("Frog/0r.png"),
                new Texture("Frog/1r.png"),
                new Texture("Frog/2r.png"),
                new Texture("Frog/3r.png"),
                new Texture("Frog/0r.png"),
                new Texture("Frog/eye2r.png"),
                new Texture("Frog/eye3r.png"),
                new Texture("Frog/eye4r.png")
        };

        private Animation animation;


        public PoisonFrog() {
            this.animation = new Animation(poisonFrogAnimationTextures);
            setSize(poisonFrogAnimationTextures[0].getWidth(), poisonFrogAnimationTextures[0].getHeight());
        }

        @Override
        public void dispose() {
            // TODO (check if we want this behavior).
            for (Texture texture : this.poisonFrogAnimationTextures) {
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
                Hud.getInstance().getLifeCounter().addLife(FROG_LIFE_PROFIT_VALUE);
                Gdx.input.vibrate(new long[] { 0, 200, 200, 200}, -1);
            }
            else {
                Hud.getInstance().getScoreCounter().addScore(FROG_SCORE_PROFIT_VALUE);
                Hud.getInstance().getLifeCounter().addLife(FROG_LIFE_PENALTY_VALUE);
            }
        }

        @Override
        public void init(RuntimeInfo runtimeInfo, float positionX, float positionY) {
            super.defaultInit(runtimeInfo, positionX, positionY);
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
