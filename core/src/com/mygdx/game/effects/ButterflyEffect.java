package com.mygdx.game.effects;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by nitsa on 27-Sep-16.
 */
public class ButterflyEffect implements Effect {

    private static final float UPDATE_SPEED=0.05f;
    private static final int RIGHT_PARAMETER=5;
    private static final int LEFT_PARAMETER=2;
    private float speed=0;
    private Texture[] butterfly={new Texture("butter.png"),new Texture("butter2.png"),new Texture("butter3.png")};
    private Random rand = new Random();
    private Vector2 butterfly1pos=new Vector2(70,465);
    private Vector2 butterfly2pos=new Vector2(200,340);
    private Vector2 butterfly3pos=new Vector2(300,520);
    private Vector2 butterfly4pos=new Vector2(450,200);
    private Vector2 butterfly5pos=new Vector2(570,170);
    private Vector2 butterfly6pos=new Vector2(640,80);
    private Vector2 speed1 =new Vector2(rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER,rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER);
    private Vector2 speed2 =new Vector2(rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER,rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER);
    private Vector2 speed3 =new Vector2(rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER,rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER);
    private Vector2 speed4 =new Vector2(rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER,rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER);
    private Vector2 speed5 =new Vector2(rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER,rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER);
    private Vector2 speed6 =new Vector2(rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER,rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER);

    public ButterflyEffect() {
    }
    private void postition(){
        Random rand = new Random();
        speed1 =new Vector2(rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER,rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER);
        speed2 =new Vector2(rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER,rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER);
        speed3 =new Vector2(rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER,rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER);
        speed4 =new Vector2(rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER,rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER);
        speed5 =new Vector2(rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER,rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER);
        speed6 =new Vector2(rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER,rand.nextInt(RIGHT_PARAMETER)-LEFT_PARAMETER);
        if(butterfly1pos.x>770)
        {
            speed1.x=-1*speed1.x;
        }
        if(butterfly2pos.x>770)
        {
            speed2.x=-1*speed2.x;
        }
        if(butterfly3pos.x>770)
        {
            speed3.x=-1*speed3.x;
        }
        if(butterfly4pos.x>770)
        {
            speed4.x=-1*speed4.x;
        }
        if(butterfly5pos.x>770)
        {
            speed5.x=-1*speed5.x;
        }
        if(butterfly6pos.x>770)
        {
            speed6.x=-1*speed6.x;
        }

        if(butterfly1pos.x<0)
        {
            speed1.x=-1*speed1.x;
        }
        if(butterfly2pos.x<0)
        {
            speed2.x=-1*speed2.x;
        }
        if(butterfly3pos.x<0)
        {
            speed3.x=-1*speed3.x;
        }
        if(butterfly4pos.x<0)
        {
            speed4.x=-1*speed4.x;
        }
        if(butterfly5pos.x<0)
        {
            speed5.x=-1*speed5.x;
        }
        if(butterfly6pos.x<0)
        {
            speed6.x=-1*speed6.x;
        }

        if(butterfly1pos.y>530)
        {
            speed1.y=-1*speed1.y;
        }
        if(butterfly2pos.y>530)
        {
            speed2.y=-1*speed2.y;
        }
        if(butterfly3pos.y>530)
        {
            speed3.y=-1*speed3.y;
        }
        if(butterfly4pos.y>530)
        {
            speed4.y=-1*speed4.y;
        }
        if(butterfly5pos.y>530)
        {
            speed5.y=-1*speed5.y;
        }
        if(butterfly6pos.y>530)
        {
            speed6.y=-1*speed6.y;
        }
        if(butterfly1pos.y<0)
        {
            speed1.y=-1*speed1.y;
        }
        if(butterfly2pos.y<0)
        {
            speed2.y=-1*speed2.y;
        }
        if(butterfly3pos.y<0)
        {
            speed3.y=-1*speed3.y;
        }
        if(butterfly4pos.y<0)
        {
            speed4.y=-1*speed4.y;
        }
        if(butterfly5pos.y<0)
        {
            speed5.y=-1*speed5.y;
        }
        if(butterfly6pos.y<0)
        {
            speed6.y=-1*speed6.y;
        }
        // speed1.scl(3,3f);
        // speed2.scl(3,3);
        // speed3.scl(3,3);
        // speed4.scl(3,3);
        // speed5.scl(3,3);
        // speed6.scl(3,3);
        System.out.println(speed1.x);
    }

    @Override
    public void update(float deltaTime) {
        speed+=deltaTime;
        butterfly1pos.add(speed1.x,speed1.y);
        butterfly2pos.add(speed2.x,speed2.y);
        butterfly3pos.add(speed3.x,speed3.y);
        butterfly4pos.add(speed4.x,speed4.y);
        butterfly5pos.add(speed5.x,speed5.y);
        butterfly6pos.add(speed6.x,speed6.y);
        if(speed>UPDATE_SPEED){
            postition();
            speed=0;
        }}

    @Override
    public void draw(Batch batch){
        batch.draw(butterfly[0],butterfly1pos.x,butterfly1pos.y);
        batch.draw(butterfly[1],butterfly2pos.x,butterfly2pos.y);
        batch.draw(butterfly[2],butterfly3pos.x,butterfly3pos.y);
        batch.draw(butterfly[0],butterfly4pos.x,butterfly4pos.y);
        batch.draw(butterfly[1],butterfly5pos.x,butterfly5pos.y);
        batch.draw(butterfly[2],butterfly6pos.x,butterfly6pos.y);
    }

    @Override
    public void reset() {
        // Nothing to reset.
    }
}
