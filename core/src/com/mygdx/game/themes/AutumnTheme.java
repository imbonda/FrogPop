package com.mygdx.game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.effects.EffectDrawer;
import com.mygdx.game.effects.RainEffect;

/**
 * TODO This is just an example class, should be removed when actual themes are created !!!
 *
 * Created by MichaelBond on 9/18/2016.
 */
public class AutumnTheme implements Theme {

    private Texture backgroundTexture;
    private RainEffect rainEffect;
    private EffectDrawer effectDrawer;

    public AutumnTheme() {
        this.backgroundTexture = new Texture("autumn.png");
        this.rainEffect = new RainEffect();
    }

    @Override
    public void init(EffectDrawer effectDrawer) {
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
