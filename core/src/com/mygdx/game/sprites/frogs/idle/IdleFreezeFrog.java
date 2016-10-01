package com.mygdx.game.sprites.frogs.idle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.animation.Animation;

/**
 * Created by MichaelBond on 10/1/2016.
 */
public class IdleFreezeFrog extends IdleFrog {

    private final Texture freezeFrogAnimationTextures [] = {
            new Texture("Frog/0b.png"),
            new Texture("Frog/1b.png"),
            new Texture("Frog/2b.png"),
            new Texture("Frog/2b.png"),
    };

    private Animation animation;
    private Vector2 position;


    public IdleFreezeFrog(Vector2 position) {
        this.position = position;
        this.animation = new Animation(freezeFrogAnimationTextures);
        setSize(freezeFrogAnimationTextures[0].getWidth(), freezeFrogAnimationTextures[0].getHeight());
    }

    public IdleFreezeFrog(Vector2 position, float width, float height) {
        this(position);
        setSize(width, height);
    }

    @Override
    public void update(float deltaTime) {
        this.animation.update(deltaTime);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(
                this.animation.getFrame(),          // Texture.
                this.position.x, this.position.y,   // Position.
                getWidth(), getHeight());           // Size.
    }
}
