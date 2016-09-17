package com.mygdx.game.managment.config;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.game.managment.metadata.AddFrogMetaData;
import com.mygdx.game.managment.metadata.LevelMetaData;
import com.mygdx.game.managment.metadata.ThemeMetaData;

/**
 * This class will hold all the configurations necessary for the game.
 *
 * Created by MichaelBond on 9/8/2016.
 */
public class Config {

    public static Array<LevelMetaData> levelsMetaData = new Array<LevelMetaData>();
    public static Array<AddFrogMetaData> addFrogsMetaData = new Array<AddFrogMetaData>();
    public static Array<ThemeMetaData> themesMetaData = new Array<ThemeMetaData>();
    private static XmlReader xmlReader = new XmlReader();

    static {
        loadConfig();
        // TODO this is temporary for testing
        themesMetaData.add(new ThemeMetaData(ThemeMetaData.DEFAULT_THEME.getClass(), 1));
    }

    private static void loadConfig() {
        LevelMapLoader.loadLevelMap(xmlReader, levelsMetaData, addFrogsMetaData);
    }

}
