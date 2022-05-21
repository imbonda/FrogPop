package com.nitsanmichael.popping_frog_game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.effects.EffectDrawer;
import com.nitsanmichael.popping_frog_game.effects.SnowEffect;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;

/**
 * A winter theme.
 *
 * Created by MichaelBond on 9/18/2016.
 */
public class WinterTheme implements Theme {

    private Texture backgroundTexture;
    private SnowEffect snowEffect;
    private EffectDrawer effectDrawer;

    public WinterTheme() {
    }

    @Override
    public void init(AssetController assetController, EffectDrawer effectDrawer,
                        RuntimeInfo runtimeInfo) {
        this.backgroundTexture = assetController.get(Assets.WINTER_THEME);
        this.snowEffect = new SnowEffect(assetController, runtimeInfo);
        this.effectDrawer = effectDrawer;
        this.effectDrawer.addEffect(this.snowEffect);
    }

    @Override
    public void update(float deltaTime) {
        this.snowEffect.update(deltaTime);
    }

    @Override
    public void draw(Batch batch) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        batch.draw(this.backgroundTexture, 0, 0);
    }

    @Override
    public void reset() {
        this.snowEffect.reset();
        this.effectDrawer.removeEffect(this.snowEffect);
    }

}
