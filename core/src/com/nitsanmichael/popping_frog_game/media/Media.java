package com.nitsanmichael.popping_frog_game.media;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;

/**
 * This class exposes some basic API for using the game media.
 *
 * Created by MichaelBond on 9/29/2016.
 */
public class Media {

    // Media default configurations.
    public static final float DEFAULT_MUSIC_VOLUME = 1;
    public static final float DEFAULT_SOUND_VOLUME = 1;

    private AssetController assetController;
    private float musicVolume;
    private float soundVolume;

    public Media(AssetController assetController, float musicVolume, float soundVolume) {
        this.assetController = assetController;
        this.musicVolume = musicVolume;
        this.soundVolume = soundVolume;
    }

    public void playMusic(String name){
        Music music = this.assetController.get(name);
        if (null != music && 0 != this.musicVolume) {
            music.setVolume(this.musicVolume);
            music.setLooping(true);
            music.play();
        }
    }

    public void stopMusic(String name) {
        Music music = this.assetController.get(name);
        if (null != music) {
            music.stop();
        }
    }

    public void pauseMusic(String name) {
        Music music = this.assetController.get(name);
        if (null != music) {
            music.pause();
        }
    }

    public void updateMusicVolume(float volume) {
        this.musicVolume = volume;
        for (String name : Assets.MUSIC_FILES) {
            Music music = this.assetController.get(name);
            if (null != music) {
                music.setVolume(volume);
            }
        }
    }

    public void playSound(String name) {
        Sound sound = this.assetController.get(name);
        if (null != sound && 0 != this.soundVolume) {
            long soundId = sound.play();
            sound.setVolume(soundId, this.soundVolume);
        }
    }

    public void updateSoundVolume(float volume) {
        this.soundVolume = volume;
    }
}
