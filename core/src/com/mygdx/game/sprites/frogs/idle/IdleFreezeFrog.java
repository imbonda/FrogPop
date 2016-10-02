package com.mygdx.game.sprites.frogs.idle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.animation.Animation;

/**
 * Created by MichaelBond on 10/1/2016.
 */

public class IdleFreezeFrog extends IdleFrog {

    public enum AnimationType { normal, big }

    private final Texture freezeFrogAnimationTextures [] = {
            new Texture("Frog/0b.png"),
            new Texture("Frog/1b.png"),
            new Texture("Frog/2b.png"),
    };
    private final Texture freezeBigFrogAnimationTextures [] = {
            new Texture("Frog/0bigpol.png"),
            new Texture("Frog/1bigpol.png"),
            new Texture("Frog/2bigpol.png"),
    };

    private Animation animation;
    private Vector2 position;


    public IdleFreezeFrog(AnimationType type,Vector2 position) {
        this.position = position;
        if (AnimationType.normal == type) {
            this.animation = new Animation(freezeFrogAnimationTextures);
            setSize(freezeFrogAnimationTextures[0].getWidth(), freezeFrogAnimationTextures[0].getHeight());
        }
        else
        {
            this.animation = new Animation(freezeBigFrogAnimationTextures);
            setSize(freezeBigFrogAnimationTextures[0].getWidth(), freezeBigFrogAnimationTextures[0].getHeight());
        }

    }
    public IdleFreezeFrog(AnimationType type,Vector2 position,float witdh,float heigh) {
        this.position = position;
        if (AnimationType.normal == type) {
            this.animation = new Animation(freezeFrogAnimationTextures);
            setSize(witdh, heigh);
        }
        else
        {
            this.animation = new Animation(freezeBigFrogAnimationTextures);
            setSize(witdh,heigh);
        }
    }



    @Override
    public void update(float deltaTime) {
        this.animation.update(deltaTime);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(this.animation.getFrame(), this.position.x, this.position.y,this.getWidth(),getHeight());
    }
}
