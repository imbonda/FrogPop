package com.nitsanmichael.popping_frog_game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.sprites.frogs.Frog;
import com.nitsanmichael.popping_frog_game.states.StateTracker;

/**
 * This class is responsible for all the touch events happening during the game runtime.
 *
 * Created by MichaelBond on 9/10/2016.
 */
public class GamePlayTouchProcessor implements InputProcessor {

    private Viewport viewport;
    private RuntimeInfo runtimeInfo;


    public GamePlayTouchProcessor(Viewport viewport, RuntimeInfo runtimeInfo) {
        this.viewport = viewport;
        this.runtimeInfo = runtimeInfo;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 touchVector = new Vector2(screenX, screenY);
        touchVector = this.viewport.unproject(touchVector);

        for (Frog frog : this.runtimeInfo.activeFrogs) {
            if (frog.isFrogTouched(touchVector) && !frog.isLifeTimeExpired()) {
                frog.touched();
                return true;
            }
        }
        Gdx.input.vibrate(500);
        int lives = this.runtimeInfo.gameLives.get();
        this.runtimeInfo.gameLives.set(lives - 1);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Input.Keys.P == keycode || Input.Keys.BACK == keycode) {
            this.runtimeInfo.stateTracker.setState(StateTracker.GameState.PAUSE);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
