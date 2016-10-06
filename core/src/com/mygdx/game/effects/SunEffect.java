package com.mygdx.game.effects;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.assets.AssetController;
import com.mygdx.game.assets.Assets;


/**
 * Created by nitsa on 27-Sep-16.
 */
public class SunEffect implements Effect {

    private static final Vector2 SUN_POSITION = new Vector2(380, 470);

    private ParticleEffect sunEffect;
    private ParticleEmitter emitter;
    private float[] oldTintColor;

    public SunEffect(AssetController assetController) {
        this.sunEffect = assetController.get(Assets.SUN_EFFECT.fileName);
        this.sunEffect.start();
        this.emitter = this.sunEffect.getEmitters().first();
        this.emitter.setPosition(SUN_POSITION.x, SUN_POSITION.y);
    }

    public SunEffect(AssetController assetController, float color []) {
        this(assetController);
        this.oldTintColor = this.emitter.getTint().getColor(1).clone();
        this.emitter.getTint().setColors(color);
    }

    @Override
    public void update(float deltaTime) {
        this.sunEffect.findEmitter(Assets.SUN_EFFECT.name).durationTimer = 0;
        this.sunEffect.update(deltaTime);
        if (this.sunEffect.isComplete()) {
            this.sunEffect.reset();
        }
    }

    @Override
    public void draw(Batch batch){
        this.sunEffect.draw(batch);
    }

    @Override
    public void reset() {
        this.emitter.reset();
        if (null != this.oldTintColor) {
            this.emitter.getTint().setColors(this.oldTintColor);
        }
    }
}
