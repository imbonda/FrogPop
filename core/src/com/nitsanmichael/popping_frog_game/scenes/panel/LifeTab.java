package com.nitsanmichael.popping_frog_game.scenes.panel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * This class is part of the HUD, and it is the part responsible for the life-counting.
 *
 * Created by MichaelBond on 9/9/2016.
 */
public class LifeTab extends Group {

    private static final Vector2 LABEL_POSITION = new Vector2(750, 480);
    // Lives' position.
    private static final Vector2 LIFE1_POSITION = new Vector2(575, 475);
    private static final Vector2 LIFE2_POSITION = new Vector2(635, 475);
    private static final Vector2 LIFE3_POSITION = new Vector2(695, 475);
    private static final Vector2 LIFE_SIZE = new Vector2(50, 50);
    private static final String EXTRA_LIFE = "x ";

    // Private members.
    private Image life1, life2, life3;
    private Label extraLifeLabel;


    public LifeTab(Texture lifeIcon, BitmapFont font, int lives) {
        setTransform(false);
        initLives(lifeIcon);
        initExtraLifeLabel(font);
        updateLives(lives);
    }

    private void initExtraLifeLabel(BitmapFont font) {
        Label.LabelStyle style = new Label.LabelStyle(font, new Color(0x6ac44fff));
        this.extraLifeLabel = new Label(EXTRA_LIFE, style);
        this.extraLifeLabel.setFontScale(0.3f);
        this.extraLifeLabel.setWidth(50);
        this.extraLifeLabel.setPosition(LABEL_POSITION.x, LABEL_POSITION.y);
        addActor(this.extraLifeLabel);
    }

    private void initLives(Texture lifeIcon) {
        // Life 1.
        this.life1 = new Image(lifeIcon);
        this.life1.setPosition(LIFE1_POSITION.x, LIFE1_POSITION.y);
        this.life1.setSize(LIFE_SIZE.x, LIFE_SIZE.y);
        // Life 2.
        this.life2 = new Image(lifeIcon);
        this.life2.setPosition(LIFE2_POSITION.x, LIFE2_POSITION.y);
        this.life2.setSize(LIFE_SIZE.x, LIFE_SIZE.y);
        // Life 3.
        this.life3 = new Image(lifeIcon);
        this.life3.setPosition(LIFE3_POSITION.x, LIFE3_POSITION.y);
        this.life3.setSize(LIFE_SIZE.x, LIFE_SIZE.y);
        addActor(this.life1);
        addActor(this.life2);
        addActor(this.life3);
    }

    public void updateLives(int lives) {
        if (lives < 4) {
            this.extraLifeLabel.remove();
        }
        else {
            this.extraLifeLabel.setText(EXTRA_LIFE + lives);
            addActor(this.extraLifeLabel);
        }
        if (lives < 3) {
            this.life3.remove();
        }
        else {
            addActor(this.life3);
        }
        if (lives < 2) {
            this.life2.remove();
        }
        else {
            addActor(this.life2);
        }
        if (lives < 1) {
            this.life1.remove();
        }
        else {
            addActor(this.life1);
        }
    }

}
