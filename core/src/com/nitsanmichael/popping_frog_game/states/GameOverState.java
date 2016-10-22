package com.nitsanmichael.popping_frog_game.states;

import com.nitsanmichael.popping_frog_game.screens.PlayScreen;


/**
 * Created by MichaelBond on 10/22/2016.
 */
public class GameOverState implements State {

    private PlayScreen playScreen;
    private boolean isOverAlready;


    public GameOverState(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.isOverAlready = false;
    }

    @Override
    public void render(float deltaTime) {
        if (!this.isOverAlready) {
            this.playScreen.gameOver();
        }
        this.isOverAlready = true;
    }

    @Override
    public void dispose() {
        // Nothing to dispose.
    }

}
