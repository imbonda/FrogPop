package com.nitsanmichael.popping_frog_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButton;
import com.nitsanmichael.popping_frog_game.sprites.Buttons;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleBritishFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleHealthFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleIllusionFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleMexicanFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleEvilFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleRegularFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleTurkishFrog;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;


/**
 * Created by MichaelBond on 9/1/2016.
 */
public class MainMenuScreen extends FadingScreen {

    private static final float FADE_OUT_TIME = 0.25f;
    private static final float FADE_IN_TIME = 0.25f;
    private static final String GAME_TITLE = "Popping Frog";

    private PoppingFrog game;
    private Texture backgroundTexture;
    private Array<IdleFrog> idleFrogs;

    private Stage stage;


    public MainMenuScreen(final PoppingFrog game) {
        super(game.batch, game.tweenController);
        this.game = game;
        BitmapFont font = this.game.assetController.get(Assets.GAME_FONT);

        // Play button.
        Texture playIcon = this.game.assetController.get(Assets.PLAY_ICON);
        Texture playPressedIcon = this.game.assetController.get(Assets.PLAY_PRESSED_ICON);
        final ToggleButton playButton = new ToggleButton(
                    new Image(playIcon), new Image(playPressedIcon));
        playButton.setSize(100, 100);
        playButton.setPosition(340, 300);
        playButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                playButton.setState(ToggleButton.OFF_STATE);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                playButton.setState(ToggleButton.ON_STATE);
                super.touchUp(event, x, y, pointer, button);
                final PoppingFrog game = MainMenuScreen.this.game;
                MainMenuScreen.this.fadeOut(FADE_OUT_TIME, new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        dispose();
                        new PlayScreen(game).fadeIn(game, FADE_IN_TIME);
                    }
                });
            }
        });
        // Settings button.
        Texture settingsIcon = this.game.assetController.get(Assets.SETTINGS_ICON);
        Texture settingsPressedIcon = this.game.assetController.get(Assets.SETTINGS_PRESSED_ICON);
        final ToggleButton settingsButton = new ToggleButton(
                    new Image(settingsIcon), new Image(settingsPressedIcon));
        settingsButton.setSize(100, 100);
        settingsButton.setPosition(200, 300);
        settingsButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                settingsButton.setState(ToggleButton.OFF_STATE);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                settingsButton.setState(ToggleButton.ON_STATE);
                super.touchUp(event, x, y, pointer, button);
                final PoppingFrog game = MainMenuScreen.this.game;
                MainMenuScreen.this.fadeOut(FADE_OUT_TIME, new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        dispose();
                        new SettingsScreen(game).fadeIn(game, FADE_IN_TIME);
                    }
                });
            }
        });
        // Hero button.
        Texture heroIcon = this.game.assetController.get(Assets.HERO_ICON);
        Texture heroPressedIcon = this.game.assetController.get(Assets.HERO_PRESSED_ICON);
        final ToggleButton heroButton = new ToggleButton(
                    new Image(heroIcon), new Image(heroPressedIcon));
        heroButton.setSize(100, 100);
        heroButton.setPosition(480, 300);
        heroButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                heroButton.setState(ToggleButton.OFF_STATE);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                heroButton.setState(ToggleButton.ON_STATE);
                super.touchUp(event, x, y, pointer, button);
                final PoppingFrog game = MainMenuScreen.this.game;
                MainMenuScreen.this.fadeOut(FADE_OUT_TIME, new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        dispose();
                        new ChooseHeroScreen(game).fadeIn(game, FADE_IN_TIME);
                    }
                });
            }
        });

        // Game title.
        Label titleLabel = new Label(GAME_TITLE, new Label.LabelStyle(font, Color.LIME));
        titleLabel.setFontScale(0.5f);
        titleLabel.setPosition(230, 450);
        titleLabel.setHeight(50);

        initIdleFrogs();
        setStage(titleLabel, playButton, settingsButton, heroButton);
        this.backgroundTexture = this.game.assetController.get(Assets.MENU_BACKGROUND);
        // Play music.
        this.game.media.playMusic(Assets.MAIN_MENU_MUSIC);

        if (game.adsController.isInternetConnected()) {
            game.adsController.showBannerAd();
        }
    }

    private void initIdleFrogs() {
        this.idleFrogs = new Array<IdleFrog>();
        this.idleFrogs.add(new IdleIllusionFrog(this.game.assetController, new Vector2(415, 125)));
        this.idleFrogs.add(new IdleRegularFrog(this.game.assetController,
                    IdleRegularFrog.AnimationType.WINK, new Vector2(255, 125)));
        this.idleFrogs.add(new IdleBritishFrog(this.game.assetController, new Vector2(340, 70)));
        this.idleFrogs.add(new IdleMexicanFrog(this.game.assetController, new Vector2(170, 180)));
        this.idleFrogs.add(new IdleTurkishFrog(this.game.assetController, new Vector2(500, 180)));
        this.idleFrogs.add(new IdleEvilFrog(this.game.assetController, new Vector2(50, 150)));
        this.idleFrogs.add(new IdleHealthFrog(this.game.assetController, new Vector2(620, 150)));
    }

    private void setStage(Label titleLabel, ToggleButton playButton, ToggleButton settingsButton,
                            ToggleButton heroButton) {
        this.stage = new Stage(new FitViewport(
                    PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT, new OrthographicCamera()),
                    this.game.batch);
        this.stage.addActor(titleLabel);
        this.stage.addActor(playButton);
        this.stage.addActor(settingsButton);
        this.stage.addActor(heroButton);
        Gdx.input.setInputProcessor(this.stage);
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
        this.game.batch.setProjectionMatrix(this.stage.getCamera().combined);
        this.game.batch.begin();
        this.game.batch.draw(this.backgroundTexture, 0, 0);
        drawIdleFrogs();
        this.game.batch.end();
        this.stage.draw();
    }

    public void update(float deltaTime) {
        updateIdleFrogs(deltaTime);
    }

    private void updateIdleFrogs(float deltaTime) {
        for (IdleFrog frog : this.idleFrogs) {
            frog.update(deltaTime);
        }
    }

    private void drawIdleFrogs() {
        for (IdleFrog frog : this.idleFrogs) {
            frog.draw(this.game.batch);
        }
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
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
