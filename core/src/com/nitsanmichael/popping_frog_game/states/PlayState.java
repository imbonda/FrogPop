package com.nitsanmichael.popping_frog_game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.nitsanmichael.popping_frog_game.managment.GamePlayTouchProcessor;
import com.nitsanmichael.popping_frog_game.screens.PlayScreen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;


/**
 * Created by MichaelBond on 10/21/2016.
 */
public class PlayState implements State {

    private static final int MIN_SCORE_FOR_REWARDED_REPLAY = 0;
    private static final int GAME_OVER_ANIMATION_DURATION = 1;

    private PlayScreen playScreen;
    private boolean isNoLongerPlaying;


    public PlayState(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.isNoLongerPlaying = false;
        Gdx.input.setInputProcessor(new GamePlayTouchProcessor(
                this.playScreen.gameViewPort, this.playScreen.runtimeInfo));
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        this.playScreen.draw();
    }

    private void update(float deltaTime) {
        this.playScreen.themeController.update(deltaTime, playScreen.runtimeInfo.gameLevel);
        if (this.isNoLongerPlaying) {
            return;
        }
        this.playScreen.levelController.update(deltaTime);
        this.playScreen.holesManager.update(deltaTime);
        this.playScreen.hud.update();
        if (this.playScreen.runtimeInfo.gameLives <= 0 && isRewardedReplayAllowed()) {
            Gdx.input.setInputProcessor(null);
            this.playScreen.game.tweenController.frogsGhostGameOverAnimation(
                    this.playScreen.runtimeInfo.frogGhosts, GAME_OVER_ANIMATION_DURATION, null);
            this.playScreen.game.tweenController.frogsGameOverAnimation(
                    this.playScreen.runtimeInfo.activeFrogs, GAME_OVER_ANIMATION_DURATION,
                        new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                switchState(StateTracker.GameState.REWARDED_REPLAY);
                            }
                    }
            );
            this.isNoLongerPlaying = true;
        }
        else if (this.playScreen.runtimeInfo.gameLives <= 0) {
            Gdx.input.setInputProcessor(null);
            this.playScreen.game.tweenController.frogsGhostGameOverAnimation(
                    this.playScreen.runtimeInfo.frogGhosts, GAME_OVER_ANIMATION_DURATION, null);
            this.playScreen.game.tweenController.frogsGameOverAnimation(
                    this.playScreen.runtimeInfo.activeFrogs, GAME_OVER_ANIMATION_DURATION,
                        new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                switchState(StateTracker.GameState.OVER);
                            }
                    }
            );
            this.isNoLongerPlaying = true;
        }
    }

    private boolean isRewardedReplayAllowed() {
        return this.playScreen.runtimeInfo.gameScore >= MIN_SCORE_FOR_REWARDED_REPLAY;
    }

    private void switchState(StateTracker.GameState state) {
        this.playScreen.runtimeInfo.stateTracker.setState(state);
    }

    @Override
    public void dispose() {
        // Nothing to dispose.
    }
}
