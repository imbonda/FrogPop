package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
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
 * This is a singleton class used to represent the HUD (head-up display) of the game.
 * It makes the maintaining of the score, life and level very easy and accessible throughout -
 * the code.
 *
 * Created by MichaelBond on 9/10/2016.
 */
public class Hud implements Disposable {

    public static final BitmapFont FONT = new BitmapFont(Gdx.files.internal("font.fnt"));

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

    private ScoreCounter scoreCounter;
    private LevelCounter levelCounter;
    private LifeCounter lifeCounter;
    private SpriteBatch batch;
    private Stage stage;

    /**
     * Sets tha batch to be used for drawing the hud.
     *
     * @param batch    The batch object to be used for drawing the hud later on.
     */
    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
        setPanel();
        setStage();
    }

    /**
     * Sets the hud's panel.
     */
    private void setPanel() {
        this.scoreCounter = new ScoreCounter();
        this.levelCounter = new LevelCounter();
        this.lifeCounter = new LifeCounter();
    }

    /**
     * Sets the hud's stage.
     * The stage is what actually hold all the hud's elements, and it draws them eventually.
     */
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

    /**
     * Updates the Hud's stage viewport.
     *
     * @param width     The width of the screen in pixels.
     * @param height    The height of the screen in pixels.
     * @param centerCamera  Whether or not to center the camera in the center of the viewport.
     */
    public void resize(int width, int height, boolean centerCamera) {
        this.stage.getViewport().update(width, height, centerCamera);
    }

    /**
     * Draws the hud.
     */
    public void draw() {
        this.batch.setProjectionMatrix(this.stage.getCamera().combined);
        stage.draw();
    }

    /**
     * Resets the hud to its default configuration.
     */
    public void reset() {
        this.scoreCounter.reset();
        this.levelCounter.reset();
        this.lifeCounter.reset();
    }

    /**
     * Disposes the hud.
     */
    @Override
    public void dispose() {
        this.stage.dispose();
    }

    public BitmapFont getFont() {
        return FONT;
    }

    public ScoreCounter getScoreCounter(){
        return this.scoreCounter;
    }

    public LifeCounter getLifeCounter() {
        return this.lifeCounter;
    }

    public LevelCounter getLevelCounter() {
        return this.levelCounter;
    }
}
