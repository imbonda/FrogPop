package com.nitsanmichael.frog_pop_game.config;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.nitsanmichael.frog_pop_game.config.metadata.AddFrogMetaData;
import com.nitsanmichael.frog_pop_game.config.metadata.LevelMetaData;
import com.nitsanmichael.frog_pop_game.config.metadata.ThemeMetaData;

/**
 * This class will hold all the configurations necessary for the game.
 *
 * Created by MichaelBond on 9/8/2016.
 */
public class Config {

    public Array<LevelMetaData> levelsMetaData;
    public Array<AddFrogMetaData> addFrogsMetaData;
    public Array<ThemeMetaData> themesMetaData;
    private XmlReader xmlReader;

    public Config() {
        this.xmlReader = new XmlReader();
        this.levelsMetaData = new Array<LevelMetaData>();
        this.addFrogsMetaData = new Array<AddFrogMetaData>();
        this.themesMetaData = new Array<ThemeMetaData>();
    }

    public void load() {
        loadLevelsMetaData();
        loadAddFrogsMetaData();
        loadThemesMetaData();
    }

    private void loadLevelsMetaData() {
        LevelsMetaDataLoader.load(this.xmlReader, this.levelsMetaData);
    }

    private void loadAddFrogsMetaData() {
        AddFrogsMetaDataLoader.load(this.xmlReader, this.addFrogsMetaData);
    }

    private void loadThemesMetaData() {
        ThemesMetaDataLoader.load(this.xmlReader, this.themesMetaData);
    }

}
