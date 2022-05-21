package com.nitsanmichael.popping_frog_game.desktop;

import com.nitsanmichael.popping_frog_game.playservice.PlayServices;

/**
 * Created by MichaelBond on 10/9/2016.
 */
public class DummyPlayServices implements PlayServices {

    @Override
    public boolean isSignedIn() {
        return false;
    }

    @Override
    public void signIn() {
    }

    @Override
    public void signOut() {
    }

    @Override
    public void rateGame() {
    }

    @Override
    public void unlockAchievement() {
    }

    @Override
    public void submitScore(LeaderBoard leaderBoard, int highScore) {
    }

    @Override
    public void showAchievement() {
    }

    @Override
    public void showLeaderBoards() {
    }

}
