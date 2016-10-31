package com.nitsanmichael.popping_frog_game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.nitsanmichael.popping_frog_game.effects.EffectDrawer;
import com.nitsanmichael.popping_frog_game.effects.RainEffect;

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
    public void init(com.nitsanmichael.popping_frog_game.assets.AssetController assetController, EffectDrawer effectDrawer) {
        this.backgroundTexture = assetController.get(com.nitsanmichael.popping_frog_game.assets.Assets.AUTUMN_THEME);
        this.rainEffect = new RainEffect(assetController);
        this.effectDrawer = effectDrawer;
        this.effectDrawer.addEffect(this.rainEffect);
    }

    @Override
    public void update(float deltaTime) {
        this.rainEffect.update(deltaTime);
    }

    @Override
    public void draw(Batch batch) {
        Gdx.gl.glClearColor(171/255f,107/255f,72/255f,1);
        batch.draw(this.backgroundTexture, 0, 0);
    }

    @Override
    public void reset() {
        this.rainEffect.reset();
        this.effectDrawer.removeEffect(this.rainEffect);
    }

}
