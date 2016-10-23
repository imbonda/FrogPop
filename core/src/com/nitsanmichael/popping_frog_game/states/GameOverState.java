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
        gameOver();
        update(deltaTime);
        this.playScreen.draw();
    }

    private void gameOver() {
        if (!this.isOverAlready) {
            this.playScreen.gameOver();
        }
        this.isOverAlready = true;
    }

    private void update(float deltaTime) {
        this.playScreen.themeController.update(deltaTime, playScreen.runtimeInfo.gameLevel);
        this.playScreen.hud.update();
    }

    @Override
    public void dispose() {
        // Nothing to dispose.
    }

}
