package com.mygdx.game.config;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.game.config.metadata.AddFrogMetaData;
import com.mygdx.game.config.metadata.LevelMetaData;
import com.mygdx.game.config.metadata.ThemeMetaData;

/**
 * This class will hold all the configurations necessary for the game.
 *
 * Created by MichaelBond on 9/8/2016.
 */
public class Config {

    private static final XmlReader xmlReader = new XmlReader();
    public static final Array<LevelMetaData> levelsMetaData = new Array<LevelMetaData>();
    public static final Array<AddFrogMetaData> addFrogsMetaData = new Array<AddFrogMetaData>();
    public static final Array<ThemeMetaData> themesMetaData = new Array<ThemeMetaData>();

    static {
        loadLevelsMetaData();
        loadAddFrogsMetaData();
        loadThemesMetaData();
    }

    private static void loadLevelsMetaData() {
        LevelsMetaDataLoader.load(xmlReader, levelsMetaData);
    }

    private static void loadAddFrogsMetaData() {
        AddFrogsMetaDataLoader.load(xmlReader, addFrogsMetaData);
    }

    private static void loadThemesMetaData() {
        ThemesMetaDataLoader.load(xmlReader, themesMetaData);
    }

}
