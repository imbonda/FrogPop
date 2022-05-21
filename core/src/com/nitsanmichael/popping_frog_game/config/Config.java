package com.nitsanmichael.popping_frog_game.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.nitsanmichael.popping_frog_game.config.metadata.AddFrogMetaData;
import com.nitsanmichael.popping_frog_game.config.metadata.HeroSpecMetaData;
import com.nitsanmichael.popping_frog_game.config.metadata.LevelMetaData;
import com.nitsanmichael.popping_frog_game.config.metadata.ThemeMetaData;

import java.util.HashMap;

/**
 * This class will hold all the configurations necessary for the game.
 *
 * Created by MichaelBond on 9/8/2016.
 */
public class Config {

    public Array<LevelMetaData> levelsMetaData;
    public Array<AddFrogMetaData> addFrogsMetaData;
    public Array<ThemeMetaData> themesMetaData;
    public HashMap<Class<? extends Actor>, HeroSpecMetaData> heroesSpec;
    private XmlReader xmlReader;

    public Config() {
        this.xmlReader = new XmlReader();
        this.levelsMetaData = new Array<LevelMetaData>();
        this.addFrogsMetaData = new Array<AddFrogMetaData>();
        this.themesMetaData = new Array<ThemeMetaData>();
        this.heroesSpec = new HashMap<Class<? extends Actor>, HeroSpecMetaData>();
    }

    public void load() {
        loadLevelsMetaData();
        loadAddFrogsMetaData();
        loadThemesMetaData();
        loadHeroesSpec();
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

    private void loadHeroesSpec() {
        HeroesSpecMetaDataLoader.load(this.xmlReader, this.heroesSpec);
    }

}
