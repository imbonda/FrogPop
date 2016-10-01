package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.FrogPop;
import com.mygdx.game.managment.GameOverTouchProcessor;

/**
 * Created by MichaelBond on 8/25/2016.
 */
public class Buttons {

    private Texture Button1Texture;
    private Texture ButtonPressed;
    private Texture TheButtonTexture;
    private Vector2 position;
    private Rectangle ButtonRectangle;
    private int checker=0;



    public Buttons(float xCord, float yCord,Texture button,Texture PressedButton) {
       // this.viewport = new FitViewport(
       //         FrogPop.VIRTUAL_WIDTH, FrogPop.VIRTUAL_HEIGHT, new OrthographicCamera());
      //  gameOverTouchProcessor=new GameOverTouchProcessor(viewport);
     //   Gdx.input.setInputProcessor(gameOverTouchProcessor);
        this.ButtonPressed=PressedButton;
        this.Button1Texture= button;
        this.TheButtonTexture=button;
        this.position = new Vector2(xCord, yCord);
        this.ButtonRectangle = new Rectangle(
                this.position.x, this.position.y,
                this.Button1Texture.getWidth(), this.Button1Texture.getHeight());

    }

    public Texture getButtonsTexture() {
        return this.TheButtonTexture;
    }

    public void setButtonTexture(int which) {
        if(which==1){
            TheButtonTexture=Button1Texture;
        }
        if(which==2) {
            TheButtonTexture=ButtonPressed;
        }
        }

    public Vector2 getPosition() {
        return position;
    }


    public boolean isButtonsTouched(Vector2 touchVector) {
        if (((this.ButtonRectangle.contains(touchVector)))&&(Gdx.input.isTouched())){
            checker=1;
            setButtonTexture(2);
        }
        if ((!(this.ButtonRectangle.contains(touchVector)))){
            checker=0;
        setButtonTexture(1);
        }
        if((checker==1)&&(Gdx.input.isTouched()==false))
        {
            return true;
        }
        return false;
   }

}
