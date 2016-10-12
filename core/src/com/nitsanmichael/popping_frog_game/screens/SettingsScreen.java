package com.nitsanmichael.popping_frog_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButton;

/**
 * Created by MichaelBond on 9/29/2016.
 */
public class SettingsScreen implements Screen {

    // Label constants.
    private static final String SETTINGS = "Settings";
    private static final String MUSIC = "Music :";
    private static final String SOUND = "Sound :";
    private static final String PERCENTAGE = "%";
    // Slider constants.
    private static final int SLIDER_RANGE = 10;
    private static final int SLIDER_STEP = 1;

    private Label musicSliderLabel;
    private Label soundSliderLabel;
    private PoppingFrog game;
    private Slider musicSlider;
    private Slider soundSlider;
    private Texture backgroundTexture;
    private Stage stage;

    public SettingsScreen(final PoppingFrog game) {
        this.game = game;
        Skin slideSkin = this.game.assetController.get(Assets.SLIDER_SKIN);
        BitmapFont font = this.game.assetController.get(Assets.GAME_FONT);
        font.getData().setScale(0.2f);

        // Go back button.
        Texture backIcon = this.game.assetController.get(Assets.BACK_ICON);
        Texture backPressedIcon = this.game.assetController.get(Assets.BACK_PRESSED_ICON);
        final ToggleButton backButton = new ToggleButton(
                new Image(backIcon), new Image(backPressedIcon));
        backButton.setSize(100, 100);
        backButton.setPosition(20, 400);
        backButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backButton.setState(ToggleButton.OFF_STATE);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                backButton.setState(ToggleButton.ON_STATE);
                super.touchUp(event, x, y, pointer, button);
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        // Header label.
        Label settingsLabel = new Label(SETTINGS, new Label.LabelStyle(font, Color.LIME));
        settingsLabel.setFontScale(0.5f);
        settingsLabel.setPosition(230, 450);
        settingsLabel.setHeight(50);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        // Music label.
        Label musicLabel = new Label(MUSIC, labelStyle);
        musicLabel.setPosition(250, 300);
        musicLabel.setWidth(250);
        // Music slider.
        float musicVolume = game.data.getMusicVolume();
        this.musicSlider = new Slider(0, SLIDER_RANGE, SLIDER_STEP, false, slideSkin);
        this.musicSlider.setPosition(350, 300);
        this.musicSlider.getStyle().knob.setMinHeight(70);
        this.musicSlider.getStyle().knob.setMinWidth(40);
        this.musicSlider.getStyle().knobDown.setMinHeight(70);
        this.musicSlider.getStyle().knobDown.setMinWidth(40);
        this.musicSlider.setAnimateDuration(0);
        this.musicSlider.setValue(musicVolume * SLIDER_RANGE);
        this.musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                float newMusicVolume = musicSlider.getValue() / SLIDER_RANGE;
                updateMedia(newMusicVolume);
                updateSliderLabel(newMusicVolume);
            }

            private void updateMedia(float volume) {
                game.data.setMusicVolume(volume);
                game.media.updateMusicVolume(volume);
                if (0 == volume) {
                    game.media.pauseMusic(Assets.MAIN_MENU_MUSIC);
                }
                else {
                    game.media.playMusic(Assets.MAIN_MENU_MUSIC);
                }
            }

            private void updateSliderLabel(float volume) {
                musicSliderLabel.setText(Integer.toString((int)(volume * 100)) + PERCENTAGE);
            }
        });
        // Music-slider label.
        this.musicSliderLabel = new Label(Integer.toString((int)(musicVolume * 100)) + PERCENTAGE,
                    labelStyle);
        this.musicSliderLabel.setPosition(520, 300);
        this.musicSliderLabel.setWidth(100);
        this.musicSliderLabel.setColor(1, 1, 1, 1);

        // Sound label.
        Label soundLabel = new Label(SOUND, labelStyle);
        soundLabel.setPosition(250, 200);
        soundLabel.setWidth(250);
        // Sound slider.
        float soundVolume = game.data.getSoundVolume();
        this.soundSlider = new Slider(0, SLIDER_RANGE, SLIDER_STEP, false, slideSkin);
        this.soundSlider.setPosition(350, 187);
        this.soundSlider.setAnimateDuration(0);
        this.soundSlider.setValue(soundVolume * SLIDER_RANGE);
        this.soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                float newSoundVolume = soundSlider.getValue() / SLIDER_RANGE;
                updateMedia(newSoundVolume);
                updateSliderLabel(newSoundVolume);
            }

            private void updateMedia(float volume) {
                game.data.setSoundVolume(volume);
                game.media.updateSoundVolume(volume);
            }

            private void updateSliderLabel(float volume) {
                soundSliderLabel.setText(Integer.toString((int)(volume * 100)) + PERCENTAGE);
            }
        });
        // Sound-slider label.
        this.soundSliderLabel = new Label(Integer.toString((int)(soundVolume * 100)) + PERCENTAGE,
                    labelStyle);
        this.soundSliderLabel.setPosition(520, 200);
        this.soundSliderLabel.setWidth(100);
        this.soundSliderLabel.setColor(1, 1, 1, 1);

        setStage(settingsLabel, musicLabel, soundLabel, backButton);
        this.backgroundTexture = this.game.assetController.get(Assets.MENU_BACKGROUND);
    }

    private void setStage(Label settingsLabel, Label musicLabel, Label soundLabel,
                            ToggleButton backButton) {
        this.stage = new Stage(
                new FitViewport(PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT, new OrthographicCamera()),
                this.game.batch);
        this.stage.addActor(settingsLabel);
        this.stage.addActor(musicLabel);
        this.stage.addActor(musicSlider);
        this.stage.addActor(musicSliderLabel);
        this.stage.addActor(soundLabel);
        this.stage.addActor(soundSlider);
        this.stage.addActor(soundSliderLabel);
        this.stage.addActor(backButton);
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0/255f, 163/255f, 232/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.setProjectionMatrix(this.stage.getCamera().combined);
        this.game.batch.begin();
        this.game.batch.draw(this.backgroundTexture, 0, 0);
        this.game.batch.end();
        this.stage.draw();
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        this.stage.dispose();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }
}
