package com.mygdx.game.managment;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.managment.config.Config;
import com.mygdx.game.managment.metadata.ThemeMetaData;
import com.mygdx.game.themes.Theme;

/**
 * Created by MichaelBond on 9/18/2016.
 */
public class ThemeController {

    private static final int INFINITY = -1;

    private static ThemeController ourInstance = new ThemeController();

    /**
     * Singleton implementation.
     *
     * @return  The singleton object.
     */
    public static ThemeController getInstance() {
        return ourInstance;
    }

    /**
     * Singleton private constructor.
     */
    private ThemeController() {
        this.currentTheme = ThemeMetaData.DEFAULT_THEME;
        this.themesMetaData = Config.themesMetaData;
        int sum = 0;
        for (ThemeMetaData meta : this.themesMetaData) {
            sum += meta.duration;
        }
        this.themesCycle = sum;
    }

    // The current active game-theme.
    public Theme currentTheme;

    private int themesCycle;
    private int nextThemeIndex;
    private int nextThemeLevel;
    private Array<ThemeMetaData> themesMetaData;


    /**
     * Initializes the singleton instance to the default starting theme.
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
     * Initializes the singleton instance to a theme corresponding to a given level.
     *
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

    public void update(float deltaTime) {
        this.currentTheme.update(deltaTime);
        LevelController levelController = LevelController.getInstance();
        int level = levelController.getCurrentLevel();
        if (level == this.nextThemeLevel) {
            // Advance to the next theme.
            setTheme();
        }
    }

    private void setTheme() {
        this.currentTheme.getMusic().stop();
        this.currentTheme = this.themesMetaData.get(this.nextThemeIndex).theme;
        this.nextThemeLevel += this.themesMetaData.get(this.nextThemeIndex).duration;
        this.nextThemeIndex = (this.nextThemeIndex + 1) % (this.themesMetaData.size);
        this.currentTheme.getMusic().setLooping(true);
        this.currentTheme.getMusic().play();
    }

}
