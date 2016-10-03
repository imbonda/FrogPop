package com.mygdx.game.effects;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by nitsa on 27-Sep-16.
 */
public class ButterflyEffect implements Effect {

    private Texture butter1=new Texture("butter.png");
    private Texture butter2=new Texture("butter2.png");
    private Texture butter3=new Texture("Butter3.png");

    Vector2 speed1 = new Vector2(1,1);
    Vector2 speed2 = new Vector2(1,1);
    Vector2 speed3 = new Vector2(1,1);
    int delay=1;
    private Vector2 cloud1pos=new Vector2(500,365);
    private Vector2 cloud2pos=new Vector2(100,365);
    private Vector2 cloud3pos=new Vector2(200,365);
    private Vector2 cloud4pos=new Vector2(300,365);
    private Vector2 cloud5pos=new Vector2(400,365);
    private Vector2 cloud6pos=new Vector2(660,365);
    public ButterflyEffect() {
        Random rand = new Random();

    }
    private void postition(){
        Random rand = new Random();
        speed1 =new Vector2(rand.nextInt(4)-2,rand.nextInt(8)-2);
        speed2 =new Vector2(rand.nextInt(4)-2,rand.nextInt(4)-2);
        speed3 =new Vector2(rand.nextInt(6)-2,rand.nextInt(6)-4);
        cloud1pos.add(speed1.x,speed2.y);
        cloud2pos.add(speed1.x,speed3.y);
        cloud3pos.add(speed2.x,speed1.y);
        cloud4pos.add(speed2.x,speed3.y);
        cloud5pos.add(speed3.x,speed1.y);
        cloud6pos.add(speed3.x,speed2.y);
        if(speed1.x>0||speed2.x>0||speed3.x>0){
        if(cloud1pos.x>800)
        {
            cloud1pos.x=0;
        }
        if(cloud2pos.x>800)
        {
            cloud2pos.x=0;
        }
        if(cloud3pos.x>800)
        {
            cloud3pos.x=0;
        }
        if(cloud4pos.x>800)
        {
            cloud4pos.x=0;
        }
        if(cloud5pos.x>800)
        {
            cloud5pos.x=0;
        }
        if(cloud6pos.x>800)
        {
            cloud6pos.x=0;
        }}
        if(speed1.x<0||speed2.x<0||speed3.x<0){
            if(cloud1pos.x<0)
            {
                cloud1pos.x=800;
            }
            if(cloud2pos.x<00)
            {
                cloud2pos.x=800;
            }
            if(cloud3pos.x<0)
            {
                cloud3pos.x=800;
            }
            if(cloud4pos.x<0)
            {
                cloud4pos.x=800;
            }
            if(cloud5pos.x<0)
            {
                cloud5pos.x=800;
            }
            if(cloud6pos.x<0)
            {
                cloud6pos.x=800;
            }}
        if(speed1.y>0||speed2.y>0||speed3.y>0){
            if(cloud1pos.y>530)
            {
                cloud1pos.y=0;
            }
            if(cloud2pos.y>530)
            {
                cloud2pos.y=0;
            }
            if(cloud3pos.y>530)
            {
                cloud3pos.y=0;
            }
            if(cloud4pos.y>530)
            {
                cloud4pos.y=0;
            }
            if(cloud5pos.y>530)
            {
                cloud5pos.x=0;
            }
            if(cloud6pos.y>530)
            {
                cloud6pos.y=0;
            }}
        if(speed1.y<0||speed2.y<0||speed3.y<0){
            if(cloud1pos.y<0)
            {
                cloud1pos.y=530;
            }
            if(cloud2pos.y<0)
            {
                cloud2pos.y=530;
            }
            if(cloud3pos.y<0)
            {
                cloud3pos.y=530;
            }
            if(cloud4pos.y<0)
            {
                cloud4pos.y=530;
            }
            if(cloud5pos.y<0)
            {
                cloud5pos.y=530;
            }
            if(cloud6pos.y<0)
            {
                cloud6pos.y=530;
            }}
    }

    @Override
    public void update(float deltaTime) {
        // Nothing to update.
    }

    @Override
    public void draw(Batch batch){
        delay++;
        if(delay%2==0) {
            postition();
        }
        batch.draw(butter1,cloud1pos.x,cloud1pos.y);
        batch.draw(butter1,cloud2pos.x,cloud2pos.y);
        batch.draw(butter2,cloud3pos.x,cloud3pos.y);
        batch.draw(butter2,cloud4pos.x,cloud4pos.y);
        batch.draw(butter3,cloud5pos.x,cloud5pos.y);
        batch.draw(butter3,cloud6pos.x,cloud6pos.y);
    }

    @Override
    public void reset() {
        // Nothing to reset.
    }
}
