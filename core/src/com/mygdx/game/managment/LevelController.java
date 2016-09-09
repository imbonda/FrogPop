package com.mygdx.game.managment;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.managment.config.Config;
import com.mygdx.game.managment.metadata.LevelMetaData;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.sprites.Timer;

import java.util.Random;

/**
 * Created by MichaelBond on 9/7/2016.
 */
public class LevelController {

    private static final int STARTING_LEVEL = 1;
    private static final float LEVEL_TIMER_INCREMENTAL_FACTOR = 1.05f;
    private static final float FROG_LIFE_TIME_DECREASE_FACTOR = 0.92f;
    private static final Random random = new Random();
    private int[] whenAddfrogs={random.nextInt(5)+3,random.nextInt(7)+13,random.nextInt(8)+20,random.nextInt(8)+30,random.nextInt(10)+40};

    private int currentLevel;
    private Array<LevelMetaData> levelsMetaData;
    private LevelMetaData currentLevelMetaData;
    private Timer levelTimer;
    private Hud hud;
    private FrogFactory frogFactory;
    private FrogManager frogManager;


    public LevelController(Timer levelTimer, Hud hud, FrogManager frogManager) {
        this.levelTimer = levelTimer;
        this.hud = hud;
        this.frogManager = frogManager;
        this.frogFactory = FrogFactory.getInstance();
        this.currentLevel = STARTING_LEVEL;
        this.levelsMetaData = Config.levelsMetaData;
        this.currentLevelMetaData = LevelMetaData.DEFAULT_METADATA;
        setCurrentLevel();
    }

    private void setCurrentLevel() {
        for (LevelMetaData levelMetaData : this.levelsMetaData) {
            if (levelMetaData.id == this.currentLevel) {
                this.currentLevelMetaData = levelMetaData;
                this.frogFactory.setLevelMetaData(this.currentLevelMetaData);
                break;
            }
        }
    }

    public void update(float deltaTime) {
        this.levelTimer.update(deltaTime);
        if (this.levelTimer.isTimedOut()) {
            this.levelTimer.setCountTimeByFactor(LEVEL_TIMER_INCREMENTAL_FACTOR);
            levelUp();
            this.frogManager.decreaseFrogMaxLifeTime(FROG_LIFE_TIME_DECREASE_FACTOR);
            if((this.currentLevel == whenAddfrogs[this.currentLevel/10]) &&
                        this.currentLevel < 59) {
                this.frogManager.addFrog();
            }
        }
    }

    private void levelUp() {
        this.currentLevel++;
        setCurrentLevel();
        this.hud.levelCounter.advance();
    }
}
