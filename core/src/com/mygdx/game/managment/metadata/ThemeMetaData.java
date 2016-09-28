package com.mygdx.game.managment.metadata;

import com.mygdx.game.themes.SpringTheme;
import com.mygdx.game.themes.Theme;

/**
 * Created by MichaelBond on 9/18/2016.
 */
public class ThemeMetaData {

    public static Theme DEFAULT_THEME = new SpringTheme();

    public Theme theme;
    public int duration;


    public ThemeMetaData(Theme theme, int duration) {
        this.theme = theme;
        this.duration = duration;
    }
}
