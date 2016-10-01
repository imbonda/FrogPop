package com.mygdx.game.sprites.frogs.idle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by MichaelBond on 10/1/2016.
 */
public class RegularFrog extends IdleFrog {

    public enum Type { TONGUE, WINK };

    private Vector2 position;


    public RegularFrog(Vector2 position) {
        this.position = position;
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void draw(Batch batch) {
    }
}
