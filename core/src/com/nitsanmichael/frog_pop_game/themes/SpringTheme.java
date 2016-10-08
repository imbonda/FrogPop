package com.nitsanmichael.frog_pop_game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.nitsanmichael.frog_pop_game.effects.ButterflyEffect;
import com.nitsanmichael.frog_pop_game.effects.EffectDrawer;
import com.nitsanmichael.frog_pop_game.assets.AssetController;
import com.nitsanmichael.frog_pop_game.assets.Assets;
import com.nitsanmichael.frog_pop_game.effects.SunEffect;

/**
 * This class is the default implementation of the Theme interface.
 *
 * Created by MichaelBond on 9/18/2016.
 */
public class SpringTheme implements Theme {

    private static final float SPRING_SUN_COLOR[] = {1f, 1f, 1f};

    private Texture backgroundTexture;
    private SunEffect sunEffect;
    private ButterflyEffect butterflyEffect;
    private EffectDrawer effectDrawer;

    public SpringTheme() {
    }

    @Override
    public void init(AssetController assetController, EffectDrawer effectDrawer) {
        this.backgroundTexture = assetController.get(Assets.SPRING_THEME);
        this.butterflyEffect = new ButterflyEffect(assetController);
        this.sunEffect = new SunEffect(assetController, SPRING_SUN_COLOR);
        this.effectDrawer = effectDrawer;
        this.effectDrawer.addEffect(this.sunEffect);
        this.effectDrawer.addEffect(this.butterflyEffect);
    }

    @Override
    public void update(float deltaTime) {
        this.sunEffect.update(deltaTime);
        this.butterflyEffect.update(deltaTime);
    }

    @Override
    public void draw(Batch batch) {
        Gdx.gl.glClearColor(171/255f,107/255f,72/255f,1);
        batch.draw(this.backgroundTexture, 0, 0);
    }

    @Override
    public void reset() {
        this.sunEffect.reset();
        this.butterflyEffect.reset();
        this.effectDrawer.removeEffect(this.sunEffect);
        this.effectDrawer.removeEffect(this.butterflyEffect);
    }

}
