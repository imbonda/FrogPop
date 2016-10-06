package com.mygdx.game.sprites.frogs.idle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.animation.Animation;
import com.mygdx.game.assets.AssetController;
import com.mygdx.game.assets.Assets;

/**
 * Created by MichaelBond on 10/1/2016.
 */
public class IdlePoisonFrog extends IdleFrog {

//    private final Texture tongueAnimationTextures [] = {
//            new Texture("Frog/0r.png"),
//            new Texture("Frog/1r.png"),
//            new Texture("Frog/2r.png"),
//
//    };


    private Animation animation;
    private Vector2 position;


    public IdlePoisonFrog(AssetController assetController, Vector2 position) {
        this.animation = assetController.getAnimation(Assets.EVIL_FROG_ANIMATION);
        this.position = position;
    }

    @Override
    public void update(float deltaTime) {
        this.animation.update(deltaTime);
        Texture frame = this.animation.getFrame();
        setSize(frame.getWidth(), frame.getHeight());
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(this.animation.getFrame(), this.position.x, this.position.y);
    }
}
