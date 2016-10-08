package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.FrogPop;
import com.mygdx.game.assets.Assets;
import com.mygdx.game.effects.EffectDrawer;
import com.mygdx.game.managment.LevelController;
import com.mygdx.game.managment.ThemeController;
import com.mygdx.game.managment.GamePlayTouchProcessor;
import com.mygdx.game.runtime.RuntimeInfo;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.sprites.SpritesDrawer;
import com.mygdx.game.scenes.panel.Timer;

/**
 * Created by MichaelBond on 9/1/2016.
 */
public class PlayScreen implements Screen {

    public static Viewport gameViewPort = new FitViewport(
                FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT, new OrthographicCamera());

    private static final int MAX_LIVES = 3;

    private boolean isAlreadyOver;
    private FrogPop game;
    private SpritesDrawer spritesDrawer;
    private EffectDrawer effectDrawer;
    private LevelController levelController;
    private ThemeController themeController;
    private RuntimeInfo runtimeInfo;
    private Hud hud;
    private TransitionController transitionController;

    public PlayScreen(FrogPop game) {
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
        this.hud = new Hud(this.game.batch, runtimeInfo, timer);
        Gdx.input.setInputProcessor(new GamePlayTouchProcessor(gameViewPort, runtimeInfo));
        this.transitionController = new TransitionController(this.game);
        this.isAlreadyOver = false;
        // Play music.
        this.game.media.stopMusic(Assets.MAIN_MENU_MUSIC);
        this.game.media.playMusic(Assets.GAME_PLAY_MUSIC);
    }

    public void update(float deltaTime) {
        this.transitionController.update(deltaTime);
        this.levelController.update(deltaTime);
        this.hud.update();
        if (this.runtimeInfo.gameLives <= 0 && !this.isAlreadyOver) {
            this.isAlreadyOver = true;
            gameOver();
        }
    }

    private void gameOver() {
        this.transitionController.setNextScreen(new GameOverScreen(this.game, this.runtimeInfo));
        this.game.data.updateHighScore(this.runtimeInfo.gameScore);
        this.game.media.stopMusic(Assets.GAME_PLAY_MUSIC);
        this.game.media.playSound(Assets.GAME_OVER_SOUND);
        this.spritesDrawer.clear();
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.setProjectionMatrix(gameViewPort.getCamera().combined);
        this.game.batch.begin();
        this.themeController.currentTheme.draw(this.game.batch);
        this.spritesDrawer.drawSprites(this.game.batch);
        this.effectDrawer.drawEffects(this.game.batch);
        this.game.batch.end();
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
