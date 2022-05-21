package com.nitsanmichael.popping_frog_game.states;

import com.badlogic.gdx.Gdx;
import com.nitsanmichael.popping_frog_game.input.GamePauseTouchProcessor;
import com.nitsanmichael.popping_frog_game.scenes.dialogs.PauseDialog;
import com.nitsanmichael.popping_frog_game.screens.PlayScreen;


/**
 * Created by MichaelBond on 10/21/2016.
 */
public class PauseState implements State {

    private PlayScreen playScreen;
    private PauseDialog pauseDialog;


    public PauseState(PlayScreen playScreen) {
        this.playScreen = playScreen;
        Gdx.input.setInputProcessor(new GamePauseTouchProcessor(playScreen.runtimeInfo.stateTracker));
        this.pauseDialog = new PauseDialog(
                    this.playScreen.game.assetController,
                    this.playScreen.game.data,
                    this.playScreen.game.media,
                    this.playScreen.gameViewPort,
                    this.playScreen.game.batch,
                    this.playScreen.runtimeInfo);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        this.playScreen.draw();
        this.playScreen.game.batch.setProjectionMatrix(
                    this.playScreen.backgroundViewport.getCamera().combined);
        this.pauseDialog.draw();
    }

    private void update(float deltaTime) {
        this.playScreen.themeController.update(deltaTime, playScreen.runtimeInfo.gameLevel);
    }

    @Override
    public void dispose() {
        this.pauseDialog.dispose();
    }
}
