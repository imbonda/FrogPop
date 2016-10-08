package com.nitsanmichael.popping_frog_game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;


/**
 * Created by nitsa on 27-Sep-16.
 */
public class FrogGhost extends Sprite {

    private static final float SPEED = 50f;
    private static final float MAX_UP_TIME = 1.0f;
    private static final float POSITION_OFFSET_X = 15f;
    private static final float POSITION_OFFSET_Y = -25f;

    private float currentUptime;

    public FrogGhost(AssetController assetController, Vector2 position) {
        super((Texture)assetController.get(Assets.FROG_GHOST));
        this.currentUptime = 0;
        super.setPosition(position.x + POSITION_OFFSET_X, position.y + POSITION_OFFSET_Y);
    }

    private void updatePosition(float deltaTime) {
        this.setAlpha(MAX_UP_TIME - this.currentUptime);
        super.setPosition(super.getX(), super.getY() + SPEED * deltaTime);
    }

    public void update(float deltaTime) {
        if (this.currentUptime < MAX_UP_TIME) {
            this.currentUptime += deltaTime;
            updatePosition(deltaTime);
        }
    }

    public boolean isTimedUp() {
        return this.currentUptime >= MAX_UP_TIME;
    }
}
