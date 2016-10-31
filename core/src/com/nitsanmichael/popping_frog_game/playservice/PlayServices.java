package com.nitsanmichael.popping_frog_game.playservice;


/**
 * Created by MichaelBond on 10/9/2016.
 */
public interface PlayServices {

    void signIn();

    void signOut();

    void rateGame();

    void unlockAchievement();

    void submitScore(int highScore);

    void showAchievement();

    void showScore();

    boolean isSignedIn();

}
