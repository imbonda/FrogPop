package com.mygdx.game.sprites.frogs.idle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by MichaelBond on 10/1/2016.
 */
public abstract class IdleFrog extends Sprite {

    public abstract void update(float deltaTime);

    @Override
    public abstract void draw(Batch batch);
}
