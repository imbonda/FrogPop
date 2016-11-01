package com.nitsanmichael.popping_frog_game.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.nitsanmichael.popping_frog_game.states.StateTracker;


/**
 * Created by MichaelBond on 11/2/2016.
 */
public class BackKeyInputProcessor implements InputProcessor {

    private Runnable backKeyAction;


    public BackKeyInputProcessor(Runnable backKeyAction) {
        this.backKeyAction = backKeyAction;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Input.Keys.P == keycode || Input.Keys.BACK == keycode) {
            this.backKeyAction.run();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

}
