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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.FrogPop;
import com.mygdx.game.sprites.Frog;
import com.mygdx.game.sprites.FrogGenerator;
import com.mygdx.game.sprites.Hole;


/**
 * Created by MichaelBond on 9/1/2016.
 */
public class PlayScreen implements Screen {

    private static float FROG_LIFE_TIME_SECS =5.0f;
    private static final Vector2[] HOLES_POSITIONS = {
            new Vector2(50, 50), new Vector2(300, 50), new Vector2(50, 200), new Vector2(300, 200),new Vector2(550, 50),new Vector2(550, 200),new Vector2(50, 350),new Vector2(300, 350),new Vector2(550,350)
    };

    private Vector3 touches;
    private BitmapFont scoreFont;
    private BitmapFont Time;
    private BitmapFont Level;
    private BitmapFont Life;
    private Texture backgroundTexture;
    private Array<Hole> holes;
    private Frog[] frog;
    private int yourscore = 0;
    private int frogs=1;
    private int lives=3;
    private FrogPop game;
    private Viewport gameViewPort;
    private FrogGenerator frogManage;
    private Texture frog1=new Texture("2.png");
    private Texture frog2=new Texture("3.png");
    private Texture frog3=new Texture("4.png");


    public PlayScreen(FrogPop game) {
        this.backgroundTexture=new Texture("world.jpg");
        this.scoreFont = new BitmapFont();
        this.Time = new BitmapFont();
        this.Level = new BitmapFont();
        this.Life = new BitmapFont();
        this.holes = new Array<Hole>();
        frogManage =new FrogGenerator();
        FROG_LIFE_TIME_SECS =5.0f;
        for (int i = 0; i < 9; ++i) {
            this.holes.add(new Hole(HOLES_POSITIONS[i].x, HOLES_POSITIONS[i].y));
        }
        frog=new Frog[9];
            this.frog[0] = frogManage.generateFrog(this.holes);
        this.frog[1] = frogManage.generateFrog(this.holes);
        this.frog[02] = frogManage.generateFrog(this.holes);
        this.frog[3] = frogManage.generateFrog(this.holes);
        this.frog[4] = frogManage.generateFrog(this.holes);
        this.frog[5] = frogManage.generateFrog(this.holes);
        this.frog[6] = frogManage.generateFrog(this.holes);
        this.frog[7] = frogManage.generateFrog(this.holes);
        this.frog[8] = frogManage.generateFrog(this.holes);
        this.game = game;
        this.gameViewPort = new FitViewport(
                    FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT, new OrthographicCamera());
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            touches=this.gameViewPort.unproject( new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            Vector2 touchVector = new Vector2(touches.x,touches.y);

            if (this.frog[0].isFrogTouched(touchVector) && !frog[0].isDead) {
                   // System.out.print(getLastfrog());
                this.yourscore++;
                this.frog[0].isDead = true;
                if((this.yourscore%10)==0&&yourscore!=0)
                {
                    this.FROG_LIFE_TIME_SECS=(float)(FROG_LIFE_TIME_SECS*0.8);
                }
            }
            else
            {
                lives--;
                if(lives==0) {
                    game.setScreen(new GameOverScreen(game,yourscore));
                }
            }

        }
    }

    public void update(float deltaTime) {
        handleInput();
        for(int i=0;i<frogs;i++) {
            this.frog[i].lifeTime += deltaTime;
        }
        if (this.frog[0].lifeTime >= FROG_LIFE_TIME_SECS) {
            game.setScreen(new GameOverScreen(game,yourscore));
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(171/255f,107/255f,72/255f,1);
        this.game.batch.setProjectionMatrix(this.gameViewPort.getCamera().combined);
        this.game.batch.begin();
        drawBackground();
        drawHole();
        drawFrog();
        drawScore();
        drawGO();
        drawLevel();
        this.game.batch.end();
    }

    public void drawBackground() {
        SpriteBatch batch = this.game.batch;
        batch.draw(this.backgroundTexture, 0, 0);
    }

    private void drawScore() {
        SpriteBatch batch = this.game.batch;

        scoreFont.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        scoreFont.draw(batch, "Your Mother Fucking Score: " + yourscore, 25, 520);
    }
    private void drawGO()
    {
        SpriteBatch batch = this.game.batch;
        Time.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        Time.draw(batch, "Time remain:"+ (int)(((FROG_LIFE_TIME_SECS- this.frog[getLastfrog()].lifeTime)*100)/(FROG_LIFE_TIME_SECS)),25,470);
        Life.setColor(Color.GREEN);
        Life.setColor(Color.GREEN);
        Life.draw(batch, "Lifes:"+lives,740,510);


    }
    private void drawLevel()
    {
        SpriteBatch batch = this.game.batch;

        Level.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        Level.draw(batch, "Your Level :" + (int) yourscore /+10, 25, 495);
        if(yourscore%10==0&yourscore!=0) {
            Level.setColor(Color.RED);
            Level.draw(batch, "Level up!", FrogPop.VIRTUAL_WIDTH/2-15, FrogPop.VIRTUAL_WIDTH/2+70);
        }
    }

    private void drawHole() {
        SpriteBatch batch = this.game.batch;

        for (Hole hole: this.holes) {
            Vector2 holePosition = hole.getPosition();
            batch.draw(hole.getHoleTexture(), holePosition.x, holePosition.y);
        }
    }

    private void drawFrog() {
        SpriteBatch batch = this.game.batch;

         // if (yourscore >= 2) {
           // Vector2 frogPosition = this.frog[1].getPosition();
         //batch.draw(this.frog[1].getFrogTexture(),frogPosition.x, frogPosition.y, 0, 0, 100, 100-(int)(((FROG_LIFE_TIME_SECS- this.frog[1].lifeTime)*100)/(FROG_LIFE_TIME_SECS)));
        //}
        if (!frog[0].isDead) {
            Vector2 frogPosition = this.frog[0].getPosition();
            batch.draw(this.frog[0].getFrogTexture(), frogPosition.x, frogPosition.y,0,0,100, 100-(int)(((FROG_LIFE_TIME_SECS- this.frog[0].lifeTime)*100)/(FROG_LIFE_TIME_SECS)));
        }
        else {
            this.frog[0] = frogManage.generateFrog(this.holes);
            Vector2 frogPosition = this.frog[0].getPosition();
            batch.draw(this.frog[0].getFrogTexture(), frogPosition.x, frogPosition.y, 0, 0, 100, 100 - (int) (((FROG_LIFE_TIME_SECS - this.frog[0].lifeTime) * 100) / (FROG_LIFE_TIME_SECS)));
        }
    }
    public int getLastfrog()
    {
        int lastindex=0;
        int i=0;
        while(i<frogs) {
            if (frog[i].lifeTime > frog[lastindex].lifeTime) {
                lastindex = i;
            }

            i++;
        }
        return lastindex;
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
