package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.FrogPop;
import com.mygdx.game.managment.LevelController;
import com.mygdx.game.managment.TouchProcessor;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.sprites.SpritesDrawer;
import com.mygdx.game.sprites.frogs.Frog;
import com.mygdx.game.managment.FrogManager;
import com.mygdx.game.sprites.Hole;
import com.mygdx.game.sprites.Timer;

import java.util.Random;


/**
 * Created by MichaelBond on 9/1/2016.
 */
public class PlayScreen implements Screen {

    private static float FROG_LIFE_TIME_SECS =5.0f;
    private static final Vector2[] HOLES_POSITIONS = { new Vector2(50, 35), new Vector2(300, 35), new Vector2(50, 185), new Vector2(300, 185),new Vector2(550, 35),new Vector2(550, 185),new Vector2(50, 325),new Vector2(300, 325),new Vector2(550,325)};
    private Texture[] backgroundTexture;
    private Array<Hole> holes;
    private FrogPop game;
    private Viewport gameViewPort;
    private FrogManager frogManager;
    private LevelController levelController;
    private Hud hud;

    public PlayScreen(FrogPop game) {
        this.game = game;

        this.backgroundTexture=new Texture[4];
        this.backgroundTexture[0]=new Texture("world.jpg");
        this.backgroundTexture[1]=new Texture("world2.jpg");
        this.backgroundTexture[2]=new Texture("world3.jpg");
        this.backgroundTexture[3]=new Texture("world4.jpg");
        this.holes = new Array<Hole>();
        for (int i = 0; i < 9; ++i) {
            this.holes.add(new Hole(HOLES_POSITIONS[i].x, HOLES_POSITIONS[i].y));
        }
        this.hud = new Hud(this.game.batch);
        this.frogManager = new FrogManager(this.holes, FROG_LIFE_TIME_SECS, this.hud);
        this.levelController = new LevelController(this.frogManager, this.hud);
        this.frogManager.addFrog();
        this.gameViewPort = new FitViewport(FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT, new OrthographicCamera());

        Gdx.input.setInputProcessor(new TouchProcessor(this.gameViewPort, this.frogManager, this.hud));
    }

    public void update(float deltaTime) {
        this.levelController.update(deltaTime);
        this.frogManager.update(deltaTime);
        if (this.hud.lifeCounter.getLife() <= 0) {
            game.setScreen(new GameOverScreen(this.game, this.hud));
            SpritesDrawer.getInstance().removeAllSprites();
            Gdx.input.setInputProcessor(null);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.setProjectionMatrix(this.gameViewPort.getCamera().combined);
        this.game.batch.begin();
        drawBackground();
        drawHoles();
        SpritesDrawer.getInstance().drawSprites();
        this.game.batch.end();

        this.game.batch.setProjectionMatrix(this.hud.stage.getCamera().combined);
        this.hud.draw();
    }

    public void drawBackground() {
        SpriteBatch batch = this.game.batch;
        Gdx.gl.glClearColor(171/255f,107/255f,72/255f,1);
        batch.draw(this.backgroundTexture[(this.hud.levelCounter.getLevel()/10)%4], 0, 0);
    }

    private void drawHoles() {
        SpriteBatch batch = this.game.batch;

        for (Hole hole: this.holes) {
            Vector2 holePosition = hole.getPosition();
            batch.draw(hole.getHoleTexture(), holePosition.x, holePosition.y);
        }
    }

    @Override
    public void resize(int width, int height) {
        this.gameViewPort.update(width, height, true);
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
