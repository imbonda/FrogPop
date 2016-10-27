package com.nitsanmichael.popping_frog_game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.nitsanmichael.popping_frog_game.scenes.PopupDrawer;
import com.nitsanmichael.popping_frog_game.screens.PlayScreen;


/**
 * Created by MichaelBond on 10/23/2016.
 */
public class CountdownState implements State {

    private PlayScreen playScreen;


    public CountdownState(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.playScreen.popupDrawer.register(PopupDrawer.PopupType.COUNTDOWN);
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
        // Nothing to dispose.
    }
}
