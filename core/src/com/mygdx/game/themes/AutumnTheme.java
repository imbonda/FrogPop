package com.mygdx.game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.effects.RainEffect;
import com.mygdx.game.themes.exceptions.UnsupportedSpriteException;
import com.mygdx.game.sprites.frogs.FreezeFrog;
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
public class AutumnTheme implements Theme {

    private Texture backgroundTexture;
    private Texture leaves[]={new Texture("leave.png"),new Texture("leave2.png"),new Texture("leave3.png"),};
    private RainEffect rainEffect;

    public AutumnTheme() {
        this.backgroundTexture = new Texture("autumn.png");

        this.rainEffect = new RainEffect();
    }

    @Override
    public void update(float deltaTime) {
        this.rainEffect.update(deltaTime);
    }

    @Override
    public void draw(Batch batch) {
        Gdx.gl.glClearColor(171/255f,107/255f,72/255f,1);
        batch.draw(this.backgroundTexture, 0, 0);
        batch.draw(leaves[0],10,20);
        batch.draw(leaves[0],100,20);
        batch.draw(leaves[0],100,200);
        batch.draw(leaves[1],300,330);
        batch.draw(leaves[1],400,120);
        batch.draw(leaves[1],550,100);
        batch.draw(leaves[2],650,170);
        batch.draw(leaves[2],740,20);
        batch.draw(leaves[2],220,20);
        batch.draw(leaves[1],650,300);
        batch.draw(leaves[2],700,250);
        batch.draw(leaves[0],10,70);
        this.rainEffect.draw(batch);
    }

}
