package com.nitsanmichael.popping_frog_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.effects.EffectDrawer;
import com.nitsanmichael.popping_frog_game.managment.HolesManager;
import com.nitsanmichael.popping_frog_game.managment.LevelController;
import com.nitsanmichael.popping_frog_game.managment.ThemeController;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.runtime.ScreenInfo;
import com.nitsanmichael.popping_frog_game.scenes.Hud;
import com.nitsanmichael.popping_frog_game.scenes.PopupDrawer;
import com.nitsanmichael.popping_frog_game.scenes.panel.Timer;
import com.nitsanmichael.popping_frog_game.sprites.SpritesDrawer;
import com.nitsanmichael.popping_frog_game.states.StateTracker;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;


/**
 * Created by MichaelBond on 9/1/2016.
 */
public class PlayScreen extends FadingScreen {

    private static final float FADE_OUT_TIME = 0.1f;
    private static final float FADE_IN_TIME = 0.25f;
    private static final int MAX_LIVES = 3;

    public PoppingFrog game;
    public Viewport gameViewPort;
    public SpritesDrawer spritesDrawer;
    public EffectDrawer effectDrawer;
    public ScreenInfo screenInfo;
    public RuntimeInfo runtimeInfo;
    public HolesManager holesManager;
    public ThemeController themeController;
    public LevelController levelController;
    public Timer timer;
    public Hud hud;
    public PopupDrawer popupDrawer;
    public Viewport backgroundViewport;

    private StateTracker stateTracker;


    public PlayScreen(PoppingFrog game) {
        super(game.batch, game.tweenController);
        this.game = game;
        this.gameViewPort = new ExtendViewport(
                    PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT);
        this.backgroundViewport = new StretchViewport(PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT);
        this.spritesDrawer = new SpritesDrawer();
        this.effectDrawer = new EffectDrawer();
        this.stateTracker = new StateTracker(this);
        this.screenInfo = new ScreenInfo(this.gameViewPort);
        this.runtimeInfo = new RuntimeInfo(0, MAX_LIVES, this.stateTracker, this.screenInfo);
        this.themeController = new ThemeController(game.config, game.assetController,
                    this.effectDrawer, this.runtimeInfo);
        this.timer = new Timer(game.assetController, game.tweenController);
        this.hud = new Hud(game.assetController, game.batch, this.runtimeInfo, this.timer);
        this.popupDrawer = new PopupDrawer(this.gameViewPort, this.game.batch,
                    game.assetController, game.tweenController, this.runtimeInfo);
        this.holesManager = new HolesManager(game.assetController, this.spritesDrawer,
                    this.runtimeInfo);
        this.levelController = new LevelController(
                    game.config, game.assetController, game.media, this.spritesDrawer,
                    this.popupDrawer, this.runtimeInfo, timer, this.themeController);
        // Pause menu music.
        game.media.pauseMusic(Assets.MAIN_MENU_MUSIC);

        this.stateTracker.setState(StateTracker.GameState.PLAY);

        game.adsController.hideBannerAd();
    }

    public void gameOver() {
        this.game.data.updateHighScore(this.runtimeInfo.gameScore);
        this.game.playServices.submitScore(this.game.data.getHighScore());
        fadeOut(FADE_OUT_TIME, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.media.stopSound(Assets.GAME_OVER_SOUND);
                spritesDrawer.clear();
                themeController.reset();
                dispose();
                new GameOverScreen(game, runtimeInfo).fadeIn(game, FADE_IN_TIME);
            }
        });
    }

    public void backToMenu() {
        fadeOut(FADE_OUT_TIME, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.media.stopMusic(Assets.GAME_PLAY_MUSIC);
                spritesDrawer.clear();
                themeController.reset();
                dispose();
                new MainMenuScreen(game).fadeIn(game, FADE_IN_TIME);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stateTracker.render(delta);
    }

    public void draw() {
        this.game.batch.begin();
        this.game.batch.setProjectionMatrix(this.backgroundViewport.getCamera().combined);
        this.themeController.currentTheme.draw(this.game.batch);
        this.game.batch.setProjectionMatrix(this.gameViewPort.getCamera().combined);
        this.spritesDrawer.drawSprites(this.game.batch);
        this.effectDrawer.drawEffects(this.game.batch);
        this.game.batch.end();
        this.popupDrawer.drawPopups();
        this.hud.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.hud.resize(width, height, true);
        this.backgroundViewport.update(width,height,true);
        this.gameViewPort.getCamera().position.set(0,0,0);
        this.gameViewPort.getCamera().translate(
                    PoppingFrog.VIRTUAL_WIDTH / 2,
                    PoppingFrog.VIRTUAL_HEIGHT / 2,
                    0);
        this.gameViewPort.update(width, height, false);
        this.screenInfo.resize(width, height);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        this.hud.dispose();
        this.popupDrawer.dispose();
        this.stateTracker.dispose();
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
