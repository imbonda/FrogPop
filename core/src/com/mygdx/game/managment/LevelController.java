package com.mygdx.game.managment;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.config.Config;
import com.mygdx.game.config.metadata.AddFrogMetaData;
import com.mygdx.game.config.metadata.LevelMetaData;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.sprites.Timer;

/**
 * This class is responsible for managing all the level related logic.
 *
 * Created by MichaelBond on 9/7/2016.
 */
public class LevelController {

    public static final int STARTING_LEVEL = 1;

    private static final float STARTING_SPEED = 1.0f;
    private static final float LEVEL_TIMER_INCREMENTAL_FACTOR = 1.04f;
    private static final float SPEED_SCALE_FACTOR = 1.087f;

    private static LevelController ourInstance = new LevelController();

    /**
     * Singleton implementation.
     *
     * @return  The singleton object.
     */
    public static LevelController getInstance() {
        return ourInstance;
    }

    /**
     * Singleton private constructor.
     */
    private LevelController() {
        this.currentLevel = STARTING_LEVEL;
        this.speed = STARTING_SPEED;
        this.levelsMetaData = Config.levelsMetaData;
        this.currentLevelMetaData = LevelMetaData.DEFAULT_METADATA;
    }

    private int currentLevel;
    private float speed;
    private Array<LevelMetaData> levelsMetaData;
    private Array<Integer> levelsToAddFrog;
    private LevelMetaData currentLevelMetaData;
    private Timer levelTimer;


    /**
     * Initializes the level-controller to it's default configuration.
     */
    public void init() {
        reset();
        this.currentLevel = STARTING_LEVEL;
        this.speed = STARTING_SPEED;
        ThemeController.getInstance().init();
        setup();
    }

    /**
     * Initializes the singleton instance to a given level.
     *
     * @param level A level to set the LevelController to.
     */
    public void init(int level) {
        reset();
        this.currentLevel = level;
        ThemeController.getInstance().init(level);
        // TODO (finish function: calculate the speed for the given level..)
        setup();
    }

    /**
     * Resets the level-controller to allow upcoming initialization.
     */
    private void reset() {
        FrogManager.getInstance().reset();
    }

    private void setup() {
        this.levelTimer = new Timer();
        setLevelsToAddFrog();
        setCurrentLevel();
    }

    /**
     * Sets the data indicating when to add new frogs.
     */
    private void setLevelsToAddFrog() {
        if (null == this.levelsToAddFrog) {
            this.levelsToAddFrog = new Array<Integer>();
        }
        else {
            this.levelsToAddFrog.clear();
        }
        for (AddFrogMetaData addFrogMetaData : Config.addFrogsMetaData) {
            this.levelsToAddFrog.add(addFrogMetaData.getLevelToAddFrog());
        }
    }

    /**
     * This method sets all the data necessary for the current level.
     */
    private void setCurrentLevel() {
        FrogClassAllocator frogClassAllocator = FrogClassAllocator.getInstance();
        // Look for a level configuration, specific for this level.
        for (LevelMetaData levelMetaData : this.levelsMetaData) {
            if (this.currentLevel >= levelMetaData.id) {
                this.currentLevelMetaData = levelMetaData;
            }
        }
        frogClassAllocator.setLevelMetaData(this.currentLevelMetaData);
        // If no specific configuration was found, use the last configuration configured.
        if (this.currentLevelMetaData.id != this.currentLevel) {
            frogClassAllocator.setLevelMetaData(this.currentLevelMetaData);
        }
        // In case a frog needs to be added, add it.
        while (this.levelsToAddFrog.size > 0 && this.currentLevel >= this.levelsToAddFrog.get(0)) {
            FrogManager.getInstance().addFrog();
            this.levelsToAddFrog.removeIndex(0);
        }
    }

    /**
     * Updating the current level and advancing to the next one if needed.
     *
     * @param deltaTime The time passed from the last update call.
     */
    public void update(float deltaTime) {
        this.levelTimer.update(deltaTime);
        FrogManager.getInstance().update(deltaTime);
        if (this.levelTimer.isTimedOut()) {
            this.levelTimer.setCountTimeByFactor(LEVEL_TIMER_INCREMENTAL_FACTOR);
            levelUp();
        }
        ThemeController.getInstance().update(deltaTime);
    }

    /**
     * This method performs a level-up.
     * Making the Level controller to advance to the next level.
     */
    private void levelUp() {
        this.currentLevel++;
        scaleSpeed(SPEED_SCALE_FACTOR);
        setCurrentLevel();
        Hud.getInstance().getLevelCounter().advance();
    }

    public void scaleSpeed(float scaleFactor) {
        this.speed *= scaleFactor;
    }

    public float getSpeed() {
        return this.speed;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}
