package com.mygdx.game.sprites.frogs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.managment.LevelController;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.screens.PlayScreen;

import java.util.Random;

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

    private final Texture frogTexture[] = {
            new Texture("Frog/0p.png"),
            new Texture("Frog/1p.png"),
            new Texture("Frog/2p.png"),
            new Texture("Frog/3p.png"),
            new Texture("Frog/0p.png"),
            new Texture("Frog/eye2p.png"),
            new Texture("Frog/eye3p.png"),
            new Texture("Frog/eye4p.png")
    };

    private double frameKey;
    private int randTextureType;
    private double dir = 0.25;
    private int rotatedirectionB=-1;
    private int rotatedirectionA=-4;
    private int rotatedelay=0;
    private int rotationcontroller=0;
    private Vector3 deafultPosition=new Vector3(400,265,0);
    private Vector3 deafultaxis=new Vector3(0,0,100);

    public IllusionFrog() {
        Random rand = new Random();
        this.randTextureType = rand.nextInt(1);
        this.frameKey = 0;
    }

    @Override
    public void dispose() {
        // TODO (check if we want this behavior).
        for (Texture texture : this.frogTexture) {
            texture.dispose();
        }
    }

    private void whileUpAbility()
    {
        if((rotatedelay>16)&&((rotationcontroller>=16)||(rotationcontroller<=-16))){
            rotatedelay=0;
            this.rotatedirectionA=this.rotatedirectionB*rotatedirectionA;
        }
        rotationcontroller=rotatedirectionA+rotationcontroller;
        PlayScreen.gameViewPort.getCamera().rotateAround(deafultPosition,deafultaxis,rotatedirectionA);
        PlayScreen.gameViewPort.getCamera().update();

    }
    @Override
    public void update(float deltaTime) {
        this.lifeTime += deltaTime * LevelController.getInstance().getSpeed();
        if(rotatedelay%4==0){
        whileUpAbility();}
        rotatedelay++;
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
        this.randTextureType = rand.nextInt(1);
    }

    @Override
    public void reset() {
        super.defaultReset();
        PlayScreen.gameViewPort.getCamera().rotateAround(deafultPosition,deafultaxis,-rotationcontroller);
        PlayScreen.gameViewPort.getCamera().update();
        Random rand = new Random();
        this.frameKey = 0;
        this.randTextureType = rand.nextInt(1);
        rotationcontroller=0;
        rotatedirectionA=-4;
        rotatedelay=0;
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
        return this.frogTexture[(int)(this.frameKey % 8)];
    }

}
