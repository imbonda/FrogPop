package com.nitsanmichael.popping_frog_game.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

/**
 * Created by MichaelBond on 10/3/2016.
 */
public class EffectDrawer {

    private Array<Effect> effects;


    public EffectDrawer() {
        this.effects = new Array<Effect>();
    }

    public void addEffect(Effect effect) {
        this.effects.add(effect);
    }

    public void removeEffect(Effect effect) {
        this.effects.removeValue(effect, true);
    }

    public void clear() {
        this.effects.clear();
    }

    public void drawEffects(Batch batch) {
        for (Effect effect : this.effects) {
            effect.draw(batch);
        }
    }
}
