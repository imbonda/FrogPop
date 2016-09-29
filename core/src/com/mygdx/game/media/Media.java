package com.mygdx.game.media;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.assets.AssetController;

/**
 * This class exposes some basic API for using the game media.
 *
 * Created by MichaelBond on 9/29/2016.
 */
public class Media {

    // File names.
    public static final String MUSIC = "media/music.ogg";
    public static final String LEVEL_UP_SOUND = "media/level_up.wav";
    // Media default configurations.
    private static final float DEFAULT_MUSIC_VOLUME = 1;
    private static final float DEFAULT_SOUND_VOLUME = 1;

    private AssetController assetController;
    private float musicVolume;
    private float soundVolume;

    public Media(AssetController assetController) {
        this.assetController = assetController;
        this.musicVolume = DEFAULT_MUSIC_VOLUME;
        this.soundVolume = DEFAULT_SOUND_VOLUME;
    }

    public void playMusic(){
        Music music = this.assetController.get(MUSIC);
        if (null != music) {
            music.setVolume(this.musicVolume);
            music.setLooping(true);
            music.play();
        }
        // todo
    }

    public void updateMusicVolume() {
        // todo
    }

    public void playSound(String fileName) {
        Sound sound = this.assetController.get(fileName);
        if (null != sound) {
            long soundId = sound.play();
            sound.setVolume(soundId, this.soundVolume);
        }
        // todo
    }

    public void updateSoundVolume(String name) {
        // todo
    }
}
