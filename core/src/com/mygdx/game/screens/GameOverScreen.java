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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.FrogPop;
import com.mygdx.game.data.Data;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.sprites.Buttons;


/**
 * Created by MichaelBond on 9/1/2016.
 */
public class GameOverScreen implements Screen {

    private Hud hud;
    private Sprite End;
    private BitmapFont Loser;
    private BitmapFont Score;
    private Buttons button1;
    private FrogPop game;
    private Viewport viewport;

    public GameOverScreen(FrogPop game) {
        this.game = game;
        this.hud = Hud.getInstance();
        this.Loser = new BitmapFont(Gdx.files.internal("font.fnt"));
        this.Score = new BitmapFont(Gdx.files.internal("font.fnt"));
        End=new Sprite(new Texture(Gdx.files.internal("end.jpg")));
        button1=new Buttons(300,245);
        this.viewport = new FitViewport(
                    FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT, new OrthographicCamera());
    }

    @Override
    public void render(float delta) {
        update(delta);
        this.game.batch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(237/255f, 27/255f, 36/255f, 1);
        End.draw(this.game.batch);
        drawGO();
        drawButtons();
        this.game.batch.end();
    }

    public void update(float deltaTime) {
        handleInput();
        this.viewport.getCamera().update();
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touches=viewport.unproject( new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            Vector2 touchVector = new Vector2(touches.x,touches.y);
            if (this.button1.isButtonsTouched(touchVector)) {
                this.hud.reset();
                this.game.setScreen(new PlayScreen(this.game));
            }
        }
    }

    private void drawGO()
    {
        SpriteBatch batch = this.game.batch;
        Loser.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        Loser.draw(batch, "You Lost",300,400);
        Score.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        Score.draw(batch, "Your score was: "+hud.getScoreCounter().getScore(),300,380);
        Score.draw(batch, "Highest score: "+ Data.getInstance().getHighScore(),300,360);
        Score.draw(batch, "Your level was: "+hud.getLevelCounter().getLevel(),300,340);

    }

    private void drawButtons()
    {
        SpriteBatch batch = this.game.batch;
        Vector2 buttonsPosition = this.button1.getPosition();
        batch.draw(this.button1.getButtonsTexture(), buttonsPosition.x, buttonsPosition.y);
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
        this.hud.resize(width, height, true);
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
