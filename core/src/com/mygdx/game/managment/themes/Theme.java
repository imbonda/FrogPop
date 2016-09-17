package com.mygdx.game.managment.themes;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * This class represents a game's theme.
 * Each theme has a unique:
 *  1) Background
 *  2) Sprites dressing-code.
 *  3) Music.
 *
 * Created by MichaelBond on 9/18/2016.
 */
public interface Theme {

    /**
     * Returns the theme's background texture.
     */
    Texture getBackgroundTexture();

    /**
     * Returns the sprite's texture suitable for this theme.
     *
     * @param sprite    The sprite whose theme's related texture we wish to retrieve.
     */
    Texture[] getSpriteTexture(Sprite sprite);

    /**
     * Returns the theme's music.
     */
    void getMusic();

}
