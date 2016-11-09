package com.nitsanmichael.popping_frog_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.input.BackKeyInputProcessor;
import com.nitsanmichael.popping_frog_game.playservice.PlayServices;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButton;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButtonListener;
import com.nitsanmichael.popping_frog_game.scenes.events.MessageEventListener;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleFreezeFrog;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;


/**
 * Created by MichaelBond on 9/1/2016.
 */
public class GameOverScreen extends FadingScreen {

    private static final float FADE_OUT_TIME = 0.3f;
    private static final float FADE_IN_TIME = 0.25f;
    // Label constants.
    private static final String LEVEL = "Level: ";
    private static final String SCORE = "Score: ";
    private static final String HIGHEST_SCORE = "Highest score: ";
    private static final String HIGHEST_LEVEL = "Highest level: ";

    private PoppingFrog game;
    private RuntimeInfo runtimeInfo;

    private boolean isListening;
    private Stage stage;
    private Label highScoreLabel;
    private Label highLevelLabel;


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
        restartButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != restartButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
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
        homeButton.setPosition(480, 80);
        homeButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != homeButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                backToMenu();
            }
        });
        // Rank button.
        Texture rankIcon = this.game.assetController.get(Assets.RANK_ICON);
        Texture rankPressedIcon = this.game.assetController.get(Assets.RANK_PRESSED_ICON);
        final ToggleButton rankButton = new ToggleButton(new Image(rankIcon), new Image(rankPressedIcon));
        rankButton.setSize(100, 100);
        rankButton.setPosition(700, 200);
        rankButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != rankButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                if (!isListening) {
                    return;
                }
                GameOverScreen.this.game.playServices.showLeaderBoards();
            }
        });

        // Highest-score label.
        this.highScoreLabel = new Label(
                    HIGHEST_SCORE + this.game.data.getHighScore(PlayServices.LeaderBoard.HIGHEST_SCORE),
                    new Label.LabelStyle(font, Color.GOLD));
        this.highScoreLabel.setFontScale(0.35f);
        this.highScoreLabel.setPosition(420, 470);
        this.highScoreLabel.setHeight(50);
        // Highest-level label.
        this.highLevelLabel = new Label(
                    HIGHEST_LEVEL + this.game.data.getHighScore(PlayServices.LeaderBoard.HIGHEST_LEVEL),
                    new Label.LabelStyle(font, Color.GOLD));
        this.highLevelLabel.setFontScale(0.35f);
        this.highLevelLabel.setPosition(420, 410);
        this.highLevelLabel.setHeight(50);
        // Score label.
        Label scoreLabel = new Label(SCORE + this.runtimeInfo.gameScore,
                new Label.LabelStyle(font, Color.WHITE));
        scoreLabel.setFontScale(0.25f);
        scoreLabel.setPosition(490, 350);
        scoreLabel.setHeight(40);
        // Level label.
        Label levelLabel = new Label(LEVEL + this.runtimeInfo.gameLevel,
                    new Label.LabelStyle(font, Color.WHITE));
        levelLabel.setFontScale(0.25f);
        levelLabel.setPosition(490, 310);
        levelLabel.setHeight(40);

        setStage(levelLabel, scoreLabel, restartButton, homeButton, rankButton);

        Gdx.input.setCatchBackKey(true);
        setInputProcessor();

        this.game.media.playMusic(Assets.MAIN_MENU_MUSIC);

        game.adsController.showBannerAd();
        // TODO add when wanted.
//        game.adsController.showInterstitialAd(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
    }

    private void setStage(Label levelLabel, Label scoreLabel, ToggleButton restartButton,
                            ToggleButton homeButton, ToggleButton rankButton) {
        this.stage = new Stage(new FitViewport(
                PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT, new OrthographicCamera()),
                this.game.batch);
        this.stage.addActor(levelLabel);
        this.stage.addActor(scoreLabel);
        this.stage.addActor(this.highScoreLabel);
        this.stage.addActor(this.highLevelLabel);
        this.stage.addActor(restartButton);
        this.stage.addActor(homeButton);
        this.stage.addActor(rankButton);
        this.stage.addActor(new IdleFreezeFrog(this.game.assetController,
                    IdleFreezeFrog.AnimationType.BIG, new Vector2(50, 50)));
    }

    private void setInputProcessor() {
        BackKeyInputProcessor backKeyInputProcessor = new BackKeyInputProcessor(
                new Runnable() {
                    @Override
                    public void run() {
                        backToMenu();
                    }
                }
        );
        Gdx.input.setInputProcessor(new InputMultiplexer(this.stage, backKeyInputProcessor));
    }

    private void backToMenu() {
        final PoppingFrog game = this.game;
        if (!isListening) {
            return;
        }
        fadeOut(FADE_OUT_TIME, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                dispose();
                new MainMenuScreen(game).fadeIn(game, FADE_IN_TIME);
            }
        });
        isListening = false;
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
        this.stage.draw();
    }

    private void update(float deltaTime) {
        updateHighScoreLabels();
        this.stage.act(deltaTime);
    }

    private void updateHighScoreLabels() {
        this.highScoreLabel.setText(
                    HIGHEST_SCORE +
                    this.game.data.getHighScore(PlayServices.LeaderBoard.HIGHEST_SCORE));
        this.highLevelLabel.setText(
                    HIGHEST_LEVEL +
                    this.game.data.getHighScore(PlayServices.LeaderBoard.HIGHEST_LEVEL));
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