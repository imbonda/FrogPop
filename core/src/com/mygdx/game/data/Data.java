package com.mygdx.game.data;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.media.Media;
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
    public float getChoosenHero() {
        return this.dataManager.getFloat(CHOOSE_HERO_KEY, 0);
    }
    public float setChoosenHero() {
        return this.dataManager.getFloat(CHOOSE_HERO_KEY, 0);
    }


}
