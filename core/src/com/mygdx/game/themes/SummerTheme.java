package com.mygdx.game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.effects.SunEffect;
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
public class SummerTheme implements Theme {

    private Texture backgroundTexture;
    private SunEffect sunEffect;

    public SummerTheme() {
        this.backgroundTexture = new Texture("summer.png");
        this.sunEffect = new SunEffect();
    }

    @Override
    public void update(float deltaTime) {
        this.sunEffect.update(deltaTime);
    }

    @Override
    public void draw(Batch batch) {
        Gdx.gl.glClearColor(171/255f,107/255f,72/255f,1);
        batch.draw(this.backgroundTexture, 0, 0);
        this.sunEffect.draw(batch);
    }

}
