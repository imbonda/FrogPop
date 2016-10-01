package com.mygdx.game.effects;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by nitsa on 27-Sep-16.
 */
public class GameOverEffect {
    private double dir = 0.1;
    private float framekey=0;
    private final Texture frogTexture[] = {
            new Texture("Frog/0b.png"),
            new Texture("Frog/1b.png"),
            new Texture("Frog/2b.png"),
    };
    private static final Vector2 Frog1_POSITION = new Vector2(520, 260);
    private static final Vector2 Frog2_POSITION = new Vector2(150, 260);


    public GameOverEffect() {
    }


    public void update(float deltaTime) {
        }

    public void draw(Batch batch){
        if (this.framekey< 0.2){
            this.dir = 0.1;
        }
        if (this.framekey > 2.7){
            dir = -0.1;
        }
        this.framekey += dir;
        System.out.print((int)framekey);
        batch.draw(this.frogTexture[(int)framekey],Frog1_POSITION.x,Frog1_POSITION.y);
        batch.draw(this.frogTexture[(int)framekey],Frog2_POSITION.x,Frog2_POSITION.y);

    }
}
