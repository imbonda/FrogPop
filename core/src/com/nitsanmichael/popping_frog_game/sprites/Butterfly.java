package com.nitsanmichael.popping_frog_game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;

/**
 * Created by MichaelBond on 10/5/2016.
 */
public class Butterfly extends Sprite {

    public enum Color { ORANGE, PURPLE, RED }

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 boxBottomLeft;
    private Vector2 boxTopRight;
    private Texture butterflyTexture;


    private Butterfly() {
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.boxBottomLeft = new Vector2(0, 0);
        this.boxTopRight = new Vector2(0, 0);
    }

    public Butterfly(AssetController assetController, Color type) {
        this();
        switch (type) {
            case ORANGE:
                this.butterflyTexture = assetController.get(Assets.BUTTERFLY_ORANGE);
                setSize(this.butterflyTexture.getWidth(), this.butterflyTexture.getHeight());
                break;
            case PURPLE:
                this.butterflyTexture = assetController.get(Assets.BUTTERFLY_PURPLE);
                setSize(this.butterflyTexture.getWidth(), this.butterflyTexture.getHeight());
                break;
            case RED:
                this.butterflyTexture = assetController.get(Assets.BUTTERFLY_RED);
                setSize(this.butterflyTexture.getWidth(), this.butterflyTexture.getHeight());
                break;
            default:
                break;
        }
    }

    /**
     * The butterfly can move only within the boundaries of the given box descriptions.
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
        this.velocity.scl(deltaTime);
        this.position.add(this.velocity);
        containInsideBox();
        this.velocity.scl(1 / deltaTime);
    }

    private void containInsideBox() {
        float boxWidth = this.boxTopRight.x - this.boxBottomLeft.x;
        float boxHeight = this.boxTopRight.y - this.boxBottomLeft.y;
        // Contain "width-wise".
        if (this.position.x > this.boxTopRight.x) {
            this.position.add(-boxWidth - getWidth(), this.velocity.y);
        }
        else if (this.position.x + getWidth() < this.boxBottomLeft.x) {
            this.position.add(boxWidth + getWidth(), this.velocity.y);
        }
        // Contain "height-wise".
        if (this.position.y > this.boxTopRight.y) {
            this.position.add(this.velocity.x, -boxHeight - getHeight());
        }
        else if (this.position.y + getHeight() < this.boxBottomLeft.y) {
            this.position.add(this.velocity.x, boxHeight + getHeight());
        }
    }

    /**
     * This method is responsible for drawing the bird properly.
     */
    @Override
    public void draw(Batch batch) {
        batch.draw(
                this.butterflyTexture,
                this.position.x, this.position.y);
    }

}
