package com.nitsanmichael.popping_frog_game.states;

import com.nitsanmichael.popping_frog_game.screens.PlayScreen;


/**
 * Created by MichaelBond on 10/23/2016.
 */
public class BackToMenuState implements State {

    private PlayScreen playScreen;
    private boolean isReturningAlready;


    public BackToMenuState(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.isReturningAlready = false;
    }

    @Override
    public void render(float deltaTime) {
        backToMenu();
        update(deltaTime);
        this.playScreen.draw();
    }

    private void backToMenu() {
        if (!this.isReturningAlready) {
            this.playScreen.backToMenu();
        }
        this.isReturningAlready = true;
    }

    private void update(float deltaTime) {
        this.playScreen.themeController.update(deltaTime, playScreen.runtimeInfo.gameLevel);
        this.playScreen.hud.update();
    }

    @Override
    public void dispose() {

    }
}
