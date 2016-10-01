package com.mygdx.game.sprites.frogs.idle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.animation.Animation;

/**
 * Created by MichaelBond on 10/1/2016.
 */
public class IdleRegularFrog extends IdleFrog {

    public enum AnimationType { TONGUE, WINK }

    private final Texture tongueAnimationTextures [] = {
            new Texture("Frog/0.png"),
            new Texture("Frog/1.png"),
            new Texture("Frog/2.png"),
            new Texture("Frog/3.png"),
    };
    private final Texture winkAnimationTextures [] = {
            new Texture("Frog/0.png"),
            new Texture("Frog/eye2.png"),
            new Texture("Frog/eye3.png"),
            new Texture("Frog/eye4.png")
    };

    private Animation animation;
    private Vector2 position;


    public IdleRegularFrog(AnimationType type, Vector2 position) {
        this.position = position;
        if (AnimationType.TONGUE == type) {
            this.animation = new Animation(tongueAnimationTextures);
            setSize(tongueAnimationTextures[0].getWidth(), tongueAnimationTextures[0].getHeight());
        }
        else {
            this.animation = new Animation(winkAnimationTextures);
            setSize(winkAnimationTextures[0].getWidth(), winkAnimationTextures[0].getHeight());
        }
    }

    @Override
    public void update(float deltaTime) {
        this.animation.update(deltaTime);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(this.animation.getFrame(), this.position.x, this.position.y);
    }
}
