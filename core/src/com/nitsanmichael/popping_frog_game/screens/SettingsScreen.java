package com.nitsanmichael.popping_frog_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButton;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButtonListener;
import com.nitsanmichael.popping_frog_game.scenes.events.MessageEventListener;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;


/**
 * Created by MichaelBond on 9/29/2016.
 */
public class SettingsScreen extends FadingScreen {

    private static final float FADE_OUT_TIME = 0.25f;
    private static final float FADE_IN_TIME = 0.25f;
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
    private Viewport viewport;


    public SettingsScreen(final PoppingFrog game) {
        super(game.batch, game.tweenController);
        this.game = game;
        Skin sliderSkin = this.game.assetController.get(Assets.SLIDER_SKIN);
        adjustSliderKnob(sliderSkin);
        BitmapFont font = this.game.assetController.get(Assets.GAME_FONT);
        font.getData().setScale(0.2f);
        this.viewport = new StretchViewport(PoppingFrog.VIRTUAL_WIDTH,PoppingFrog.VIRTUAL_HEIGHT);
        // Go back button.
        Texture backIcon = this.game.assetController.get(Assets.BACK_ICON);
        Texture backPressedIcon = this.game.assetController.get(Assets.BACK_PRESSED_ICON);
        final ToggleButton backButton = new ToggleButton(
                new Image(backIcon), new Image(backPressedIcon));
        backButton.setSize(100, 100);
        backButton.setPosition(20, 400);
        backButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != backButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                final PoppingFrog game = SettingsScreen.this.game;
                SettingsScreen.this.fadeOut(FADE_OUT_TIME, new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        dispose();
                        new MainMenuScreen(game).fadeIn(game, FADE_IN_TIME);
                    }
                });
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
        musicLabel.setPosition(200, 300);
        musicLabel.setWidth(250);
        // Music slider.
        float musicVolume = game.data.getMusicVolume();
        this.musicSlider = new Slider(0, SLIDER_RANGE, SLIDER_STEP, false, sliderSkin);
        this.musicSlider.setPosition(330, 287);
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
                PoppingFrog game = SettingsScreen.this.game;
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
        soundLabel.setPosition(200, 200);
        soundLabel.setWidth(250);
        // Sound slider.
        float soundVolume = game.data.getSoundVolume();
        this.soundSlider = new Slider(0, SLIDER_RANGE, SLIDER_STEP, false, sliderSkin);
        this.soundSlider.setPosition(330, 187);
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
                PoppingFrog game = SettingsScreen.this.game;
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

        this.game.adsController.showBannerAd();
    }

    private void adjustSliderKnob(Skin sliderSkin) {
        Slider slider = new Slider(0, SLIDER_RANGE, SLIDER_STEP, false, sliderSkin);
        slider.getStyle().knob.setMinHeight(70);
        slider.getStyle().knob.setMinWidth(40);
        slider.getStyle().knobDown.setMinHeight(70);
        slider.getStyle().knobDown.setMinWidth(40);
    }

    private void setStage(Label settingsLabel, Label musicLabel, Label soundLabel,
                            ToggleButton backButton) {
        this.stage = new Stage(
                new ExtendViewport(PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT, new OrthographicCamera()),
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
    public void setScreenColor(float r, float g, float b, float a) {
        super.setScreenColor(r, g, b, a);
        for (Actor actor : this.stage.getActors()) {
            actor.getColor().a = a;
        }
    }

    @Override
    public void resize(int width, int height) {
    	this.viewport.update(width, height, true);
		this.stage.getViewport().update(width, height, false);
		this.stage.getCamera().position.set(0,0,0);
		this.stage.getCamera().translate(game.VIRTUAL_WIDTH/2,game.VIRTUAL_HEIGHT/2,0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0/255f, 163/255f, 232/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.begin();
        this.game.batch.setProjectionMatrix(this.viewport.getCamera().combined);
        this.game.batch.draw(this.backgroundTexture, 0, 0);
        this.game.batch.setProjectionMatrix(this.stage.getCamera().combined);
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