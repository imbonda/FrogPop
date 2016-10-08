package com.nitsanmichael.frog_pop_game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.nitsanmichael.frog_pop_game.assets.AssetController;
import com.nitsanmichael.frog_pop_game.assets.Assets;
import com.nitsanmichael.frog_pop_game.effects.BirdsEffect;
import com.nitsanmichael.frog_pop_game.effects.EffectDrawer;
import com.nitsanmichael.frog_pop_game.effects.SunEffect;

/**
 * TODO This is just an example class, should be removed when actual themes are created !!!
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
    public void init(AssetController assetController, EffectDrawer effectDrawer) {
        this.backgroundTexture = assetController.get(Assets.SUMMER_THEME);
        this.birdsEffect = new BirdsEffect(assetController);
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
        Gdx.gl.glClearColor(171/255f,107/255f,72/255f,1);
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
