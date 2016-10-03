package com.mygdx.game.effects;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by nitsa on 27-Sep-16.
 */
public class BirdsEffect implements Effect {

    private Texture[] bird={new Texture("bird1n.png"),new Texture("bird2n.png"),new Texture("bird3n.png")};
    private static final int Frame_Speed=6;
    int delay=1;
    private Random rand = new Random();
    private Vector2 cloud1pos=new Vector2(500,465);
    private Vector2 cloud2pos=new Vector2(100,465);
    private Vector2 cloud3pos=new Vector2(200,480);
    private Vector2 speed1 =new Vector2(rand.nextInt(4)-2,rand.nextInt(2)-1);
    private Vector2 speed2 =new Vector2(rand.nextInt(4)-2,rand.nextInt(2)-1);
    private Vector2 speed3 =new Vector2(rand.nextInt(6)-2,rand.nextInt(2)-1);
    private float frameToDisplay=0;
    private int increasment=1;
    private boolean faceSideBird1=true;
    private boolean faceSideBird3=true;
    private boolean faceSideBird2=true;
    public BirdsEffect() {
        if(speed1.x==0){speed1.x=1;}
        if(speed2.x==0){speed2.x=1;}
        if(speed3.x==0){speed3.x=1;}

    }
    private void postition() {
        cloud1pos.add(speed1.x,speed1.y);
        cloud2pos.add(speed2.x,speed2.y);
        cloud3pos.add(speed3.x,speed3.y);

        if(speed1.x>0||speed2.x>0||speed3.x>0) {
            if(cloud1pos.x>770) {
                speed1.x=-1*speed1.x;
            }
            if(cloud2pos.x>770) {
                speed2.x=-1*speed2.x;
            }
            if(cloud3pos.x>770) {
                speed3.x=-1*speed3.x;
            }
        }
        if(speed1.x<0||speed2.x<0||speed3.x<0) {
            if(cloud1pos.x<0) {
                speed1.x=-1*speed1.x;
            }
            if(cloud2pos.x < 0) {
                speed2.x=-1*speed2.x;
            }
            if(cloud3pos.x<0) {
                speed3.x=-1*speed3.x;
            }
        }
        if(speed1.y>0||speed2.y>0||speed3.y>0) {
            if(cloud1pos.y>530) {
                speed1.y=-1*speed1.y;
            }
            if(cloud2pos.y>530) {
                speed2.y=-1*speed2.y;
            }
            if(cloud3pos.y>530) {
                speed3.y=-1*speed3.y;
            }
        }
        if(speed1.y<0||speed2.y<0||speed3.y<0) {
            if(cloud1pos.y<370) {
                speed1.y=-1*speed1.y;
            }
            if(cloud2pos.y<370) {
                speed2.y=-1*speed2.y;
            }
            if(cloud3pos.y<370) {
                speed3.y=-1*speed3.y;
            }
        }
        if(speed1.x>0) {
            faceSideBird1=true;
        }
        else if(speed1.x<0) {
            faceSideBird1=false;
        }
        if(speed2.x>0) {
            faceSideBird2=true;
        }
        else if(speed2.x<0) {
            faceSideBird2=false;
        }
        if(speed2.x>0) {
            faceSideBird3=true;
        }
        else if(speed3.x<0) {
            faceSideBird3=false;
        }
    }

    @Override
    public void update(float deltaTime) {
        if(frameToDisplay>bird.length+0.5f) {
            increasment=-1*increasment;
        }
        if(frameToDisplay<0) {
            increasment=-1*increasment;
        }
        frameToDisplay+=increasment*deltaTime*Frame_Speed;
    }

    @Override
    public void draw(Batch batch){
        delay++;
        if(delay%2==0)
        {
        postition();}
        batch.draw(bird[(int)frameToDisplay%3],cloud1pos.x,cloud1pos.y,30,30,1,1,bird[(int)frameToDisplay%3].getWidth(),bird[(int)frameToDisplay%3].getHeight(),faceSideBird1,false);
        batch.draw(bird[(int)frameToDisplay%3],cloud2pos.x,cloud2pos.y,30,30,1,1,bird[(int)frameToDisplay%3].getWidth(),bird[(int)frameToDisplay%3].getHeight(),faceSideBird2,false);
        batch.draw(bird[(int)frameToDisplay%3],cloud3pos.x,cloud3pos.y,30,30,1,1,bird[(int)frameToDisplay%3].getWidth(),bird[(int)frameToDisplay%3].getHeight(),faceSideBird3,false);
        //batch.draw(bird[(int)frameToDisplay%2],cloud4pos.x,cloud4pos.y);
        //batch.draw(bird[(int)frameToDisplay%2],cloud5pos.x,cloud5pos.y);
       // batch.draw(bird[(int)frameToDisplay%2],cloud6pos.x,cloud6pos.y);
    }

    @Override
    public void reset() {
        // Nothing to reset.
    }
}
