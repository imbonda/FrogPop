package com.mygdx.game.managment;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.managment.config.Config;
import com.mygdx.game.managment.metadata.ThemeMetaData;
import com.mygdx.game.managment.themes.Theme;

/**
 * Created by MichaelBond on 9/18/2016.
 */
public class ThemeController {

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
    }

    // The current active game-theme.
    public Theme currentTheme;
    private int nextThemeIndex;
    private Array<ThemeMetaData> themesMetaData;
    private int nextThemeLevel;
    /**
     * Initializes the singleton instance to the default starting theme.
     */
    public void init() {
        this.nextThemeIndex = 0;
        setup();
    }

    /**
     * Initializes the singleton instance to a theme corresponding to a given level.
     *
     * @param level A level to set the LevelController to.
     */
    public void init(int level) {
        for (int i = 0; i < this.themesMetaData.size; ++i) {
            if (level >= this.themesMetaData.get(i).duration) {
                this.nextThemeIndex = i;
                continue;
            }
            break;
        }
        setup();
    }

    private void setup() {
        if (this.themesMetaData.size > 0) {
            this.nextThemeLevel=this.themesMetaData.get(nextThemeIndex).duration;
            // Advance to the next theme.
            setTheme();
            this.currentTheme.getMusic().play();
        }
    }

    public void update(float deltaTime) {
        LevelController levelController = LevelController.getInstance();
        int level = levelController.getCurrentLevel();
        if(level==nextThemeLevel) {
            // Advance to the next theme.
            setTheme();
        }
    }

    private void setTheme() {
        this.nextThemeLevel+=this.themesMetaData.get(nextThemeIndex).duration;
        this.nextThemeIndex =(this.nextThemeIndex+1)%themesMetaData.size;
        this.currentTheme=this.themesMetaData.get(this.nextThemeIndex).theme;
       // this.currentTheme.getMusic().stop();
        this.currentTheme = this.themesMetaData.get(this.nextThemeIndex).theme;
        this.currentTheme.getMusic().setLooping(true);
        //this.currentTheme.getMusic().play();
    }

}
