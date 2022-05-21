package com.nitsanmichael.popping_frog_game.config.metadata;

/**
 * Created by MichaelBond on 9/18/2016.
 */
public class ThemeMetaData {

    public Class<? extends com.nitsanmichael.popping_frog_game.themes.Theme> themeClass;
    public int duration;


    public ThemeMetaData(Class<? extends com.nitsanmichael.popping_frog_game.themes.Theme> themeClass, int duration) {
        this.themeClass = themeClass;
        this.duration = duration;
    }
}
