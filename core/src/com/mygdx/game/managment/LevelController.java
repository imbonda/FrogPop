package com.mygdx.game.managment;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.config.Config;
import com.mygdx.game.config.metadata.AddFrogMetaData;
import com.mygdx.game.config.metadata.LevelMetaData;
import com.mygdx.game.runtime.RuntimeInfo;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.sprites.Timer;

/**
 * This class is responsible for managing all the level related logic.
 *
 * Created by MichaelBond on 9/7/2016.
 */
public class LevelController {

    // Public class variables.
    public static final int STARTING_LEVEL = 1;
    public static final float STARTING_SPEED = 1.0f;
    // Private class variables.
    private static final float LEVEL_TIMER_INCREMENTAL_FACTOR = 1.04f;
    private static final float SPEED_SCALE_FACTOR = 1.087f;

    // Private members.
    private int currentLevel;
    private Array<LevelMetaData> levelsMetaData;
    private Array<Integer> levelsToAddFrog;
    private LevelMetaData currentLevelMetaData;
    private Timer levelTimer;
    private RuntimeInfo runtimeInfo;
    private ThemeController themeController;
    private FrogManager frogManager;

    private LevelController(RuntimeInfo runtimeInfo) {
        this.runtimeInfo = runtimeInfo;
        this.currentLevel = STARTING_LEVEL;
        this.levelsMetaData = Config.levelsMetaData;
        this.currentLevelMetaData = LevelMetaData.DEFAULT_METADATA;
        this.frogManager = new FrogManager(runtimeInfo);
    }

    /**
     * Initializes the level-controller to it's default configuration.
     */
    public LevelController(RuntimeInfo runtimeInfo, ThemeController themeController) {
        this(runtimeInfo);
        this.currentLevel = STARTING_LEVEL;
        this.runtimeInfo.gameSpeed = STARTING_SPEED;
        this.themeController = themeController;
        this.themeController.init();
        setup();
    }

    /**
     * Initializes the singleton instance to a given level.
     *
     * @param level A level to set the LevelController to.
     */
    public LevelController(RuntimeInfo runtimeInfo, ThemeController themeController, int level) {
        this(runtimeInfo);
        this.currentLevel = level;
        this.themeController = themeController;
        this.themeController.init(level);
        // TODO (finish function: calculate the this.runtimeInfo.gameSpeed for the given level..)
        setup();
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
        this.themeController.update(deltaTime, this.currentLevel);
    }

    /**
     * This method performs a level-up.
     * Making the Level controller to advance to the next level.
     */
    private void levelUp() {
        this.currentLevel++;
        this.runtimeInfo.gameSpeed *= SPEED_SCALE_FACTOR;
        setCurrentLevel();
        Hud.getInstance().getLevelCounter().advance();
    }

}
