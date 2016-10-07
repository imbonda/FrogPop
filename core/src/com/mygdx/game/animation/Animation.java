package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

/**
 * Created by MichaelBond on 10/6/2016.
 */
public class Animation {

    private static final float DEFAULT_ANIMATION_FRAME_TIME = 0.07f;
    private static final int INFINITE_ANIMATION = -1;

    private int frameCount;
    private float maxFrameTime;
    private float currentFrameTime;
    private int currentFrameId;
    private int limitCycles;
    private int cycleCount;
    private Array<Texture> frames;


    public Animation(Array<Texture> frames) {
        this(frames, DEFAULT_ANIMATION_FRAME_TIME);
    }

    public Animation(Array<Texture> frames, float frameTime) {
        this.frames = frames;
        this.maxFrameTime = frameTime;
        this.frameCount = frames.size;
        this.currentFrameId = 0;
        this.currentFrameTime = 0;
        this.cycleCount = 0;
        this.limitCycles = INFINITE_ANIMATION;
    }

    public Animation(Array<Texture> frames, int limitCycles) {
        this(frames, DEFAULT_ANIMATION_FRAME_TIME, limitCycles);
    }

    public Animation(Array<Texture> frames, float frameTime, int limitCycles) {
        this(frames, frameTime);
        this.limitCycles = limitCycles;
    }

    public void update(float deltaTime) {
        this.currentFrameTime += deltaTime;
        if (!isCompleted() && this.currentFrameTime >= maxFrameTime) {
            this.currentFrameTime = 0;
            this.currentFrameId  = (this.currentFrameId + 1) % this.frameCount;
            if (0 == this.currentFrameId) {
                this.cycleCount += 1;
            }
        }
    }

    public Texture getFrame() {
        return this.frames.get(currentFrameId);
    }

    public boolean isCompleted() {
        return this.limitCycles != INFINITE_ANIMATION && this.limitCycles == this.cycleCount;
    }

    public void reset() {
        this.currentFrameId = 0;
        this.currentFrameTime = 0;
    }
}
