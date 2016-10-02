package com.mygdx.game.managment;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.config.Config;
import com.mygdx.game.config.metadata.AddFrogMetaData;
import com.mygdx.game.config.metadata.LevelMetaData;
import com.mygdx.game.media.Media;
import com.mygdx.game.runtime.RuntimeInfo;
import com.mygdx.game.sprites.SpritesDrawer;
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
    private Array<LevelMetaData> levelsMetaData;
    private Array<Integer> levelsToAddFrog;
    private LevelMetaData currentLevelMetaData;
    private Timer levelTimer;
    private Config config;
    private Media media;
    private RuntimeInfo runtimeInfo;
    private ThemeController themeController;
    private FrogClassAllocator frogClassAllocator;
    private FrogManager frogManager;

    /**
     * @param config    The game configuration.
     * @param media A media object for playing music and sounds.
     * @param spritesDrawer A class that is used to draw queued sprites.
     * @param runtimeInfo A runtime information regarding the game state.
     **/
    private LevelController(Config config, Media media, SpritesDrawer spritesDrawer, RuntimeInfo runtimeInfo) {
        this.config = config;
        this.media = media;
        this.runtimeInfo = runtimeInfo;
        this.levelsMetaData = config.levelsMetaData;
        this.frogClassAllocator = new FrogClassAllocator();
        this.frogManager = new FrogManager(spritesDrawer, runtimeInfo, this.frogClassAllocator);
        this.levelTimer = new Timer();
        spritesDrawer.addSprite(this.levelTimer);
    }

    /**
     * @param config    The game configuration.
     * @param media A media object for playing music and sounds.
     * @param spritesDrawer A class that is used to draw queued sprites.
     * @param runtimeInfo A runtime information regarding the game state.
     * @param themeController A theme-controller to use for switching between themes.
     */
    public LevelController(Config config, Media media, SpritesDrawer spritesDrawer, RuntimeInfo runtimeInfo,
                            ThemeController themeController) {
        this(config, media, spritesDrawer, runtimeInfo);
        this.runtimeInfo.gameLevel = STARTING_LEVEL;
        this.runtimeInfo.gameSpeed = STARTING_SPEED;
        this.themeController = themeController;
        this.themeController.init();
        setup();
    }

    /**
     * @param config    The game configuration.
     * @param media A media object for playing music and sounds.
     * @param spritesDrawer A class that is used to draw queued sprites.
     * @param runtimeInfo A runtime information regarding the game state.
     * @param themeController A theme-controller to use for switching between themes.
     * @param level A level to set the LevelController to.
     */
    public LevelController(Config config, Media media, SpritesDrawer spritesDrawer, RuntimeInfo runtimeInfo,
                            ThemeController themeController, int level) {
        this(config, media, spritesDrawer, runtimeInfo);
        this.runtimeInfo.gameLevel = level;
        this.themeController = themeController;
        this.themeController.init(level);
        // TODO (finish function: calculate the this.runtimeInfo.gameSpeed for the given level..)
        setup();
    }

    private void setup() {
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
        for (AddFrogMetaData addFrogMetaData : this.config.addFrogsMetaData) {
            this.levelsToAddFrog.add(addFrogMetaData.getLevelToAddFrog());
        }
    }

    /**
     * This method sets all the data necessary for the current level.
     */
    private void setCurrentLevel() {
        // Look for a level configuration, specific for this level.
        for (LevelMetaData levelMetaData : this.levelsMetaData) {
            if (this.runtimeInfo.gameLevel >= levelMetaData.id) {
                this.currentLevelMetaData = levelMetaData;
            }
        }
        this.frogClassAllocator.setLevelMetaData(this.currentLevelMetaData);
        // If no specific configuration was found, use the last configuration configured.
        if (this.currentLevelMetaData.id != this.runtimeInfo.gameLevel) {
            this.frogClassAllocator.setLevelMetaData(this.currentLevelMetaData);
        }
        // In case a frog needs to be added, add it.
        while (this.levelsToAddFrog.size > 0 &&
                    this.runtimeInfo.gameLevel >= this.levelsToAddFrog.get(0)) {
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
            if(this.runtimeInfo.gameLevel<=14){
                this.levelTimer.setCountTimeByFactor(LEVEL_TIMER_INCREMENTAL_FACTOR);
            }
            levelUp();
        }
        this.themeController.update(deltaTime, this.runtimeInfo.gameLevel);
    }

    /**
     * This method performs a level-up.
     * Making the Level controller to advance to the next level.
     */
    private void levelUp() {
        this.runtimeInfo.gameLevel++;
        if(this.runtimeInfo.gameLevel<=14)
        {
            this.runtimeInfo.gameSpeed *= SPEED_SCALE_FACTOR;
        }
        setCurrentLevel();
        this.media.playSound(Media.LEVEL_UP_SOUND);
    }

}
