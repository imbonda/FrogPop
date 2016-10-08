package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.FrogPop;
import com.mygdx.game.runtime.RuntimeInfo;
import com.mygdx.game.scenes.TransactionScreen;
import com.mygdx.game.sprites.Buttons;
import com.mygdx.game.sprites.frogs.idle.IdleFreezeFrog;
import com.mygdx.game.sprites.frogs.idle.IdleFrog;

/**
 * Created by MichaelBond on 9/1/2016.
 */
public class GameOverScreen implements Screen {

    private Sprite End;
    private BitmapFont Loser;
    private BitmapFont Score;
    private Buttons button1;
    private Buttons button2;
    private FrogPop game;
    private RuntimeInfo runtimeInfo;
    private Viewport viewport;
    private Array<IdleFrog> idleFrogs;
    private Texture playAgin=new Texture("buttons/button1.png");
    private Texture pressedplayAgin=new Texture("buttons/button2.png");
    private Texture tomenu=new Texture("buttons/menu.png");
    private Texture tomenupressed=new Texture("buttons/menu2.png");
    private TransactionScreen transactionScreen;

    public GameOverScreen(FrogPop game, RuntimeInfo runtimeInfo) {
        this.viewport = new FitViewport(
                FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT, new OrthographicCamera());
        if (game.adsController.isInternetConnected()) {
            game.adsController.showBannerAd();
        }
        this.game = game;
        this.runtimeInfo = runtimeInfo;
        this.Loser = new BitmapFont(Gdx.files.internal("font.fnt"));
        this.Score = new BitmapFont(Gdx.files.internal("font.fnt"));
        End=new Sprite(new Texture(Gdx.files.internal("end.jpg")));
        button1=new Buttons(600,355,playAgin,pressedplayAgin);
        button2=new Buttons(600,275,tomenu,tomenupressed);
        initIdleFrogs();
        transactionScreen=new TransactionScreen(this.game);
    }

    private void initIdleFrogs() {
        this.idleFrogs = new Array<IdleFrog>();
        idleFrogs.add(new IdleFreezeFrog(this.game.assetController,
                    IdleFreezeFrog.AnimationType.BIG, new Vector2(100, 50)));
        //idleFrogs.add(new IdleFreezeFrog(IdleFreezeFrog.AnimationType.normal, new Vector2(175, 175)));
    }

    @Override
    public void render(float delta) {
        update(delta);
        this.game.batch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       // Gdx.gl.glClearColor(237/255f, 27/255f, 36/255f, 1);
        Gdx.gl.glClearColor(1/255f, 1/255f, 1/255f, 1);
        this.game.batch.setProjectionMatrix(viewport.getCamera().combined);
        End.draw(this.game.batch);
        drawGO();
        drawButtons();
        drawIdleFrogs();
        this.game.batch.end();
    }

    public void update(float deltaTime) {
        transactionScreen.update(deltaTime);
        handleInput();
        updateIdleFrogs(deltaTime);
        this.viewport.getCamera().update();
    }

    private void updateIdleFrogs(float deltaTime) {
        for (IdleFrog frog : this.idleFrogs) {
            frog.update(deltaTime);
        }
    }

    public void handleInput() {
            Vector3 touches=viewport.unproject( new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            Vector2 touchVector = new Vector2(touches.x,touches.y);
            if (this.button1.isButtonsTouched(touchVector)) {
                this.transactionScreen.setNextScreen(new PlayScreen(this.game));
            }
        if (this.button2.isButtonsTouched(touchVector)) {
            this.transactionScreen.setNextScreen(new MainMenuScreen(this.game));
        }
    }

    private void drawGO()
    {
        SpriteBatch batch = this.game.batch;
        Loser.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        Loser.getData().setScale(0.2f);
        Loser.draw(batch, "You Lost",600,200);
        Score.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        Score.getData().setScale(0.2f);
        Score.draw(batch, "Your score was: " + this.runtimeInfo.gameScore, 600, 180);
        Score.draw(batch, "Highest score: " + this.game.data.getHighScore(), 600, 160);
        Score.draw(batch, "Your level was: " + this.runtimeInfo.gameLevel, 600, 140);

    }

    private void drawButtons()
    {
        SpriteBatch batch = this.game.batch;
        Vector2 button1Position = this.button1.getPosition();
        Vector2 button2Position = this.button2.getPosition();
        batch.draw(this.button1.getButtonsTexture(), button1Position.x, button1Position.y);
        batch.draw(this.button2.getButtonsTexture(), button2Position.x, button2Position.y);
    }

    private void drawIdleFrogs() {
        for (IdleFrog frog : this.idleFrogs) {
            frog.draw(this.game.batch);
        }
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        End.getTexture().dispose();
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
