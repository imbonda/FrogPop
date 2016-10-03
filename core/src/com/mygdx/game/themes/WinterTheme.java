package com.mygdx.game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.effects.EffectDrawer;
import com.mygdx.game.effects.SnowEffect;

/**
 * TODO This is just an example class, should be removed when actual themes are created !!!
 *
 * Created by MichaelBond on 9/18/2016.
 */
public class WinterTheme implements Theme {

    private Texture backgroundTexture;
    private SnowEffect snowEffect;
    private EffectDrawer effectDrawer;

    public WinterTheme() {
        this.backgroundTexture = new Texture("winter.png");
        this.snowEffect = new SnowEffect();
    }

    @Override
    public void init(EffectDrawer effectDrawer) {
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
