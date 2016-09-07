package com.mygdx.game.managment.metadata;

import com.mygdx.game.sprites.frogs.Frog;

/**
 * Created by MichaelBond on 9/8/2016.
 */
public class FrogMetaData {

    public Class<? extends Frog> frogClass;
    public float spawnProb;


    public FrogMetaData(Class<? extends Frog> frogClass, float spawnProbability) {
        this.frogClass = frogClass;
        this.spawnProb = spawnProbability;
    }
}
