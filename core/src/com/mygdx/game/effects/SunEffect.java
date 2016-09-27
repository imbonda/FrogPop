package com.mygdx.game.effects;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Random;

/**
 * Created by nitsa on 27-Sep-16.
 */
public class SunEffect {
    private final Texture sunTexture[] = {
            new Texture("sun0.png"),
            new Texture("sun1.png"),
            new Texture("sun2.png"),
            new Texture("sun3.png"),
            new Texture("sun4.png"),
            new Texture("sun5.png"),
            new Texture("sun6.png"),
    };
    private double i = 0;
    int delay=1;
    private int sunpos=380;
    private double dir = 0.25;

    public SunEffect() {
    }
    private void postition(){
        if (i==0){
            dir=0.25;
        }
        if(i>6.6){
            i=0;
        }
        i+=dir;
    }

    public void draw(Batch batch){
        delay++;

        if(delay%6==0){
        postition();}
        batch.draw(sunTexture[(int)i],sunpos,460);
    }
}
