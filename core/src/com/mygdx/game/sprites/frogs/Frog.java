package com.mygdx.game.sprites.frogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import java.util.Random;


/**
 * This class represents a frog.
 * Created by MichaelBond on 8/25/2016.
 */
public class Frog implements Pool.Poolable {
    private Texture frogTexture[];
    private Vector2 position;
    private Rectangle frogRectangle;
    private double framekey;
    private int randtype;
    public float maxLifeTime;
    public float lifeTime;
    public boolean isKilled;
    private double dir=0.25;

    public Frog() {
        Random rand = new Random();
        this.frogTexture=new Texture[8];
        this.frogTexture[0] = new Texture("0.png");
        this.frogTexture[1] = new Texture("1.png");
        this.frogTexture[2] = new Texture("2.png");
        this.frogTexture[3] = new Texture("3.png");
        this.frogTexture[4] = new Texture("0.png");
        this.frogTexture[5] = new Texture("eye2.png");
        this.frogTexture[6] = new Texture("eye3.png");
        this.frogTexture[7] = new Texture("eye4.png");
        this.randtype=rand.nextInt(2);
        this.lifeTime = 0;
        this.isKilled = false;
        this.position = new Vector2(0, 0);
        framekey=0;
    }
    public void init(float positionX, float positionY, float timeToLive) {
        Random rand=new Random();
        this.position.set(positionX, positionY);
        this.frogRectangle = new Rectangle(
                this.position.x-20, this.position.y-35,
                    this.frogTexture[0].getWidth()+40, this.frogTexture[0].getHeight()+35);
        this.maxLifeTime = timeToLive;
        this.lifeTime = 0;
        this.randtype=rand.nextInt(2);
    }

    @Override
    public void reset() {
        Random rand = new Random();
        this.isKilled = false;
        this.position.set(0, 0);
        framekey=0;
        this.randtype=rand.nextInt(2);
    }

    public void update(float deltaTime) {
        this.lifeTime += deltaTime;
    }

    public Texture getFrogTexture() {
        if(randtype==0){
        if(framekey==0){
            dir=0.25;
        }
        if(framekey>3.7){
            dir=-0.25;
        }
        framekey+=dir;}
        if(randtype==1)
        {

                if(framekey==0||framekey==4){
                    framekey=4;
                    dir=0.25;
                }
                if(framekey>7.7){
                    dir=-0.25;
                }
                framekey+=dir;
                   }
        System.out.print((int)framekey);
        return this.frogTexture[(int)(framekey%8)];
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isLifeTimeExpired() {
        return this.lifeTime >= this.maxLifeTime;
    }
    public void setKilled() {
        isKilled=true;
    }

    public boolean isFrogTouched(Vector2 touchVector) {
        return this.frogRectangle.contains(touchVector.x,touchVector.y);
    }

    public void dispose() {
        this.frogTexture[(int)framekey%4].dispose();
    }
}
