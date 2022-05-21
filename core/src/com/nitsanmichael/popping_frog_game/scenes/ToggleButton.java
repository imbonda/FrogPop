package com.nitsanmichael.popping_frog_game.scenes;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nitsanmichael.popping_frog_game.scenes.events.MessageEvent;


/**
 * Created by MichaelBond on 9/30/2016.
 */
public class ToggleButton extends Group {

    public static final int ON_STATE = 0;
    public static final int OFF_STATE = 1;

    private Image onImage;
    private Image offImage;


    public ToggleButton(Image on, Image off) {
        this.onImage = on;
        this.offImage = off;
        // Default state is on.
        setState(ON_STATE);
        // Setting click listener.
        addListener(new ToggleButtonListener(this));
    }

    public void setState(int state) {
        if (ON_STATE == state) {
            removeActor(this.offImage);
            addActor(this.onImage);
            setSize(this.onImage.getWidth(), this.onImage.getHeight());
        }
        else if (OFF_STATE == state) {
            removeActor(this.onImage);
            addActor(this.offImage);
            setSize(this.offImage.getWidth(), this.offImage.getHeight());
        }
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        this.onImage.setSize(width, height);
        this.offImage.setSize(width, height);
    }
}
