package com.mygdx.game.sprites.frogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

import java.util.Random;


/**
 * This class represents an abstract frog.
 *
 * Created by MichaelBond on 8/25/2016.
 */
public abstract class Frog extends Sprite implements Pool.Poolable, Disposable {

    protected Vector2 position;
    protected Rectangle frogRectangle;
    protected float maxLifeTime;
    protected float lifeTime;
    protected boolean isKilled;


    public Frog() {
        this.lifeTime = 0;
        this.isKilled = false;
        this.position = new Vector2(0, 0);
    }

    /**
     * This method should be implemented by each sub-class.
     * It ensures it will be drawn
     * properly.
     */
    @Override
    public abstract void draw(Batch batch);

    /**
     * This method should be implemented by each sub-class.
     *
     * @return  The profit-value for killing this frog.
     */
    public abstract int getProfitValue();

    /**
     * This method should be implemented by each sub-class.
     *
     * @return  The penalty-value for failing to kill this frog.
     */
    public abstract int getPenaltyValue();

    /**
     * This method should be implemented by each sub-class.
     * This method applies the frog's special ability.
     */
    public abstract void applyAbility();

    /**
     * This method should be implemented by each sub-class.
     * Used to initialize the frog object after retrieving it from a Pool.
     *
     * @param positionX The x coordinate the the frog new positing.
     * @param positionY The y coordinate the the frog new positing.
     * @param timeToLive    The new time-to-live of the frog.
     */
    public abstract void init(float positionX, float positionY, float timeToLive);

    /**
     * This method should be implemented by each sub-class.
     * Used to reset the frog object after freeing it from a Pool.
     */
    @Override
    public abstract void reset();

    /**
     * This method should be implemented by each sub-class.
     * Used to dispose all the resources the frog has used, and should be disposed.
     */
    public abstract void dispose();

    public void defaultInit(float positionX, float positionY, float timeToLive) {
        this.position.set(positionX, positionY);
        this.maxLifeTime = timeToLive;
        this.lifeTime = 0;
    }

    public void defaultReset() {
        this.isKilled = false;
        this.position.set(0, 0);
    }

    public void update(float deltaTime) {
        this.lifeTime += deltaTime;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isLifeTimeExpired() {
        return this.lifeTime >= this.maxLifeTime;
    }

    public void setKilled() {
        this.isKilled = true;
    }

    public boolean isKilled() {
        return this.isKilled;
    }

    public boolean isFrogTouched(Vector2 touchVector) {
        return this.frogRectangle.contains(touchVector.x,touchVector.y);
    }
}
