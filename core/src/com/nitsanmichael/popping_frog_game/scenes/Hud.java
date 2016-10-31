package com.nitsanmichael.popping_frog_game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.scenes.panel.LevelTab;
import com.nitsanmichael.popping_frog_game.scenes.panel.LifeTab;
import com.nitsanmichael.popping_frog_game.scenes.panel.ScoreTab;
import com.nitsanmichael.popping_frog_game.scenes.panel.Timer;

/**
 * This is a singleton class used to represent the HUD (head-up display) of the game.
 * It makes the maintaining of the score, life and level very easy and accessible throughout -
 * the code.
 *
 * Created by MichaelBond on 9/10/2016.
 */
public class Hud implements Disposable {

    private BitmapFont font;
    private ScoreTab scoreTab;
    private LevelTab levelTab;
    private LifeTab lifeTab;
    private SpriteBatch batch;
    private RuntimeInfo runtimeInfo;
    private Timer timer;
    private Stage stage;

    /**
     * @param assetController   An asset controller instance for retrieving the loaded assets.
     * @param batch    The batch object to be used for drawing the hud later on.
     * @param runtimeInfo The runtime information that is to be used by the hud to draw the -
     *                    score, the lives and the level.
     * @param timer A timer used for timing the levels.
     */
    public Hud(AssetController assetController, SpriteBatch batch, RuntimeInfo runtimeInfo,
                Timer timer) {
        this.font = assetController.get(Assets.GAME_FONT);
        this.font.getData().setScale(0.2f);
        this.batch = batch;
        this.runtimeInfo = runtimeInfo;
        this.timer = timer;
        setPanel();
        setStage();
    }

    /**
     * Sets the hud's panel.
     */
    private void setPanel() {
        this.scoreTab = new ScoreTab(this.font, this.runtimeInfo.gameScore);
        this.levelTab = new LevelTab(this.font, this.runtimeInfo.gameLevel);
        this.lifeTab = new LifeTab(this.font, this.runtimeInfo.gameLives);
    }

    /**
     * Sets the hud's stage.
     * The stage is what actually hold all the hud's elements, and it draws them eventually.
     */
    private void setStage() {
        Viewport hudViewPort = new ExtendViewport(
                PoppingFrog.VIRTUAL_WIDTH,
                PoppingFrog.VIRTUAL_HEIGHT,
                new OrthographicCamera());
        this.stage = new Stage(hudViewPort, this.batch);
        this.stage.addActor(this.scoreTab);
        this.stage.addActor(this.levelTab);
        this.stage.addActor(this.lifeTab);
        this.stage.addActor(this.timer);
    }

    /**
     * Updates the Hud's stage viewport.
     *
     * @param width     The width of the screen in pixels.
     * @param height    The height of the screen in pixels.
     * @param centerCamera  Whether or not to center the camera in the center of the viewport.
     */
    public void resize(int width, int height, boolean centerCamera) {
        this.stage.getViewport().update(width, height, false);
        this.stage.getViewport().getCamera().position.set(0,0,0);
        this.stage.getViewport().getCamera().translate(800/2,530/2,0);
    }

    /**
     * Updates the hud to indicate the most updated game-state.
     */
    public void update() {
        this.scoreTab.updateScore(this.runtimeInfo.gameScore);
        this.levelTab.updateLevel(this.runtimeInfo.gameLevel);
        this.lifeTab.updateLives(this.runtimeInfo.gameLives);
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
