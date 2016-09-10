package com.mygdx.game.scenes.panel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.scenes.Hud;

/**
 * Created by MichaelBond on 9/9/2016.
 */
public class LifeCounter extends Group {

    private static final int INITIAL_LIFE = 3;
    private static final Vector2 LABEL_POSITION = new Vector2(720, 510);

    private int life;
    private Label lifeLabel;


    public LifeCounter() {
        setTransform(false);
        initLifeLabel();
        addActor(this.lifeLabel);
        this.life = INITIAL_LIFE;
        updateLifeLabel();
    }

    private void initLifeLabel() {
        Label.LabelStyle style = new Label.LabelStyle();
        Hud hud = Hud.getInstance();
        style.font = hud.FONT;
        style.fontColor = new Color(0xff0000ff);
        this.lifeLabel = new Label("", style);
        this.lifeLabel.setWidth(260);
        this.lifeLabel.setPosition(LABEL_POSITION.x, LABEL_POSITION.y);
    }

    public int getLife() {
        return this.life;
    }

    public void addLife(int value) {
        this.life += value;
        updateLifeLabel();
    }

    public void reset() {
        this.life = INITIAL_LIFE;
        updateLifeLabel();
    }

    private void updateLifeLabel() {
        this.lifeLabel.setText("Life: " + this.life);
    }
}
