package com.nitsanmichael.popping_frog_game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
        this.pauseDialog = new PauseDialog(
                    this.playScreen.game.assetController,
                    this.playScreen.gameViewPort,
                    this.playScreen.game.batch,
                    this.playScreen.runtimeInfo);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.playScreen.game.batch.setProjectionMatrix(playScreen.gameViewPort.getCamera().combined);
        this.playScreen.game.batch.begin();
        this.playScreen.themeController.currentTheme.draw(playScreen.game.batch);
        this.playScreen.spritesDrawer.drawSprites(playScreen.game.batch);
        this.playScreen.effectDrawer.drawEffects(playScreen.game.batch);
        this.playScreen.game.batch.end();
        this.playScreen.popupDrawer.drawPopups();
        this.playScreen.hud.draw();
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
