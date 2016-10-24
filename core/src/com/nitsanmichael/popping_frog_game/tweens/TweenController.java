package com.nitsanmichael.popping_frog_game.tweens;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.nitsanmichael.popping_frog_game.screens.FadingScreen;
import com.nitsanmichael.popping_frog_game.tweens.accessors.FadingScreenAccessor;
import com.nitsanmichael.popping_frog_game.tweens.accessors.LabelAccessor;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by MichaelBond on 10/9/2016.
 */
public class TweenController {

    private TweenManager manager;


    public TweenController() {
        this.manager = new TweenManager();
        registerAccessors();
    }

    private void registerAccessors() {
        Tween.registerAccessor(FadingScreen.class, new FadingScreenAccessor());
        Tween.registerAccessor(Label.class, new LabelAccessor());
    }

    public void update(float deltaTime) {
        this.manager.update(deltaTime);
    }

    public void fadeInScreen(FadingScreen screen, float duration, int trigger,
                                TweenCallback callback) {
        Tween.set(screen, FadingScreenAccessor.ALPHA_TYPE).target(0).start(this.manager);
        if (null != callback) {
            Tween.to(screen, FadingScreenAccessor.ALPHA_TYPE, duration).target(1).
                        setCallbackTriggers(trigger).setCallback(callback).start(this.manager);
        }
        else {
            Tween.to(screen, FadingScreenAccessor.ALPHA_TYPE, duration).target(1).start(this.manager);
        }
    }

    public void fadeOutScreen(FadingScreen screen, float duration, int trigger,
                                TweenCallback callback) {
        Tween.set(screen, FadingScreenAccessor.ALPHA_TYPE).target(1).start(this.manager);
        if (null != callback) {
            Tween.to(screen, FadingScreenAccessor.ALPHA_TYPE, duration).target(0).
                    setCallbackTriggers(trigger).setCallback(callback).start(this.manager);
        }
        else {
            Tween.to(screen, FadingScreenAccessor.ALPHA_TYPE, duration).target(0).start(this.manager);
        }
    }

    public void popupLabelFade(Label label, float duration, float yoyoDelay, TweenCallback callback) {
        Tween.set(label, LabelAccessor.ALPHA_TYPE).target(0).start(this.manager);
        if (null != callback) {
            Tween.to(label, LabelAccessor.ALPHA_TYPE, duration).target(1).repeatYoyo(1, yoyoDelay).
                        setCallback(callback).start(this.manager);
        }
        else {
            Tween.to(label, LabelAccessor.ALPHA_TYPE, duration).target(1).repeatYoyo(1, yoyoDelay).
                        start(this.manager);
        }
    }

    public void popupLabelFontScale(Label label, Vector2 srcScale, Vector2 dstScale, float duration,
                                        int yoyoTimes, float yoyoDelay, TweenCallback callback) {
        Tween.set(label, LabelAccessor.SCALE_TYPE).target(srcScale.x, srcScale.y).start(this.manager);
        if (null != callback) {
            Tween.to(label, LabelAccessor.SCALE_TYPE, duration).target(dstScale.x, dstScale.y).
                        setCallback(callback).repeatYoyo(yoyoTimes, yoyoDelay).start(this.manager);
        }
        else {
            Tween.to(label, LabelAccessor.SCALE_TYPE, duration).target(dstScale.x, dstScale.y).
                        repeatYoyo(yoyoTimes, yoyoDelay).start(this.manager);
        }
    }
}
