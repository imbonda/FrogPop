package com.nitsanmichael.popping_frog_game.themes;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.effects.EffectDrawer;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;

/**
 * This class represents a game's theme.
 * Each theme has a unique background.
 *
 * Created by MichaelBond on 9/18/2016.
 */
public interface Theme {

    /**
     * Initializes the theme.
     *
     * @param assetController   An asset controller instance for retrieving the loaded assets.
     * @param effectDrawer  An effect drawer to be used in case the theme is using some effects.
     * @param runtimeInfo A class containing runtime information regarding the app.
     */
    void init (AssetController assetController, EffectDrawer effectDrawer, RuntimeInfo runtimeInfo);

    /**
     * Updates the theme with regard to the time passed since the last call to 'update'.
     *
     * @param deltaTime The time passed from the last call.
     */
    void update(float deltaTime);

    /**
     * Draws the theme on to the screen.
     */
    void draw(Batch batch);

    /**
     * Resets the theme.
     */
    void reset();

}
