package com.mygdx.game.sprites.frogs.active;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.animation.Animation;
import com.mygdx.game.assets.AssetController;
import com.mygdx.game.assets.Assets;
import com.mygdx.game.runtime.RuntimeInfo;
import com.mygdx.game.screens.PlayScreen;

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
    private int rotatedirectionB=-1;
    private int rotatedirectionA=-4;
    private int rotatedelay=0;
    private int rotationcontroller=0;
    private Vector3 deafultPosition=new Vector3(400,265,0);
    private Vector3 deafultaxis=new Vector3(0,0,100);

    public IllusionFrog() {
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
        this.animation = this.assetController.getAnimation(Assets.ILLUSION_FROG_ANIMATION);
        Texture frame = this.animation.getFrame();
        setSize(frame.getWidth(), frame.getHeight());
    }

    @Override
    public void update(float deltaTime) {
        updateAnimation(deltaTime);
        this.lifeTime += deltaTime * this.runtimeInfo.gameSpeed;
        if(rotatedelay%4==0){
        whileUpAbility();}
        rotatedelay++;
    }

    private void updateAnimation(float deltaTime) {
        this.animation.update(deltaTime);
        Texture frame = this.animation.getFrame();
        setSize(frame.getWidth(), frame.getHeight());
    }

    private void whileUpAbility() {
        if((rotatedelay>16)&&((rotationcontroller>=16)||(rotationcontroller<=-16))) {
            rotatedelay=0;
            this.rotatedirectionA=this.rotatedirectionB*rotatedirectionA;
        }
        rotationcontroller=rotatedirectionA+rotationcontroller;
        PlayScreen.gameViewPort.getCamera().rotateAround(deafultPosition,deafultaxis,rotatedirectionA);
        PlayScreen.gameViewPort.getCamera().update();
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
    public void draw(Batch batch) {
        batch.draw(
                    this.animation.getFrame(),
                    this.position.x, this.position.y,
                    0, 0,
                    (int)getWidth(), (int)getHeight() -
                        (int)(((FROG_MAX_LIFE_TIME - this.lifeTime)*100)/(FROG_MAX_LIFE_TIME)));
    }

    @Override
    public void reset() {
        super.defaultReset();
        this.animation.reset();
        PlayScreen.gameViewPort.getCamera().rotateAround(deafultPosition,deafultaxis,-rotationcontroller);
        PlayScreen.gameViewPort.getCamera().update();
        rotationcontroller=0;
        rotatedirectionA=-4;
        rotatedelay=0;
    }

}
