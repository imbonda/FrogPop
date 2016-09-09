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
import com.mygdx.game.scenes.Hud;
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
    private static final float FROG_LIFE_TIME_DECREASE_FACTOR = 0.92f;
    private static final Vector2[] HOLES_POSITIONS = { new Vector2(50, 35), new Vector2(300, 35), new Vector2(50, 185), new Vector2(300, 185),new Vector2(550, 35),new Vector2(550, 185),new Vector2(50, 325),new Vector2(300, 325),new Vector2(550,325)};
    private BitmapFont scoreFont;
    private BitmapFont Time;
    private BitmapFont Level;
    private BitmapFont Life;
    private Texture[] backgroundTexture;
    private Array<Hole> holes;
    private FrogPop game;
    private Viewport gameViewPort;
    private FrogManager frogManager;
    private Timer levelTimer;
    private LevelController levelController;
    private Hud hud;
    private int level=0;
    private Random randAdd=new Random();
    private int[] whenAddfrogs={randAdd.nextInt(5)+3,randAdd.nextInt(7)+13,randAdd.nextInt(8)+20,randAdd.nextInt(8)+30,randAdd.nextInt(10)+40};

    public PlayScreen(FrogPop game) {
        this.game = game;

        this.backgroundTexture=new Texture[4];
        this.backgroundTexture[0]=new Texture("world.jpg");
        this.backgroundTexture[1]=new Texture("world2.jpg");
        this.backgroundTexture[2]=new Texture("world3.jpg");
        this.backgroundTexture[3]=new Texture("world4.jpg");
        this.scoreFont = new BitmapFont(Gdx.files.internal("font.fnt"));
        this.Time = new BitmapFont(Gdx.files.internal("font.fnt"));
        this.Level = new BitmapFont(Gdx.files.internal("font.fnt"));
        this.Life = new BitmapFont(Gdx.files.internal("font.fnt"));
        this.holes = new Array<Hole>();
        for (int i = 0; i < 9; ++i) {
            this.holes.add(new Hole(HOLES_POSITIONS[i].x, HOLES_POSITIONS[i].y));
        }
        this.levelTimer = new Timer();
        this.hud = new Hud(this.game.batch);
        this.frogManager = new FrogManager(this.holes, FROG_LIFE_TIME_SECS);
        //TODO (Test)
        this.levelController = new LevelController(this.levelTimer, this.hud, this.frogManager);
        this.frogManager.addFrog();
        this.gameViewPort = new FitViewport(FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT, new OrthographicCamera());
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector2 touchVector = this.gameViewPort.unproject(
                        new Vector2(Gdx.input.getX(),Gdx.input.getY()));

            for (Frog frog: this.frogManager.getFrogs()) {
                if (frog.isFrogTouched(touchVector) && !frog.isLifeTimeExpired()) {
                    this.hud.scoreCounter.addScore(1);
                    frog.setKilled();
                    return;
                }
            }
            Gdx.input.vibrate(500);
            this.hud.lifeCounter.subdtractLife(1);
        }

    }

    public void update(float deltaTime) {
        handleInput();
        this.levelController.update(deltaTime);
        this.frogManager.update(deltaTime);
        for (Frog frog: this.frogManager.getFrogs()) {
            if (frog.isLifeTimeExpired()) {
                this.hud.lifeCounter.subdtractLife(1);
                frog.setKilled();
                Gdx.input.vibrate(500);
            }
        }
        if(this.hud.lifeCounter.getLife() <= 0) {
            game.setScreen(new GameOverScreen(this.game, this.hud));
        }
        if(this.levelTimer.isTimedOut()) {
            this.frogManager.decreaseFrogMaxLifeTime(FROG_LIFE_TIME_DECREASE_FACTOR);
            this.levelTimer.setCountTimeByFactor(1.05f);
            level++;
            if((level==whenAddfrogs[level/10])&&level<59)
            {frogManager.addFrog();
                FROG_LIFE_TIME_SECS=FROG_LIFE_TIME_SECS*1.2f;
            }
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.setProjectionMatrix(this.gameViewPort.getCamera().combined);
        this.game.batch.begin();
        drawBackground();
        drawHole();
        drawFrogs();
        //drawScore();
        //drawGO();
        //drawLevel();
        this.levelTimer.draw(this.game.batch);
        this.game.batch.end();

        this.game.batch.setProjectionMatrix(this.hud.stage.getCamera().combined);
        this.hud.draw();
    }

    public void drawBackground() {
        SpriteBatch batch = this.game.batch;
        Gdx.gl.glClearColor(171/255f,107/255f,72/255f,1);
        batch.draw(this.backgroundTexture[(level/10)%4], 0, 0);
    }

    private void drawHole() {
        SpriteBatch batch = this.game.batch;

        for (Hole hole: this.holes) {
            Vector2 holePosition = hole.getPosition();
            batch.draw(hole.getHoleTexture(), holePosition.x, holePosition.y);
        }
    }

    private void drawFrogs() {
        SpriteBatch batch = this.game.batch;
        for (Frog frog: this.frogManager.getFrogs()) {
            if (!frog.isKilled() && !frog.isLifeTimeExpired()) {
                frog.draw(batch);
            }
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
