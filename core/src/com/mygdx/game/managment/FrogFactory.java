package com.mygdx.game.managment;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.managment.metadata.FrogMetaData;
import com.mygdx.game.managment.metadata.LevelMetaData;
import com.mygdx.game.sprites.frogs.Frog;

import java.util.Random;

/**
 * This class is a singleton used for generating random frog instances by a variable requested -
 *  probability function.
 *
 * Created by MichaelBond on 9/8/2016.
 */
public class FrogFactory {

    private static FrogFactory ourInstance = new FrogFactory();

    private final Random random = new Random();
    private LevelMetaData levelMetaData = LevelMetaData.DEFAULT_METADATA;

    private class RangeMap {
        public int minBound;
        public int maxBound;
        public Class<? extends Frog> frogClass;
    }

    private class RandomFrogClassGenerator {

        private Array<RangeMap> rangeMaps;
        private int worldLeftBound;
        private int worldRightBound;

        public RandomFrogClassGenerator(Array<RangeMap> rangeMaps,
                                    int worldLeftBound, int worldRightBound) {
            this.rangeMaps = rangeMaps;
            this.worldLeftBound = worldLeftBound;
            this.worldRightBound = worldRightBound;
        }

        public Class<? extends Frog> random() {
            int randVal = this.worldLeftBound +
                        FrogFactory.this.random.nextInt(this.worldRightBound);
            for (RangeMap rangeMap : this.rangeMaps) {
                if (randVal >= rangeMap.minBound && randVal <= rangeMap.maxBound) {
                    return rangeMap.frogClass;
                }
            }
            return null;
        }
    }
    private RandomFrogClassGenerator randomFrogClassGenerator;


    /**
     * Singleton implementation.
     *
     * @return  The singleton object.
     */
    public static FrogFactory getInstance() {
        return ourInstance;
    }

    /**
     * Singleton private constructor.
     */
    private FrogFactory() {
    }

    /**
     * Sets the FrogFactory to be compliant with the given level-configuration.
     *
     * @param levelMetaData A level configuration to adapt to.
     */
    public void setLevelMetaData(LevelMetaData levelMetaData) {
        this.levelMetaData = levelMetaData;
        setFrogGenerationAlgorithm();
    }

    /**
     * Sets the algorithm for generating frogs for the specific level.
     */
    private void setFrogGenerationAlgorithm() {
        int probWorldLeftBound = 0;
        Array<RangeMap> rangeMaps = new Array<RangeMap>();
        for (FrogMetaData frogMetaData : this.levelMetaData.levelRelatedFrogs) {
            float spawnProb = frogMetaData.spawnProb;
            RangeMap rangeMap = new RangeMap();
            rangeMap.minBound = probWorldLeftBound;
            rangeMap.maxBound = (int)Math.ceil(spawnProb * 100) + probWorldLeftBound;
            rangeMap.frogClass = frogMetaData.frogClass;
            rangeMaps.add(rangeMap);
            probWorldLeftBound += rangeMap.maxBound + 1;
        }

        this.randomFrogClassGenerator = new RandomFrogClassGenerator(
                    rangeMaps, 0, probWorldLeftBound);
    }

    /**
     *  This method returns a random frog instance according to the probability function
     *     supplied in the xml config.
     *
     * @return  A random frog according to the probability function supplied in the xml config.
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Frog getRandomFrog() throws IllegalAccessException, InstantiationException{
        return this.randomFrogClassGenerator.random().newInstance();
    }

}
