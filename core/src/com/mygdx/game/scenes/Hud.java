package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.FrogPop;
import com.mygdx.game.runtime.RuntimeInfo;
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

    private final BitmapFont FONT = new BitmapFont(Gdx.files.internal("font.fnt"));

    private ScoreCounter scoreCounter;
    private LevelCounter levelCounter;
    private LifeCounter lifeCounter;
    private SpriteBatch batch;
    private RuntimeInfo runtimeInfo;
    private Stage stage;

    /**
     * @param batch    The batch object to be used for drawing the hud later on.
     * @param runtimeInfo The runtime information that is to be used by the hud to draw the -
     *                    score, the lives and the level.
     */
    public Hud(SpriteBatch batch, RuntimeInfo runtimeInfo) {
        this.batch = batch;
        this.runtimeInfo = runtimeInfo;
        setPanel();
        setStage();
    }

    /**
     * Sets the hud's panel.
     */
    private void setPanel() {
        this.scoreCounter = new ScoreCounter(FONT, this.runtimeInfo.gameScore);
        this.levelCounter = new LevelCounter(FONT, this.runtimeInfo.gameLevel);
        this.lifeCounter = new LifeCounter(FONT, this.runtimeInfo.gameLives);
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
     * Updates the hud to indicate the most updated game-state.
     */
    public void update() {
        this.scoreCounter.updateScore(this.runtimeInfo.gameScore);
        this.levelCounter.updateLevel(this.runtimeInfo.gameLevel);
        this.lifeCounter.updateLives(this.runtimeInfo.gameLives);
    }

    /**
     * Draws the hud.
     */
    public void draw() {
        this.batch.setProjectionMatrix(this.stage.getCamera().combined);
        stage.draw();
    }

    /**
     * Disposes the hud.
     */
    @Override
    public void dispose() {
        this.stage.dispose();
    }

}
