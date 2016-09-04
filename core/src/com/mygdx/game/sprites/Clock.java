package com.mygdx.game.sprites;

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
public class Clock {

    private Texture clockTexture;
    private Vector2 position;
    public float maxLifeTime;
    public float clockTime;
    public float timetoadd;
    public float rotationbugfixer=0;
    public float rotation;
//i got fucked up with level soconds finaly got something but messy
    public Clock() {
        timetoadd=6f;
        maxLifeTime=6f;
        this.clockTexture = new Texture("0c.png");
        this.clockTime = 0;
        this.position = new Vector2(0, 0);
    }

    //public void init(float positionX, float positionY, float timeToLive) {
     //  this.position.set(positionX, positionY);
        //this.frogRectangle = new Rectangle(
          //      this.position.x, this.position.y,
            //    this.frogTexture[0].getWidth(), this.frogTexture[0].getHeight()+35);
      //  this.maxLifeTime = timeToLive;
       // this.lifeTime = 0;
   // }

    public void update(float deltaTime) {
        rotationbugfixer += deltaTime;
        this.clockTime += deltaTime;
    }

    public Texture getFrogTexture() {
        //if(randtype==1)
        return this.clockTexture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getTimetoadd() {
        return timetoadd;
    }

    public float getClocltime() {
        return clockTime;
    }

    public boolean isLevelup() {

        return this.clockTime >= this.maxLifeTime;
    }

    public void clockUP() {
        timetoadd=timetoadd*1.05f;
        this.maxLifeTime=this.maxLifeTime+timetoadd;
        rotationbugfixer=0;
    }
    public float getRotation()
    {
        rotation=(this.timetoadd-this.rotationbugfixer)*360/(this.timetoadd);
        return rotation;
        //return (timetoadd)*360/this.timetoadd;
    }


    public void dispose() {
        this.clockTexture.dispose();
    }
}
