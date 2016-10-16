package com.nitsanmichael.popping_frog_game.scenes;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * Created by MichaelBond on 10/17/2016.
 */
public class ToggleButtonListener extends ClickListener {


    public interface Callback {
        void call();
    }

    private ToggleButton button;
    private Callback touchUpCallback;


    public ToggleButtonListener(ToggleButton toggleButton, Callback touchUpCallback) {
        this.button = toggleButton;
        this.touchUpCallback = touchUpCallback;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        this.button.setState(ToggleButton.OFF_STATE);
        // todo consider return true;
        return super.touchDown(event, x, y, pointer, button);
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        super.touchDragged(event, x, y, pointer);
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        this.button.setState(ToggleButton.ON_STATE);
        super.touchUp(event, x, y, pointer, button);
        this.touchUpCallback.call();
    }

}
