package com.nitsanmichael.popping_frog_game.scenes.popups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.tweens.TweenController;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

/**
 * Created by MichaelBond on 10/12/2016.
 */
public class CountdownPopup implements Popup {

    // Popup messages.
    private static final String COUNTDOWN_1 = "1";
    private static final String COUNTDOWN_2 = "2";
    private static final String COUNTDOWN_3 = "3";
    private static final String COUNTDOWN_GO = "TAP 'em !";
    // Font.
    private static final float INITIAL_FONT_SCALE = 0.7f;
    private static final Color GREEN = Color.LIME;
    private static final Vector2 SRC_FONT_SCALE = new Vector2(0.9f, 0.1f);
    private static final Vector2 DST_FONT_SCALE = new Vector2(0.9f, 0.9f);
    private static final Vector2 POSITION_COUNT = new Vector2(370, 270);
    private static final Vector2 POSITION_GO = new Vector2(210, 270);

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
        style.fontColor = GREEN;
        this.countDownLabel = new Label("", style);
        this.countDownLabel.setPosition(POSITION_COUNT.x, POSITION_COUNT.y);
        this.countDownLabel.setFontScale(INITIAL_FONT_SCALE);
    }

    @Override
    public void perform() {
        this.stage.addActor(this.countDownLabel);
        countdown3();
    }

    private void countdown3() {
        this.countDownLabel.setText(COUNTDOWN_3);
        this.tweenController.popupLabelFontScale(
                this.countDownLabel, SRC_FONT_SCALE, DST_FONT_SCALE, 0.5f, 0, 0, null);
        this.tweenController.popupLabelFade(this.countDownLabel, 0.5f, 0.1f, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                countDownLabel.setFontScale(INITIAL_FONT_SCALE);
                countdown2();
            }
        });
    }

    private void countdown2() {
        this.countDownLabel.setText(COUNTDOWN_2);
        this.tweenController.popupLabelFontScale(
                this.countDownLabel, SRC_FONT_SCALE, DST_FONT_SCALE, 0.5f, 0, 0, null);
        this.tweenController.popupLabelFade(this.countDownLabel, 0.5f, 0.1f, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                countDownLabel.setFontScale(INITIAL_FONT_SCALE);
                countdown1();
            }
        });
    }

    private void countdown1() {
        this.countDownLabel.setText(COUNTDOWN_1);
        this.tweenController.popupLabelFontScale(
                this.countDownLabel, SRC_FONT_SCALE, DST_FONT_SCALE, 0.5f, 0, 0, null);
        this.tweenController.popupLabelFade(this.countDownLabel, 0.5f, 0.1f, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                countDownLabel.setFontScale(INITIAL_FONT_SCALE);
                countdownGo();
            }
        });
    }

    private void countdownGo() {
        this.countDownLabel.setPosition(POSITION_GO.x, POSITION_GO.y);
        this.countDownLabel.setText(COUNTDOWN_GO);
        this.tweenController.popupLabelFontScale(
                this.countDownLabel, SRC_FONT_SCALE, DST_FONT_SCALE, 0.5f, 0, 0, null);
        this.tweenController.popupLabelFade(this.countDownLabel, 0.5f, 0.1f, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                countDownLabel.setFontScale(INITIAL_FONT_SCALE);
                // Remove the label from the stage.
                countDownLabel.remove();
                runtimeInfo.gameStarted = true;
            }
        });
    }

}
