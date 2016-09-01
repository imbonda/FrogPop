package states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by MichaelBond on 8/23/2016.
 */
public abstract class State {

    protected OrthographicCamera cam;
    protected Viewport viewport;
    protected Vector3 mouse;
    protected GameStateManager gsm;
    protected SpriteBatch sb;


    public State(GameStateManager gsm) {
        this.gsm = gsm;
        this.cam = new OrthographicCamera();
        this.mouse = new Vector3();
        viewport = new FitViewport(800,530,cam);
    }



    public abstract void handleInput();

    public abstract void setViewport(Viewport v);

    public abstract void update(float deltaTime);

    public abstract void render(SpriteBatch batch);

    public abstract void dispose();
}
