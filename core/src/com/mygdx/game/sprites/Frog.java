package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;


/**
 * This class represents a frog.
 * Created by MichaelBond on 8/25/2016.
 */
public class Frog implements Pool.Poolable {

    private Texture frogTexture;
    private Vector2 position;
    private Rectangle frogRectangle;

    public float maxLifeTime;
    public float lifeTime;
    public boolean isKilled;


    public Frog() {
        this.frogTexture = new Texture("frog1.png");
        this.lifeTime = 0;
        this.isKilled = false;
        this.position = new Vector2(0, 0);
    }

    public void init(float positionX, float positionY, float timeToLive) {
        this.position.set(positionX, positionY);
        this.frogRectangle = new Rectangle(
                this.position.x, this.position.y,
                    this.frogTexture.getWidth(), this.frogTexture.getHeight()+35);
        this.maxLifeTime = timeToLive;
        this.lifeTime = 0;
    }

    @Override
    public void reset() {
        this.isKilled = false;
        this.position.set(0, 0);
    }

    public void update(float deltaTime) {
        this.lifeTime += deltaTime;
    }

    public Texture getFrogTexture() {
        return this.frogTexture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isLifeTimeExpired() {
        return this.lifeTime >= this.maxLifeTime;
    }

    public boolean isFrogTouched(Vector2 touchVector) {
        return this.frogRectangle.contains(touchVector.x,touchVector.y+35);
    }

    public void dispose() {
        this.frogTexture.dispose();
    }
}
