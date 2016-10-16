package com.nitsanmichael.popping_frog_game.scenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * Created by MichaelBond on 10/17/2016.
 */
public class ToggleButtonListener extends ClickListener {

    private static final float MAX_VALIDITY_TOUCH_DISTANCE = 80;


    public interface Callback {
        void call();
    }

    private ToggleButton button;
    private Callback touchUpCallback;
    private Vector2 initialTouch;
    private boolean isValid;


    public ToggleButtonListener(ToggleButton toggleButton, Callback touchUpCallback) {
        this.button = toggleButton;
        this.touchUpCallback = touchUpCallback;
        this.initialTouch = new Vector2(0, 0);
        this.isValid = true;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        this.button.setState(ToggleButton.OFF_STATE);
        this.initialTouch.set(x, y);
        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        super.touchDragged(event, x, y, pointer);
        if (isTouchedAway(x, y)) {
            this.isValid = false;
            this.button.setState(ToggleButton.ON_STATE);
        }
        else {
            this.isValid = true;
            this.button.setState(ToggleButton.OFF_STATE);
        }
    }

    private boolean isTouchedAway(float x, float y) {
        return (MAX_VALIDITY_TOUCH_DISTANCE < this.initialTouch.dst(x, y));
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (!this.isValid) {
            return;
        }
        this.button.setState(ToggleButton.ON_STATE);
        super.touchUp(event, x, y, pointer, button);
        this.touchUpCallback.call();
    }

}
