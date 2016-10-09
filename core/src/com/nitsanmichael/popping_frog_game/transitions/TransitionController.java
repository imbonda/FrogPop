package com.nitsanmichael.popping_frog_game.transitions;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.nitsanmichael.popping_frog_game.screens.FadingScreen;
import com.nitsanmichael.popping_frog_game.transitions.accessors.FadingScreenAccessor;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by MichaelBond on 10/9/2016.
 */
public class TransitionController {

    private TweenManager manager;


    public TransitionController() {
        this.manager = new TweenManager();
        registerAccessors();
    }

    private void registerAccessors() {
        Tween.registerAccessor(FadingScreen.class, new FadingScreenAccessor());
    }

    public void update(float deltaTime) {
        this.manager.update(deltaTime);
    }

    public void fadeInScreen(FadingScreen screen, float duration, TweenCallback callback) {
        Tween.set(screen, FadingScreenAccessor.ALPHA_TYPE).target(0).start(this.manager);
        if (null != callback) {
            Tween.to(screen, FadingScreenAccessor.ALPHA_TYPE, duration).target(1).
                        setCallback(callback).start(this.manager);
        }
        else {
            Tween.to(screen, FadingScreenAccessor.ALPHA_TYPE, duration).target(1).start(this.manager);
        }
    }

    public void fadeOutScreen(FadingScreen screen, float duration, TweenCallback callback) {
        Tween.set(screen, FadingScreenAccessor.ALPHA_TYPE).target(1).start(this.manager);
        if (null != callback) {
            Tween.to(screen, FadingScreenAccessor.ALPHA_TYPE, duration).target(0).
                    setCallback(callback).start(this.manager);
        }
        else {
            Tween.to(screen, FadingScreenAccessor.ALPHA_TYPE, duration).target(0).start(this.manager);
        }
    }
}
