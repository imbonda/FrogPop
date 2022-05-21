package com.nitsanmichael.popping_frog_game.scenes.idlefrogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nitsanmichael.popping_frog_game.animation.Animation;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;


/**
 * Created by MichaelBond on 10/1/2016.
 */
public class IdleFreezeFrog extends Actor {

    public enum AnimationType { NORMAL, BIG }

    private Animation animation;


    public IdleFreezeFrog(AssetController assetController, AnimationType type, Vector2 position) {
        this.animation = (AnimationType.NORMAL == type) ?
                (assetController.getAnimation(Assets.FREEZE_FROG_ANIMATION)) :
                (assetController.getAnimation(Assets.FREEZE_FROG_BIG_ANIMATION));
        setPosition(position.x, position.y);
    }

    @Override
    public void act(float deltaTime) {
        this.animation.update(deltaTime);
        Texture frame = this.animation.getFrame();
        setSize(frame.getWidth(), frame.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = this.getColor();
        this.setColor(c.r, c.g, c.b, parentAlpha);
        batch.draw(
                this.animation.getFrame(),
                getX(), getY(),
                getWidth(), getHeight());
    }
}
