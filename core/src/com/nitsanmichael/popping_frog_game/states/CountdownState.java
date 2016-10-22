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
        this.playScreen.themeController.update(deltaTime, playScreen.runtimeInfo.gameLevel);
    }

    @Override
    public void dispose() {
        // Nothing to dispose.
    }
}
