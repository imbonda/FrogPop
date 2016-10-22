package com.nitsanmichael.popping_frog_game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.nitsanmichael.popping_frog_game.managment.GamePlayTouchProcessor;
import com.nitsanmichael.popping_frog_game.screens.PlayScreen;


/**
 * Created by MichaelBond on 10/21/2016.
 */
public class PlayState implements State {

    private PlayScreen playScreen;


    public PlayState(PlayScreen playScreen) {
        this.playScreen = playScreen;
        Gdx.input.setInputProcessor(new GamePlayTouchProcessor(
                this.playScreen.gameViewPort, this.playScreen.runtimeInfo));
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
    }

    private void update(float deltaTime) {
        this.playScreen.levelController.update(deltaTime);
        this.playScreen.themeController.update(deltaTime, playScreen.runtimeInfo.gameLevel);
        this.playScreen.hud.update();
        if (this.playScreen.runtimeInfo.gameLives <= 0) {
            this.playScreen.runtimeInfo.stateTracker.setState(StateTracker.GameState.OVER);
        }
    }

    @Override
    public void dispose() {
        // Nothing to dispose.
    }
}
