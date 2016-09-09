package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.FrogPop;
import com.mygdx.game.scenes.panel.LevelCounter;
import com.mygdx.game.scenes.panel.LifeCounter;
import com.mygdx.game.scenes.panel.ScoreCounter;

/**
 * Created by MichaelBond on 9/8/2016.
 */
public class Hud implements Disposable {

    public static final BitmapFont FONT;

    static {
        // init font.
        FONT = new BitmapFont(Gdx.files.internal("font.fnt"));
    }

    public ScoreCounter scoreCounter;
    public LevelCounter levelCounter;
    public LifeCounter lifeCounter;

    private SpriteBatch batch;
    private Stage stage;


    public Hud(SpriteBatch batch) {
        this.batch = batch;
        this.scoreCounter = new ScoreCounter();
        this.levelCounter = new LevelCounter();
        this.lifeCounter = new LifeCounter();
        setStage();
    }

    private void setStage() {
        Viewport hudViewPort = new FitViewport(
                FrogPop.VIRTUAL_WIDTH,
                FrogPop.VIRTUAL_HEIGHT,
                new OrthographicCamera());

        this.stage = new Stage(hudViewPort, this.batch);
        this.stage.addActor(this.scoreCounter);
        this.stage.addActor(this.levelCounter);
        this.stage.addActor(this.lifeCounter);
    }

    public void draw() {
        this.stage.draw();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }

}
