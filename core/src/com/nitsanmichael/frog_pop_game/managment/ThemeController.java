package com.nitsanmichael.frog_pop_game.managment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.nitsanmichael.frog_pop_game.effects.EffectDrawer;
import com.nitsanmichael.frog_pop_game.FrogPop;
import com.nitsanmichael.frog_pop_game.assets.AssetController;
import com.nitsanmichael.frog_pop_game.config.Config;
import com.nitsanmichael.frog_pop_game.config.metadata.ThemeMetaData;
import com.nitsanmichael.frog_pop_game.themes.Theme;

/**
 * Created by MichaelBond on 9/18/2016.
 */
public class ThemeController {

    private static final int INFINITY = -1;

    // The current active game-theme.
    public Theme currentTheme;
    // Privates.
    private int themesCycle;
    private int nextThemeIndex;
    private int nextThemeLevel;
    private Array<ThemeMetaData> themesMetaData;
    private AssetController assetController;
    private EffectDrawer effectDrawer;

    public ThemeController(Config config, AssetController assetController,
                            EffectDrawer effectDrawer) {
        this.themesMetaData = config.themesMetaData;
        this.assetController = assetController;
        this.effectDrawer = effectDrawer;
        int sum = 0;
        for (ThemeMetaData meta : this.themesMetaData) {
            sum += meta.duration;
        }
        this.themesCycle = sum;
    }

    /**
     * Default initialization.
     */
    public void init() {
        if (0 == this.themesMetaData.size) {
            this.nextThemeLevel = INFINITY;
        }
        else {
            this.nextThemeIndex = 0;
            this.nextThemeLevel = LevelController.STARTING_LEVEL;
        }
        setTheme();
    }

    /**
     * @param level A level to set the LevelController to.
     */
    public void init(int level) {
        if (0 == this.themesMetaData.size) {
            this.nextThemeLevel = INFINITY;
        }
        else {
            int cyclesCompleted = this.themesCycle *
                        (level / (LevelController.STARTING_LEVEL + this.themesCycle));
            this.nextThemeLevel = LevelController.STARTING_LEVEL + cyclesCompleted;
            for (int i = 0; i < this.themesMetaData.size; ++i) {
                if (level >= this.nextThemeLevel) {
                    setTheme();
                    continue;
                }
                break;
            }
        }
    }

    public void update(float deltaTime, int currentLevel) {
        this.currentTheme.update(deltaTime);
        if (currentLevel == this.nextThemeLevel) {
            // Advance to the next theme.
            setTheme();
        }
    }

    private void setTheme() {
        if (null != this.currentTheme) {
            this.currentTheme.reset();
        }
        Class<? extends Theme> themeClass = this.themesMetaData.get(this.nextThemeIndex).themeClass;
        try {
            this.currentTheme = themeClass.newInstance();
            this.currentTheme.init(this.assetController, this.effectDrawer);
            this.nextThemeLevel += this.themesMetaData.get(this.nextThemeIndex).duration;
            this.nextThemeIndex = (this.nextThemeIndex + 1) % (this.themesMetaData.size);
        }
        catch (InstantiationException e) {
            e.printStackTrace();
            // Log this incident.
            Gdx.app.log(FrogPop.LOGGER_TAG,
                        "Failed instantiating a theme object of the following class: " +
                                    themeClass.getSimpleName());
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            // Log this incident.
            Gdx.app.log(FrogPop.LOGGER_TAG,
                    "Failed instantiating a theme object of the following class: " +
                            themeClass.getSimpleName());
        }
    }

}
