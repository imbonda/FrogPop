package com.nitsanmichael.popping_frog_game.sprites.frogs.active;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;

/**
 * This class represents an abstract frog.
 *
 * Created by MichaelBond on 8/25/2016.
 */
public abstract class Frog extends Sprite implements Pool.Poolable {

    protected static final float FROG_MAX_LIFE_TIME = 3.0f;

    protected Vector2 position;
    protected Rectangle frogRectangle;
    protected float lifeTime;
    protected boolean isKilled;
    protected AssetController assetController;
    protected RuntimeInfo runtimeInfo;


    public Frog() {
        this.lifeTime = 0;
        this.isKilled = false;
        this.position = Vector2.Zero;
    }

    /**
     * This method should be implemented by each sub-class.
     * It method is called whenever a frog is considered dead, and should take care of all the -
     * underlying logic. (Updating the Hud, etc.)
     */
    public abstract void onDeath();

    /**
     * This method should be implemented by each sub-class.
     * This method is called whenever a frog is being touched by the user.
     */
    public abstract void touched();

    /**
     * This method should be implemented by each sub-class.
     * Used to initialize the frog object after retrieving it from a Pool.
     *
     * @param assetController   An asset controller instance for retrieving the loaded assets.
     * @param runtimeInfo The game runtime information.
     * @param position The position of the frog.
     */
    public abstract void init(AssetController assetController, RuntimeInfo runtimeInfo,
                                Vector2 position);

    /**
     * This method should be implemented by each sub-class.
     * Used to reset the frog object after freeing it from a Pool.
     */
    @Override
    public abstract void reset();

    public void defaultInit(AssetController assetController, RuntimeInfo runtimeInfo,
                                Vector2 position) {
        this.assetController = assetController;
        this.runtimeInfo = runtimeInfo;
        this.position = position;
        this.lifeTime = 0;
    }

    public void defaultReset() {
        this.isKilled = false;
        this.position = Vector2.Zero;
    }

    public void update(float deltaTime) {
        this.lifeTime += deltaTime * this.runtimeInfo.gameSpeed;
    }

    public boolean isLifeTimeExpired() {
        return this.lifeTime >= FROG_MAX_LIFE_TIME;
    }

    public boolean isKilled() {
        return this.isKilled;
    }

    public boolean isFrogTouched(Vector2 touchVector) {
        return this.frogRectangle.contains(touchVector.x,touchVector.y);
    }
    
	public Vector2 getCenterPosition() {
        float fromPeekFraction = Math.abs(FROG_MAX_LIFE_TIME / 2 - this.lifeTime) /
                (FROG_MAX_LIFE_TIME / 2);
        Vector2 center = this.position.cpy();
        center.add(0, (int)(getHeight() * (1 - fromPeekFraction)));
        return center;
	}
}
