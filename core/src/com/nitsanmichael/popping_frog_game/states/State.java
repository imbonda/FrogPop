package com.nitsanmichael.popping_frog_game.states;

import com.badlogic.gdx.utils.Disposable;


/**
 * Created by MichaelBond on 10/21/2016.
 */
public interface State extends Disposable {

    void render(float deltaTime);

}
