package com.mygdx.game.managment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Iterator;

import com.mygdx.game.FrogPop;
import com.mygdx.game.assets.AssetController;
import com.mygdx.game.managment.exceptions.OverpopulatedHolesException;
import com.mygdx.game.runtime.RuntimeInfo;
import com.mygdx.game.sprites.FrogsDeathGhostEffect;
import com.mygdx.game.sprites.SpritesDrawer;
import com.mygdx.game.sprites.frogs.active.Frog;
import com.mygdx.game.sprites.Hole;


/**
 * This class is responsible for managing all the (active frogs) on the screen.
 *
 * Created by MichaelBond on 8/28/2016.
 */
public class FrogManager {

    private static final int FROG_OFFSET_X = 55;
    private static final int FROG_OFFSET_Y = 20;
    private static final Vector2[] HOLES_POSITIONS = {
            new Vector2(50, 10), new Vector2(300, 10), new Vector2(50, 135),
            new Vector2(300, 135), new Vector2(550, 10), new Vector2(550, 135),
            new Vector2(50, 260), new Vector2(300, 260), new Vector2(550,260)
    };

    private Array<Hole> holes;
    private Array<Integer> unpopulatedHolesIndexes;
    private HashMap<Frog, Integer> frogToHoleIndexMap;
    private FrogPool frogPool;
    private AssetController assetController;
    private SpritesDrawer spritesDrawer;
    private RuntimeInfo runtimeInfo;
    private Array<FrogsDeathGhostEffect> frogsDeathGhostEffects;

    public FrogManager(AssetController assetController, SpritesDrawer spritesDrawer,
                       RuntimeInfo runtimeInfo, FrogClassAllocator frogClassAllocator) {
        this.assetController = assetController;
        this.spritesDrawer = spritesDrawer;
        this.holes = new Array<Hole>();
        for (Vector2 holePosition : HOLES_POSITIONS) {
            holes.add(new Hole(this.assetController, holePosition.x, holePosition.y));
            this.spritesDrawer.addSprites(holes);
        }
        this.frogToHoleIndexMap = new HashMap<Frog, Integer>();
        this.unpopulatedHolesIndexes = new Array<Integer>();
        setUnpopulatedHolesIndexes();
        this.runtimeInfo = runtimeInfo;
        this.frogPool = new FrogPool(frogClassAllocator);
        frogsDeathGhostEffects=new Array<FrogsDeathGhostEffect>();
    }

    private void setUnpopulatedHolesIndexes() {
        for (int i = 0; i < holes.size; ++i) {
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
            frog.init(this.assetController, this.runtimeInfo, frogPosition.x, frogPosition.y);
            this.spritesDrawer.addSprite(frog);
            this.runtimeInfo.activeFrogs.add(frog);
            this.frogToHoleIndexMap.put(frog, randomHoleIndex);
            // The hole is now populated.
            this.unpopulatedHolesIndexes.removeValue(randomHoleIndex, true);
        }
        else {
            // Log this incident.
            Gdx.app.log(FrogPop.LOGGER_TAG, "Could not obtain a frog to add to the screen.");
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
        Vector2 holePosition = this.holes.get(holeIndex).getPosition();
        return new Vector2(
                holePosition.x + FROG_OFFSET_X, holePosition.y + FROG_OFFSET_Y);
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
        Iterator<FrogsDeathGhostEffect> iterator = this.frogsDeathGhostEffects.iterator();
        while (iterator.hasNext()) {
            FrogsDeathGhostEffect frogsDeathGhostEffect = iterator.next();
            frogsDeathGhostEffect.update(deltaTime);
            if (frogsDeathGhostEffect.isTimedUp()) {
                this.spritesDrawer.removeSprite(frogsDeathGhostEffect);
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
        if(frog.isKilled()){
        frogsDeathGhostEffects.add(new FrogsDeathGhostEffect(frog.getPosition()));
        this.spritesDrawer.addSprite(frogsDeathGhostEffects.peek());}
        int frogHoleIndex = this.frogToHoleIndexMap.remove(frog);
        frogIterator.remove();
        this.frogPool.free(frog);
        this.spritesDrawer.removeSprite(frog);
        addFrog();
        this.unpopulatedHolesIndexes.add(frogHoleIndex);
    }

}
