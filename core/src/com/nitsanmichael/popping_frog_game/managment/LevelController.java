package com.nitsanmichael.popping_frog_game.managment;

import com.badlogic.gdx.utils.Array;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.config.Config;
import com.nitsanmichael.popping_frog_game.config.metadata.AddFrogMetaData;
import com.nitsanmichael.popping_frog_game.media.Media;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.scenes.PopupDrawer;
import com.nitsanmichael.popping_frog_game.scenes.panel.Timer;
import com.nitsanmichael.popping_frog_game.config.metadata.LevelMetaData;
import com.nitsanmichael.popping_frog_game.screens.PlayScreen;
import com.nitsanmichael.popping_frog_game.sprites.SpritesDrawer;

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
    private static final int MAX_DIFFICALITY_LEVEL = 14;

    // Private members.
    private Array<LevelMetaData> levelsMetaData;
    private Array<Integer> levelsToAddFrog;
    private LevelMetaData currentLevelMetaData;
    private Timer levelTimer;
    private Config config;
    private Media media;
    private PopupDrawer popupDrawer;
    private RuntimeInfo runtimeInfo;
    private ThemeController themeController;
    private FrogClassAllocator frogClassAllocator;
    private FrogManager frogManager;

    /**
     * @param config    The game configuration.
     * @param assetController   An asset controller instance for retrieving the loaded assets.
     * @param media A media object for playing music and sounds.
     * @param spritesDrawer A class that is used to draw queued sprites.
     * @param popupDrawer A class that is used to draw popups to the screen (level up).
     * @param runtimeInfo A runtime information regarding the game state.
     * @param timer A timer to be used for timing the levels.
     **/
    private LevelController(Config config, AssetController assetController, Media media,
                            SpritesDrawer spritesDrawer, PopupDrawer popupDrawer,
                            RuntimeInfo runtimeInfo, Timer timer) {
        this.config = config;
        this.media = media;
        this.popupDrawer = popupDrawer;
        this.runtimeInfo = runtimeInfo;
        this.levelsMetaData = config.levelsMetaData;
        this.frogClassAllocator = new FrogClassAllocator();
        this.frogManager = new FrogManager(assetController, spritesDrawer, runtimeInfo,
                    this.frogClassAllocator);
        this.levelTimer = timer;
    }

    /**
     * @param config    The game configuration.
     * @param assetController   An asset controller instance for retrieving the loaded assets.
     * @param media A media object for playing music and sounds.
     * @param spritesDrawer A class that is used to draw queued sprites.
     * @param popupDrawer A class that is used to draw popups to the screen (level up).
     * @param runtimeInfo A runtime information regarding the game state.
     * @param timer A timer to be used for timing the levels.
     * @param themeController A theme-controller to use for switching between themes.
     */
    public LevelController(Config config, AssetController assetController, Media media,
                           SpritesDrawer spritesDrawer, PopupDrawer popupDrawer,
                           RuntimeInfo runtimeInfo, Timer timer, ThemeController themeController) {
        this(config, assetController, media, spritesDrawer, popupDrawer, runtimeInfo, timer);
        this.runtimeInfo.gameLevel = STARTING_LEVEL;
        this.runtimeInfo.gameSpeed = STARTING_SPEED;
        this.themeController = themeController;
        this.themeController.init();
        setup();
    }

    public void reset() {
        this.frogManager.reset();
    }

    public void setup() {
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
            setNewTimer();
            levelUp();
        }
    }

    private void setNewTimer() {
        float countdownTime = this.levelTimer.getCountdownTime();
        if (this.runtimeInfo.gameLevel <= MAX_DIFFICALITY_LEVEL) {
            this.levelTimer.setCountdownTime(countdownTime * LEVEL_TIMER_INCREMENTAL_FACTOR);
        }
        else {
            this.levelTimer.setCountdownTime(countdownTime);
        }
    }

    /**
     * This method performs a level-up.
     * Making the Level controller to advance to the next level.
     */
    private void levelUp() {
        this.runtimeInfo.gameLevel++;
        if(this.runtimeInfo.gameLevel <= MAX_DIFFICALITY_LEVEL) {
            this.runtimeInfo.gameSpeed *= SPEED_SCALE_FACTOR;
        }
        setCurrentLevel();
        this.media.playSound(Assets.LEVEL_UP_SOUND);
        this.popupDrawer.register(PopupDrawer.PopupType.LEVEL_UP);
    }

}