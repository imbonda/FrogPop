package com.nitsanmichael.popping_frog_game.playservices;

import com.nitsanmichael.popping_frog_game.playservice.PlayServices;

import java.util.HashMap;

/**
 * Created by MichaelBond on 11/8/2016.
 */
public class PlayServicesData {

    private static final int DEFAULT_HIGHEST_SCORE = 0;

    private HashMap<PlayServices.LeaderBoard, Integer> bestScores;


    public PlayServicesData() {
        this.bestScores = new HashMap<>();
        for (PlayServices.LeaderBoard leaderBoard : PlayServices.LeaderBoard.values()) {
            this.bestScores.put(leaderBoard, DEFAULT_HIGHEST_SCORE);
        }
    }

    public void updateScore(PlayServices.LeaderBoard leaderBoard, int newScore) {
        if (this.bestScores.get(leaderBoard) < newScore) {
            this.bestScores.put(leaderBoard, newScore);
        }
    }

    public int getHighestScore(PlayServices.LeaderBoard leaderBoard) {
        return this.bestScores.get(leaderBoard);
    }

}
