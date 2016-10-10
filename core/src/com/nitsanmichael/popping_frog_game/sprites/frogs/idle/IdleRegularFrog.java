package com.nitsanmichael.popping_frog_game.sprites.frogs.idle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.nitsanmichael.popping_frog_game.animation.Animation;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;


/**
 * Created by MichaelBond on 10/1/2016.
 */
public class IdleRegularFrog extends IdleFrog {

    public enum AnimationType { TONGUE, WINK }

    private Animation animation;
    private Vector2 position;


    public IdleRegularFrog(AssetController assetController, AnimationType type, Vector2 position) {
        if (AnimationType.TONGUE == type) {
            this.animation = assetController.getAnimation(Assets.HERO_REGULAR_TONGUE_ANIMATION);
        }
        else {
            this.animation = assetController.getAnimation(Assets.HERO_REGULAR_WINK_ANIMATION);
        }
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
        batch.draw(
                    this.animation.getFrame(),          // Texture.
                    this.position.x, this.position.y,   // Position.
                    getWidth(), getHeight());           // Size.
    }
}
