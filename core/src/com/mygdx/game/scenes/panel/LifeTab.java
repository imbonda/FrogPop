package com.mygdx.game.scenes.panel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * This class is part of the HUD, and it is the part responsible for the life-counting.
 *
 * Created by MichaelBond on 9/9/2016.
 */
public class LifeTab extends Group {

    private static final Vector2 LABEL_POSITION = new Vector2(720, 510);
    // Private members.
    private Label lifeLabel;


    public LifeTab(BitmapFont font, int lives) {
        setTransform(false);
        initLifeLabel(font);
        addActor(this.lifeLabel);
        updateLives(lives);
    }

    private void initLifeLabel(BitmapFont font) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = new Color(0xff0000ff);
        this.lifeLabel = new Label("", style);
        this.lifeLabel.setWidth(260);
        this.lifeLabel.setPosition(LABEL_POSITION.x, LABEL_POSITION.y);
    }

    public void updateLives(int lives) {
        this.lifeLabel.setText("Life: " + lives);
    }
}
