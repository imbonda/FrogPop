package states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by MichaelBond on 8/23/2016.
 */
public class GameStateManager {

    private Stack<State> states;


    public GameStateManager() {
        this.states = new Stack<State>();
    }

    public void push(State state) {
        this.states.push(state);
    }

    public State pop() {
        State poppedState = this.states.pop();
        poppedState.dispose();
        return poppedState;
    }

    public void set(State state) {
        pop();
        push(state);
    }

    public void update(float deltaTime) {
        State stateToUpdate = this.states.peek();
        stateToUpdate.update(deltaTime);
    }

    public void render(SpriteBatch batch) {
        State stateToRender = this.states.peek();
        stateToRender.render(batch);
    }
}
