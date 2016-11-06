package com.nitsanmichael.popping_frog_game.scenes.popups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.nitsanmichael.popping_frog_game.tweens.TweenController;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;


/**
 * Created by MichaelBond on 10/12/2016.
 */
public class LevelUpPopup implements Popup {

    private static final float INITIAL_FONT_SCALE = 0.3f;
    private static final String LEVEL_UP_MESSAGE = "Level up !";
    private static final Vector2 SRC_FONT_SCALE = new Vector2(0.3f, 0.1f);
    private static final Vector2 DST_FONT_SCALE = new Vector2(0.3f, 0.3f);
    private static final Vector2 POSITION = new Vector2(320, 400);
    private static final Color TRANSPARENT_FIREBRICK = new Color(0xb2222200);

    private Stage stage;
    private TweenController tweenController;
    private Label levelUpLabel;

    public LevelUpPopup(Stage stage, TweenController tweenController, BitmapFont font) {
        this.stage = stage;
        this.tweenController = tweenController;
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = TRANSPARENT_FIREBRICK;
        this.levelUpLabel = new Label(LEVEL_UP_MESSAGE, style);
        this.levelUpLabel.setPosition(POSITION.x, POSITION.y);
        this.levelUpLabel.setFontScale(INITIAL_FONT_SCALE);
    }

    @Override
    public void perform() {
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
