package com.mygdx.game.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FrogPop;
import com.mygdx.game.assets.AssetController;
import com.mygdx.game.sprites.Cloud;

import java.util.Random;

/**
 * Created by MichaelBond on 9/28/2016.
 */
public class SnowEffect implements Effect {

    private static final String SNOW_EFFECT_FILE = "effects/snow_effect";
    private static final String SNOW_EFFECT_DIR = "effects";
    private static final String EMITTER_NAME = "snow";
    private static final Vector2 CLOUD_SPACE [] = {
            new Vector2(-50, 480), new Vector2(50, 440), new Vector2(150, 430),
            new Vector2(200, 470), new Vector2(250, 430), new Vector2(300, 460),
            new Vector2(350, 470), new Vector2(400, 450), new Vector2(550, 440),
            new Vector2(600, 475), new Vector2(750, 425), new Vector2(800, 465)
    };

    private Random random;
    private int direction;
    private ParticleEffect snowEffect;
    private ParticleEmitter emitter;
    private Array<Cloud> clouds;

    public SnowEffect(AssetController assetController) {
        this.random = new Random();
        this.snowEffect = new ParticleEffect();
        this.snowEffect.load(
                    Gdx.files.internal(SNOW_EFFECT_FILE),
                    Gdx.files.internal(SNOW_EFFECT_DIR));
        this.snowEffect.start();
        this.emitter = this.snowEffect.getEmitters().first();
        this.direction = (random.nextInt(2) == 0) ? (-1) : (1);
        initializeClouds(assetController);
    }

    public void initializeClouds(AssetController assetController) {
        this.clouds = new Array<Cloud>();
        for (Vector2 position : CLOUD_SPACE) {
            Cloud c = new Cloud(assetController);
            c.setBox(new Vector2(0, 0), new Vector2(FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT));
            int speed = this.random.nextInt(10) + 20;
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
        this.snowEffect.findEmitter(EMITTER_NAME).durationTimer = 0;
        this.snowEffect.update(deltaTime);
        if (this.snowEffect.isComplete()) {
            this.snowEffect.reset();
        }
    }

    @Override
    public void draw(Batch batch){
        // Draw snow.
        this.snowEffect.draw(batch);
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
