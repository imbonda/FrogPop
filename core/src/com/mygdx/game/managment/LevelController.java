package com.mygdx.game.managment;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.managment.config.Config;
import com.mygdx.game.managment.metadata.AddFrogMetaData;
import com.mygdx.game.managment.metadata.LevelMetaData;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.sprites.Timer;

/**
 * Created by MichaelBond on 9/7/2016.
 */
public class LevelController {

    private static final int STARTING_LEVEL = 1;
    private static final float LEVEL_TIMER_INCREMENTAL_FACTOR = 1.05f;
    private static final float FROG_LIFE_TIME_DECREASE_FACTOR = 0.92f;

    private int currentLevel;
    private Array<LevelMetaData> levelsMetaData;
    private Array<Integer> levelsToAddFrog;
    private LevelMetaData currentLevelMetaData;
    private Timer levelTimer;
    private FrogClassFactory frogClassFactory;
    private FrogManager frogManager;


    public LevelController(FrogManager frogManager) {
        this.frogManager = frogManager;
        this.levelTimer = new Timer();
        this.frogClassFactory = FrogClassFactory.getInstance();
        this.currentLevel = STARTING_LEVEL;
        this.levelsMetaData = Config.levelsMetaData;
        setLevelsToAddFrog();
        this.currentLevelMetaData = LevelMetaData.DEFAULT_METADATA;
        setCurrentLevel();
    }

    private void setLevelsToAddFrog() {
        this.levelsToAddFrog = new Array<Integer>();
        for (AddFrogMetaData addFrogMetaData : Config.addFrogsMetaData) {
            this.levelsToAddFrog.add(addFrogMetaData.getLevelToAddFrog());
        }
    }

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
        while (this.levelsToAddFrog.size > 0 && this.currentLevel == this.levelsToAddFrog.get(0)) {
            this.frogManager.addFrog();
            this.levelsToAddFrog.removeIndex(0);
        }
    }

    public void update(float deltaTime) {
        this.levelTimer.update(deltaTime);
        if (this.levelTimer.isTimedOut()) {
            this.levelTimer.setCountTimeByFactor(LEVEL_TIMER_INCREMENTAL_FACTOR);
            this.frogManager.decreaseFrogMaxLifeTime(FROG_LIFE_TIME_DECREASE_FACTOR);
            levelUp();
        }
    }

    private void levelUp() {
        this.currentLevel++;
        setCurrentLevel();
        Hud.getInstance().getLevelCounter().advance();
    }
}
