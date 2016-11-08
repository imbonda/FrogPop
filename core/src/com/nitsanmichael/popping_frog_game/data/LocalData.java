package com.nitsanmichael.popping_frog_game.data;

import com.nitsanmichael.popping_frog_game.media.Media;
import com.nitsanmichael.popping_frog_game.playservice.PlayServices;
import com.nitsanmichael.popping_frog_game.screens.ChooseHeroScreen;

import java.util.HashMap;

/**
 *
 * This class exposes some basic API for manipulating the game saved local data and preferences.
 *
 * Created by MichaelBond on 9/26/2016.
 */
public class LocalData {

    private static final String PREFERENCES_NAME = "PoppingFrog-preferences";
    // Keys.
    private static final String HIGH_SCORE_KEY = "hs";
    private static final String HIGH_LEVEL_KEY = "hl";
    private static final String MUSIC_VOLUME_KEY = "mv";
    private static final String SOUND_VOLUME_KEY = "sv";
    private static final String CHOOSE_HERO_KEY = "ch";

    private static HashMap<PlayServices.LeaderBoard, String> leaderBoardStringToKey;
    static {
        leaderBoardStringToKey = new HashMap<PlayServices.LeaderBoard, String>();
        leaderBoardStringToKey.put(PlayServices.LeaderBoard.HIGHEST_SCORE, HIGH_SCORE_KEY);
        leaderBoardStringToKey.put(PlayServices.LeaderBoard.HIGHEST_LEVEL, HIGH_LEVEL_KEY);
    }

    // Private members.
    private LocalDataManager localDataManager;
    // Cache.
    private HashMap<String, CachedVariable> cachedKeyValues;


    public LocalData() {
        this.localDataManager = new LocalDataManager(PREFERENCES_NAME);
        // Initialize cache.
        this.cachedKeyValues = new HashMap<String, CachedVariable>();
        this.cachedKeyValues.put(HIGH_SCORE_KEY, new CachedVariable());
        this.cachedKeyValues.put(HIGH_LEVEL_KEY, new CachedVariable());
        this.cachedKeyValues.put(MUSIC_VOLUME_KEY, new CachedVariable());
        this.cachedKeyValues.put(SOUND_VOLUME_KEY, new CachedVariable());
        this.cachedKeyValues.put(CHOOSE_HERO_KEY, new CachedVariable());
    }

    /**
     * @return  The highest score on the given leader-board, achieved so far in the game.
     */
    public int getHighScore(PlayServices.LeaderBoard leaderBoard) {
        String key = leaderBoardStringToKey.get(leaderBoard);
        CachedVariable cachedHighScore = this.cachedKeyValues.get(key);
        if (null == cachedHighScore.intValue()) {
            int highScore = this.localDataManager.getInt(key, 0);
            cachedHighScore.set(highScore);
        }
        return cachedHighScore.intValue();
    }

    /**
     * Updated the highest score on the given leader-board, if necessary.
     *
     * @param leaderBoard The leader-board we are working on.
     * @param newScore  The newly achieved score.
     */
    synchronized public void updateHighScore(PlayServices.LeaderBoard leaderBoard, int newScore) {
        String key = leaderBoardStringToKey.get(leaderBoard);
        int highScore = getHighScore(leaderBoard);
        if (newScore > highScore) {
            this.localDataManager.saveInt(key, newScore);
            this.cachedKeyValues.get(key).set(newScore);
        }
    }

    /**
     * @return  The volume of the music that the user prefers, or 1 in case no prior adjustment
     *  has been made.
     */
    public float getMusicVolume() {
        CachedVariable cachedMusicVolume = this.cachedKeyValues.get(MUSIC_VOLUME_KEY);
        if (null == cachedMusicVolume.floatValue()) {
            float musicVolume = this.localDataManager.getFloat(
                        MUSIC_VOLUME_KEY, Media.DEFAULT_MUSIC_VOLUME);
            cachedMusicVolume.set(musicVolume);
        }
        return cachedMusicVolume.floatValue();
    }

    /**
     * Sets music's volume to the given value.
     *
     * @param volume  The volume to set to.
     */
    public void setMusicVolume(float volume) {
        this.localDataManager.saveFloat(MUSIC_VOLUME_KEY, volume);
        this.cachedKeyValues.get(MUSIC_VOLUME_KEY).set(volume);
    }

    /**
     * @return  The volume of the sound that the user prefers, or 1 in case no prior adjustment
     *  has been made.
     */
    public float getSoundVolume() {
        CachedVariable cachedSoundVolume = this.cachedKeyValues.get(SOUND_VOLUME_KEY);
        if (null == cachedSoundVolume.floatValue()) {
            float soundVolume = this.localDataManager.getFloat(
                        SOUND_VOLUME_KEY, Media.DEFAULT_SOUND_VOLUME);
            cachedSoundVolume.set(soundVolume);
        }
        return cachedSoundVolume.floatValue();
    }

    /**
     * Sets sound's volume to the given value.
     *
     * @param volume  The volume to set to.
     */
    public void setSoundVolume(float volume) {
        this.localDataManager.saveFloat(SOUND_VOLUME_KEY, volume);
        this.cachedKeyValues.get(SOUND_VOLUME_KEY).set(volume);
    }

    /**
     * @return  The index of the hero that is preferred by the user.
     */
    public int getHeroIndex() {
        CachedVariable cachedHeroIndex = this.cachedKeyValues.get(CHOOSE_HERO_KEY);
        if (null == cachedHeroIndex.intValue()) {
            int chooseHeroIndex = this.localDataManager.getInt(
                        CHOOSE_HERO_KEY, ChooseHeroScreen.DEFAULT_HERO_INDEX);
            cachedHeroIndex.set(chooseHeroIndex);
        }
        return cachedHeroIndex.intValue();
    }

    /**
     * Sets the user-preferred hero index.
     *
     * @param index The index of the new hero.
     */
    public void setHeroIndex(int index) {
        this.localDataManager.saveInt(CHOOSE_HERO_KEY, index);
        this.cachedKeyValues.get(CHOOSE_HERO_KEY).set(index);
    }

}
