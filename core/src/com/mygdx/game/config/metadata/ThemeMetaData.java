package com.mygdx.game.config.metadata;

import com.mygdx.game.themes.Theme;

/**
 * Created by MichaelBond on 9/18/2016.
 */
public class ThemeMetaData {

    public Class<? extends Theme> themeClass;
    public int duration;


    public ThemeMetaData(Class<? extends Theme> themeClass, int duration) {
        this.themeClass = themeClass;
        this.duration = duration;
    }
}
