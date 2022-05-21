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


    public IdleMexicanFrog(AssetController assetController, Vector2 position) {
        this.animation = assetController.getAnimation(Assets.HERO_MEXICAN_ANIMATION);
        setPosition(position.x, position.y);
    }

    @Override
    public void act(float deltaTime) {
        this.animation.update(deltaTime);
        Texture frame = this.animation.getFrame();
        setSize(frame.getWidth(), frame.getHeight());
    }

    @Override
    public void setColor(float r,float g, float b, float a) {
        super.setColor(r, g, b, a);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color batchColor = batch.getColor();
        Color c = this.getColor();
        batch.setColor(c.r, c.g, c.b, c.a * batchColor.a);
        batch.draw(
                this.animation.getFrame(),
                getX(), getY(),
                getWidth(), getHeight());
        batch.setColor(batchColor.r, batchColor.g, batchColor.b, batchColor.a);
    }
}