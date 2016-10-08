package com.nitsanmichael.popping_frog_game.animation;

import com.badlogic.gdx.graphics.Texture;


/**
 * Created by MichaelBond on 10/1/2016.
 */
public class CAnimation {

    private static final float DEFAULT_ANIMATION_FRAME_TIME = 0.07f;
    private static final int INFINITE_ANIMATION = -1;

    private int frameCount;
    private float maxFrameTime;
    private float currentFrameTime;
    private int currentFrameId;
    private int increment;
    private int limitCycles;
    private int cycleCount;
    private Texture frames [];


    public CAnimation(Texture textures[]) {
        this(textures, DEFAULT_ANIMATION_FRAME_TIME);
    }

    public CAnimation(Texture textures[], float frameTime) {
        this.frames = textures;
        this.maxFrameTime = frameTime;
        this.frameCount = textures.length;
        this.currentFrameId = 0;
        this.currentFrameTime = 0;
        this.increment = 1;
        this.cycleCount = 0;
        this.limitCycles = INFINITE_ANIMATION;
    }

    public CAnimation(Texture textures[], int limitCycles) {
        this(textures, DEFAULT_ANIMATION_FRAME_TIME, limitCycles);
    }

    public CAnimation(Texture textures[], float frameTime, int limitCycles) {
        this(textures, frameTime);
        this.limitCycles = limitCycles;
    }

    public void update(float deltaTime) {
        this.currentFrameTime += deltaTime;
        if (!isCompleted() && this.currentFrameTime >= maxFrameTime) {
            this.currentFrameTime = 0;
            this.currentFrameId = this.currentFrameId + this.increment;
            // In case went out of bound.
            if (this.currentFrameId == -1 || this.currentFrameId == this.frameCount) {
                this.increment = -this.increment;
                this.currentFrameId = this.currentFrameId + this.increment;
            }
            if (0 == this.currentFrameId) {
                this.cycleCount += 1;
            }
        }
    }

    public Texture getFrame() {
        return this.frames[currentFrameId];
    }

    public boolean isCompleted() {
        return this.limitCycles != INFINITE_ANIMATION && this.limitCycles == this.cycleCount;
    }

    public void reset() {
        this.currentFrameId = 0;
        this.currentFrameTime = 0;
    }
}
