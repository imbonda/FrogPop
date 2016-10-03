package com.mygdx.game.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.sprites.Cloud;

import java.util.Random;

/**
 * Created by MichaelBond on 9/28/2016.
 */
public class RainEffect implements Effect {

    private static final String RAIN_EFFECT_FILE = "effects/rain_effect";
    private static final String RAIN_EFFECT_DIR = "effects";
    private static final String EMITTER_NAME = "rain";
    private static final Vector2 CLOUD_SPACE [] = {
            new Vector2(-50, 480), new Vector2(50, 440), new Vector2(150, 430),
            new Vector2(200, 470), new Vector2(250, 430), new Vector2(300, 460),
            new Vector2(350, 470), new Vector2(400, 450), new Vector2(550, 440),
            new Vector2(600, 475), new Vector2(750, 425), new Vector2(800, 465)
    };

    private Random random;
    private int direction;
    private ParticleEffect rainEffect;
    private ParticleEmitter emitter;
    private Array<Cloud> clouds;

    public RainEffect() {
        this.random = new Random();
        this.rainEffect = new ParticleEffect();
        this.rainEffect.load(
                Gdx.files.internal(RAIN_EFFECT_FILE),
                Gdx.files.internal(RAIN_EFFECT_DIR));
        this.rainEffect.start();
        this.emitter = this.rainEffect.getEmitters().first();
        this.direction = (random.nextInt(2) == 0) ? (-1) : (1);
        setRainParticleRotation();
        setRainAngle();
        initializeClouds();
    }

    public void setRainParticleRotation() {
        ParticleEmitter.ScaledNumericValue rotation = this.emitter.getRotation();
        float lowMin = rotation.getLowMin();
        float lowMax = rotation.getLowMax();
        float highMin = rotation.getHighMin();
        float highMax = rotation.getHighMax();
        rotation.setLow(lowMin * this.direction, lowMax * this.direction);
        rotation.setHigh(highMin * this.direction, highMax * this.direction);
    }

    public void setRainAngle() {
        ParticleEmitter.ScaledNumericValue angle = this.emitter.getAngle();
        float lowMin = angle.getLowMin();
        float lowMax = angle.getLowMax();
        float highMin = angle.getHighMin();
        float highMax = angle.getHighMax();
        float angleAddFactor = this.direction * 20;
        angle.setLow(lowMin + angleAddFactor, lowMax + angleAddFactor);
        angle.setHigh(highMin + angleAddFactor, highMax + angleAddFactor);
    }

    public void initializeClouds() {
        this.clouds = new Array<Cloud>();
        for (Vector2 position : CLOUD_SPACE) {
            Cloud c = new Cloud();
            int speed = this.random.nextInt(13) + 20;
            c.setVelocity(new Vector2(this.direction * speed, 0));
            c.setPosition(position);
            this.clouds.add(c);
        }
    }

    public void updateClouds(float deltaTime) {
        for (Cloud c : this.clouds) {
            c.update(deltaTime);
        }
    }

    @Override
    public void update(float deltaTime) {
        updateClouds(deltaTime);
        Vector2 position = this.clouds.random().getCenter();
        this.emitter.setPosition(position.x, position.y);
        this.rainEffect.findEmitter(EMITTER_NAME).durationTimer = 0;
        this.rainEffect.update(deltaTime);
        if (this.rainEffect.isComplete()) {
            this.rainEffect.reset();
        }
    }

    @Override
    public void draw(Batch batch){
        // Draw rain.
        this.rainEffect.draw(batch);
        // Draw clouds.
        for (Cloud c : this.clouds) {
            c.draw(batch);
        }
    }

    @Override
    public void reset() {
        this.emitter.reset();
    }
}
