package com.mygdx.game.managment;

import com.mygdx.game.managment.config.Config;
import com.mygdx.game.managment.metadata.ThemeMetaData;
import com.mygdx.game.managment.themes.DefaultTheme;
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
    }

    private Theme currentTheme;


    public void setTheme(Theme theme) {
        this.currentTheme = theme;
    }

}
