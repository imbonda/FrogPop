package com.mygdx.game.managment;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.managment.config.Config;
import com.mygdx.game.managment.metadata.LevelMetaData;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.sprites.Timer;

/**
 * Created by MichaelBond on 9/7/2016.
 */
public class LevelController {

    private static final int STARTING_LEVEL = 1;
    private static final float LEVEL_TIMER_INCREMENTAL_FACTOR = 1.05f;

    private int currentLevel;
    private Array<LevelMetaData> levelsMetaData;
    private LevelMetaData currentLevelMetaData;
    private Timer levelTimer;
    private Hud hud;
    private FrogFactory frogFactory;


    public LevelController(Timer levelTimer, Hud hud) {
        this.levelTimer = levelTimer;
        this.hud = hud;
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
        }
    }

    private void levelUp() {
        this.currentLevel++;
        setCurrentLevel();
        this.hud.levelCounter.advance();
    }
}
