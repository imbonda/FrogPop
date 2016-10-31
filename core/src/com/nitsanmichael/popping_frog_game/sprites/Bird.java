package com.nitsanmichael.popping_frog_game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.nitsanmichael.popping_frog_game.animation.Animation;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;

/**
 * Created by MichaelBond on 10/5/2016.
 */
public class Bird extends Sprite {

    private static final Vector2 BIRD_SIZE = new Vector2(30, 30);

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 boxBottomLeft;
    private Vector2 boxTopRight;
    private Animation birdAnimation;
    private float horizonHeight;
    private RuntimeInfo runtimeInfo;


    public Bird(AssetController assetController, RuntimeInfo runtimeInfo, float horizonHeight) {
        this.runtimeInfo = runtimeInfo;
        this.horizonHeight = horizonHeight;
        this.birdAnimation = assetController.getAnimation(Assets.BIRD_ANIMATION);
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.boxBottomLeft = new Vector2(0, 0);
        this.boxTopRight = new Vector2(0, 0);
    }

    /**
     * The bird can move only within the boundaries of the given box descriptions.
     */
    private void setBox() {
        this.boxBottomLeft.set(this.runtimeInfo.screenInfo.getScreenBottomLeft().x, this.horizonHeight);
        this.boxTopRight.set(this.runtimeInfo.screenInfo.getScreenTopRight());
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    public void update(float deltaTime) {
        if (0 == deltaTime) {
            return;
        }
        this.birdAnimation.update(deltaTime);
        setSize(BIRD_SIZE.x, BIRD_SIZE.y);
        this.velocity.scl(deltaTime);
        this.position.add(this.velocity);
        setBox();
        containInsideBox();
        this.velocity.scl(1 / deltaTime);
    }

    private void containInsideBox() {
        // Contain "width-wise".
        if (this.position.x > this.boxTopRight.x) {
            this.velocity.set(-this.velocity.x, this.velocity.y);
            this.position.add(this.velocity);
        }
        else if (this.position.x + getWidth() < this.boxBottomLeft.x) {
            this.velocity.set(-this.velocity.x, this.velocity.y);
            this.position.add(this.velocity);
        }
        // Contain "height-wise".
        if (this.position.y > this.boxTopRight.y) {
            this.velocity.set(this.velocity.x, -this.velocity.y);
            this.position.add(this.velocity);
        }
        else if (this.position.y + getHeight() < this.boxBottomLeft.y) {
            this.velocity.set(this.velocity.x, -this.velocity.y);
            this.position.add(this.velocity);
        }
    }

    /**
     * This method is responsible for drawing the bird properly.
     */
    @Override
    public void draw(Batch batch) {
        Texture frame = this.birdAnimation.getFrame();
        boolean isFlipX = (this.velocity.x >= 0);
        batch.draw(
                    frame,
                    this.position.x, this.position.y,
                    getWidth(), getHeight(), 1, 1,
                    frame.getWidth(), frame.getHeight(),
                    isFlipX,
                    false);
    }

}
