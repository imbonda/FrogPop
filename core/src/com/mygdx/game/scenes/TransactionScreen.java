package com.mygdx.game.scenes;
import com.badlogic.gdx.Screen;
import com.mygdx.game.FrogPop;

/**
 * Created by nitsa on 05-Oct-16.
 */
public class TransactionScreen {
    private float fadeTime=1f;
    private FrogPop game;
    private boolean isSet=false;
    private Screen screen;

    public TransactionScreen(FrogPop game)
    {
        this.game=game;


    }
    public void update(float deltaTime)
    {
        if(isSet==true){
        fadeTime-=deltaTime;}
        game.batch.setColor(1,1,1,fadeTime);
        if(fadeTime<=0.01f){
            isSet=false;
            fadeTime=1f;
            game.batch.setColor(1,1,1,fadeTime);
            this.game.setScreen(screen);
        }
    }
    public void setNextScreen(Screen nextScreen)
    {
        isSet=true;
        this.screen=nextScreen;
    }


}
