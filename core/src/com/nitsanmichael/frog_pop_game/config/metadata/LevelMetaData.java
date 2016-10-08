package com.nitsanmichael.frog_pop_game.config.metadata;

import com.badlogic.gdx.utils.Array;

/**
 * Created by MichaelBond on 9/8/2016.
 */
public class LevelMetaData {

    public int id;
    public Array<FrogMetaData> levelRelatedFrogs;


    public LevelMetaData(int id, Array<FrogMetaData>levelRelatedFrogs) {
        this.id = id;
        this.levelRelatedFrogs = levelRelatedFrogs;
    }
}
