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
import com.mygdx.game.media.Media;
import com.mygdx.game.runtime.RuntimeInfo;
import com.mygdx.game.sprites.Buttons;
import com.mygdx.game.sprites.frogs.idle.IdleBritishFrog;
import com.mygdx.game.sprites.frogs.idle.IdleFreezeFrog;
import com.mygdx.game.sprites.frogs.idle.IdleFrog;
import com.mygdx.game.sprites.frogs.idle.IdleHealthFrog;
import com.mygdx.game.sprites.frogs.idle.IdleMexicanFrog;
import com.mygdx.game.sprites.frogs.idle.IdlePoisonFrog;
import com.mygdx.game.sprites.frogs.idle.IdleRegularFrog;
import com.mygdx.game.sprites.frogs.idle.IdleTurkishFrog;

import java.util.Iterator;

/**
 * Created by MichaelBond on 9/1/2016.
 */
public class ChooseHero implements Screen {
    private Sprite End;
    private BitmapFont Score;
    private Buttons button1;
    private Buttons button2;
    private FrogPop game;
    private Viewport viewport;
    private Array<IdleFrog> idleFrogs;
    private Texture mainMenu=new Texture("buttons/menu.png");
    private Texture pressedMainMenu=new Texture("buttons/menu2.png");
    private Texture next=new Texture("buttons/choosehero.png");
    private Texture nextpressed=new Texture("buttons/choosehero2.png");
    private  IdleFrog frog;
    private int index=0;
    private static final float TimeToStayPressedBeforeNext=1;
    private float pressedTime=0;


    public ChooseHero(FrogPop game) {
        this.viewport = new FitViewport(
                FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT, new OrthographicCamera());
        if (game.adsController.isInternetConnected()) {
            game.adsController.showBannerAd();
        }
        this.game = game;
        this.Score = new BitmapFont(Gdx.files.internal("font.fnt"));
        End=new Sprite(new Texture(Gdx.files.internal("intro2.jpg")));
        this.game.media.playSound(Media.GAME_OVER_SOUND);
        button1=new Buttons(570,355,mainMenu,pressedMainMenu);
        button2=new Buttons(570,275,next,nextpressed);
        this.idleFrogs= new Array<IdleFrog>();
        initIdleFrogs();
        frog=idleFrogs.get(index);
    }

    private void initIdleFrogs() {
        idleFrogs.add(new IdleRegularFrog(IdleRegularFrog.AnimationType.TONGUE, new Vector2(400, 200)));
        idleFrogs.add(new IdleBritishFrog(IdleBritishFrog.AnimationType.TONGUE, new Vector2(400, 200)));
        idleFrogs.add(new IdleMexicanFrog(IdleMexicanFrog.AnimationType.TONGUE, new Vector2(400, 200)));
        idleFrogs.add(new IdleTurkishFrog(IdleTurkishFrog.AnimationType.TONGUE, new Vector2(400, 200)));
    }

    @Override
    public void render(float delta) {
        update(delta);
        this.game.batch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1/255f, 1/255f, 1/255f, 1);
        this.game.batch.setProjectionMatrix(viewport.getCamera().combined);
        End.draw(this.game.batch);
        drawGO();
        drawButtons();
        drawIdleFrogs();
        this.game.batch.end();
    }

    public void update(float deltaTime) {
        pressedTime+=deltaTime;
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
                this.game.setScreen(new MainMenuScreen(this.game));
            }
        if (this.button2.isButtonsTouched(touchVector)&&pressedTime>TimeToStayPressedBeforeNext){
            index++;
            frog=idleFrogs.get(index%4);
            pressedTime=0;
        }
    }

    private void drawGO()
    {
        SpriteBatch batch = this.game.batch;
        Score.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        Score.draw(batch, "Highest score: " + this.game.data.getHighScore(), 600, 160);
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
        frog.draw(this.game.batch);
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
