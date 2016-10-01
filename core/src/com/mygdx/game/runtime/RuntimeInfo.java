package com.mygdx.game.runtime;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.managment.LevelController;
import com.mygdx.game.sprites.frogs.Frog;

/**
 * Created by MichaelBond on 9/30/2016.
 */
public class RuntimeInfo {

    public float gameSpeed;
    public Array<Frog> activeFrogs;

    public RuntimeInfo() {
        this.gameSpeed = LevelController.STARTING_SPEED;
        this.activeFrogs = new Array<Frog>();
    }
}
