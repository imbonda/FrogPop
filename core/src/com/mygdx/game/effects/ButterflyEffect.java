package com.mygdx.game.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FrogPop;
import com.mygdx.game.sprites.Butterfly;

import java.util.HashMap;
import java.util.Random;


/**
 * Created by nitsa on 27-Sep-16.
 */
public class ButterflyEffect implements Effect {

    private enum ButterflySpeedType { TYPE1, TYPE2, TYPE3 }

    private class ButterflyConfig {
        public Vector2 position;
        public ButterflySpeedType speedType;
        public Butterfly.Color color;

        ButterflyConfig (Vector2 position, ButterflySpeedType speedType, Butterfly.Color color) {
            this.position = position;
            this.speedType = speedType;
            this.color = color;
        }
    }

    private final ButterflyConfig BUTTERFLIES_CONFIG [] = {
            new ButterflyConfig(new Vector2(100, 365), ButterflySpeedType.TYPE1, Butterfly.Color.ORANGE),
            new ButterflyConfig(new Vector2(500, 300), ButterflySpeedType.TYPE1, Butterfly.Color.PURPLE),
            new ButterflyConfig(new Vector2(200, 365), ButterflySpeedType.TYPE2, Butterfly.Color.RED),
            new ButterflyConfig(new Vector2(300, 100), ButterflySpeedType.TYPE2, Butterfly.Color.ORANGE),
            new ButterflyConfig(new Vector2(400, 200), ButterflySpeedType.TYPE3, Butterfly.Color.PURPLE),
            new ButterflyConfig(new Vector2(660, 400), ButterflySpeedType.TYPE3, Butterfly.Color.RED)
    };

    private Random random;
    private Array<Butterfly> butterflies;
    private HashMap<Butterfly, ButterflyConfig> butterflyToConfigMap;


    public ButterflyEffect() {
        this.random = new Random();
        initializeButterflies();
    }

    public void initializeButterflies() {
        this.butterflies = new Array<Butterfly>();
        this.butterflyToConfigMap = new HashMap<Butterfly, ButterflyConfig>();
        for (ButterflyConfig config : BUTTERFLIES_CONFIG) {
            Vector2 position = config.position;
            Butterfly butterfly = new Butterfly(config.color);
            butterfly.setBox(
                    new Vector2(0, 0),    // Bottom left.
                    new Vector2(FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT)); // Top right.
            butterfly.setPosition(position);
            this.butterflies.add(butterfly);
            this.butterflyToConfigMap.put(butterfly, config);
        }
    }

    @Override
    public void update(float deltaTime) {
        // Nothing to update.
        for (Butterfly butterfly : this.butterflies) {
            ButterflyConfig config = this.butterflyToConfigMap.get(butterfly);

            if (config.speedType == ButterflySpeedType.TYPE1) {
                Vector2 velocity = new Vector2(
                        20 * (this.random.nextInt(4) - 2),
                        20 * (this.random.nextInt(8) - 2));
                butterfly.setVelocity(velocity);
            }
            else if (config.speedType == ButterflySpeedType.TYPE2) {
                Vector2 velocity = new Vector2(
                        20 * (this.random.nextInt(4) - 2),
                        20 * (this.random.nextInt(4) - 2));
                butterfly.setVelocity(velocity);
            }
            else {
                Vector2 velocity = new Vector2(
                        20 * (this.random.nextInt(6) - 2),
                        20 * (this.random.nextInt(6) - 4));
                butterfly.setVelocity(velocity);
            }
            butterfly.update(deltaTime);
        }
    }

    @Override
    public void draw(Batch batch){
        for (Butterfly butterfly : this.butterflies) {
            butterfly.draw(batch);
        }
    }

    @Override
    public void reset() {
        this.butterflies.clear();
        this.butterflyToConfigMap.clear();
    }
}
