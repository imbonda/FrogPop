package com.nitsanmichael.popping_frog_game.states;

import com.nitsanmichael.popping_frog_game.screens.PlayScreen;


/**
 * Created by MichaelBond on 10/23/2016.
 */
public class BackToMenuState implements State {

    private PlayScreen playScreen;


    public BackToMenuState(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.playScreen.backToMenu();
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        this.playScreen.draw();
    }

    private void update(float deltaTime) {
        this.playScreen.themeController.update(deltaTime, playScreen.runtimeInfo.gameLevel);
        this.playScreen.hud.update();
    }

    @Override
    public void dispose() {

    }
}
