package com.nitsanmichael.popping_frog_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButton;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleFreezeFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleFrog;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;


/**
 * Created by MichaelBond on 9/1/2016.
 */
public class GameOverScreen extends FadingScreen {

    private static final float FADE_OUT_TIME = 0.3f;
    private static final float FADE_IN_TIME = 0.25f;
    private static final float GAME_FADE_IN = 0.3f;
    // Label constants.
    private static final String LEVEL = "Level: ";
    private static final String SCORE = "Score: ";
    private static final String HIGHEST_SCORE = "Highest score: ";

    private PoppingFrog game;
    private RuntimeInfo runtimeInfo;
    private Array<IdleFrog> idleFrogs;

    private boolean isListening;
    private Stage stage;


    public GameOverScreen(PoppingFrog game, RuntimeInfo runtimeInfo) {
        super(game.batch, game.tweenController);
        this.game = game;
        this.runtimeInfo = runtimeInfo;
        BitmapFont font = this.game.assetController.get(Assets.GAME_FONT);

        this.isListening = true;

        // Restart button.
        Texture restartIcon = this.game.assetController.get(Assets.RESTART_ICON);
        Texture restartPressedIcon = this.game.assetController.get(Assets.RESTART_PRESSED_ICON);
        final ToggleButton restartButton = new ToggleButton(
                    new Image(restartIcon), new Image(restartPressedIcon));
        restartButton.setSize(100, 100);
        restartButton.setPosition(480, 200);
        restartButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                restartButton.setState(ToggleButton.OFF_STATE);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                restartButton.setState(ToggleButton.ON_STATE);
                super.touchUp(event, x, y, pointer, button);
                final PoppingFrog game = GameOverScreen.this.game;
                if (!isListening) {
                    return;
                }
                GameOverScreen.this.fadeOut(FADE_OUT_TIME, new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        dispose();
                        new PlayScreen(game).fadeIn(game, FADE_IN_TIME);
                    }
                });
                isListening = false;
            }
        });
        // Home button.
        Texture homeIcon = this.game.assetController.get(Assets.HOME_ICON);
        Texture homePressedIcon = this.game.assetController.get(Assets.HOME_PRESSED_ICON);
        final ToggleButton homeButton = new ToggleButton(new Image(homeIcon), new Image(homePressedIcon));
        homeButton.setSize(100, 100);
        homeButton.setPosition(480, 70);
        homeButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                homeButton.setState(ToggleButton.OFF_STATE);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                homeButton.setState(ToggleButton.ON_STATE);
                super.touchUp(event, x, y, pointer, button);
                final PoppingFrog game = GameOverScreen.this.game;
                if (!isListening) {
                    return;
                }
                GameOverScreen.this.fadeOut(FADE_OUT_TIME, new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        dispose();
                        new MainMenuScreen(game).fadeIn(game, FADE_IN_TIME);
                    }
                });
                isListening = false;
            }
        });
        // Rank button.
        Texture rankIcon = this.game.assetController.get(Assets.RANK_ICON);
        Texture rankPressedIcon = this.game.assetController.get(Assets.RANK_PRESSED_ICON);
        final ToggleButton rankButton = new ToggleButton(new Image(rankIcon), new Image(rankPressedIcon));
        rankButton.setSize(100, 100);
        rankButton.setPosition(700, 200);
        rankButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rankButton.setState(ToggleButton.OFF_STATE);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rankButton.setState(ToggleButton.ON_STATE);
                super.touchUp(event, x, y, pointer, button);
                if (!isListening) {
                    return;
                }
                GameOverScreen.this.game.playServices.showScore();
            }
        });

        // Highest-score label.
        Label highestScoreLabel = new Label(HIGHEST_SCORE + this.game.data.getHighScore(),
                new Label.LabelStyle(font, Color.GOLD));
        highestScoreLabel.setFontScale(0.35f);
        highestScoreLabel.setPosition(420, 450);
        highestScoreLabel.setHeight(50);
        // Level label.
        Label levelLabel = new Label(LEVEL + this.runtimeInfo.gameLevel,
                    new Label.LabelStyle(font, Color.WHITE));
        levelLabel.setFontScale(0.25f);
        levelLabel.setPosition(500, 330);
        levelLabel.setHeight(50);
        // Score label.
        Label scoreLabel = new Label(SCORE + this.runtimeInfo.gameScore,
                    new Label.LabelStyle(font, Color.WHITE));
        scoreLabel.setFontScale(0.25f);
        scoreLabel.setPosition(500, 380);
        scoreLabel.setHeight(50);

        initIdleFrogs();
        setStage(levelLabel, scoreLabel, highestScoreLabel, restartButton, homeButton, rankButton);

        this.game.media.playMusic(Assets.MAIN_MENU_MUSIC);

        if (game.adsController.isInternetConnected()) {
            game.adsController.showBannerAd();
        }
    }

    private void initIdleFrogs() {
        this.idleFrogs = new Array<IdleFrog>();
        this.idleFrogs.add(new IdleFreezeFrog(this.game.assetController,
                IdleFreezeFrog.AnimationType.BIG, new Vector2(50, 50)));
    }

    private void setStage(Label levelLabel, Label scoreLabel, Label highestScoreLabel,
                            ToggleButton restartButton, ToggleButton homeButton, ToggleButton rankButton) {
        this.stage = new Stage(new FitViewport(
                PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT, new OrthographicCamera()),
                this.game.batch);
        this.stage.addActor(levelLabel);
        this.stage.addActor(scoreLabel);
        this.stage.addActor(highestScoreLabel);
        this.stage.addActor(restartButton);
        this.stage.addActor(homeButton);
        this.stage.addActor(rankButton);
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
        Gdx.gl.glClearColor(64/255f, 64/255f, 64/255f, 1);
        this.game.batch.setProjectionMatrix(this.stage.getCamera().combined);
        this.game.batch.begin();
        drawIdleFrogs();
        this.game.batch.end();
        this.stage.draw();
    }

    public void update(float deltaTime) {
        updateIdleFrogs(deltaTime);
    }

    private void updateIdleFrogs(float deltaTime) {
        for (com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleFrog frog : this.idleFrogs) {
            frog.update(deltaTime);
        }
    }

    private void drawIdleFrogs() {
        for (com.nitsanmichael.popping_frog_game.sprites.frogs.idle.IdleFrog frog : this.idleFrogs) {
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