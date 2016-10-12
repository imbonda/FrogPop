package com.nitsanmichael.popping_frog_game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;

import java.util.Random;


/**
 * Created by MichaelBond on 8/25/2016.
 */
public class Hole extends Sprite {

    private static final Vector2 SHUFFLE_ON_SPEED = new Vector2(30f, 15f);
    private static final Vector2 SHUFFLE_OFF_SPEED = new Vector2(60f, 60f);
    private static final Vector2 FROG_POSITION_OFFSET = new Vector2(55, 20);

    private enum State { STATIC, SHUFFLE_ON, SHUFFLE_OFF }

    private State state;
    private Random random;
    private Vector2 origin;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 boxBottomLeft;
    private Vector2 boxTopRight;
    private Vector2 frogPlacementPosition;
    private int shuffleRequestCounter;
    private Texture holeTexture;


    public Hole(AssetController assetController, float xCord, float yCord) {
        this.holeTexture = assetController.get(Assets.HOLE);
        this.random = new Random();
        this.state = State.STATIC;
        this.position = new Vector2(xCord, yCord);
        this.origin = new Vector2(this.position);
        this.velocity = new Vector2(0 ,0);
        this.boxBottomLeft = new Vector2(0 ,0);
        this.boxTopRight = new Vector2(0 ,0);
        this.frogPlacementPosition = new Vector2(0, 0);
        this.shuffleRequestCounter = 0;
        updateFrogPlacementPosition();
        setSize(this.holeTexture.getWidth(), this.holeTexture.getHeight());
    }

    private void updateFrogPlacementPosition() {
        this.frogPlacementPosition.set(
                this.position.x + FROG_POSITION_OFFSET.x,
                this.position.y + FROG_POSITION_OFFSET.y);
    }

    public Vector2 getFrogPlacementPosition() {
        return this.frogPlacementPosition;
    }

    /**
     * The hole can move only within the boundaries of the given box descriptions.
     */
    public void setBox(Vector2 bottomLeft, Vector2 topRight) {
        this.boxBottomLeft.set(bottomLeft);
        this.boxTopRight.set(topRight);
    }

    public void shuffleOn() {
        if (State.SHUFFLE_ON != this.state) {
            int directionX = (this.random.nextInt(2) == 0) ? (-1) : 1;
            int directionY = (this.random.nextInt(2) == 0) ? (-1) : 1;
            this.velocity.set(directionX * SHUFFLE_ON_SPEED.x, directionY * SHUFFLE_ON_SPEED.y);
            this.state = State.SHUFFLE_ON;
        }
        this.shuffleRequestCounter += 1;
    }

    public void shuffleOff() {
        this.shuffleRequestCounter -= 1;
        if (State.SHUFFLE_ON == this.state && 0 == this.shuffleRequestCounter) {
            Vector2 toOrigin = new Vector2(
                    this.origin.x - this.position.x,
                    this.origin.y - this.position.y).nor();
            this.velocity.set(toOrigin.x * SHUFFLE_OFF_SPEED.x, toOrigin.y * SHUFFLE_OFF_SPEED.y);
            this.state = State.SHUFFLE_OFF;
        }
    }

    public void update(float deltaTime) {
        this.velocity.scl(deltaTime);
        switch (this.state) {
            case STATIC:
                break;
            case SHUFFLE_ON:
                this.position.add(this.velocity);
                containInsideBox();
                updateFrogPlacementPosition();
                break;
            case SHUFFLE_OFF:
                this.velocity.clamp(0, this.position.dst(this.origin));
                this.position.add(this.velocity);
                updateFrogPlacementPosition();
                if (this.position.equals(this.origin)) {
                    this.velocity.set(0, 0);
                    this.state = State.STATIC;
                }
                break;
            default:
                break;
        }
        this.velocity.scl(1 / deltaTime);
    }

    private void containInsideBox() {
        // Contain "width-wise".
        if (this.position.x + getWidth() > this.boxTopRight.x) {
            this.velocity.set(-this.velocity.x, this.velocity.y);
            this.position.add(this.velocity);
        }
        else if (this.position.x < this.boxBottomLeft.x) {
            this.velocity.set(-this.velocity.x, this.velocity.y);
            this.position.add(this.velocity);
        }
        // Contain "height-wise".
        if (this.position.y + getHeight() > this.boxTopRight.y) {
            this.velocity.set(this.velocity.x, -this.velocity.y);
            this.position.add(this.velocity);
        }
        else if (this.position.y < this.boxBottomLeft.y) {
            this.velocity.set(this.velocity.x, -this.velocity.y);
            this.position.add(this.velocity);
        }
    }

    /**
     * This method is responsible for drawing the hole properly.
     */
    @Override
    public void draw(Batch batch) {
        batch.draw(this.holeTexture, this.position.x, this.position.y);
    }

}
