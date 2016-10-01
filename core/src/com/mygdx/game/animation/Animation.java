package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.Texture;


/**
 * Created by MichaelBond on 10/1/2016.
 */
public class Animation {

    private static final float DEFAULT_ANIMATION_FRAME_TIME = 0.07f;

    private int frameCount;
    private float maxFrameTime;
    private float currentFrameTime;
    private int currentFrameId;
    private int increment;
    private Texture frames [];


    public Animation(Texture textures[]) {
        this(textures, DEFAULT_ANIMATION_FRAME_TIME);
    }

    public Animation(Texture textures[], float frameTime) {
        this.frames = textures;
        this.maxFrameTime = frameTime;
        this.frameCount = textures.length;
        this.currentFrameId = 0;
        this.currentFrameTime = 0;
        this.increment = 1;
    }

    public void update(float deltaTime) {
        this.currentFrameTime += deltaTime;
        if (this.currentFrameTime >= maxFrameTime) {
            this.currentFrameTime = 0;
            this.currentFrameId = this.currentFrameId + this.increment;
            if (currentFrameId == -1 || currentFrameId == this.frameCount) {
                this.increment = -this.increment;
                this.currentFrameId = this.currentFrameId + this.increment;
            }
        }
    }

    public Texture getFrame() {
        return this.frames[currentFrameId];
    }

    public void reset() {
        this.currentFrameId = 0;
        this.currentFrameTime = 0;
    }
}
