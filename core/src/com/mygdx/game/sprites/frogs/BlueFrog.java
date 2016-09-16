package com.mygdx.game.sprites.frogs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.managment.LevelController;
import com.mygdx.game.scenes.Hud;

import java.util.Random;

/**
 * This class represents a regular-frog.
 * It has no special abilities, and it's profit and penalty values are the default ones:
 *  (+1 and -1 respectively).
 *
 * Created by MichaelBond on 9/8/2016.
 */
public class BlueFrog extends Frog {

    private static final int FROG_SCORE_PROFIT_VALUE = 1;
    private static final int FROG_LIFE_PENALTY_VALUE = -1;

    private Texture frogTexture[];
    private double frameKey;
    private int randTextureType;
    private double dir = 0.25;


    public BlueFrog() {
        Random rand = new Random();
        this.frogTexture=new Texture[8];
        this.frogTexture[0] = new Texture("Frog/0b.png");
        this.frogTexture[1] = new Texture("Frog/1b.png");
        this.frogTexture[2] = new Texture("Frog/2b.png");
        this.frogTexture[3] = new Texture("Frog/3b.png");
        this.frogTexture[4] = new Texture("Frog/0b.png");
        this.frogTexture[5] = new Texture("Frog/eye2b.png");
        this.frogTexture[6] = new Texture("Frog/eye3b.png");
        this.frogTexture[7] = new Texture("Frog/eye4b.png");
        this.randTextureType = rand.nextInt(2);
        this.frameKey = 0;
    }

    @Override
    public void dispose() {
        this.frogTexture[(int)this.frameKey%4].dispose();
    }

    @Override
    public void touched() {
        this.isKilled = true;
    }

    @Override
    public void onDeath() {
        if (isKilled()) {
            Hud.getInstance().getScoreCounter().addScore(FROG_SCORE_PROFIT_VALUE);
        }
        else {
            Hud.getInstance().getLifeCounter().addLife(FROG_LIFE_PENALTY_VALUE);
            Gdx.input.vibrate(500);
        }
    }

    @Override
    public void init(float positionX, float positionY) {
        super.defaultInit(positionX, positionY);
        Random rand=new Random();
        this.frogRectangle = new Rectangle(
                this.position.x-20, this.position.y-35,
                this.frogTexture[0].getWidth()+40, this.frogTexture[0].getHeight()+35);
        this.randTextureType = rand.nextInt(2);
        initAbility();
    }

    private void initAbility() {
        LevelController.getInstance().scaleSpeed(0.3f);
    }

    @Override
    public void reset() {
        super.defaultReset();
        Random rand = new Random();
        this.frameKey = 0;
        this.randTextureType = rand.nextInt(2);
        LevelController.getInstance().scaleSpeed(3.333333f);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(getFrogTexture(), this.position.x, this.position.y,
                0, 0, 100, 100-(int)(((FROG_MAX_LIFE_TIME - this.lifeTime)*100)/(FROG_MAX_LIFE_TIME)));
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
        /////////////////////System.out.print((int)this.frameKey);
        return this.frogTexture[(int)(this.frameKey % 8)];
    }

}
