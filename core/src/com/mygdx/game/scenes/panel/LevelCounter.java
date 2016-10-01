package com.mygdx.game.scenes.panel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.scenes.Hud;

/**
 * This class is part of the HUD, and it is the part responsible for the level-counting.
 *
 * Created by MichaelBond on 9/9/2016.
 */
public class LevelCounter extends Group {

    private static final Vector2 LABEL_POSITION = new Vector2(25, 495);
    // Private members.
    private Label levelLabel;


    public LevelCounter(BitmapFont font, int level) {
        setTransform(false);
        initLevelLabel(font);
        addActor(this.levelLabel);
        updateLevel(level);
    }

    private void initLevelLabel(BitmapFont font) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = new Color(0x000000ff);
        this.levelLabel = new Label("", style);
        this.levelLabel.setWidth(260);
        this.levelLabel.setPosition(LABEL_POSITION.x, LABEL_POSITION.y);
    }

    public void updateLevel(int level) {
        this.levelLabel.setText("Level: " + level);
    }

}
