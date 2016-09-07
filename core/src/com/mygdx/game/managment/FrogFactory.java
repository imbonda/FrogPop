package com.mygdx.game.managment;

import com.mygdx.game.managment.metadata.LevelMetaData;
import com.mygdx.game.sprites.frogs.Frog;

/**
 * Created by MichaelBond on 9/8/2016.
 */
public class FrogFactory {

    private static FrogFactory ourInstance = new FrogFactory();
    private LevelMetaData levelMetaData = LevelMetaData.DEFAULT_METADATA;


    public static FrogFactory getInstance() {
        return ourInstance;
    }

    /**
     * Singleton private constructor.
     */
    private FrogFactory() {
    }

    public void setLevelMetaData(LevelMetaData levelMetaData) {
        this.levelMetaData = levelMetaData;
    }

    public Frog getRandomFrog() throws IllegalAccessException, InstantiationException{
        //.
        //for (this.levelMetaData.levelRelatedFrogs)
        // TODO (this is just for test)
        return levelMetaData.levelRelatedFrogs.random().frogClass.newInstance();
    }
}
