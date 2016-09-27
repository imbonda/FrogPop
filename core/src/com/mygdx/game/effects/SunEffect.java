package com.mygdx.game.effects;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Random;

/**
 * Created by nitsa on 27-Sep-16.
 */
public class SunEffect {
    private Texture sun=new Texture("sun.jpg");
    int speed1 = 8;
    int delay=1;
    private int sunpos=0;

    public SunEffect() {
    }
    private void postition(){
        sunpos+=-speed1;
    }

    public void draw(Batch batch){
        delay++;
        if(delay%2==0){
        postition();}
        batch.draw(sun,sunpos,365);
    }
}
