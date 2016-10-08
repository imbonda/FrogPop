package com.mygdx.game.sprites;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.FrogPop;

import java.util.Random;

/**
 * Created by nitsa on 27-Sep-16.
 */
public class FrogsDeathGhostEffect extends Sprite{
    private Texture ghost=new Texture("ghost.png");
    private static final float Time_To_Death=1.0f;
    private Vector2 ghostPos;
    private float speed1 =1f;
    private float upTime=0;

    public FrogsDeathGhostEffect(Vector2 position) {
        super(new Texture("ghost.png"));
        ghostPos=new Vector2(position);
        //ghostPos.add(15,0);
        super.setPosition(position.x+15,position.y);
    }

    private void postition() {
        this.setAlpha(Time_To_Death-upTime);
        ghostPos.add(0,speed1);
        super.setPosition(super.getX(),super.getY()+speed1);
    }

    public void update(float deltaTime) {
        if (upTime < Time_To_Death) {
            upTime+=deltaTime;
            postition();
        }
    }

    public boolean isTimedUp() {
        return this.upTime >= Time_To_Death;
    }
}
