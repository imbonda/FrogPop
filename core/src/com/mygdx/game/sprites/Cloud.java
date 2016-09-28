package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.FrogPop;

/**
 * Created by MichaelBond on 9/28/2016.
 */
public class Cloud extends Sprite {

    private Vector2 position;
    private Vector2 velocity;
    private Texture cloudTexture;


    public Cloud() {
        this.cloudTexture = new Texture("cloud.png");
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
    }

    public Cloud(Vector2 position, Vector2 velocity) {
        this();
        setPosition(position);
        setVelocity(velocity);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getCenter() {
        Vector2 center = this.position.cpy();
        center.add(this.cloudTexture.getWidth() / 2, this.cloudTexture.getHeight() / 2);
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
        this.velocity.scl(1 / deltaTime);
        // Handle out of screen scenario.
        if (this.position.x > FrogPop.VIRTUAL_WIDTH) {
            this.position.add(-FrogPop.VIRTUAL_WIDTH - this.cloudTexture.getWidth(), 0);
        }
        else if (this.position.x + this.cloudTexture.getWidth() < 0) {
            this.position.add(FrogPop.VIRTUAL_WIDTH + this.cloudTexture.getWidth(), 0);
        }
    }

    /**
     * This method is responsible for drawing the hole properly.
     */
    @Override
    public void draw(Batch batch) {
        batch.draw(this.cloudTexture, this.position.x, this.position.y);
    }
}
