package com.nitsanmichael.popping_frog_game.scenes.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButton;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButtonListener;
import com.nitsanmichael.popping_frog_game.scenes.events.MessageEventListener;
import com.nitsanmichael.popping_frog_game.states.StateTracker;


/**
 * Created by MichaelBond on 10/27/2016.
 */
public class RewardedVideoDialog implements Disposable {

    private Stage stage;


    public RewardedVideoDialog(AssetController assetController, Viewport viewport, Batch batch,
                                final RuntimeInfo runtimeInfo) {
        // Rewarded video button.
        Texture rewardedReplayIcon = assetController.get(Assets.REWARDED_REPLAY_ICON);
        Texture rewardedReplayPressedIcon = assetController.get(Assets.REWARDED_REPLAY_PRESSED_ICON);
        ToggleButton rewardedReplayButton = new ToggleButton(
                    new Image(rewardedReplayIcon), new Image(rewardedReplayPressedIcon));
        rewardedReplayButton.setSize(250, 150);
        rewardedReplayButton.setPosition(265, 220);
        rewardedReplayButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
//                if (actor != playButton || message != ToggleButtonListener.ON_TOUCH_UP) {
//                    return;
//                }
//                runtimeInfo.stateTracker.setState(StateTracker.GameState.PLAY);
            }
        });

        // X button.
        Texture xIcon = assetController.get(Assets.X_ICON);
        Texture xPressedIcon = assetController.get(Assets.X_PRESSED_ICON);
        final ToggleButton xButton = new ToggleButton(
                    new Image(xIcon), new Image(xPressedIcon));
        xButton.setSize(30, 27);
        xButton.setPosition(490, 350);
        xButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != xButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                runtimeInfo.stateTracker.setState(StateTracker.GameState.OVER);
            }
        });

        setStage(viewport, batch, rewardedReplayButton, xButton);
    }

    private void setStage(Viewport viewport, Batch batch,
                            ToggleButton rewardedReplayButton, ToggleButton xButton) {
        this.stage = new Stage(viewport, batch);
        this.stage.addActor(rewardedReplayButton);
        this.stage.addActor(xButton);
        Gdx.input.setInputProcessor(this.stage);
    }

    public void draw() {
        this.stage.draw();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }

}
