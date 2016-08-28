package states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import sprites.Buttons;
import sprites.Frog;
import sprites.FrogGenerator;
import sprites.Hole;

/**
 * Created by nitsa on 27-Aug-16.
 */

    public class GameOver extends State {
    Sprite End;
    BitmapFont Loser;
    private Buttons button1;
    public GameOver(GameStateManager gsm) {
        super(gsm);
        this.Loser = new BitmapFont();
        End=new Sprite(new Texture(Gdx.files.internal("end.jpg")));
        button1=new Buttons(320,265);
    }
    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector2 touchVector = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (this.button1.isButtonsTouched(touchVector)) {
                this.gsm.push(new PlayState(gsm));
    }}}
    @Override
    public void update(float deltaTime) {
        handleInput();
    }


    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        End.draw(batch);
        drawGO(batch);
        drawButtons(batch);
        batch.end();
    }

    private void drawGO(SpriteBatch batch)
    {

        Loser.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        Loser.draw(batch, "You Lost",350,400);
    }
    private void drawButtons(SpriteBatch batch)
    {
        Vector2 buttonsPosition = this.button1.getPosition();
        batch.draw(this.button1.getButtonsTexture(), buttonsPosition.x, buttonsPosition.y);
    }
    @Override
    public void dispose() {
        End.getTexture().dispose();
    }
}
