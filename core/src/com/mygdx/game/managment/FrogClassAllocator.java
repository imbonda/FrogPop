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
public class FrogClassAllocator {

    private static FrogClassAllocator ourInstance = new FrogClassAllocator();

    /**
     * Singleton implementation.
     *
     * @return  The singleton object.
     */
    public static FrogClassAllocator getInstance() {
        return ourInstance;
    }

    /**
     * Singleton private constructor.
     */
    private FrogClassAllocator() {
        this.randomFrogClassGenerator = new RandomFrogClassGenerator(null, 0);
        this.levelMetaData = LevelMetaData.DEFAULT_METADATA;
        this.frogClassesRuntimeInfo = new HashMap<Class<? extends Frog>, FrogClassRuntimeInfo>();
    }

    private class ProbPortion {
        public int portion;
        public FrogMetaData frogMetaData;
    }

    private class FrogClassRuntimeInfo {
        public int total;
        public int parallel;
        public ProbPortion probPortion;

        FrogClassRuntimeInfo () {
            this.total = 0;
            this.parallel = 0;
            this.probPortion = null;
        }
    }

    private LevelMetaData levelMetaData;
    private HashMap<Class<? extends Frog>, FrogClassRuntimeInfo> frogClassesRuntimeInfo;

    private final Random random = new Random();

    /**
     * A private class that is responsible of generating random frog classes.
     */
    private class RandomFrogClassGenerator {

        public Array<ProbPortion> probPortions;
        public int worldPortionsSum;

        public RandomFrogClassGenerator(Array<ProbPortion> probPortions, int probWorldPortionsSum) {
            this.probPortions = probPortions;
            this.worldPortionsSum = probWorldPortionsSum;
        }

        public Class<? extends Frog> random() {
            if (this.worldPortionsSum <= 0) {
                return null;
            }
            int randVal = FrogClassAllocator.this.random.nextInt(this.worldPortionsSum);
            int portionsSum = 0;
            FrogClassRuntimeInfo runtimeInfo = null;
            Iterator<ProbPortion> portionMapIterator = probPortions.iterator();
            while (portionMapIterator.hasNext()) {
                ProbPortion portionMap = portionMapIterator.next();
                if (randVal < portionMap.portion + portionsSum) {
                    Class<? extends Frog> frogClass = portionMap.frogMetaData.frogClass;
                    // Update the map to know that a new frog is going to be generated.
                    runtimeInfo = FrogClassAllocator.this.frogClassesRuntimeInfo.get(frogClass);
                    runtimeInfo.total += 1;
                    runtimeInfo.parallel += 1;
                    updateProbabilityPortions(portionMapIterator, portionMap);
                    return frogClass;
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
         * @param probPortion    The portion-map to be removed from the underlying probability -
         *                      world if needed.
         */
        private void updateProbabilityPortions(
                    Iterator<ProbPortion> portionMapIterator, ProbPortion probPortion) {
            Class<? extends Frog> frogClass = probPortion.frogMetaData.frogClass;
            FrogClassRuntimeInfo runtimeInfo = FrogClassAllocator.this.frogClassesRuntimeInfo.get(frogClass);
            if (probPortion.frogMetaData.isLimitedTotal &&
                        runtimeInfo.total >= probPortion.frogMetaData.maxAllowed) {
                // The maximal number of frogs of this class has been reached,
                // update the probability mapping function.
                this.worldPortionsSum -= probPortion.portion;
                portionMapIterator.remove();
            }
            if (probPortion.frogMetaData.isLimitedParallel &&
                    runtimeInfo.parallel >= probPortion.frogMetaData.maxParallel) {
                // The maximal number of frogs of this class has been reached,
                // update the probability mapping function.
                this.worldPortionsSum -= probPortion.portion;
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
        setFrogGenerationAlgorithm();
    }

    /**
     * Sets the algorithm for generating frogs for the specific level.
     */
    private void setFrogGenerationAlgorithm() {
        int probWorldPortionsSum = 0;
        Array<ProbPortion> probPortions = new Array<ProbPortion>();
        FrogClassRuntimeInfo runtimeInfo;
        for (FrogMetaData frogMetaData : this.levelMetaData.levelRelatedFrogs) {
            if (null == this.frogClassesRuntimeInfo.get(frogMetaData.frogClass)) {
                this.frogClassesRuntimeInfo.put(frogMetaData.frogClass, new FrogClassRuntimeInfo());
            }
            runtimeInfo = this.frogClassesRuntimeInfo.get(frogMetaData.frogClass);
            if (frogMetaData.isLimitedTotal &&
                        frogMetaData.maxAllowed <= runtimeInfo.total) {
                continue;
            }
            else if (frogMetaData.isLimitedParallel &&
                        frogMetaData.maxParallel <= runtimeInfo.parallel ) {
                continue;
            }
            float spawnProb = frogMetaData.spawnProb;
            ProbPortion probPortion = new ProbPortion();
            probPortion.portion = (int) Math.ceil(spawnProb * 100);
            probPortion.frogMetaData = frogMetaData;
            probPortions.add(probPortion);
            runtimeInfo.probPortion = probPortion;
            probWorldPortionsSum += probPortion.portion;
        }

        this.randomFrogClassGenerator = new RandomFrogClassGenerator(
                probPortions, probWorldPortionsSum);
    }

    /**
     *  This method returns a random frog instance according to the probability function
     *     supplied in the xml config.
     *
     * @return  A random frog according to the probability function supplied in the xml config.
     */
    public Class<? extends Frog> allocateRandomFrogClass() {
        return this.randomFrogClassGenerator.random();
    }

    public void deallocate(Class<? extends Frog> frogClass) {
        FrogClassRuntimeInfo runtimeInfo = this.frogClassesRuntimeInfo.get(frogClass);
        FrogMetaData frogMeta = runtimeInfo.probPortion.frogMetaData;
        runtimeInfo.parallel -= 1;
        boolean isSupportedFrogClass = false;
        for (FrogMetaData m : this.levelMetaData.levelRelatedFrogs) {
            if (m.frogClass == frogMeta.frogClass) {
                isSupportedFrogClass = true;
            }
        }
        if (isSupportedFrogClass &&
                    frogMeta.isLimitedParallel &&
                    runtimeInfo.parallel < frogMeta.maxParallel &&
                    (!frogMeta.isLimitedTotal || runtimeInfo.total < frogMeta.maxAllowed)) {
            this.randomFrogClassGenerator.probPortions.insert(0, runtimeInfo.probPortion);
            this.randomFrogClassGenerator.worldPortionsSum += runtimeInfo.probPortion.portion;
        }
    }

}
