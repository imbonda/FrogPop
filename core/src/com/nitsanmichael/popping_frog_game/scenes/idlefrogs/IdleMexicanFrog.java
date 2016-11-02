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
public class IdleMexicanFrog extends Actor {

    private Animation animation;
    private Vector2 position;


    public IdleMexicanFrog(AssetController assetController, Vector2 position) {
        this.animation = assetController.getAnimation(Assets.HERO_MEXICAN_ANIMATION);
        this.position = position;
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
                this.position.x, this.position.y,
                getWidth(), getHeight());
    }
}
