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
public class HealthFrog extends Frog {

    private static final int FROG_SCORE_PROFIT_VALUE = 2;
    private static final int FROG_LIFE_PROFIT_VALUE = 1;
    private static final int FROG_LIFE_PENALTY_VALUE = -1;

    private final Texture frogTexture[] = {
            new Texture("Frog/0y.png"),
            new Texture("Frog/1y.png"),
            new Texture("Frog/2y.png"),
            new Texture("Frog/3y.png"),
            new Texture("Frog/0y.png"),
            new Texture("Frog/eye2y.png"),
            new Texture("Frog/eye3y.png"),
            new Texture("Frog/eye4y.png")
    };

    private double frameKey;
    private int randTextureType;
    private double dir = 0.15;


    public HealthFrog() {
        Random rand = new Random();
        this.randTextureType = rand.nextInt(1);
        this.frameKey = 0;
    }

    @Override
    public void update(float deltaTime) {
        this.lifeTime += deltaTime * LevelController.getInstance().getSpeed()*1.2f;
    }

    @Override
    public void dispose() {
        // TODO (check if we want this behavior).
        for (Texture texture : this.frogTexture) {
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
            Hud.getInstance().getScoreCounter().addScore(FROG_SCORE_PROFIT_VALUE);
            Hud.getInstance().getLifeCounter().addLife(FROG_LIFE_PROFIT_VALUE);
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
                0, 0, 100, 100-(int)(((FROG_MAX_LIFE_TIME - this.lifeTime)*100)/(FROG_MAX_LIFE_TIME)));
    }

    public Texture getFrogTexture() {
        if (this.randTextureType == 0){
            if (this.frameKey < 0.15){
                this.dir = 0.15;
            }
            if (this.frameKey > 3.7){
                dir = -0.15;
            }
            this.frameKey += dir;
        System.out.print(frameKey);}
        if (this.randTextureType == 1)
        {
            if (this.frameKey == 0 || this.frameKey == 4){
                this.frameKey = 4;
                this.dir = 0.15;
            }
            if(this.frameKey>7.7){
                dir = -0.15;
            }
            this.frameKey += dir;
        }

        return this.frogTexture[(int)(this.frameKey % 4)];
    }

}
