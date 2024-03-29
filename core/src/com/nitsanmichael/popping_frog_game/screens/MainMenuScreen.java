package com.nitsanmichael.popping_frog_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButton;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButtonListener;
import com.nitsanmichael.popping_frog_game.scenes.events.MessageEventListener;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleBritishFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleHealthFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleIllusionFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleMexicanFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleEvilFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleRegularFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleTurkishFrog;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;


/**
 * Created by MichaelBond on 9/1/2016.
 */
public class MainMenuScreen extends FadingScreen {

    private static final float FADE_OUT_TIME = 0.25f;
    private static final float FADE_IN_TIME = 0.25f;
    private static final String GAME_TITLE = "Popping Frog";
    private Viewport backgroundViewport;
    private PoppingFrog game;
    private Texture backgroundTexture;

    private boolean isListening;
    private Stage stage;


    public MainMenuScreen(final PoppingFrog game) {
        super(game.batch, game.tweenController);
        this.game = game;
        this.backgroundViewport = new StretchViewport(
                PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT);

        BitmapFont font = this.game.assetController.get(Assets.GAME_FONT);
        this.isListening = true;

        // Play button.
        Texture playIcon = this.game.assetController.get(Assets.PLAY_ICON);
        Texture playPressedIcon = this.game.assetController.get(Assets.PLAY_PRESSED_ICON);
        final ToggleButton playButton = new ToggleButton(
                    new Image(playIcon), new Image(playPressedIcon));
        playButton.setSize(100, 100);
        playButton.setPosition(340, 300);
        playButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != playButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                final PoppingFrog game = MainMenuScreen.this.game;
                if (!isListening) {
                    return;
                }
                MainMenuScreen.this.fadeOut(FADE_OUT_TIME, new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        dispose();
                        new PlayScreen(game).fadeIn(game, FADE_IN_TIME);
                    }
                });
                isListening = false;
            }
        });
        // Settings button.
        Texture settingsIcon = this.game.assetController.get(Assets.SETTINGS_ICON);
        Texture settingsPressedIcon = this.game.assetController.get(Assets.SETTINGS_PRESSED_ICON);
        final ToggleButton settingsButton = new ToggleButton(
                    new Image(settingsIcon), new Image(settingsPressedIcon));
        settingsButton.setSize(100, 100);
        settingsButton.setPosition(200, 300);
        settingsButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != settingsButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                final PoppingFrog game = MainMenuScreen.this.game;
                if (!isListening) {
                    return;
                }
                MainMenuScreen.this.fadeOut(FADE_OUT_TIME, new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        dispose();
                        new SettingsScreen(game).fadeIn(game, FADE_IN_TIME);
                    }
                });
                isListening = false;
            }
        });
        // Hero button.
        Texture heroIcon = this.game.assetController.get(Assets.HERO_ICON);
        Texture heroPressedIcon = this.game.assetController.get(Assets.HERO_PRESSED_ICON);
        final ToggleButton heroButton = new ToggleButton(
                    new Image(heroIcon), new Image(heroPressedIcon));
        heroButton.setSize(100, 100);
        heroButton.setPosition(480, 300);
        heroButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != heroButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                final PoppingFrog game = MainMenuScreen.this.game;
                if (!isListening) {
                    return;
                }
                MainMenuScreen.this.fadeOut(FADE_OUT_TIME, new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        dispose();
                        new ChooseHeroScreen(game).fadeIn(game, FADE_IN_TIME);
                    }
                });
                isListening = false;
            }
        });
        // Info button.
        Texture infoIcon = this.game.assetController.get(Assets.INFO_ICON);
        Texture infoPressedIcon = this.game.assetController.get(Assets.INFO_PRESSED_ICON);
        final ToggleButton infoButton = new ToggleButton(
                    new Image(infoIcon), new Image(infoPressedIcon));
        infoButton.setSize(70, 70);
        infoButton.setPosition(700, 430);
        infoButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != infoButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                final PoppingFrog game = MainMenuScreen.this.game;
                if (!isListening) {
                    return;
                }
                MainMenuScreen.this.fadeOut(FADE_OUT_TIME, new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        dispose();
                        new InfoScreen(game).fadeIn(game, FADE_IN_TIME);
                    }
                });
                isListening = false;
            }
        });

        // Game title.
        Label titleLabel = new Label(GAME_TITLE, new Label.LabelStyle(font, Color.LIME));
        titleLabel.setFontScale(0.5f);
        titleLabel.setPosition(230, 450);
        titleLabel.setHeight(50);

        setStage(titleLabel, playButton, settingsButton, heroButton, infoButton);

        this.backgroundTexture = this.game.assetController.get(Assets.MENU_BACKGROUND);
        // Play music.
        this.game.media.playMusic(Assets.MAIN_MENU_MUSIC);

        Gdx.input.setCatchBackKey(false);

        game.adsController.showBannerAd();
    }

    private void setStage(Label titleLabel, ToggleButton playButton, ToggleButton settingsButton,
                            ToggleButton heroButton, ToggleButton infoButton) {
        this.stage = new Stage(new ExtendViewport(
                    PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT, new OrthographicCamera()),
                    this.game.batch);
        this.stage.addActor(titleLabel);
        this.stage.addActor(playButton);
        this.stage.addActor(settingsButton);
        this.stage.addActor(heroButton);
        this.stage.addActor(infoButton);
        addIdleFrogsToStage();
        Gdx.input.setInputProcessor(this.stage);
    }

    private void addIdleFrogsToStage() {
        this.stage.addActor(new IdleIllusionFrog(this.game.assetController, new Vector2(415, 125)));
        this.stage.addActor(new IdleRegularFrog(this.game.assetController,
                IdleRegularFrog.AnimationType.WINK, new Vector2(255, 125)));
        this.stage.addActor(new IdleBritishFrog(this.game.assetController, new Vector2(340, 70)));
        this.stage.addActor(new IdleMexicanFrog(this.game.assetController, new Vector2(170, 180)));
        this.stage.addActor(new IdleTurkishFrog(this.game.assetController, new Vector2(500, 180)));
        this.stage.addActor(new IdleEvilFrog(this.game.assetController, new Vector2(50, 150)));
        this.stage.addActor(new IdleHealthFrog(this.game.assetController, new Vector2(620, 150)));
    }

    @Override
    public void setScreenColor(float r, float g, float b, float a) {
        super.setScreenColor(r, g, b, a);
        for (Actor actor : this.stage.getActors()) {
            Color color = actor.getColor();
            actor.setColor(color.r, color.g, color.b, a);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0/255f, 163/255f, 232/255f, 1);

        this.game.batch.begin();
        this.game.batch.setProjectionMatrix(this.backgroundViewport.getCamera().combined);
        this.game.batch.draw(this.backgroundTexture, 0, 0);
        this.game.batch.end();
        this.game.batch.setProjectionMatrix(this.stage.getCamera().combined);
        this.stage.draw();
    }

    private void update(float deltaTime) {
        this.stage.act(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        this.backgroundViewport.update(width, height, true);
        this.stage.getCamera().position.set(0,0,0);
        this.stage.getCamera().translate(
                    PoppingFrog.VIRTUAL_WIDTH / 2,
                    PoppingFrog.VIRTUAL_HEIGHT / 2,
                    0);
        this.stage.getViewport().update(width, height, false);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        this.stage.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

}
