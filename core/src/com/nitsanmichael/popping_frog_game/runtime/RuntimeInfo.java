package com.nitsanmichael.popping_frog_game.runtime;

import com.badlogic.gdx.utils.Array;
import com.nitsanmichael.popping_frog_game.managment.LevelController;
import com.nitsanmichael.popping_frog_game.sprites.FrogGhost;
import com.nitsanmichael.popping_frog_game.sprites.Hole;
import com.nitsanmichael.popping_frog_game.sprites.frogs.Frog;
import com.nitsanmichael.popping_frog_game.states.StateTracker;


/**
 * Created by MichaelBond on 9/30/2016.
 */
public class RuntimeInfo {

    /**
     * A helper class, which represents an integer bound to a minimal value (or not if 'null' given),
     * and a maximal value (or not if 'null' given).
     */
    public static class RestrictedInteger {

        private int value;
        private Integer min, max;

        public RestrictedInteger(Integer value, Integer min, Integer max) {
            this.min = min;
            this.max = max;
            set(value);
        }

        public void set(Integer value) {
            if (null != min && value < min) {
                this.value = min;
            }
            else if (null != max && max < value) {
                this.value = max;
            }
            else {
                this.value = value;
            }
        }

        public Integer get() {
            return this.value;
        }
    }

    // Score bounds.
    private static final int MIN_SCORE = 0;
    // Life bounds.
    private static final int MIN_LIVES = 0;
    private static final int MAX_LIVES = 10;

    public RestrictedInteger gameScore;
    public RestrictedInteger gameLives;
    public int gameLevel;
    public float gameSpeed;
    public int rewardedReplays;
    public StateTracker stateTracker;
    public Array<Frog> activeFrogs;
    public Array<Hole> holes;
    public Array<FrogGhost> frogGhosts;
    public ScreenInfo screenInfo;

    public RuntimeInfo(int score, int lives, StateTracker stateTracker, ScreenInfo screenInfo) {
        this.gameScore = new RestrictedInteger(score, MIN_SCORE, null);
        this.gameLives = new RestrictedInteger(lives, MIN_LIVES, MAX_LIVES);
        this.gameLevel = LevelController.STARTING_LEVEL;
        this.gameSpeed = LevelController.STARTING_SPEED;
        this.stateTracker = stateTracker;
        this.screenInfo = screenInfo;
        this.rewardedReplays = 0;
        this.activeFrogs = new Array<Frog>();
        this.holes = new Array<Hole>();
        this.frogGhosts = new Array<FrogGhost>();
    }
}
