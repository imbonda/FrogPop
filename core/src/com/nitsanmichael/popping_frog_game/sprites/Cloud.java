package com.nitsanmichael.popping_frog_game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;


/**
 * Created by MichaelBond on 9/28/2016.
 */
public class Cloud extends Sprite {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 boxBottomLeft;
    private Vector2 boxTopRight;
    private Texture cloudTexture;


    public Cloud(AssetController assetController) {
        this.cloudTexture = assetController.get(Assets.CLOUD);
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.boxBottomLeft = new Vector2(0, 0);
        this.boxTopRight = new Vector2(0, 0);
        setSize(this.cloudTexture.getWidth(), this.cloudTexture.getHeight());
    }

    public Cloud(AssetController assetController, Vector2 position, Vector2 velocity) {
        this(assetController);
        setPosition(position);
        setVelocity(velocity);
    }

    /**
     * The cloud can move only within the boundaries of the given box descriptions.
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

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getCenter() {
        Vector2 center = this.position.cpy();
        center.add(getWidth() / 2, getHeight() / 2);
        return center;
    }

    /**
     * Updates the cloud with regard to the time passed from the last call to 'update'.
     *
     * @param deltaTime The time passed from the last call.
     */
    public void update(float deltaTime) {
        this.velocity.scl(deltaTime);
        this.position.add(this.velocity);
        containInsideBox();
        this.velocity.scl(1 / deltaTime);
    }

    private void containInsideBox() {
        float boxWidth = this.boxTopRight.x - this.boxBottomLeft.x;
        // Contain "width-wise".
        if (this.position.x > this.boxTopRight.x) {
            this.position.add(-boxWidth - getWidth(), this.velocity.y);
        }
        else if (this.position.x + getWidth() < this.boxBottomLeft.x) {
            this.position.add(boxWidth + getWidth(), this.velocity.y);
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
     * This method is responsible for drawing the cloud properly.
     */
    @Override
    public void draw(Batch batch) {
        batch.draw(this.cloudTexture, this.position.x, this.position.y);
    }

}
