package com.nitsanmichael.popping_frog_game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.nitsanmichael.popping_frog_game.screens.PlayScreen;


/**
 * Created by MichaelBond on 10/22/2016.
 */
public class StateTracker implements Disposable {

    public enum GameState { COUNTDOWN, PLAY, PAUSE, OVER, BACK_TO_MENU, REWARDED_REPLAY }

    private PlayScreen playScreen;
    private GameState currentStateName;
    private State currentState;


    public StateTracker(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

    public void setState(GameState state) {
        if (this.currentStateName == state) {
            return;
        }
        if (null != this.currentState) {
            this.currentState.dispose();
        }
        this.currentStateName = state;
        Gdx.input.setInputProcessor(null);
        switch (state) {
            case COUNTDOWN:
                this.currentState = new CountdownState(this.playScreen);
                break;
            case PLAY:
                this.currentState = new PlayState(this.playScreen);
                break;
            case PAUSE:
                this.currentState = new PauseState(this.playScreen);
                break;
            case OVER:
                this.currentState = new GameOverState(this.playScreen);
                break;
            case BACK_TO_MENU:
                this.currentState = new BackToMenuState(this.playScreen);
                break;
            case REWARDED_REPLAY:
                this.currentState = new RewardedReplayState(this.playScreen);
                break;
            default:
                break;
        }
        this.playScreen.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    public void render(float deltaTime) {
        this.currentState.render(deltaTime);
    }

    @Override
    public void dispose() {
        this.currentState.dispose();
    }

}
