package com.nitsanmichael.popping_frog_game.scenes.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButton;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButtonListener;
import com.nitsanmichael.popping_frog_game.screens.MainMenuScreen;
import com.nitsanmichael.popping_frog_game.screens.PlayScreen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

/**
 * Created by MichaelBond on 10/17/2016.
 */
public class PauseDialog implements Disposable {

    private static final String DIALOG_TITLE = "PAUSE";

    private Texture background;
    private Stage stage;

    public PauseDialog(AssetController assetController, Viewport viewport, Batch batch) {
        BitmapFont font = assetController.get(Assets.GAME_FONT);
        this.background = assetController.get(Assets.PAUSE_BACKGROUND);

        // Play button.
        Texture playIcon = assetController.get(Assets.PLAY_ICON);
        Texture playPressedIcon = assetController.get(Assets.PLAY_PRESSED_ICON);
        final ToggleButton playButton = new ToggleButton(
                new Image(playIcon), new Image(playPressedIcon));
        playButton.setSize(100, 100);
        playButton.setPosition(340, 250);
        ToggleButtonListener.Callback touchUp = new ToggleButtonListener.Callback() {
            @Override
            public void call() {
                // todo (implement)
            }
        };
        playButton.addListener(new ToggleButtonListener(playButton, touchUp));

        // Home button.
        Texture homeIcon = assetController.get(Assets.HOME_ICON);
        Texture homePressedIcon = assetController.get(Assets.HOME_PRESSED_ICON);
        final ToggleButton homeButton = new ToggleButton(new Image(homeIcon), new Image(homePressedIcon));
        homeButton.setSize(50, 50);
        homeButton.setPosition(365, 150);
        touchUp = new ToggleButtonListener.Callback() {
            @Override
            public void call() {
                // todo (implement)
            }
        };
        homeButton.addListener(new ToggleButtonListener(homeButton, touchUp));

        Label titleLabel = new Label(DIALOG_TITLE, new Label.LabelStyle(font, Color.WHITE));
        titleLabel.setFontScale(0.3f);
        titleLabel.setPosition(330, 400);

        setStage(viewport, batch, playButton, homeButton, titleLabel);
    }

    private void setStage(Viewport viewport, Batch batch,
                            ToggleButton homeButton, ToggleButton playButton, Label title) {
        this.stage = new Stage(viewport, batch);
        this.stage.addActor(title);
        this.stage.addActor(playButton);
        this.stage.addActor(homeButton);
        InputProcessor processor = Gdx.input.getInputProcessor();
        // todo check if we want this behavior
        Gdx.input.setInputProcessor(new InputMultiplexer(this.stage, processor));
    }

    public void draw() {
        Batch batch = this.stage.getBatch();
        batch.begin();
        batch.draw(this.background, 0, 0);
        batch.end();
        this.stage.draw();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }
}
