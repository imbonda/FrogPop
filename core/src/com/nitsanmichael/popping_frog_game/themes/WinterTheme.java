package com.nitsanmichael.popping_frog_game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.nitsanmichael.popping_frog_game.effects.EffectDrawer;
import com.nitsanmichael.popping_frog_game.effects.SnowEffect;

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
    public void init(com.nitsanmichael.popping_frog_game.assets.AssetController assetController, EffectDrawer effectDrawer) {
        this.backgroundTexture = assetController.get(com.nitsanmichael.popping_frog_game.assets.Assets.WINTER_THEME);
        this.snowEffect = new SnowEffect(assetController);
        this.effectDrawer = effectDrawer;
        this.effectDrawer.addEffect(this.snowEffect);
    }

    @Override
    public void update(float deltaTime) {
        this.snowEffect.update(deltaTime);
    }

    @Override
    public void draw(Batch batch) {
        Gdx.gl.glClearColor(171/255f,107/255f,72/255f,1);
        batch.draw(this.backgroundTexture, 0, 0);
    }

    @Override
    public void reset() {
        this.snowEffect.reset();
        this.effectDrawer.removeEffect(this.snowEffect);
    }

}
