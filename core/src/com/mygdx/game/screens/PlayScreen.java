package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.FrogPop;
import com.mygdx.game.managment.LevelController;
import com.mygdx.game.managment.ThemeController;
import com.mygdx.game.managment.TouchProcessor;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.sprites.SpritesDrawer;
import com.mygdx.game.sprites.Hole;


/**
 * Created by MichaelBond on 9/1/2016.
 */
public class PlayScreen implements Screen {

    public static Viewport gameViewPort = new FitViewport(
                FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT, new OrthographicCamera());
    public static Array<Hole> holes = new Array<Hole>();

    private static final Vector2[] HOLES_POSITIONS = {
            new Vector2(50, 35), new Vector2(300, 35), new Vector2(50, 185),
            new Vector2(300, 185), new Vector2(550, 35), new Vector2(550, 185),
            new Vector2(50, 325), new Vector2(300, 325), new Vector2(550,325)
    };

    static {
        for (Vector2 holePosition : HOLES_POSITIONS) {
            holes.add(new Hole(holePosition.x, holePosition.y));
        }
    }

    private FrogPop game;
    private LevelController levelController;
    private ThemeController themeController;
    private Hud hud;

    public PlayScreen(FrogPop game) {
        SpritesDrawer.getInstance().addSprites(holes);
        this.game = game;
        this.hud = Hud.getInstance();
        this.levelController = LevelController.getInstance();
        this.levelController.init();
        this.themeController = ThemeController.getInstance();
        Gdx.input.setInputProcessor(new TouchProcessor(gameViewPort));
    }

    public void update(float deltaTime) {
        this.levelController.update(deltaTime);
        if (this.hud.getLifeCounter().getLife() <= 0) {
            gameOver();
        }
    }

    private void gameOver() {
        this.game.setScreen(new GameOverScreen(this.game));
        this.themeController.currentTheme.getMusic().stop();
        dispose();
        SpritesDrawer.getInstance().clear();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.setProjectionMatrix(gameViewPort.getCamera().combined);
        this.game.batch.begin();
        this.themeController.currentTheme.draw(this.game.batch);
        SpritesDrawer.getInstance().drawSprites();
        this.game.batch.end();
        this.game.batch.setProjectionMatrix(this.hud.getStage().getCamera().combined);
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
