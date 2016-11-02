package com.nitsanmichael.popping_frog_game.runtime;

import com.badlogic.gdx.utils.Array;
import com.nitsanmichael.popping_frog_game.managment.LevelController;
import com.nitsanmichael.popping_frog_game.sprites.FrogGhost;
import com.nitsanmichael.popping_frog_game.sprites.Hole;
import com.nitsanmichael.popping_frog_game.sprites.frogs.active.Frog;
import com.nitsanmichael.popping_frog_game.states.StateTracker;


/**
 * Created by MichaelBond on 9/30/2016.
 */
public class RuntimeInfo {

    public int gameScore;
    public int gameLives;
    public int gameLevel;
    public float gameSpeed;
    public int rewardedReplays;
    public StateTracker stateTracker;
    public Array<Frog> activeFrogs;
    public Array<Hole> holes;
    public Array<FrogGhost> frogGhosts;
    public ScreenInfo screenInfo;

    public RuntimeInfo(int score, int lives, StateTracker stateTracker, ScreenInfo screenInfo) {
        this.gameScore = score;
        this.gameLives = lives;
        this.stateTracker = stateTracker;
        this.screenInfo = screenInfo;
        this.gameLevel = LevelController.STARTING_LEVEL;
        this.gameSpeed = LevelController.STARTING_SPEED;
        this.rewardedReplays = 0;
        this.activeFrogs = new Array<Frog>();
        this.holes = new Array<Hole>();
        this.frogGhosts = new Array<FrogGhost>();
    }
}
