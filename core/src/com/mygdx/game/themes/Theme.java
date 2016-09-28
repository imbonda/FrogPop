package com.mygdx.game.themes;


import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
    void draw(Batch batch);

    void update(float deltaTime);


    /**
     * Returns the theme's music.
     */
    Music getMusic();

    /**
     * Returns the sprite's texture suitable for this theme.
     *
     * @param sprite    The sprite whose theme's related texture we wish to retrieve.
     */
    Texture[] getSpriteTexture(Sprite sprite);

}
