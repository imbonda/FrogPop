package com.mygdx.game.managment;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.managment.config.Config;
import com.mygdx.game.managment.metadata.AddFrogMetaData;
import com.mygdx.game.managment.metadata.LevelMetaData;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.sprites.Timer;

/**
 * This class is responsible for managing all the level related logic.
 *
 * Created by MichaelBond on 9/7/2016.
 */
public class LevelController {

    private static final int STARTING_LEVEL = 1;
    private static final float STARTING_SPEED = 1.0f;
    private static final float LEVEL_TIMER_INCREMENTAL_FACTOR = 1.05f;
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
        this.frogClassFactory = FrogClassFactory.getInstance();
        this.frogManager = FrogManager.getInstance();
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
    private FrogClassFactory frogClassFactory;
    private FrogManager frogManager;

    /**
     * Initializes the singleton instance to a given level.
     *
     * @param level         A level to set the LevelController to.
     */
    public void init(int level) {
        this.currentLevel = level;
        // TODO (finish function: calculate the speed for the given level..)
        setup();
    }

    /**
     * Initializes the singleton instance to a given level.
     */
    public void init() {
        this.currentLevel = STARTING_LEVEL;
        this.speed = STARTING_SPEED;
        setup();
    }

    private void setup() {
        this.levelTimer = new Timer();
        this.frogManager.reset();
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
        // Look for a level configuration, specific for this level.
        for (LevelMetaData levelMetaData : this.levelsMetaData) {
            if (levelMetaData.id == this.currentLevel) {
                this.currentLevelMetaData = levelMetaData;
                this.frogClassFactory.setLevelMetaData(this.currentLevelMetaData);
                break;
            }
        }
        // If no specific configuration was found, use the last configuration configured.
        if (this.currentLevelMetaData.id != this.currentLevel) {
            this.frogClassFactory.setLevelMetaData(this.currentLevelMetaData);
        }
        // In case a frog needs to be added, add it.
        while (this.levelsToAddFrog.size > 0 && this.currentLevel >= this.levelsToAddFrog.get(0)) {
            this.frogManager.addFrog();
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
        this.frogManager.update(deltaTime);
        if (this.levelTimer.isTimedOut()) {
            this.levelTimer.setCountTimeByFactor(LEVEL_TIMER_INCREMENTAL_FACTOR);
            levelUp();
        }
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
}
