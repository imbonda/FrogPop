package com.nitsanmichael.popping_frog_game.states;

import com.nitsanmichael.popping_frog_game.scenes.dialogs.RewardedVideoDialog;
import com.nitsanmichael.popping_frog_game.screens.PlayScreen;


/**
 * Created by MichaelBond on 10/27/2016.
 */
public class RewardedReplayState implements State {

    private PlayScreen playScreen;
    private RewardedVideoDialog rewardedVideoDialog;


    public RewardedReplayState(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.rewardedVideoDialog = new RewardedVideoDialog(
                this.playScreen.game.assetController,
                this.playScreen.gameViewPort,
                this.playScreen.game.batch,
                this.playScreen.runtimeInfo
        );
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        this.playScreen.draw();
        this.rewardedVideoDialog.draw();
    }

    private void update(float deltaTime) {
        this.playScreen.themeController.update(deltaTime, playScreen.runtimeInfo.gameLevel);
        this.rewardedVideoDialog.update(deltaTime);
    }

    @Override
    public void dispose() {
        this.rewardedVideoDialog.dispose();
    }
}
