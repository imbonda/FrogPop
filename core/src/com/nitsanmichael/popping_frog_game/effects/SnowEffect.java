package com.nitsanmichael.popping_frog_game.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.sprites.Cloud;

import java.util.Random;

/**
 * Created by MichaelBond on 9/28/2016.
 */
public class SnowEffect implements Effect {

    private static final float HORIZON_HEIGHT = 430;

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

    public SnowEffect(AssetController assetController, RuntimeInfo runtimeInfo) {
        this.random = new Random();
        this.snowEffect = assetController.get(Assets.SNOW_EFFECT.fileName);
        this.snowEffect.start();
        this.emitter = this.snowEffect.getEmitters().first();
        this.direction = (random.nextInt(2) == 0) ? (-1) : (1);
        initializeClouds(assetController, runtimeInfo);
    }

    public void initializeClouds(AssetController assetController, RuntimeInfo runtimeInfo) {
        this.clouds = new Array<Cloud>();
        for (Vector2 position : CLOUD_SPACE) {
            Cloud c = new Cloud(assetController, runtimeInfo, HORIZON_HEIGHT);
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
        this.snowEffect.findEmitter(Assets.SNOW_EFFECT.name).durationTimer = 0;
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
