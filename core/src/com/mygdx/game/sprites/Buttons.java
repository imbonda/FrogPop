package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by MichaelBond on 8/25/2016.
 */
public class Buttons {
    private Texture Button1Texture;
    private Vector2 position;
    private Rectangle ButtonRectangle;


    public Buttons(float xCord, float yCord) {
        this.Button1Texture = new Texture("button1.png");
        this.position = new Vector2(xCord, yCord);
        this.ButtonRectangle = new Rectangle(
                this.position.x, this.position.y,
                this.Button1Texture.getWidth(), this.Button1Texture.getHeight());
    }

    public Texture getButtonsTexture() {
        return this.Button1Texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isButtonsTouched(Vector2 touchVector) {
        System.out.println("button is at "+ position.x+"  "+position.y);
        return this.ButtonRectangle.contains(touchVector);

    }
}
