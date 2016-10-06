package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.animation.Animation;
import com.mygdx.game.assets.AssetController;
import com.mygdx.game.assets.Assets;

/**
 * Created by MichaelBond on 10/5/2016.
 */
public class Bird extends Sprite {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 boxBottomLeft;
    private Vector2 boxTopRight;
    private Animation birdAnimation;


    public Bird(AssetController assetController) {
        this.birdAnimation = assetController.getAnimation(Assets.BIRD_ANIMATION);
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.boxBottomLeft = new Vector2(0, 0);
        this.boxTopRight = new Vector2(0, 0);
    }

    /**
     * The bird will always be contained inside the given box.
     */
    public void setBox(Vector2 bottomLeft, Vector2 topRight) {
        this.boxBottomLeft.set(bottomLeft);
        this.boxTopRight.set(topRight);
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    public void update(float deltaTime) {
        this.birdAnimation.update(deltaTime);
        Texture frame = this.birdAnimation.getFrame();
        setSize(frame.getWidth(), frame.getHeight());
        this.velocity.scl(deltaTime);
        this.position.add(this.velocity);
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
                    30,30,1,1,
                    frame.getWidth(), frame.getHeight(),
                    isFlipX,
                    false);
    }

}
