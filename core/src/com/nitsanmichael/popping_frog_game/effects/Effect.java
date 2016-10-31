package com.nitsanmichael.popping_frog_game.effects;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * This class represents a game's effect.
 * An effect is something that is happening over the theme's background.
 * Some basic examples are: rain, snow, etc'.
 *
 * Created by MichaelBond on 10/3/2016.
 */
public interface Effect {

    /**
     * Updates the effect with regard to the time passed since the last call to 'update'.
     *
     * @param deltaTime The time passed from the last call.
     */
    void update(float deltaTime);

    /**
     * Draws the effect on to the screen.
     */
    void draw(Batch batch);

    /**
     * Resets the effect.
     */
    void reset();

}
