package com.nitsanmichael.popping_frog_game.states;

import com.badlogic.gdx.Gdx;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.input.GamePlayTouchProcessor;
import com.nitsanmichael.popping_frog_game.scenes.dialogs.PlayDialog;
import com.nitsanmichael.popping_frog_game.screens.PlayScreen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;


/**
 * Created by MichaelBond on 10/21/2016.
 */
public class PlayState implements State {

    private static final int MIN_SCORE_FOR_REWARDED_REPLAY = 80;
    private static final int GAME_OVER_ANIMATION_DURATION = 1;

    private PlayScreen playScreen;
    private PlayDialog playDialog;
    private boolean isNoLongerPlaying;


    public PlayState(PlayScreen playScreen, boolean isSetupNeeded) {
        this.playScreen = playScreen;
        this.isNoLongerPlaying = false;
        Gdx.input.setInputProcessor(new GamePlayTouchProcessor(
                    this.playScreen.gameViewPort, this.playScreen.runtimeInfo));
        this.playDialog = new PlayDialog(
                    this.playScreen.game.assetController,
                    this.playScreen.gameViewPort,
                    this.playScreen.game.batch,
                    this.playScreen.runtimeInfo);
        if (isSetupNeeded) {
            playScreen.levelController.setup();
        }
        // Play music.
        playScreen.game.media.playMusic(Assets.GAME_PLAY_MUSIC);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        this.playScreen.draw();
        this.playDialog.draw();
    }

    private void update(float deltaTime) {
        this.playScreen.themeController.update(deltaTime, playScreen.runtimeInfo.gameLevel);
        if (this.isNoLongerPlaying) {
            return;
        }
        this.playScreen.levelController.update(deltaTime);
        this.playScreen.holesManager.update(deltaTime);
        this.playScreen.hud.update();
        if (this.playScreen.runtimeInfo.gameLives.get() == 0 && isRewardedReplayAllowed()) {
            switchState(StateTracker.GameState.REWARDED_REPLAY);
        }
        else if (this.playScreen.runtimeInfo.gameLives.get() == 0) {
            switchState(StateTracker.GameState.OVER);
        }
    }

    private boolean isRewardedReplayAllowed() {
        boolean videoAvailable = this.playScreen.game.adsController.isRewardedVideoAvailable();
        boolean minReplayScoreReached =
                    this.playScreen.runtimeInfo.gameScore >= MIN_SCORE_FOR_REWARDED_REPLAY;
        boolean maxReplaysReached = this.playScreen.runtimeInfo.rewardedReplays > 0;
        return videoAvailable && minReplayScoreReached && (!maxReplaysReached);
    }

    private void switchState(final StateTracker.GameState state) {
        Gdx.input.setInputProcessor(null);
        this.playScreen.game.media.stopMusic(Assets.GAME_PLAY_MUSIC);
        this.playScreen.game.media.playSound(Assets.GAME_OVER_SOUND);
        this.playScreen.game.tweenController.gameOverAnimation(
                this.playScreen.runtimeInfo.activeFrogs, this.playScreen.runtimeInfo.frogGhosts,
                GAME_OVER_ANIMATION_DURATION,
                new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        playScreen.runtimeInfo.stateTracker.setState(state);
                    }
                }
        );
        this.isNoLongerPlaying = true;
    }

    @Override
    public void dispose() {
        this.playDialog.dispose();
    }
}
