package com.nitsanmichael.popping_frog_game.managment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Iterator;

import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.managment.exceptions.OverpopulatedHolesException;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.sprites.FrogGhost;
import com.nitsanmichael.popping_frog_game.sprites.frogs.active.Frog;
import com.nitsanmichael.popping_frog_game.sprites.SpritesDrawer;


/**
 * This class is responsible for managing all the (active frogs) on the screen.
 *
 * Created by MichaelBond on 8/28/2016.
 */
public class FrogManager {

    private Array<Integer> unpopulatedHolesIndexes;
    private HashMap<Frog, Integer> frogToHoleIndexMap;
    private FrogPool frogPool;
    private AssetController assetController;
    private SpritesDrawer spritesDrawer;
    private RuntimeInfo runtimeInfo;


    public FrogManager(AssetController assetController, SpritesDrawer spritesDrawer,
                       RuntimeInfo runtimeInfo, FrogClassAllocator frogClassAllocator) {
        this.assetController = assetController;
        this.spritesDrawer = spritesDrawer;
        this.runtimeInfo = runtimeInfo;
        this.frogToHoleIndexMap = new HashMap<Frog, Integer>();
        this.unpopulatedHolesIndexes = new Array<Integer>();
        setUnpopulatedHolesIndexes();
        this.frogPool = new FrogPool(frogClassAllocator);
    }

    private void setUnpopulatedHolesIndexes() {
        for (int i = 0; i < this.runtimeInfo.holes.size; ++i) {
            this.unpopulatedHolesIndexes.add(i);
        }
    }

    /**
     * Adds a frog to pop on the screen.
     */
    public void addFrog() {
        int randomHoleIndex = this.getRandomUnpopulatedHoleIndex();
        Vector2 frogPosition = this.getFrogPlacementPosition(randomHoleIndex);
        // Add a new frog positioned at the chosen hole.
        Frog frog = this.frogPool.obtain();
        if (null != frog) {
            frog.init(this.assetController, this.runtimeInfo, frogPosition);
            this.spritesDrawer.addSprite(frog);
            this.runtimeInfo.activeFrogs.add(frog);
            this.frogToHoleIndexMap.put(frog, randomHoleIndex);
            // The hole is now populated.
            this.unpopulatedHolesIndexes.removeValue(randomHoleIndex, true);
        }
        else {
            // Log this incident.
            Gdx.app.log(PoppingFrog.LOGGER_TAG, "Could not obtain a frog to add to the screen.");
        }
    }

    /**
     * Used for fetching a random unpopulated hole index.
     *
     * @return  A random unpopulated hole index.
     */
    private int getRandomUnpopulatedHoleIndex() {
        Integer unpopulatedHoleIndex = this.unpopulatedHolesIndexes.random();
        if (null == unpopulatedHolesIndexes) {
            throw new OverpopulatedHolesException("There are no free holes to populate");
        }
        return unpopulatedHoleIndex;
    }

    private Vector2 getFrogPlacementPosition(int holeIndex) {
        return this.runtimeInfo.holes.get(holeIndex).getFrogPlacementPosition();
    }

    /**
     * Updates all the (active) frog on the screen.
     *
     * @param deltaTime The time passed from the last call to update.
     */
    public void update(float deltaTime) {
        // Updating frogs.
        Iterator<Frog> frogIterator = this.runtimeInfo.activeFrogs.iterator();
        while (frogIterator.hasNext()) {
            Frog frog = frogIterator.next();
            frog.update(deltaTime);
            if (frog.isKilled() || frog.isLifeTimeExpired()) {
                frog.onDeath();
                recycleDeadFrog(frogIterator, frog);
            }
        }
        // Updating ghosts.
        Iterator<FrogGhost> iterator = this.runtimeInfo.frogGhosts.iterator();
        while (iterator.hasNext()) {
            FrogGhost frogGhost = iterator.next();
            frogGhost.update(deltaTime);
            if (frogGhost.isTimedUp()) {
                this.spritesDrawer.removeSprite(frogGhost);
                iterator.remove();
            }
        }
    }

    /**
     * Replaces an already dead frog with a new one.
     *
     * @param frogIterator  A frog-iterator to be used in order to remove the given frog.
     * @param frog  The frog to be removed from the screen.
     */
    private void recycleDeadFrog(Iterator<Frog> frogIterator, Frog frog) {
        if (frog.isKilled()) {
            FrogGhost ghost = new FrogGhost(this.assetController, frog.getCenterPosition());
            this.runtimeInfo.frogGhosts.add(ghost);
            this.spritesDrawer.addSprite(ghost);
        }
        int frogHoleIndex = this.frogToHoleIndexMap.remove(frog);
        frogIterator.remove();
        this.frogPool.free(frog);
        this.spritesDrawer.removeSprite(frog);
        addFrog();
        this.unpopulatedHolesIndexes.add(frogHoleIndex);
    }

    public void reset() {
        // Frogs.
        Iterator<Frog> frogIterator = this.runtimeInfo.activeFrogs.iterator();
        while (frogIterator.hasNext()) {
            Frog frog = frogIterator.next();
            int frogHoleIndex = this.frogToHoleIndexMap.remove(frog);
            this.unpopulatedHolesIndexes.add(frogHoleIndex);
            frogIterator.remove();
            this.frogPool.free(frog);
            this.spritesDrawer.removeSprite(frog);
        }
        this.frogPool.clear();
        // Frog-ghosts.
        Iterator<FrogGhost> iterator = this.runtimeInfo.frogGhosts.iterator();
        while (iterator.hasNext()) {
            FrogGhost frogGhost = iterator.next();
            this.spritesDrawer.removeSprite(frogGhost);
            iterator.remove();
        }
    }

}
