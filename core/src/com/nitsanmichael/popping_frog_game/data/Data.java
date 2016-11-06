package com.nitsanmichael.popping_frog_game.data;

import com.nitsanmichael.popping_frog_game.media.Media;
import com.nitsanmichael.popping_frog_game.screens.ChooseHeroScreen;

/**
 *
 * This class exposes some basic API for manipulating the game saved data and preferences.
 *
 * Created by MichaelBond on 9/26/2016.
 */
public class Data {

    private static final String PREFERENCES_NAME = "PoppingFrog-preferences";
    // Keys.
    private static final String HIGH_SCORE_KEY = "hs";
    private static final String HIGH_LEVEL_KEY = "hl";
    private static final String MUSIC_VOLUME_KEY = "mv";
    private static final String SOUND_VOLUME_KEY = "sv";
    private static final String CHOOSE_HERO_KEY = "ch";
    // Private members.
    private DataManager dataManager;

    public Data() {
        this.dataManager = new DataManager(PREFERENCES_NAME);
    }

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
     * @return  The highest level achieved so far in the game.
     */
    public int getHighLevel() {
        return this.dataManager.getInt(HIGH_LEVEL_KEY, 0);
    }

    /**
     * Updated the highest level if necessary.
     *
     * @param newLevel  The newly achieved level.
     */
    public void updateHighLevel(int newLevel) {
        int highLevel = getHighLevel();
        if (newLevel > highLevel) {
            this.dataManager.saveInt(HIGH_LEVEL_KEY, newLevel);
        }
    }

    /**
     * @return  The volume of the music that the user prefers, or 1 in case no prior adjustment
     *  has been made.
     */
    public float getMusicVolume() {
        return this.dataManager.getFloat(MUSIC_VOLUME_KEY, Media.DEFAULT_MUSIC_VOLUME);
    }

    /**
     * Sets music's volume to the given value.
     *
     * @param volume  The volume to set to.
     */
    public void setMusicVolume(float volume) {
        this.dataManager.saveFloat(MUSIC_VOLUME_KEY, volume);
    }

    /**
     * @return  The volume of the sound that the user prefers, or 1 in case no prior adjustment
     *  has been made.
     */
    public float getSoundVolume() {
        return this.dataManager.getFloat(SOUND_VOLUME_KEY, Media.DEFAULT_SOUND_VOLUME);
    }

    /**
     * Sets sound's volume to the given value.
     *
     * @param volume  The volume to set to.
     */
    public void setSoundVolume(float volume) {
        this.dataManager.saveFloat(SOUND_VOLUME_KEY, volume);
    }

    /**
     * @return  The index of the hero that is preferred by the user.
     */
    public int getHeroIndex() {
        return this.dataManager.getInt(CHOOSE_HERO_KEY, ChooseHeroScreen.DEFAULT_HERO_INDEX);
    }

    /**
     * Sets the user-preferred hero index.
     *
     * @param index The index of the new hero.
     */
    public void setHeroIndex(int index) {
        this.dataManager.saveInt(CHOOSE_HERO_KEY, index);
    }


}
