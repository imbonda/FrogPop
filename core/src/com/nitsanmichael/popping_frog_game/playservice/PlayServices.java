package com.nitsanmichael.popping_frog_game.playservice;


/**
 * Created by MichaelBond on 10/9/2016.
 */
public interface PlayServices {

    enum LeaderBoard {
        HIGHEST_SCORE,
        HIGHEST_LEVEL
    }

    void signIn();

    void signOut();

    void rateGame();

    void unlockAchievement();

    void submitScore(LeaderBoard leaderBoard, int highScore);

    void showAchievement();

    void showLeaderBoards();

    boolean isSignedIn();

}
