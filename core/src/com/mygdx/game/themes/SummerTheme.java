package com.mygdx.game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.effects.BirdsEffect;
import com.mygdx.game.effects.EffectDrawer;
import com.mygdx.game.effects.SunEffect;

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
        this.backgroundTexture = new Texture("summer.png");
        this.sunEffect = new SunEffect();
        this.birdsEffect = new BirdsEffect();
    }

    @Override
    public void init(EffectDrawer effectDrawer) {
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
