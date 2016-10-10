package com.nitsanmichael.popping_frog_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.effects.EffectDrawer;
import com.nitsanmichael.popping_frog_game.managment.LevelController;
import com.nitsanmichael.popping_frog_game.managment.ThemeController;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.scenes.Hud;
import com.nitsanmichael.popping_frog_game.scenes.PopupDrawer;
import com.nitsanmichael.popping_frog_game.scenes.panel.Timer;
import com.nitsanmichael.popping_frog_game.managment.GamePlayTouchProcessor;
import com.nitsanmichael.popping_frog_game.sprites.SpritesDrawer;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;


/**
 * Created by MichaelBond on 9/1/2016.
 */
public class PlayScreen extends FadingScreen {

    public static Viewport gameViewPort = new FitViewport(
                PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT, new OrthographicCamera());

    private static final float FADE_OUT_TIME = 0.1f;
    private static final float FADE_IN_TIME = 1f;
    private static final int MAX_LIVES = 3;

    private boolean isAlreadyOver;
    private PoppingFrog game;
    private SpritesDrawer spritesDrawer;
    private EffectDrawer effectDrawer;
    private LevelController levelController;
    private ThemeController themeController;
    private RuntimeInfo runtimeInfo;
    private Hud hud;
    private PopupDrawer popupDrawer;

    public PlayScreen(PoppingFrog game) {
        super(game.batch, game.tweenController);
        game.adsController.hideBannerAd();
        this.game = game;
        this.spritesDrawer = new SpritesDrawer();
        this.effectDrawer = new EffectDrawer();
        this.themeController = new ThemeController(this.game.config, this.game.assetController,
                    this.effectDrawer);
        this.runtimeInfo = new RuntimeInfo(0, MAX_LIVES);
        Timer timer = new Timer(this.game.assetController);
        this.levelController = new LevelController(
                    this.game.config, this.game.assetController, this.game.media, spritesDrawer,
                    this.runtimeInfo, timer, this.themeController);
        this.hud = new Hud(this.game.assetController, this.game.batch, runtimeInfo, timer);
        this.popupDrawer = new PopupDrawer(gameViewPort, this.game.batch, this.game.assetController);
        Gdx.input.setInputProcessor(new GamePlayTouchProcessor(gameViewPort, runtimeInfo));
        this.isAlreadyOver = false;
        // Play music.
        this.game.media.stopMusic(Assets.MAIN_MENU_MUSIC);
        this.game.media.playMusic(Assets.GAME_PLAY_MUSIC);
    }

    public void update(float deltaTime) {
        this.levelController.update(deltaTime);
        this.hud.update();
        if (this.runtimeInfo.gameLives <= 0 && !this.isAlreadyOver) {
            this.isAlreadyOver = true;
            gameOver();
        }
    }

    private void gameOver() {
        this.game.data.updateHighScore(this.runtimeInfo.gameScore);
        this.game.playServices.submitScore(this.game.data.getHighScore());
        this.game.media.stopMusic(Assets.GAME_PLAY_MUSIC);
        this.game.media.playSound(Assets.GAME_OVER_SOUND);
        this.spritesDrawer.clear();
        this.themeController.reset();
        Gdx.input.setInputProcessor(null);
        dispose();
        this.game.tweenController.fadeOutScreen(this, FADE_OUT_TIME, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                FadingScreen screen = new GameOverScreen(game, runtimeInfo);
                game.setScreen(screen);
                game.tweenController.fadeInScreen(screen, FADE_IN_TIME, null);
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.setProjectionMatrix(gameViewPort.getCamera().combined);
        this.game.batch.begin();
        this.themeController.currentTheme.draw(this.game.batch);
        this.spritesDrawer.drawSprites(this.game.batch);
        this.effectDrawer.drawEffects(this.game.batch);
        if (this.levelController.isLeveledUp()) {
            this.popupDrawer.register(PopupDrawer.Popup.LEVEL_UP);
        }
        else {
            this.popupDrawer.unregister(PopupDrawer.Popup.LEVEL_UP);
        }
        this.game.batch.end();
        this.popupDrawer.drawPopups();
        this.hud.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameViewPort.update(width, height, true);
        this.hud.resize(width, height, true);
    }

    @Override
    public void dispose() {
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
