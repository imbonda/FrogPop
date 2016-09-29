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

/**
 * Created by nitsa on 27-Sep-16.
 */
public class SunEffect {

    private static final String SUN_EFFECT_FILE = "effects/sun_effect";
    private static final String SUN_EFFECT_DIR = "effects";
    private static final String EMITTER_NAME = "sun";
    private static final Vector2 SUN_POSITION = new Vector2(380, 470);

    private ParticleEffect sומEffect;

    public SunEffect() {
        this.sומEffect = new ParticleEffect();
        this.sומEffect.load(
                Gdx.files.internal(SUN_EFFECT_FILE),
                Gdx.files.internal(SUN_EFFECT_DIR));
        this.sומEffect.start();
        ParticleEmitter emitter = this.sומEffect.getEmitters().first();
        emitter.setPosition(SUN_POSITION.x, SUN_POSITION.y);
    }

    public void update(float deltaTime) {
        this.sומEffect.findEmitter(EMITTER_NAME).durationTimer = 0;
        this.sומEffect.update(deltaTime);
        if (this.sומEffect.isComplete()) {
            this.sומEffect.reset();
        }
    }

    public void draw(Batch batch){
        this.sומEffect.draw(batch);
    }
}
