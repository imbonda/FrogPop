package com.mygdx.game.scenes.panel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.scenes.Hud;

/**
 * Created by MichaelBond on 9/9/2016.
 */
public class ScoreCounter extends Group {

    private static final Vector2 LABEL_POSITION = new Vector2(25, 520);

    private int score;
    private Label scoreLabel;


    public ScoreCounter() {
        setTransform(false);
        initScoreLabel();
        addActor(this.scoreLabel);
        this.score = 0;
        updateScoreLabel();
    }

    private void initScoreLabel() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = Hud.FONT;
        style.fontColor = new Color(0x000000ff);
        this.scoreLabel = new Label("", style);
        this.scoreLabel.setWidth(260);
        this.scoreLabel.setPosition(LABEL_POSITION.x, LABEL_POSITION.y);
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int value) {
        this.score += value;
        updateScoreLabel();
    }

    public void subtractScore(int value) {
        this.score -= value;
        updateScoreLabel();
    }

    private void updateScoreLabel() {
        this.scoreLabel.setText("Score: " + this.score);
    }

}
