package com.mygdx.game.effects;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by nitsa on 27-Sep-16.
 */
public class CloudEffect {
    private Texture cloud=new Texture("cloud.png");
    int speed1 = 1;
    int speed2 = 1;
    int speed3 = 1;
    int direction=1;
    int delay=1;
    private int cloud1pos=500;
    private int cloud2pos=100;
    private int cloud3pos=200;
    private int cloud4pos=300;
    private int cloud5pos=400;
    private int cloud6pos=660;
    public CloudEffect() {
        Random rand = new Random();
        speed1 = rand.nextInt(2)+1;
        speed2 = rand.nextInt(5)+1;
        speed3 = rand.nextInt(5)+1;
        direction=rand.nextInt(2);
        direction=(direction==0)?1:-1;
    }
    private void postition(){
        cloud1pos=cloud1pos+direction*speed1;
        cloud2pos=cloud2pos+direction*speed1;
        cloud3pos=cloud3pos+direction*speed2;
        cloud4pos=cloud4pos+direction*speed2;
        cloud5pos=cloud5pos+direction*speed3;
        cloud6pos=cloud6pos+direction*speed3;
        if(direction==1){
        if(cloud1pos>800)
        {
            cloud1pos=-70;
        }
        if(cloud2pos>800)
        {
            cloud2pos=-70;
        }
        if(cloud3pos>800)
        {
            cloud3pos=-70;
        }
        if(cloud4pos>800)
        {
            cloud4pos=-70;
        }
        if(cloud5pos>800)
        {
            cloud5pos=-70;
        }
        if(cloud6pos>800)
        {
            cloud6pos=-70;
        }}
        if(direction==-1){
            if(cloud1pos<-40)
            {
                cloud1pos=+800;
            }
            if(cloud2pos<-40)
            {
                cloud2pos=+800;
            }
            if(cloud3pos>800)
            {
                cloud3pos=+800;
            }
            if(cloud4pos<-40)
            {
                cloud4pos=+870;
            }
            if(cloud5pos<-40)
            {
                cloud5pos=+800;
            }
            if(cloud6pos<-40)
            {
                cloud6pos=+800;
            }}


    }

    public void draw(Batch batch){
        delay++;
        if(delay%2==0){
        postition();}
        batch.draw(cloud,cloud1pos,460);
        batch.draw(cloud,cloud2pos,470);
        batch.draw(cloud,cloud3pos,440);
        batch.draw(cloud,cloud4pos,480);
        batch.draw(cloud,cloud5pos,480);
        batch.draw(cloud,cloud6pos,465);
    }
}
