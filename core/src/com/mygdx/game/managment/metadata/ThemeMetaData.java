package com.mygdx.game.managment.metadata;

import com.mygdx.game.managment.themes.DefaultTheme;
import com.mygdx.game.managment.themes.Theme;

/**
 * Created by MichaelBond on 9/18/2016.
 */
public class ThemeMetaData {

    public static Theme DEFAULT_THEME = new DefaultTheme();

    public Theme theme;
    public int startingLevel;


    public ThemeMetaData(Theme theme, int startingLevel) {
        this.theme = theme;
        this.startingLevel = startingLevel;
    }
}
