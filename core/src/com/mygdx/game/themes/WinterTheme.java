package com.mygdx.game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.effects.CloudEffect;
import com.mygdx.game.themes.exceptions.UnsupportedSpriteException;
import com.mygdx.game.sprites.frogs.FreezeFrog;
import com.mygdx.game.sprites.frogs.ColorfullFrog;
import com.mygdx.game.sprites.frogs.IllusionFrog;
import com.mygdx.game.sprites.frogs.HealthFrog;
import com.mygdx.game.sprites.frogs.RegularFrog;
import com.mygdx.game.sprites.frogs.PoisonFrog;

import java.util.HashMap;

/**
 * TODO This is just an example class, should be removed when actual themes are created !!!
 *
 * Created by MichaelBond on 9/18/2016.
 */
public class WinterTheme implements Theme {

    private static final Texture BACKGROUND_TEXTURE = new Texture("world4.jpg");
    private static final Music MUSIC = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
    private static CloudEffect clouds=new CloudEffect();

    // Blue frog.
    private static final Texture BLUE_FROG_TEXTURE[] = {
            new Texture("Frog/0b.png"),
            new Texture("Frog/1b.png"),
            new Texture("Frog/2b.png"),
            new Texture("Frog/3b.png"),
            new Texture("Frog/0b.png"),
            new Texture("Frog/eye2b.png"),
            new Texture("Frog/eye3b.png"),
            new Texture("Frog/eye4b.png")
    };
    // Colorful frog.
    private static final Texture COLORFUL_FROG_TEXTURE[] = {
            new Texture("Frog/0p.png"),
            new Texture("Frog/1r.png"),
            new Texture("Frog/2b.png"),
            new Texture("Frog/3y.png"),
            new Texture("Frog/0p.png"),
            new Texture("Frog/eye2r.png"),
            new Texture("Frog/eye3b.png"),
            new Texture("Frog/eye4y.png")
    };
    // Pink frog.
    private static final Texture PINK_FROG_TEXTURE[] = {
            new Texture("Frog/0p.png"),
            new Texture("Frog/1p.png"),
            new Texture("Frog/2p.png"),
            new Texture("Frog/3p.png"),
            new Texture("Frog/0p.png"),
            new Texture("Frog/eye2p.png"),
            new Texture("Frog/eye3p.png"),
            new Texture("Frog/eye4p.png")
    };
    // Red frog.
    private static final Texture RED_FROG_TEXTURE[] = {
            new Texture("Frog/0r.png"),
            new Texture("Frog/1r.png"),
            new Texture("Frog/2r.png"),
            new Texture("Frog/3r.png"),
            new Texture("Frog/0r.png"),
            new Texture("Frog/eye2r.png"),
            new Texture("Frog/eye3r.png"),
            new Texture("Frog/eye4r.png")
    };
    // Regular frog.
    private static final Texture REGULAR_FROG_TEXTURE[] = {
            new Texture("Frog/0.png"),
            new Texture("Frog/1.png"),
            new Texture("Frog/2.png"),
            new Texture("Frog/3.png"),
            new Texture("Frog/0.png"),
            new Texture("Frog/eye2.png"),
            new Texture("Frog/eye3.png"),
            new Texture("Frog/eye4.png")
    };
    // Yellow frog.
    private static final Texture YELLOW_FROG_TEXTURE[] = {
            new Texture("Frog/0y.png"),
            new Texture("Frog/1y.png"),
            new Texture("Frog/2y.png"),
            new Texture("Frog/3y.png"),
            new Texture("Frog/0y.png"),
            new Texture("Frog/eye2y.png"),
            new Texture("Frog/eye3y.png"),
            new Texture("Frog/eye4y.png")
    };

    private static final HashMap<Class<? extends Sprite>, Texture[]> SPRITE_CLASS_TO_TEXTURE_MAP =
            new HashMap<Class<? extends Sprite>, Texture[]>();

    static {
        SPRITE_CLASS_TO_TEXTURE_MAP.put(FreezeFrog.class, BLUE_FROG_TEXTURE);
        SPRITE_CLASS_TO_TEXTURE_MAP.put(ColorfullFrog.class, COLORFUL_FROG_TEXTURE);
        SPRITE_CLASS_TO_TEXTURE_MAP.put(IllusionFrog.class, PINK_FROG_TEXTURE);
        SPRITE_CLASS_TO_TEXTURE_MAP.put(HealthFrog.class, RED_FROG_TEXTURE);
        SPRITE_CLASS_TO_TEXTURE_MAP.put(RegularFrog.class, REGULAR_FROG_TEXTURE);
        SPRITE_CLASS_TO_TEXTURE_MAP.put(PoisonFrog.class, YELLOW_FROG_TEXTURE);
    }

    @Override
    public void draw(Batch batch) {
        Gdx.gl.glClearColor(171/255f,107/255f,72/255f,1);
        batch.draw(BACKGROUND_TEXTURE, 0, 0);
        clouds.draw(batch);
    }

    @Override
    public Music getMusic() {
        return MUSIC;
    }

    @Override
    public Texture[] getSpriteTexture(Sprite sprite) throws UnsupportedSpriteException {
        Texture spriteTexture[] = SPRITE_CLASS_TO_TEXTURE_MAP.get(sprite.getClass());
        if (null == spriteTexture) {
            throw new UnsupportedSpriteException("No appropriate theme texture could be found" +
                    " for a sprite of the following class: " + sprite.getClass());
        }
        return spriteTexture;
    }

}
