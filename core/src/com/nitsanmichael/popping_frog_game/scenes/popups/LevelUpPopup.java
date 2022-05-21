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
public class LevelUpPopup implements Popup {

    private static final float INITIAL_FONT_SCALE = 0.3f;
    private static final float LABEL_HEIGHT = 30;
    private static final String LEVEL_UP_MESSAGE = "Level ";
    private static final Vector2 SRC_FONT_SCALE = new Vector2(0.3f, 0.1f);
    private static final Vector2 DST_FONT_SCALE = new Vector2(0.7f, 0.7f);
    private static final Vector2 POSITION = new Vector2(260, 400);
    private static final Color POPUP_COLOR = new Color(0x25576077);

    private Stage stage;
    private RuntimeInfo runtimeInfo;
    private TweenController tweenController;
    private Label levelUpLabel;

    public LevelUpPopup(RuntimeInfo runtimeInfo, Stage stage, TweenController tweenController, BitmapFont font) {
        this.runtimeInfo = runtimeInfo;
        this.stage = stage;
        this.tweenController = tweenController;
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = POPUP_COLOR;
        this.levelUpLabel = new Label(LEVEL_UP_MESSAGE, style);
        this.levelUpLabel.setPosition(POSITION.x, POSITION.y);
        this.levelUpLabel.setFontScale(INITIAL_FONT_SCALE);
        this.levelUpLabel.setHeight(LABEL_HEIGHT);
    }

    @Override
    public void perform() {
        // TODO this is a hotfix (update is made only in the next iteration, so alpha=1 for 1 frame).
        this.levelUpLabel.getStyle().fontColor.a = 0;
        this.levelUpLabel.setText(LEVEL_UP_MESSAGE + this.runtimeInfo.gameLevel + " !");
        this.stage.addActor(this.levelUpLabel);
        this.tweenController.actorFade(this.levelUpLabel, 0.5f, 0, 1, 1, 0.5f, null);
        this.tweenController.labelFontScale(
                this.levelUpLabel,
                SRC_FONT_SCALE, DST_FONT_SCALE, 0.5f, 1, 0.5f, new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        levelUpLabel.setFontScale(INITIAL_FONT_SCALE);
                        // Remove the label from the stage.
                        levelUpLabel.remove();
                    }
                });
    }

}
