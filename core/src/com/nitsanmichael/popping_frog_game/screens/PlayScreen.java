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
    public RuntimeInfo runtimeInfo;
    public ThemeController themeController;
    public LevelController levelController;
    public Hud hud;
    public PopupDrawer popupDrawer;

    private StateTracker stateTracker;


    public PlayScreen(PoppingFrog game) {
        super(game.batch, game.tweenController);
        this.game = game;
        this.gameViewPort = new FitViewport(
                    PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT, new OrthographicCamera());
        this.spritesDrawer = new SpritesDrawer();
        this.effectDrawer = new EffectDrawer();
        this.themeController = new ThemeController(this.game.config, this.game.assetController,
                    this.effectDrawer);
        this.stateTracker = new StateTracker(this);
        this.runtimeInfo = new RuntimeInfo(0, MAX_LIVES, this.stateTracker);
        Timer timer = new Timer(this.game.assetController);
        this.hud = new Hud(this.game.assetController, this.game.batch, runtimeInfo, timer);
        this.popupDrawer = new PopupDrawer(this.gameViewPort, this.game.batch,
                this.game.assetController, this.game.tweenController, this.runtimeInfo);
        this.levelController = new LevelController(
                    this.game.config, this.game.assetController, this.game.media, this.spritesDrawer,
                    this.popupDrawer, this.runtimeInfo, timer, this.themeController);
        // Play music.
        this.game.media.pauseMusic(Assets.MAIN_MENU_MUSIC);
        this.game.media.playMusic(Assets.GAME_PLAY_MUSIC);

        this.stateTracker.setState(StateTracker.GameState.PLAY);

        game.adsController.hideBannerAd();
    }

    public void gameOver() {
        this.game.data.updateHighScore(this.runtimeInfo.gameScore);
        this.game.playServices.submitScore(this.game.data.getHighScore());
        this.game.media.stopMusic(Assets.GAME_PLAY_MUSIC);
        this.game.media.playSound(Assets.GAME_OVER_SOUND);
        this.spritesDrawer.clear();
        this.themeController.reset();
        fadeOut(FADE_OUT_TIME, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                dispose();
                new GameOverScreen(game, runtimeInfo).fadeIn(game, FADE_IN_TIME);
            }
        });
    }

    @Override
    public void render(float delta) {
        this.stateTracker.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        this.gameViewPort.update(width, height, true);
        this.hud.resize(width, height, true);
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
