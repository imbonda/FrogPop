package com.mygdx.game.screens;

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
import com.mygdx.game.FrogPop;
import com.mygdx.game.assets.Assets;
import com.mygdx.game.scenes.ToggleButton;
import com.mygdx.game.scenes.events.MessageEventListener;

/**
 * Created by MichaelBond on 9/29/2016.
 */
public class SettingsScreen implements Screen {

    // Files
    private static final String SKIN_JSON_FILE = "skin/uiskin.json";
    private static final String FOND_FILE = "font.fnt";
    private static final String GO_BACK_IMAGE = "icons/back_icon.png";
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
    private FrogPop game;
    private Slider musicSlider;
    private Slider soundSlider;
    private Stage stage;

    public SettingsScreen(final FrogPop game) {
        this.game = game;
        Skin slideSkin = new Skin(Gdx.files.internal(SKIN_JSON_FILE));
        BitmapFont font = new BitmapFont(Gdx.files.internal(FOND_FILE));

        // Go back button.
        ImageButton backButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture(GO_BACK_IMAGE))));
        backButton.setPosition(0, 400);
        backButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        // Header label.
        Label settingsLabel = new Label(SETTINGS, new Label.LabelStyle(font, Color.WHITE));
        settingsLabel.setFontScale(2);
        settingsLabel.setPosition(300, 440);
        settingsLabel.setHeight(50);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.LIGHT_GRAY);
        // Music label.
        Label musicLabel = new Label(MUSIC, labelStyle);
        musicLabel.setPosition(250, 300);
        musicLabel.setWidth(250);
        // Music slider.
        float musicVolume = game.data.getMusicVolume();
        this.musicSlider = new Slider(0, SLIDER_RANGE, SLIDER_STEP, false, slideSkin);
        this.musicSlider.setPosition(350, 300);
        this.musicSlider.getStyle().knob.setMinHeight(50);
        this.musicSlider.getStyle().knob.setMinWidth(20);
        this.musicSlider.getStyle().knobDown.setMinHeight(50);
        this.musicSlider.getStyle().knobDown.setMinWidth(20);
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
                    game.media.stopMusic(Assets.GAME_PLAY_MUSIC);
                }
                else {
                    game.media.playMusic(Assets.GAME_PLAY_MUSIC);
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

        setStage(backButton, settingsLabel, musicLabel, soundLabel);
    }

    private void setStage(ImageButton backButton, Label settingsLabel,
                            Label musicLabel, Label soundLabel) {
        this.stage = new Stage(
                new FitViewport(FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT, new OrthographicCamera()),
                this.game.batch);
        this.stage.addActor(backButton);
        this.stage.addActor(settingsLabel);
        this.stage.addActor(musicLabel);
        this.stage.addActor(musicSlider);
        this.stage.addActor(musicSliderLabel);
        this.stage.addActor(soundLabel);
        this.stage.addActor(soundSlider);
        this.stage.addActor(soundSliderLabel);
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(60 / 256f, 93 / 256f, 117 / 256f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.setProjectionMatrix(this.stage.getCamera().combined);
        this.stage.draw();
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
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
