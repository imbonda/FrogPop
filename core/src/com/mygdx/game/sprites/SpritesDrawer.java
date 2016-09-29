package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

/**
 * Created by MichaelBond on 9/10/2016.
 */
public class SpritesDrawer {

    private static SpritesDrawer ourInstance = new SpritesDrawer();

    /**
     * Singleton implementation.
     *
     * @return  The singleton object.
     */
    public static SpritesDrawer getInstance() {
        return ourInstance;
    }

    /**
     * Singleton private constructor.
     */
    private SpritesDrawer() {
    }

    private final static Array<Sprite> sprites = new Array<Sprite>();


    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    public void addSprites(Array<? extends Sprite> sprites) {
        for (Sprite sprite : sprites) {
            addSprite(sprite);
        }
    }

    public void removeSprite(Sprite sprite) {
        sprites.removeValue(sprite, true);
    }

    public void clear() {
        sprites.clear();
    }

    public void drawSprites(Batch batch) {
        for (Sprite sprite : sprites) {
            sprite.draw(batch);
        }
    }
}