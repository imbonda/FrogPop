package com.nitsanmichael.popping_frog_game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.effects.BirdsEffect;
import com.nitsanmichael.popping_frog_game.effects.EffectDrawer;
import com.nitsanmichael.popping_frog_game.effects.SunEffect;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;

/**
 * A summer theme.
 *
 * Created by MichaelBond on 9/18/2016.
 */
public class SummerTheme implements Theme {

    private Texture backgroundTexture;
    private SunEffect sunEffect;
    private BirdsEffect birdsEffect;
    private EffectDrawer effectDrawer;

    public SummerTheme() {
    }

    @Override
    public void init(AssetController assetController, EffectDrawer effectDrawer,
                        RuntimeInfo runtimeInfo) {
        this.backgroundTexture = assetController.get(Assets.SUMMER_THEME);
        this.birdsEffect = new BirdsEffect(assetController, runtimeInfo);
        this.sunEffect = new SunEffect(assetController);
        this.effectDrawer = effectDrawer;
        this.effectDrawer.addEffect(this.sunEffect);
        this.effectDrawer.addEffect(this.birdsEffect);
    }

    @Override
    public void update(float deltaTime) {
        this.sunEffect.update(deltaTime);
        this.birdsEffect.update(deltaTime);
    }

    @Override
    public void draw(Batch batch) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        batch.draw(this.backgroundTexture, 0, 0);
    }

    @Override
    public void reset() {
        this.sunEffect.reset();
        this.birdsEffect.reset();
        this.effectDrawer.removeEffect(this.sunEffect);
        this.effectDrawer.removeEffect(this.birdsEffect);
    }

}
