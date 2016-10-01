package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.Texture;


/**
 * Created by MichaelBond on 10/1/2016.
 */
public class Animation {

    private static final float DEFAULT_ANIMATION_FRAME_TIME = 0.12f;

    private int frameCount;
    private float maxFrameTime;
    private float currentFrameTime;
    private int currentFrameId;
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
    }

    public void update(float deltaTime) {
        this.currentFrameTime += deltaTime;
        if (this.currentFrameTime >= maxFrameTime) {
            this.currentFrameTime = 0;
            this.currentFrameId = (this.currentFrameId + 1) % this.frameCount;
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
