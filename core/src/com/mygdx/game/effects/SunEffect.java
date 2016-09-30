package com.mygdx.game.effects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.sprites.Cloud;

import java.util.Random;

import sun.security.provider.Sun;

/**
 * Created by nitsa on 27-Sep-16.
 */
public class SunEffect {

    private static final String SUN_EFFECT_FILE = "effects/sun_effect";
    private static final String SUN_EFFECT_DIR = "effects";
    private static final String EMITTER_NAME = "sun";
    private static final Vector2 SUN_POSITION = new Vector2(380, 470);

    private ParticleEffect sunEffect;
    private ParticleEmitter emitter;

    public SunEffect() {
        this.sunEffect = new ParticleEffect();
        this.sunEffect.load(
                Gdx.files.internal(SUN_EFFECT_FILE),
                Gdx.files.internal(SUN_EFFECT_DIR));
        this.sunEffect.start();
        this.emitter = this.sunEffect.getEmitters().first();
        this.emitter.setPosition(SUN_POSITION.x, SUN_POSITION.y);
    }

    public SunEffect(float color []) {
        this();
        this.emitter.getTint().setColors(color);
    }

    public void update(float deltaTime) {
        this.sunEffect.findEmitter(EMITTER_NAME).durationTimer = 0;
        this.sunEffect.update(deltaTime);
        if (this.sunEffect.isComplete()) {
            this.sunEffect.reset();
        }
    }

    public void draw(Batch batch){
        this.sunEffect.draw(batch);
    }
}
