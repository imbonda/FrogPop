package com.mygdx.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.config.Config;
import com.mygdx.game.media.Media;

/**
 * Created by MichaelBond on 9/27/2016.
 */
public class AssetController {

    private static final String MUSIC_FILES [] = {
            Media.MUSIC
    };
    private static final String SOUND_FILES [] = {
            Media.LEVEL_UP_SOUND,
            Media.GAME_OVER_SOUND
    };

    // Public members.
    public Config config;
    // Private members.
    private AssetManager manager;

    public AssetController() {
        this.manager = new AssetManager();
    }


    public void loadAll() {
        // todo
        loadMusics();
        loadSounds();
        this.manager.finishLoading();
        // Load config.
        this.config = new Config();
        this.config.load();
    }

    private void loadMusics() {
        for (String fileName : MUSIC_FILES) {
            this.manager.load(fileName, Music.class);
        }
    }

    private void loadSounds() {
        for (String fileName : SOUND_FILES) {
            this.manager.load(fileName, Sound.class);
        }
    }

    public <T> T get(String fileName) {
        return this.manager.get(fileName);
    }

    public void unload(String fileName) {
        this.manager.unload(fileName);
    }

    public void clearAll() {
        this.manager.clear();
    }
}
