package com.nitsanmichael.popping_frog_game.scenes.popups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.screens.PlayScreen;
import com.nitsanmichael.popping_frog_game.states.StateTracker;
import com.nitsanmichael.popping_frog_game.tweens.TweenController;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

/**
 * Created by MichaelBond on 10/12/2016.
 */
public class CountdownPopup implements Popup {

    // Popup messages.
    private static final String READY = "Ready";
    private static final String STEADY = "Steady";
    private static final String GO = "Goooo";
    // Font.
    private static final float INITIAL_FONT_SCALE = 0.7f;
    private static final Color GREEN_LIME = new Color(0x32cd32ff);
    private static final Vector2 SRC_FONT_SCALE = new Vector2(0.9f, 0.1f);
    private static final Vector2 DST_FONT_SCALE = new Vector2(0.9f, 0.9f);
    private static final Vector2 POSITION_READY = new Vector2(260, 270);
    private static final Vector2 POSITION_STEADY = new Vector2(255, 270);
    private static final Vector2 POSITION_GO = new Vector2(265, 270);

    private TweenController tweenController;
    private RuntimeInfo runtimeInfo;
    private Stage stage;
    private Label countDownLabel;


    public CountdownPopup(TweenController tweenController, RuntimeInfo runtimeInfo, Stage stage,
                            BitmapFont font) {
        this.runtimeInfo = runtimeInfo;
        this.stage = stage;
        this.tweenController = tweenController;
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = GREEN_LIME;
        this.countDownLabel = new Label("", style);
    }

    @Override
    public void perform() {
        this.stage.addActor(this.countDownLabel);
        ready();
    }

    private void ready() {
        // TODO this is a hotfix (update is made only in the next iteration, so alpha=1 for 1 frame).
        this.countDownLabel.getStyle().fontColor.a = 0;
        this.countDownLabel.setPosition(POSITION_READY.x, POSITION_READY.y);
        this.countDownLabel.setFontScale(INITIAL_FONT_SCALE);
        this.countDownLabel.setText(READY);
        this.tweenController.labelFontScale(
                this.countDownLabel, SRC_FONT_SCALE, DST_FONT_SCALE, 0.5f, 0, 0, null);
        this.tweenController.actorFade(this.countDownLabel, 0.5f, 0, 1, 1, 0.1f, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                steady();
            }
        });
    }

    private void steady() {
        this.countDownLabel.setPosition(POSITION_STEADY.x, POSITION_STEADY.y);
        countDownLabel.setFontScale(INITIAL_FONT_SCALE);
        this.countDownLabel.setText(STEADY);
        this.tweenController.labelFontScale(
                this.countDownLabel, SRC_FONT_SCALE, DST_FONT_SCALE, 0.5f, 0, 0, null);
        this.tweenController.actorFade(this.countDownLabel, 0.5f, 0, 1, 1, 0.1f, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                go();
            }
        });
    }

    private void go() {
        this.countDownLabel.setPosition(POSITION_GO.x, POSITION_GO.y);
        countDownLabel.setFontScale(INITIAL_FONT_SCALE);
        this.countDownLabel.setText(GO);
        this.tweenController.labelFontScale(
                this.countDownLabel, SRC_FONT_SCALE, DST_FONT_SCALE, 0.5f, 0, 0, null);
        this.tweenController.actorFade(this.countDownLabel, 0.5f, 0, 1, 1, 0.1f,
                    new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            countDownLabel.setFontScale(INITIAL_FONT_SCALE);
                            // Remove the label from the stage.
                            countDownLabel.remove();
                            runtimeInfo.stateTracker.setState(StateTracker.GameState.PLAY);
                        }
        });
    }

}
