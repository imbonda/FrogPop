package com.nitsanmichael.popping_frog_game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.effects.EffectDrawer;
import com.nitsanmichael.popping_frog_game.effects.RainEffect;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;

/**
 * An autumn theme.
 *
 * Created by MichaelBond on 9/18/2016.
 */
public class AutumnTheme implements Theme {

    private Texture backgroundTexture;
    private RainEffect rainEffect;
    private EffectDrawer effectDrawer;

    public AutumnTheme() {
    }

    @Override
    public void init(AssetController assetController,
                        EffectDrawer effectDrawer, RuntimeInfo runtimeInfo) {
        this.backgroundTexture = assetController.get(Assets.AUTUMN_THEME);
        this.rainEffect = new RainEffect(assetController, runtimeInfo);
        this.effectDrawer = effectDrawer;
        this.effectDrawer.addEffect(this.rainEffect);
    }

    @Override
    public void update(float deltaTime) {
        this.rainEffect.update(deltaTime);
    }

    @Override
    public void draw(Batch batch) {
        Gdx.gl.glClearColor(1, 1, 1 ,1);
        batch.draw(this.backgroundTexture, 0, 0);
    }

    @Override
    public void reset() {
        this.rainEffect.reset();
        this.effectDrawer.removeEffect(this.rainEffect);
    }

}
