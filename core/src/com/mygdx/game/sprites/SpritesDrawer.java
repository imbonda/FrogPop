package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Created by MichaelBond on 9/10/2016.
 */
public class SpritesDrawer {

    private SpriteBatch batch;
    private Array<Sprite> sprites;


    public SpritesDrawer(SpriteBatch batch) {
        this.batch = batch;
    }

    public void addSprite(Sprite sprite) {
        this.sprites.add(sprite);
    }

    public void removeSprite(Sprite sprite) {
        this.sprites.removeValue(sprite, true);
    }

    public void drawSprites() {
        for (Sprite sprite : this.sprites) {
            sprite.draw(this.batch);
        }
    }
}
