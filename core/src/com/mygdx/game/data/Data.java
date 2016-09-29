package com.mygdx.game.data;

/**
 *
 * This class exposes some basic API for manipulating the game saved data and preferences.
 *
 * Created by MichaelBond on 9/26/2016.
 */
public class Data {

    private static final String PREFERENCES_NAME = "FrogPop-preferences";
    // Keys.
    private static final String HIGH_SCORE_KEY = "hs";
    private static final String MUSIC_MUTE_KEY = "mmt";
    private static final String SOUND_MUTE_KEY = "smt";

    private static Data ourInstance = new Data();

    /**
     * Singleton implementation.
     *
     * @return  The singleton object.
     */
    public static Data getInstance() {
        return ourInstance;
    }

    /**
     * Singleton private constructor.
     */
    private Data() {
        this.dataManager = new DataManager(PREFERENCES_NAME);
    }

    private DataManager dataManager;


    /**
     * @return  The highest score achieved so far in the game.
     */
    public int getHighScore() {
        return this.dataManager.getInt(HIGH_SCORE_KEY, 0);
    }

    /**
     * Updated the highest score if necessary.
     *
     * @param newScore  The newly achieved score.
     */
    public void updateHighScore(int newScore) {
        int highScore = getHighScore();
        if (newScore > highScore) {
            this.dataManager.saveInt(HIGH_SCORE_KEY, newScore);
        }
    }

    /**
     * @return  Whether or not the music preference is mute.
     */
    public boolean isMusicMuted() {
        return this.dataManager.getBoolean(MUSIC_MUTE_KEY, false);
    }

    /**
     * @return  Whether or not the sound preference is mute.
     */
    public boolean isSoundMuted() {
        return this.dataManager.getBoolean(SOUND_MUTE_KEY, false);
    }

    /**
     * Sets music's sound to the given preference.
     *
     * @param mute  If true music is to be muted, else to be sound.
     */
    public void setMusicMute(boolean mute) {
        this.dataManager.saveBoolean(MUSIC_MUTE_KEY, mute);
    }

    /**
     * Sets sound's sound to the given preference.
     *
     * @param mute  If true sound is to be muted, else to be sound.
     */
    public void setSoundMuteKey(boolean mute) {
        this.dataManager.saveBoolean(SOUND_MUTE_KEY, mute);
    }
}
