package com.nitsanmichael.popping_frog_game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.nitsanmichael.popping_frog_game.managment.GamePlayTouchProcessor;
import com.nitsanmichael.popping_frog_game.screens.PlayScreen;


/**
 * Created by MichaelBond on 10/21/2016.
 */
public class PlayState implements State {

    private static final int MIN_SCORE_FOR_REWARDED_REPLAY = 0;

    private PlayScreen playScreen;


    public PlayState(PlayScreen playScreen) {
        this.playScreen = playScreen;
        Gdx.input.setInputProcessor(new GamePlayTouchProcessor(
                this.playScreen.gameViewPort, this.playScreen.runtimeInfo));
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        this.playScreen.draw();
    }

    private void update(float deltaTime) {
        this.playScreen.levelController.update(deltaTime);
        this.playScreen.themeController.update(deltaTime, playScreen.runtimeInfo.gameLevel);
        this.playScreen.hud.update();
        if (this.playScreen.runtimeInfo.gameLives <= 0 && isRewardedReplayAllowed()) {
            this.playScreen.runtimeInfo.stateTracker.setState(StateTracker.GameState.REWARDED_REPLAY);
        }
        else if (this.playScreen.runtimeInfo.gameLives <= 0) {
            this.playScreen.runtimeInfo.stateTracker.setState(StateTracker.GameState.OVER);
        }
    }

    private boolean isRewardedReplayAllowed() {
        return this.playScreen.runtimeInfo.gameScore >= MIN_SCORE_FOR_REWARDED_REPLAY;
    }

    @Override
    public void dispose() {
        // Nothing to dispose.
    }
}
