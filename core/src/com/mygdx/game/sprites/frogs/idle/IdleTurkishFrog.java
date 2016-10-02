package com.mygdx.game.sprites.frogs.idle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.animation.Animation;

/**
 * Created by MichaelBond on 10/1/2016.
 */
public class IdleTurkishFrog extends IdleFrog {

    public enum AnimationType { TONGUE, WINK }

    private final Texture tongueAnimationTextures [] = {
            new Texture("Frog/special/turk1.png"),
            new Texture("Frog/special/turk2.png"),
            new Texture("Frog/special/turk3.png"),
            new Texture("Frog/special/turk2.png"),
    };


    private Animation animation;
    private Vector2 position;


    public IdleTurkishFrog(AnimationType type, Vector2 position) {
        this.position = position;
        if (AnimationType.TONGUE == type) {
            this.animation = new Animation(tongueAnimationTextures,0.3f);
            setSize(tongueAnimationTextures[0].getWidth(), tongueAnimationTextures[0].getHeight());
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
