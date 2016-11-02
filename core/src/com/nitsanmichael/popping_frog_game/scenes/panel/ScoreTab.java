package com.nitsanmichael.popping_frog_game.scenes.panel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * This class is part of the HUD, and it is the part responsible for the score-counting.
 *
 * Created by MichaelBond on 9/9/2016.
 */
public class ScoreTab extends Group {

    private static final Vector2 LABEL_POSITION = new Vector2(280, 500);
    // Private members.
    private Label scoreLabel;

    public ScoreTab(BitmapFont font, int score) {
        setTransform(false);
        initScoreLabel(font);
        addActor(this.scoreLabel);
        updateScore(score);
    }

    private void initScoreLabel(BitmapFont font) {
        Label.LabelStyle style = new Label.LabelStyle(font, new Color(0x2c7b6d));
        this.scoreLabel = new Label("", style);
        this.scoreLabel.setFontScale(0.35f);
        this.scoreLabel.setWidth(260);
        this.scoreLabel.setPosition(LABEL_POSITION.x, LABEL_POSITION.y);
    }

    public void updateScore(int newScore) {
        this.scoreLabel.setText("Score: " + newScore);
    }

}
