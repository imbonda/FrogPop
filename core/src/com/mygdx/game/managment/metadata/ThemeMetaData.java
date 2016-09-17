package com.mygdx.game.managment.metadata;

import com.mygdx.game.managment.themes.DefaultTheme;
import com.mygdx.game.managment.themes.Theme;

/**
 * Created by MichaelBond on 9/18/2016.
 */
public class ThemeMetaData {

    public static Theme DEFAULT_THEME = new DefaultTheme();

    public Class<? extends Theme> themeClass;
    public int startingLevel;


    public ThemeMetaData(Class<? extends Theme> themeClass, int startingLevel) {
        this.themeClass = themeClass;
        this.startingLevel = startingLevel;
    }
}
