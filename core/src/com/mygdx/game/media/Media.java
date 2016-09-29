package com.mygdx.game.media;

/**
 * This class exposes some basic API for using the game media.
 *
 * Created by MichaelBond on 9/29/2016.
 */
public class Media {

    private static Media ourInstance = new Media();

    /**
     * Singleton implementation.
     *
     * @return  The singleton object.
     */
    public static Media getInstance() {
        return ourInstance;
    }

    /**
     * Singleton private constructor.
     */
    private Media() {
    }

    public void playMusic(){
        // todo
    }

    public void updateMusicVolume() {
        // todo
    }

    public void playSound(String name) {
        // todo
    }

    public void updateSoundVolume(String name) {
        // todo
    }
}
