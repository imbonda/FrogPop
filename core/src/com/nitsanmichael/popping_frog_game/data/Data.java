package com.nitsanmichael.popping_frog_game.data;

import com.nitsanmichael.popping_frog_game.playservice.PlayServices;


/**
 * This class exposes some basic API for manipulating the game local and non-local data -
 * (such as highest scores via play-services).
 *
 * Created by MichaelBond on 11/8/2016.
 */
public class Data {

    private LocalData localData;
    private PlayServices playServices;


    public Data(PlayServices playServices) {
        this.playServices = playServices;
        this.localData = new LocalData();
    }

    /**
     * @return  The highest score on the given leader-board, achieved so far in the game.
     */
    public int getHighScore(PlayServices.LeaderBoard leaderBoard) {
        return this.localData.getHighScore(leaderBoard);
    }

    /**
     * Updated the highest score on the given leader-board, if necessary.
     *
     * @param leaderBoard The leader-board we are working on.
     * @param newScore  The newly achieved score.
     */
    public void submitScore(PlayServices.LeaderBoard leaderBoard, int newScore) {
        this.localData.updateHighScore(leaderBoard, newScore);
        if (this.playServices.isSignedIn()) {
            this.playServices.submitScore(leaderBoard, newScore);
        }
    }

    /**
     * Synchronizes the local score with the score on the play-service's leader-board.
     *
     * @param leaderBoard The leader-board we are working on.
     * @param externalScore  The score on the external play service leader-board.
     */
    public void syncLocalScore(PlayServices.LeaderBoard leaderBoard, int externalScore) {
        this.localData.updateHighScore(leaderBoard, externalScore);
    }

    /**
     * @return  The volume of the music that the user prefers, or 1 in case no prior adjustment
     *  has been made.
     */
    public float getMusicVolume() {
        return this.localData.getMusicVolume();
    }

    /**
     * Sets music's volume to the given value.
     *
     * @param volume  The volume to set to.
     */
    public void setMusicVolume(float volume) {
        this.localData.setMusicVolume(volume);
    }

    /**
     * @return  The volume of the sound that the user prefers, or 1 in case no prior adjustment
     *  has been made.
     */
    public float getSoundVolume() {
        return this.localData.getSoundVolume();
    }

    /**
     * Sets sound's volume to the given value.
     *
     * @param volume  The volume to set to.
     */
    public void setSoundVolume(float volume) {
        this.localData.setSoundVolume(volume);
    }

    /**
     * @return  The index of the hero that is preferred by the user.
     */
    public int getHeroIndex() {
        return this.localData.getHeroIndex();
    }

    /**
     * Sets the user-preferred hero index.
     *
     * @param index The index of the new hero.
     */
    public void setHeroIndex(int index) {
        this.localData.setHeroIndex(index);
    }

    /**
     * @return  True if the media is muted, false otherwise.
     */
    public boolean isMute() {
        return this.localData.isMute();
    }

    /**
     * Sets whether or not the media is to be muted.
     * @param isMute true if the media is to be muted, false otherwise.
     */
    public void setMute(boolean isMute) {
        this.localData.setMute(isMute);
    }

}
