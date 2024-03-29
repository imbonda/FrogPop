package com.nitsanmichael.popping_frog_game.effects;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.sprites.Bird;

import java.util.Random;


/**
 * Created by nitsa on 27-Sep-16.
 */
public class BirdsEffect implements Effect {

    private static final float HORIZON_HEIGHT = 430;

    private enum BirdType { NORMAL, FAST }

    private class BirdConfig {
        public Vector2 position;
        public BirdType type;

        BirdConfig (Vector2 position, BirdType type) {
            this.position = position;
            this.type = type;
        }
    }

    private final BirdConfig BIRDS_CONFIG [] = {
            new BirdConfig(new Vector2(500, 465), BirdType.NORMAL),
            new BirdConfig(new Vector2(100, 465), BirdType.NORMAL),
            new BirdConfig(new Vector2(200, 480), BirdType.FAST)
    };

    private Random random;
    private Array<Bird> birds;


    public BirdsEffect(AssetController assetController, RuntimeInfo runtimeInfo) {
        this.random = new Random();
        initializeBirds(assetController, runtimeInfo);
    }

    public void initializeBirds(AssetController assetController, RuntimeInfo runtimeInfo) {
        this.birds = new Array<Bird>();
        for (BirdConfig birdConfig : BIRDS_CONFIG) {
            Vector2 position = birdConfig.position;
            BirdType type = birdConfig.type;
            Bird bird = new Bird(assetController, runtimeInfo, HORIZON_HEIGHT);
            int direction = (this.random.nextInt(2) == 0) ? (-1) : (1);
            if (type == BirdType.NORMAL) {
                Vector2 velocity = new Vector2(
                            20 * direction * (this.random.nextInt(3) + 2),
                            20 * direction);
                bird.setVelocity(velocity);
            }
            else {
                Vector2 velocity = new Vector2(
                        20 * direction * (this.random.nextInt(6) + 2),
                        20 * (this.random.nextInt(2) - 1));
                bird.setVelocity(velocity);
            }
            bird.setPosition(position);
            this.birds.add(bird);
        }
    }

    @Override
    public void update(float deltaTime) {
        for (Bird bird : this.birds) {
            bird.update(deltaTime);
        }
    }

    @Override
    public void draw(Batch batch){
        for (Bird bird : this.birds) {
            bird.draw(batch);
        }
    }

    @Override
    public void reset() {
        this.birds.clear();
    }
}
