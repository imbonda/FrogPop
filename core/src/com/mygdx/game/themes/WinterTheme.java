package com.mygdx.game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.effects.SnowEffect;
import com.mygdx.game.themes.exceptions.UnsupportedSpriteException;
import com.mygdx.game.sprites.frogs.FreezeFrog;
import com.mygdx.game.sprites.frogs.IllusionFrog;
import com.mygdx.game.sprites.frogs.HealthFrog;
import com.mygdx.game.sprites.frogs.RegularFrog;
import com.mygdx.game.sprites.frogs.PoisonFrog;

import java.util.HashMap;

/**
 * TODO This is just an example class, should be removed when actual themes are created !!!
 *
 * Created by MichaelBond on 9/18/2016.
 */
public class WinterTheme implements Theme {

    private Texture backgroundTexture;
    private SnowEffect snowEffect;

    public WinterTheme() {
        this.backgroundTexture = new Texture("world4.jpg");
        this.snowEffect = new SnowEffect();
    }

    @Override
    public void update(float deltaTime) {
        this.snowEffect.update(deltaTime);
    }

    @Override
    public void draw(Batch batch) {
        Gdx.gl.glClearColor(171/255f,107/255f,72/255f,1);
        batch.draw(this.backgroundTexture, 0, 0);
        this.snowEffect.draw(batch);
    }

}
