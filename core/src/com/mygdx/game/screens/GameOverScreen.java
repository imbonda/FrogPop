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
import com.mygdx.game.effects.GameOverEffect;
import com.mygdx.game.media.Media;
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
    private Buttons button2;
    private FrogPop game;
    private Viewport viewport;
    private GameOverEffect over;
    private Texture playAgin=new Texture("buttons/button1.png");
    private Texture pressedplayAgin=new Texture("buttons/button2.png");
    private Texture tomenu=new Texture("buttons/menu.png");
    private Texture tomenupressed=new Texture("buttons/menu2.png");

    public GameOverScreen(FrogPop game) {
        this.viewport = new FitViewport(
                FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT, new OrthographicCamera());
        if (game.adsController.isInternetConnected()) {
            game.adsController.showBannerAd();
        }
        over=new GameOverEffect();
        this.game = game;
        this.hud = Hud.getInstance();
        this.Loser = new BitmapFont(Gdx.files.internal("font.fnt"));
        this.Score = new BitmapFont(Gdx.files.internal("font.fnt"));
        End=new Sprite(new Texture(Gdx.files.internal("end.jpg")));
        this.game.media.stopMusic();
        this.game.media.playSound(Media.GAME_OVER_SOUND);
        button1=new Buttons(300,355,playAgin,pressedplayAgin);
        button2=new Buttons(300,275,tomenu,tomenupressed);
    }

    @Override
    public void render(float delta) {
        update(delta);
        this.game.batch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(237/255f, 27/255f, 36/255f, 1);
        this.game.batch.setProjectionMatrix(viewport.getCamera().combined);
        End.draw(this.game.batch);
        drawGO();
        drawButtons();
        over.draw(this.game.batch);
        this.game.batch.end();
    }

    public void update(float deltaTime) {
        handleInput();
        this.viewport.getCamera().update();
    }

    public void handleInput() {
            Vector3 touches=viewport.unproject( new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            Vector2 touchVector = new Vector2(touches.x,touches.y);
            if (this.button1.isButtonsTouched(touchVector)) {
                this.hud.reset();
                this.game.setScreen(new PlayScreen(this.game));
            }
        if (this.button2.isButtonsTouched(touchVector)) {
            this.hud.reset();
            this.game.setScreen(new MainMenuScreen(this.game));
        }
    }

    private void drawGO()
    {
        SpriteBatch batch = this.game.batch;
        Loser.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        Loser.draw(batch, "You Lost",300,200);
        Score.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        Score.draw(batch, "Your score was: "+hud.getScoreCounter().getScore(),300,180);
        Score.draw(batch, "Highest score: "+ this.game.data.getHighScore(),300,160);
        Score.draw(batch, "Your level was: "+hud.getLevelCounter().getLevel(),300,140);

    }

    private void drawButtons()
    {
        SpriteBatch batch = this.game.batch;
        Vector2 button1Position = this.button1.getPosition();
        Vector2 button2Position = this.button2.getPosition();
        batch.draw(this.button1.getButtonsTexture(), button1Position.x, button1Position.y);
        batch.draw(this.button2.getButtonsTexture(), button2Position.x, button2Position.y);
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
