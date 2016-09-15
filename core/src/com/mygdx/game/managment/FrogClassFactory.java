package com.mygdx.game.managment;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.managment.metadata.FrogMetaData;
import com.mygdx.game.managment.metadata.LevelMetaData;
import com.mygdx.game.sprites.frogs.Frog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * This class is a singleton used for generating random frog classes by a variable requested -
 *  probability function.
 *
 * Created by MichaelBond on 9/8/2016.
 */
public class FrogClassFactory {

    private static FrogClassFactory ourInstance = new FrogClassFactory();

    /**
     * Singleton implementation.
     *
     * @return  The singleton object.
     */
    public static FrogClassFactory getInstance() {
        return ourInstance;
    }

    /**
     * Singleton private constructor.
     */
    private FrogClassFactory() {
        this.randomFrogClassGenerator = new RandomFrogClassGenerator(null, 0);
        this.levelMetaData = LevelMetaData.DEFAULT_METADATA;
        this.randomizedFrogClassesCounterMap = new HashMap<Class<? extends Frog>, Integer>();
    }

    private LevelMetaData levelMetaData;
    private HashMap<Class<? extends Frog>, Integer> randomizedFrogClassesCounterMap;

    private final Random random = new Random();

    private class PortionMap {
        public int portion;
        public FrogMetaData frogMetaData;
    }

    /**
     * A private class that is responsible of generating random frog classes.
     */
    private class RandomFrogClassGenerator {

        private Array<PortionMap> portionMaps;
        private int worldPortionsSum;

        public RandomFrogClassGenerator(Array<PortionMap> portionMaps, int probWorldPortionsSum) {
            this.portionMaps = portionMaps;
            this.worldPortionsSum = probWorldPortionsSum;
        }

        public Class<? extends Frog> random() {
            if (this.worldPortionsSum <= 0) {
                return null;
            }
            int randVal = FrogClassFactory.this.random.nextInt(this.worldPortionsSum);
            int portionsSum = 0;
            Iterator<PortionMap> portionMapIterator = portionMaps.iterator();
            while (portionMapIterator.hasNext()) {
                PortionMap portionMap = portionMapIterator.next();
                if (randVal < portionMap.portion + portionsSum) {
                    updateFrogGenerationProbability(portionMapIterator, portionMap);
                    return portionMap.frogMetaData.frogClass;
                }
                portionsSum += portionMap.portion;
            }
            return null;
        }

        /**
         * Updates the probability function by modifying the underlying probability world.
         *
         * @param portionMapIterator    A portion-map iterator to be used to remove the given -
         *                              portion from the underlying probability world if needed.
         * @param portionMap    The portion-map to be removed from the underlying probability -
         *                      world if needed.
         */
        private void updateFrogGenerationProbability(Iterator<PortionMap> portionMapIterator,PortionMap portionMap) {
            Class<? extends Frog> frogClass = portionMap.frogMetaData.frogClass;
            Integer frogsCreated = FrogClassFactory.this.randomizedFrogClassesCounterMap.get(frogClass);
            frogsCreated = (null == frogsCreated) ? 1 : frogsCreated + 1;
            // Update the map to know that a new frog is going to be generated.
            FrogClassFactory.this.randomizedFrogClassesCounterMap.put(frogClass, frogsCreated);
            if (portionMap.frogMetaData.isLimited &&
                        frogsCreated >= portionMap.frogMetaData.maxAllowed) {
                // The maximal number of frogs of this class has been reached,
                // update the probability mapping function.
                this.worldPortionsSum -= portionMap.portion;
                portionMapIterator.remove();
            }
        }
    }
    private RandomFrogClassGenerator randomFrogClassGenerator;

    /**
     * Sets the FrogFactory to be compliant with the given level-configuration.
     *
     * @param levelMetaData A level configuration to adapt to.
     */
    public void setLevelMetaData(LevelMetaData levelMetaData) {
        this.levelMetaData = levelMetaData;
        this.randomizedFrogClassesCounterMap.clear();
        setFrogGenerationAlgorithm();
    }

    /**
     * Sets the algorithm for generating frogs for the specific level.
     */
    private void setFrogGenerationAlgorithm() {
        int probWorldPortionsSum = 0;
        Array<PortionMap> portionMaps = new Array<PortionMap>();
        for (FrogMetaData frogMetaData : this.levelMetaData.levelRelatedFrogs) {
            Integer frogClassCounter = this.randomizedFrogClassesCounterMap.get(frogMetaData.frogClass);
            frogClassCounter = (null == frogClassCounter) ? 0 : frogClassCounter;
            if (!frogMetaData.isLimited || frogMetaData.maxAllowed > frogClassCounter) {
                float spawnProb = frogMetaData.spawnProb;
                PortionMap portionMap = new PortionMap();
                portionMap.portion = (int) Math.ceil(spawnProb * 100);
                portionMap.frogMetaData = frogMetaData;
                portionMaps.add(portionMap);
                probWorldPortionsSum += portionMap.portion;
            }
        }

        this.randomFrogClassGenerator = new RandomFrogClassGenerator(
                portionMaps, probWorldPortionsSum);
    }

    /**
     *  This method returns a random frog instance according to the probability function
     *     supplied in the xml config.
     *
     * @return  A random frog according to the probability function supplied in the xml config.
     */
    public Class<? extends Frog> getRandomFrogClass() {
        return this.randomFrogClassGenerator.random();
    }

}