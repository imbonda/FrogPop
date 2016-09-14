package com.mygdx.game.managment;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.sprites.frogs.Frog;

import java.util.HashMap;

/**
 * This class represents a pool of frogs.
 * It is used in order to assist the garbage-collector, because using it reduces drastically -
 * the number of objects created during the application runtime.
 *
 * Created by MichaelBond on 9/10/2016.
 */
public class FrogPool {

    private Array<Frog> allFrogs;
    private HashMap<Class<? extends Frog>, Array<Integer>> classToFreeInstancesIndexesMap;


    public FrogPool() {
        this.allFrogs = new Array<Frog>();
        this.classToFreeInstancesIndexesMap = new HashMap<Class<? extends Frog>, Array<Integer>>();
    }

    /**
     * Specifies the logic for creating new frog objects.
     * Checks for the frog-class to be instantiated, then tries to use an already instantiated -
     * instance that has been freed, or generates a new one if could not find any free ones to reuse.
     */
    protected Frog newObject() {
        Class<? extends Frog> frogClass = FrogClassFactory.getInstance().getRandomFrogClass();
        if (null == frogClass) {
            return null;
        }
        Array<Integer> freeInstancesIndexes = this.classToFreeInstancesIndexesMap.get(frogClass);
        try {
            if (null == freeInstancesIndexes || 0 == freeInstancesIndexes.size) {
                Frog frog = frogClass.newInstance();
                this.allFrogs.add(frog);
                return frog;
            }
            else {
                return this.allFrogs.get(freeInstancesIndexes.pop());
            }
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Specifies the logic for obtaining frog objects.
     *
     * @return  A frog object ready to be used.
     */
    public Frog obtain() {
        return newObject();
    }

    /**
     * Puts the specified object in the pool, making it eligible to be returned by -
     * {@link #obtain()}.
     *
     * @param frog  The frog to free.
     */
    public void free(Frog frog) {
        Class<? extends Frog> frogClass = frog.getClass();
        Array<Integer> freeInstancesIndexes = this.classToFreeInstancesIndexesMap.get(frogClass);
        if (null == freeInstancesIndexes) {
            freeInstancesIndexes = new Array<Integer>();
        }
        freeInstancesIndexes.insert(0, this.allFrogs.indexOf(frog, true));
        this.classToFreeInstancesIndexesMap.put(frogClass, freeInstancesIndexes);
        frog.reset();
    }

    /**
     * Puts the specified objects in the pool. Null objects within the array are silently ignored.
     *
     * @param frogs     The array of frogs to be freed.
     */
    public void freeAll(Array<Frog> frogs) {
        for (Frog frog: frogs) {
            free(frog);
        }
    }

    /**
     * Clears the frog pool.
     */
    public void clear() {
        // TODO (consider disposing all frogs).
        this.allFrogs.clear();
        this.classToFreeInstancesIndexesMap.clear();
    }

}
