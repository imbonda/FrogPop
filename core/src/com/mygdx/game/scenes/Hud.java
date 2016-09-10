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
import com.mygdx.game.managment.LevelController;
import com.mygdx.game.scenes.panel.LevelCounter;
import com.mygdx.game.scenes.panel.LifeCounter;
import com.mygdx.game.scenes.panel.ScoreCounter;

/**
 * Created by MichaelBond on 9/10/2016.
 */
public class Hud implements Disposable {

    private static Hud ourInstance = new Hud();

    /**
     * Singleton implementation.
     *
     * @return  The singleton object.
     */
    public static Hud getInstance() {
        return ourInstance;
    }

    /**
     * Singleton private constructor.
     */
    private Hud() {
    }

    public static final BitmapFont FONT = new BitmapFont(Gdx.files.internal("font.fnt"));
    private static ScoreCounter scoreCounter = new ScoreCounter();
    private static LevelCounter levelCounter = new LevelCounter();
    private static LifeCounter lifeCounter = new LifeCounter();
    public static Stage stage;
    private static SpriteBatch batch;

    public void setBatch(SpriteBatch batch2) {
        batch = batch2;
        setStage();
    }

    private static void setStage() {
        Viewport hudViewPort = new FitViewport(
                FrogPop.VIRTUAL_WIDTH,
                FrogPop.VIRTUAL_HEIGHT,
                new OrthographicCamera());

        stage = new Stage(hudViewPort, batch);
        stage.addActor(scoreCounter);
        stage.addActor(levelCounter);
        stage.addActor(lifeCounter);
    }

    public void draw() {
        stage.draw();
    }

    public void reset() {
        scoreCounter.reset();
        levelCounter.reset();
        lifeCounter.reset();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public BitmapFont getFont() {
        return FONT;
    }

    public Stage getStage() {
        return stage;
    }

    public ScoreCounter getScoreCounter(){
        return scoreCounter;
    }

    public LifeCounter getLifeCounter() {
        return lifeCounter;
    }

    public LevelCounter getLevelCounter() {
        return levelCounter;
    }
}
