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
import com.mygdx.game.scenes.Hud;

import java.util.Random;

    /**
     * This class represents a regular-frog.
     * It has no special abilities, and it's profit and penalty values are the default ones:
     *  (+1 and -1 respectively).
     *
     * Created by MichaelBond on 9/8/2016.
     */
    public class YellowFrog extends Frog {
        private static final int FROG_PROFIT_VALUE = 0;
        private static final int FROG_PENALTY_VALUE = 0;
        private Texture frogTexture[];
        private double frameKey;
        private int randTextureType;
        private double dir = 0.25;

        public YellowFrog() {
            Random rand = new Random();
            this.frogTexture=new Texture[8];
            this.frogTexture[0] = new Texture("Frog/0y.png");
            this.frogTexture[1] = new Texture("Frog/1y.png");
            this.frogTexture[2] = new Texture("Frog/2y.png");
            this.frogTexture[3] = new Texture("Frog/3y.png");
            this.frogTexture[4] = new Texture("Frog/0y.png");
            this.frogTexture[5] = new Texture("Frog/eye2y.png");
            this.frogTexture[6] = new Texture("Frog/eye3y.png");
            this.frogTexture[7] = new Texture("Frog/eye4y.png");
            this.randTextureType = rand.nextInt(2);
            this.frameKey = 0;
        }

        @Override
        public void dispose() {
            this.frogTexture[(int)this.frameKey%4].dispose();
        }

        @Override
        public void applyAbility() {
            Hud.getInstance().getLifeCounter().addLife(-1);
            Gdx.input.vibrate(new long[] { 0, 200, 200, 200}, -1);
        }
        public void initAbility()
        {
        }
        @Override
        public boolean isLifeTimeExpired() {
            if(this.lifeTime >= this.maxLifeTime) {
                return true;
            }
            else return false;
        }

        @Override
        public int getProfitValue() {
            return FROG_PROFIT_VALUE;
        }

        @Override
        public int getPenaltyValue() {
            return FROG_PENALTY_VALUE;
        }

        @Override
        public void init(float positionX, float positionY, float timeToLive) {
            super.defaultInit(positionX, positionY, timeToLive);
            Random rand=new Random();
            this.frogRectangle = new Rectangle(
                    this.position.x-20, this.position.y-35,
                    this.frogTexture[0].getWidth()+40, this.frogTexture[0].getHeight()+35);
            this.randTextureType = rand.nextInt(2);
        }

        @Override
        public void reset() {
            super.defaultReset();
            Random rand = new Random();
            this.frameKey = 0;
            this.randTextureType = rand.nextInt(2);
        }
        @Override
        public void draw(Batch batch) {
            batch.draw(getFrogTexture(), this.position.x, this.position.y,
                    0, 0, 100, 100-(int)(((this.maxLifeTime - this.lifeTime)*100)/(this.maxLifeTime)));
        }

        public Texture getFrogTexture() {
            if (this.randTextureType == 0){
                if (this.frameKey == 0){
                    this.dir = 0.25;
                }
                if (this.frameKey > 3.7){
                    dir = -0.25;
                }
                this.frameKey += dir;}
            if (this.randTextureType == 1)
            {
                if (this.frameKey == 0 || this.frameKey == 4){
                    this.frameKey = 4;
                    this.dir = 0.25;
                }
                if(this.frameKey>7.7){
                    dir = -0.25;
                }
                this.frameKey += dir;
            }

            return this.frogTexture[(int)(this.frameKey % 8)];
        }

    }
