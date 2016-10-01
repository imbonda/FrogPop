package com.mygdx.game.effects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by nitsa on 27-Sep-16.
 */
public class MenuEffect {
    private double dir = 0.25;
    private float framekey=0;
    private float framekey2=4;
    private final Texture frogTexture[] = {
            new Texture("Frog/0.png"),
            new Texture("Frog/1.png"),
            new Texture("Frog/2.png"),
            new Texture("Frog/3.png"),
            new Texture("Frog/0.png"),
            new Texture("Frog/eye2.png"),
            new Texture("Frog/eye3.png"),
            new Texture("Frog/eye4.png")
    };
    private static final Vector2 Frog1_POSITION = new Vector2(520, 320);
    private static final Vector2 Frog2_POSITION = new Vector2(150, 320);


    public MenuEffect() {
    }


    public void update(float deltaTime) {
        }

    public void draw(Batch batch){
        if (this.framekey == 0){
            this.dir = 0.25;
        }
        if (this.framekey > 3.7){
            dir = -0.25;
        }
        this.framekey += dir;
        batch.draw(this.frogTexture[(int)framekey],Frog1_POSITION.x,Frog1_POSITION.y);
        if (this.framekey2 == 4){
            this.dir = 0.25;
        }
        if (this.framekey2 > 7.7){
            dir = -0.25;
        }
        this.framekey2 += dir;
        batch.draw(this.frogTexture[(int)framekey2],Frog2_POSITION.x,Frog2_POSITION.y);
    }
}
