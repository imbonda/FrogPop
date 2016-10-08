package com.nitsanmichael.popping_frog_game.config.metadata;

import com.nitsanmichael.popping_frog_game.sprites.frogs.active.Frog;

/**
 * Created by MichaelBond on 9/8/2016.
 */
public class FrogMetaData {

    public Class<? extends Frog> frogClass;
    public float spawnProb;
    public boolean isLimitedTotal;
    public int maxAllowed;
    public boolean isLimitedParallel;
    public int maxParallel;


    public FrogMetaData(Class<? extends Frog> frogClass,
                            Float spawnProbability, Integer maxAllowed, Integer maxParallel) {
        this.frogClass = frogClass;
        // Set frog spawn probability.
        if (null == spawnProbability) {
            this.spawnProb = 0;
        }
        else {
            this.spawnProb = spawnProbability;
        }
        // Set number of frogs allowed in total.
        if (null == maxAllowed) {
            this.isLimitedTotal = false;
        }
        else {
            this.isLimitedTotal = true;
            this.maxAllowed = maxAllowed;
        }
        // Set number of frogs allowed in parallel.
        if (null == maxParallel) {
            this.isLimitedParallel = false;
        }
        else {
            this.isLimitedParallel = true;
            this.maxParallel = maxParallel;
        }
    }
}
