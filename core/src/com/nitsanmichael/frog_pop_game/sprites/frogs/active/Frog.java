package com.nitsanmichael.frog_pop_game.sprites.frogs.active;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.nitsanmichael.frog_pop_game.runtime.RuntimeInfo;
import com.nitsanmichael.frog_pop_game.assets.AssetController;

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
     * @param positionX The x coordinate the the frog new positing.
     * @param positionY The y coordinate the the frog new positing.
     */
    public abstract void init(AssetController assetController, RuntimeInfo runtimeInfo,
                                float positionX, float positionY);

    /**
     * This method should be implemented by each sub-class.
     * Used to reset the frog object after freeing it from a Pool.
     */
    @Override
    public abstract void reset();

    public void defaultInit(AssetController assetController, RuntimeInfo runtimeInfo,
                                float positionX, float positionY) {
        this.assetController = assetController;
        this.runtimeInfo = runtimeInfo;
        this.position.set(positionX, positionY);
        this.lifeTime = 0;
    }

    public void defaultReset() {
        this.isKilled = false;
        this.position.set(0, 0);
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
    
	public Vector2 getPosition() {
		return position;
	}
}
