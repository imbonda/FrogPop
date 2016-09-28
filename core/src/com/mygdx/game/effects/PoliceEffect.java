package com.mygdx.game.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.sprites.frogs.FreezeFrog;
import com.mygdx.game.sprites.frogs.Frog;

/**
 * Created by nitsa on 28-Sep-16.
 */
public class PoliceEffect {
    private int i=0;
    public void setEffect(Batch batch) {
        if (i % 50 == 0) {
            batch.setColor(1f, 0.2f, 0.2f, 1);
        }
        if (i % 60 == 0) {
            batch.setColor(0.2f, 0.2f, 1f, 1);
        }
        i++;
    }
}
