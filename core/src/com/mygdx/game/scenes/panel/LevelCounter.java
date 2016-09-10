package com.mygdx.game.scenes.panel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.scenes.Hud;

/**
 * Created by MichaelBond on 9/9/2016.
 */
public class LevelCounter extends Group {

    private static final int INITIAL_LEVEL = 1;
    private static final Vector2 LABEL_POSITION = new Vector2(25, 495);

    private int level;
    private Label levelLabel;


    public LevelCounter() {
        setTransform(false);
        initLevelLabel();
        addActor(this.levelLabel);
        this.level = INITIAL_LEVEL;
        updateLevelLabel();
    }

    private void initLevelLabel() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = Hud.FONT;
        style.fontColor = new Color(0x000000ff);
        this.levelLabel = new Label("", style);
        this.levelLabel.setWidth(260);
        this.levelLabel.setPosition(LABEL_POSITION.x, LABEL_POSITION.y);
    }

    public int getLevel() {
        return this.level;
    }

    public void advance() {
        this.level += 1;
        updateLevelLabel();
    }

    public void reset() {
        this.level = INITIAL_LEVEL;
        updateLevelLabel();
    }

    private void updateLevelLabel() {
        this.levelLabel.setText("Level: " + this.level);
    }

}
