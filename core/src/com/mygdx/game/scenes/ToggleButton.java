package com.mygdx.game.scenes;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.scenes.events.MessageEvent;

/**
 * Created by MichaelBond on 9/30/2016.
 */
public class ToggleButton extends Group {

    public static final int ON_STATE = 0;
    public static final int OFF_STATE = 1;
    public static final int UNSET_STATE = 2;

    private Image onImage;
    private Image offImage;
    private int currentState;

    public ToggleButton(Image on, Image off) {
        this.onImage = on;
        this.offImage = off;
        // Default state is on.
        setState(ON_STATE);
        // Setting click listener.
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggle();
            }
        });
    }

    private void toggle() {
        if (ON_STATE == this.currentState) {
            setState(OFF_STATE);
            fire(new MessageEvent(OFF_STATE, UNSET_STATE));
        }
        else if (OFF_STATE == this.currentState) {
            setState(ON_STATE);
            fire(new MessageEvent(ON_STATE, UNSET_STATE));
        }
    }

    public void setState(int state) {
        this.currentState = state;
        if (ON_STATE == state) {
            addActor(this.onImage);
            setSize(this.onImage.getWidth(), this.onImage.getHeight());
            this.offImage.remove();
        }
        else if (OFF_STATE == state) {
            addActor(this.offImage);
            setSize(this.offImage.getWidth(), this.offImage.getHeight());
            this.onImage.remove();
        }
    }
}
