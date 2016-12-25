package com.nitsanmichael.popping_frog_game.scenes.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButton;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButtonListener;
import com.nitsanmichael.popping_frog_game.scenes.events.MessageEventListener;
import com.nitsanmichael.popping_frog_game.states.StateTracker;

/**
 * Created by MichaelBond on 11/13/2016.
 */
public class PlayDialog implements Disposable {

    private static final Vector2 PAUSE_BUTTON_SIZE = new Vector2(75, 50);
    private static final Vector2 PAUSE_BUTTON_POSITION = new Vector2(720, 475);

    private Stage stage;


    public PlayDialog(AssetController assetController, Viewport viewport, Batch batch,
                        final RuntimeInfo runtimeInfo) {
        Texture pauseIcon = assetController.get(Assets.PAUSE_ICON);
        Texture pauseIconPressed = assetController.get(Assets.PAUSE_PRESSED_ICON);
        final ToggleButton pauseButton = new ToggleButton(new Image(pauseIcon), new Image(pauseIconPressed));
        pauseButton.setSize(PAUSE_BUTTON_SIZE.x, PAUSE_BUTTON_SIZE.y);
        pauseButton.setPosition(PAUSE_BUTTON_POSITION.x, PAUSE_BUTTON_POSITION.y);
        pauseButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != pauseButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                runtimeInfo.stateTracker.setState(StateTracker.GameState.PAUSE);
            }
        });

        setStage(viewport, batch, pauseButton);
    }

    private void setStage(Viewport viewport, Batch batch, ToggleButton pauseButton) {
        this.stage = new Stage(viewport, batch);
        this.stage.addActor(pauseButton);
        InputProcessor processor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(new InputMultiplexer(this.stage, processor));
    }

    public void draw() {
        this.stage.draw();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }

}
