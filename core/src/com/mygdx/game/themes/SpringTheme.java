package com.mygdx.game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.effects.ButterflyEffect;
import com.mygdx.game.effects.SunEffect;
import com.mygdx.game.themes.exceptions.UnsupportedSpriteException;
import com.mygdx.game.sprites.frogs.FreezeFrog;
import com.mygdx.game.sprites.frogs.IllusionFrog;
import com.mygdx.game.sprites.frogs.HealthFrog;
import com.mygdx.game.sprites.frogs.RegularFrog;
import com.mygdx.game.sprites.frogs.PoisonFrog;

import java.util.HashMap;

/**
 * This class is the default implementation of the Theme interface.
 *
 * Created by MichaelBond on 9/18/2016.
 */
public class SpringTheme implements Theme {

    private Texture backgroundTexture;
    private SunEffect sunEffect;
    private ButterflyEffect butterflyEffect;

    public SpringTheme() {
        this.backgroundTexture = new Texture("spring.png");
        this.sunEffect = new SunEffect();
        this.butterflyEffect = new ButterflyEffect();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void draw(Batch batch) {
        Gdx.gl.glClearColor(171/255f,107/255f,72/255f,1);
        batch.draw(this.backgroundTexture, 0, 0);
        this.sunEffect.draw(batch);
		this.butterflyEffect.draw(batch);
    }

}
