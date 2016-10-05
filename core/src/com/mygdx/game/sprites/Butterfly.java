package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.animation.Animation;

/**
 * Created by MichaelBond on 10/5/2016.
 */
public class Butterfly extends Sprite {

    public enum Color { ORANGE, PURPLE, RED }

    private static final String ORANGE_BUTTERFLY_TEXTURE_NAME = "butter.png";
    private static final String PURPLE_BUTTERFLY_TEXTURE_NAME = "butter2.png";
    private static final String RED_BUTTERFLY_TEXTURE_NAME = "Butter3.png";

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

    public Butterfly(Color type) {
        this();
        switch (type) {
            case ORANGE:
                this.butterflyTexture = new Texture(ORANGE_BUTTERFLY_TEXTURE_NAME);
                setSize(this.butterflyTexture.getWidth(), this.butterflyTexture.getHeight());
                break;
            case PURPLE:
                this.butterflyTexture = new Texture(PURPLE_BUTTERFLY_TEXTURE_NAME);
                setSize(this.butterflyTexture.getWidth(), this.butterflyTexture.getHeight());
                break;
            case RED:
                this.butterflyTexture = new Texture(RED_BUTTERFLY_TEXTURE_NAME);
                setSize(this.butterflyTexture.getWidth(), this.butterflyTexture.getHeight());
                break;
            default:
                break;
        }
    }

    /**
     * The butterfly will always be contained inside the given box.
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
