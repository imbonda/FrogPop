package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.FrogPop;
import com.mygdx.game.scenes.ToggleButton;
import com.mygdx.game.scenes.events.MessageEventListener;

/**
 * Created by MichaelBond on 9/29/2016.
 */
public class SettingsScreen implements Screen {

    private static final String MUSIC = "Music";
    private static final String SOUND = "Sound";
    private static final String PERCENTAGE = "%";
    private static final int SLIDER_RANGE = 10;
    private static final int SLIDER_STEP = 1;

//    private Texture musicTexture;
//    private Texture soundTexture;
    private Label musicLabel;
    private Label soundLabel;
    private Label musicSliderLable;
    private Label soundSliderLable;
    private FrogPop game;
    private Slider musicSlider;
    private Slider soundSlider;
    private Stage stage;

    public SettingsScreen(final FrogPop game) {
        this.game = game;
        Skin slideSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        // Music label.
        this.musicLabel = new Label(MUSIC, labelStyle);
        this.musicLabel.setPosition(250, 300);
        this.musicLabel.setWidth(250);
        this.musicLabel.setColor(1, 1, 1, 1);
        // Music slider.
        float musicVolume = game.data.getMusicVolume();
        this.musicSlider = new Slider(0, SLIDER_RANGE, SLIDER_STEP, false, slideSkin);
        this.musicSlider.setPosition(350, 300);
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
                    game.media.stopMusic();
                }
                else {
                    game.media.playMusic();
                }
            }

            private void updateSliderLabel(float volume) {
                musicSliderLable.setText(Integer.toString((int)(volume * 100)) + PERCENTAGE);
            }
        });
        // Music-slider label.
        this.musicSliderLable = new Label(Integer.toString((int)(musicVolume * 100)) + PERCENTAGE,
                    labelStyle);
        this.musicSliderLable.setPosition(520, 300);
        this.musicSliderLable.setWidth(100);
        this.musicSliderLable.setColor(1, 1, 1, 1);

        // Sound label.
        this.soundLabel = new Label(SOUND, labelStyle);
        this.soundLabel.setPosition(250, 200);
        this.soundLabel.setWidth(250);
        this.soundLabel.setColor(1, 1, 1, 1);
        // Sound slider.
        float soundVolume = game.data.getSoundVolume();
        this.soundSlider = new Slider(0, SLIDER_RANGE, SLIDER_STEP, false, slideSkin);
        this.soundSlider.setPosition(350, 200);
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
                soundSliderLable.setText(Integer.toString((int)(volume * 100)) + PERCENTAGE);
            }
        });
        // Sound-slider label.
        this.soundSliderLable = new Label(Integer.toString((int)(soundVolume * 100)) + PERCENTAGE,
                    labelStyle);
        this.soundSliderLable.setPosition(520, 200);
        this.soundSliderLable.setWidth(100);
        this.soundSliderLable.setColor(1, 1, 1, 1);

        this.stage = new Stage(
                new FitViewport(FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT, new OrthographicCamera()),
                this.game.batch);
        this.stage.addActor(musicLabel);
        this.stage.addActor(musicSlider);
        this.stage.addActor(musicSliderLable);
        this.stage.addActor(soundLabel);
        this.stage.addActor(soundSlider);
        this.stage.addActor(soundSliderLable);
        Gdx.input.setInputProcessor(this.stage);
        Gdx.gl.glClearColor(0, 1, 1, 1);
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.setProjectionMatrix(this.stage.getCamera().combined);
        this.stage.draw();
    }

    @Override
    public void dispose() {

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
