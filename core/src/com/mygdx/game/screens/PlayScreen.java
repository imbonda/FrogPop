package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.FrogPop;
import com.mygdx.game.managment.LevelController;
import com.mygdx.game.managment.ThemeController;
import com.mygdx.game.managment.GamePlayTouchProcessor;
import com.mygdx.game.runtime.RuntimeInfo;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.sprites.SpritesDrawer;


/**
 * Created by MichaelBond on 9/1/2016.
 */
public class PlayScreen implements Screen {

    public static Viewport gameViewPort = new FitViewport(
                FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT, new OrthographicCamera());

    private FrogPop game;
    private SpritesDrawer spritesDrawer;
    private LevelController levelController;
    private ThemeController themeController;
    private Hud hud;

    public PlayScreen(FrogPop game) {
        game.adsController.hideBannerAd();
        this.spritesDrawer = new SpritesDrawer();
        this.game = game;
        this.hud = Hud.getInstance();
        this.themeController = new ThemeController(this.game.config);
        RuntimeInfo runtimeInfo = new RuntimeInfo();
        this.levelController = new LevelController(
                    this.game.config, this.game.media, spritesDrawer, runtimeInfo, this.themeController);
        Gdx.input.setInputProcessor(new GamePlayTouchProcessor(gameViewPort, runtimeInfo));
        this.game.media.playMusic();
    }

    public void update(float deltaTime) {
        this.levelController.update(deltaTime);
        if (this.hud.getLifeCounter().getLife() <= 0) {
            gameOver();
        }
    }

    private void gameOver() {
        this.game.data.updateHighScore(this.hud.getScoreCounter().getScore());
        this.game.setScreen(new GameOverScreen(this.game));
        dispose();
        this.spritesDrawer.clear();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.setProjectionMatrix(gameViewPort.getCamera().combined);
        this.game.batch.begin();
        this.themeController.currentTheme.draw(this.game.batch);
        this.spritesDrawer.drawSprites(this.game.batch);
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
