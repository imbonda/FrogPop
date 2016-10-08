package com.nitsanmichael.popping_frog_game.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.Assets;

import java.util.Random;

/**
 * Created by MichaelBond on 9/28/2016.
 */
public class RainEffect implements Effect {

    private static final Vector2 CLOUD_SPACE [] = {
            new Vector2(-50, 480), new Vector2(50, 440), new Vector2(150, 430),
            new Vector2(200, 470), new Vector2(250, 430), new Vector2(300, 460),
            new Vector2(350, 470), new Vector2(400, 450), new Vector2(550, 440),
            new Vector2(600, 475), new Vector2(750, 425), new Vector2(800, 465)
    };

    private enum AngleState { ON, OFF }

    private Random random;
    private int direction;
    private ParticleEffect rainEffect;
    private ParticleEmitter emitter;
    private Array<com.nitsanmichael.popping_frog_game.sprites.Cloud> clouds;

    public RainEffect(com.nitsanmichael.popping_frog_game.assets.AssetController assetController) {
        this.random = new Random();
        this.rainEffect = assetController.get(Assets.RAIN_EFFECT.fileName);
        this.rainEffect.start();
        this.emitter = this.rainEffect.getEmitters().first();
        this.direction = (random.nextInt(2) == 0) ? (-1) : (1);
        setRainParticleRotation();
        setRainAngle(AngleState.ON);
        initializeClouds(assetController);
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

    public void setRainAngle(AngleState state) {
        ParticleEmitter.ScaledNumericValue angle = this.emitter.getAngle();
        float lowMin = angle.getLowMin();
        float lowMax = angle.getLowMax();
        float highMin = angle.getHighMin();
        float highMax = angle.getHighMax();
        float angleAddFactor = this.direction * 20;
        if (AngleState.OFF == state) {
            angleAddFactor *= -1;
        }
        angle.setLow(lowMin + angleAddFactor, lowMax + angleAddFactor);
        angle.setHigh(highMin + angleAddFactor, highMax + angleAddFactor);
    }

    public void initializeClouds(com.nitsanmichael.popping_frog_game.assets.AssetController assetController) {
        this.clouds = new Array<com.nitsanmichael.popping_frog_game.sprites.Cloud>();
        for (Vector2 position : CLOUD_SPACE) {
            com.nitsanmichael.popping_frog_game.sprites.Cloud c = new com.nitsanmichael.popping_frog_game.sprites.Cloud(assetController);
            c.setBox(new Vector2(0, 0), new Vector2(PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT));
            int speed = this.random.nextInt(13) + 20;
            c.setVelocity(new Vector2(this.direction * speed, 0));
            c.setPosition(position);
            this.clouds.add(c);
        }
    }

    public void updateClouds(float deltaTime) {
        for (com.nitsanmichael.popping_frog_game.sprites.Cloud c : this.clouds) {
            c.update(deltaTime);
        }
    }

    @Override
    public void update(float deltaTime) {
        updateClouds(deltaTime);
        Vector2 position = this.clouds.random().getCenter();
        this.emitter.setPosition(position.x, position.y);
        this.rainEffect.findEmitter(Assets.RAIN_EFFECT.name).durationTimer = 0;
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
        for (com.nitsanmichael.popping_frog_game.sprites.Cloud c : this.clouds) {
            c.draw(batch);
        }
    }

    @Override
    public void reset() {
        this.emitter.reset();
        setRainAngle(AngleState.OFF);
    }
}
