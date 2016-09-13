package com.mygdx.game.managment.metadata;

import com.badlogic.gdx.utils.Array;

/**
 * Created by MichaelBond on 9/8/2016.
 */
public class LevelMetaData {

    public static LevelMetaData DEFAULT_METADATA = new LevelMetaData(0, null);

    public int id;
    public Array<FrogMetaData> levelRelatedFrogs;


    public LevelMetaData(int id, Array<FrogMetaData>levelRelatedFrogs) {
        this.id = id;
        this.levelRelatedFrogs = levelRelatedFrogs;
    }
}
