package com.mygdx.game.sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;
import java.util.Iterator;

import com.mygdx.game.sprites.Exceptions.OverpopulatedHolesException;


/**
 * Created by MichaelBond on 8/28/2016.
 */
public class FrogManager {

    private static final int FROG_OFFSET_X = 70;
    private static final int FROG_OFFSET_Y = 20;

    private float frogMaxLifeTime;
    private final Pool<Frog> frogPool = new Pool<Frog>() {
        @Override
        protected Frog newObject() {
            return new Frog();
        }
    };
    private Array<Frog> activeFrogs;
    private Array<Hole> holes;
    private Array<Integer> unpopulatedHolesIndexes;
    private HashMap<Frog, Integer> frogToHoleIndexMap;


    public FrogManager(Array<Hole> holes, float frogMaxLifeTime) {
        this.frogMaxLifeTime = frogMaxLifeTime;
        this.holes = holes;
        this.activeFrogs = new Array<Frog>();
        this.frogToHoleIndexMap = new HashMap<Frog, Integer>();
        this.unpopulatedHolesIndexes = new Array<Integer>();
        for (int i = 0; i < holes.size; ++i) {
            this.unpopulatedHolesIndexes.add(i);
        }
    }

    public void addFrog() {
        int randomHoleIndex = this.getRandomUnpopulatedHoleIndex();
        Vector2 frogPosition = this.getFrogPlacementPosition(randomHoleIndex);
        // Add a new frog positioned at the chosen hole.
        Frog frog = this.frogPool.obtain();
        // // TODO: 9/3/2016 check if null ?
        frog.init(frogPosition.x, frogPosition.y, this.frogMaxLifeTime);
        this.activeFrogs.add(frog);
        this.frogToHoleIndexMap.put(frog, randomHoleIndex);
        // The hole is now populated.
        this.unpopulatedHolesIndexes.removeValue(randomHoleIndex, true);
    }

    private int getRandomUnpopulatedHoleIndex() {
        Integer unpopulatedHoleIndex = this.unpopulatedHolesIndexes.random();
        if (null == unpopulatedHolesIndexes) {
            throw new OverpopulatedHolesException("There are no free holes to populate");
        }
        return unpopulatedHoleIndex;
    }

    private Vector2 getFrogPlacementPosition(int holeIndex) {
        Vector2 holePosition = this.holes.get(holeIndex).getPosition();
        return new Vector2(
                holePosition.x + FROG_OFFSET_X, holePosition.y + FROG_OFFSET_Y);
    }

//    private float getRandomLifeTime() {
//        //..
//    }

    public Array<Frog> getFrogs() {
        return this.activeFrogs;
    }

    public void decreaseFrogMaxLifeTime(float decreaseFactor) {
        this.frogMaxLifeTime *= decreaseFactor;
    }

    public void update(float deltaTime) {
        Iterator<Frog> frogIterator = this.activeFrogs.iterator();

        while (frogIterator.hasNext()) {
            Frog frog = frogIterator.next();
            frog.update(deltaTime);

            if (frog.isKilled) {
                int frogHoleIndex = this.frogToHoleIndexMap.remove(frog);
                frogIterator.remove();
                this.frogPool.free(frog);
                addFrog();
                this.unpopulatedHolesIndexes.add(frogHoleIndex);
            }
        }
    }

    public void dispose() {
        for (Frog frog: this.getFrogs()) {
            frog.dispose();
        }
    }

}
