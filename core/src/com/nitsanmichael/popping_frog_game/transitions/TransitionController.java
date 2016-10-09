package com.nitsanmichael.popping_frog_game.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.transitions.accessors.BatchAccessor;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by MichaelBond on 10/9/2016.
 */
public class TransitionController {

    public interface Callback {
        void call();
    }

    private static final Color DEFAULT_CLEAN_COLOR = Color.BLACK;

    private TweenManager manager;


    public TransitionController() {
        this.manager = new TweenManager();
        registerAccessors();
    }

    private void registerAccessors() {
        Tween.registerAccessor(SpriteBatch.class, new BatchAccessor());
    }

    public void screenTransition(PoppingFrog game, TweenCallback callback) {
        screenTransition(game, callback, DEFAULT_CLEAN_COLOR);
    }

    public void screenTransition(PoppingFrog game, TweenCallback callback, Color cleanColor) {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        Gdx.gl.glClearColor(cleanColor.r, cleanColor.g, cleanColor.b, cleanColor.a);
        Tween.set(game.batch, BatchAccessor.ALPHA_TYPE).target(0).start(this.manager);
        if (null != callback) {
            Tween.to(game.batch, BatchAccessor.ALPHA_TYPE, 2).target(1).repeatYoyo(1, 2).
                    setCallback(callback).start(this.manager);
        }
        else {
            Tween.to(game.batch, BatchAccessor.ALPHA_TYPE, 2).target(1).repeatYoyo(1, 2).
                    start(this.manager);
        }
        // todo
    }
}
