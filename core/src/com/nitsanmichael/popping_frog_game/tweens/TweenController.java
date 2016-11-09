package com.nitsanmichael.popping_frog_game.tweens;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.nitsanmichael.popping_frog_game.scenes.panel.Timer;
import com.nitsanmichael.popping_frog_game.screens.FadingScreen;
import com.nitsanmichael.popping_frog_game.sprites.FrogGhost;
import com.nitsanmichael.popping_frog_game.sprites.frogs.Frog;
import com.nitsanmichael.popping_frog_game.tweens.accessors.ActorAccessor;
import com.nitsanmichael.popping_frog_game.tweens.accessors.FadingScreenAccessor;
import com.nitsanmichael.popping_frog_game.tweens.accessors.FrogAccessor;
import com.nitsanmichael.popping_frog_game.tweens.accessors.FrogGhostAccessor;
import com.nitsanmichael.popping_frog_game.tweens.accessors.LabelAccessor;
import com.nitsanmichael.popping_frog_game.tweens.accessors.TimerAccessor;

import aurelienribon.tweenengine.Timeline;
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
        Tween.registerAccessor(Actor.class, new ActorAccessor());
        Tween.registerAccessor(Label.class, new LabelAccessor());
        Tween.registerAccessor(Frog.class, new FrogAccessor());
        Tween.registerAccessor(FrogGhost.class, new FrogGhostAccessor());
        Tween.registerAccessor(Timer.class, new TimerAccessor());
    }

    public void update(float deltaTime) {
        this.manager.update(deltaTime);
    }

    public void fadeInScreen(FadingScreen screen, float duration, int trigger,
                                TweenCallback callback) {
        Tween.set(screen, FadingScreenAccessor.ALPHA_TYPE).target(0).start(this.manager);
        Tween tween = Tween.to(screen, FadingScreenAccessor.ALPHA_TYPE, duration).target(1);
        if (null != callback) {
            tween = tween.setCallbackTriggers(trigger).setCallback(callback);
        }
        tween.start(this.manager);
    }

    public void fadeOutScreen(FadingScreen screen, float duration, int trigger,
                                TweenCallback callback) {
        Tween.set(screen, FadingScreenAccessor.ALPHA_TYPE).target(1).start(this.manager);
        Tween tween = Tween.to(screen, FadingScreenAccessor.ALPHA_TYPE, duration).target(0);
        if (null != callback) {
            tween = tween.setCallbackTriggers(trigger).setCallback(callback);
        }
        tween.start(this.manager);
    }

    public void actorFade(Actor actor, float duration, float origin, float dest,
                            int yoyoTimes, float yoyoDelay, TweenCallback callback) {
        Tween.set(actor, LabelAccessor.ALPHA_TYPE).target(origin).start(this.manager);
        Tween tween = Tween.to(actor, LabelAccessor.ALPHA_TYPE, duration).target(dest).
                    repeatYoyo(yoyoTimes, yoyoDelay);
        if (null != callback) {
            tween = tween.setCallback(callback);
        }
        tween.start(this.manager);
    }

    public void labelFontScale(Label label, Vector2 srcScale, Vector2 dstScale, float duration,
                                        int yoyoTimes, float yoyoDelay, TweenCallback callback) {
        Tween.set(label, LabelAccessor.SCALE_TYPE).target(srcScale.x, srcScale.y).start(this.manager);
        Tween tween = Tween.to(label, LabelAccessor.SCALE_TYPE, duration).
                    target(dstScale.x, dstScale.y);
        if (null != callback) {
            tween = tween.setCallback(callback).repeatYoyo(yoyoTimes, yoyoDelay);
        }
        tween.repeatYoyo(yoyoTimes, yoyoDelay).start(this.manager);
    }

    public void frogsGameOverAnimation(Array<Frog> frogs, float duration, TweenCallback callback) {
        if (null == frogs || frogs.size == 0) {
            return;
        }
        Timeline timeline = Timeline.createSequence();
        for (Frog frog : frogs) {
            timeline = timeline.push(Tween.set(frog, FrogAccessor.ALPHA_TYPE).target(1));
        }
        timeline = timeline.beginParallel();
        for (Frog frog : frogs) {
            timeline = timeline.push(Tween.to(frog, FrogAccessor.ALPHA_TYPE, duration).target(0));
        }
        timeline = timeline.end();
        if (null != callback) {
            timeline = timeline.setCallback(callback);
        }
        timeline.start(this.manager);
    }

    public void frogsGhostGameOverAnimation(Array<FrogGhost> ghosts, float duration, TweenCallback callback) {
        if (null == ghosts || ghosts.size == 0) {
            return;
        }
        Timeline timeline = Timeline.createSequence();
        for (FrogGhost ghost : ghosts) {
            timeline = timeline.push(Tween.set(ghost, FrogGhostAccessor.ALPHA_TYPE).target(ghost.getColor().a));
        }
        timeline = timeline.beginParallel();
        for (FrogGhost ghost : ghosts) {
            timeline = timeline.push(Tween.to(ghost, FrogGhostAccessor.ALPHA_TYPE, duration).target(0));
        }
        timeline = timeline.end();
        if (null != callback) {
            timeline = timeline.setCallback(callback);
        }
        timeline.start(this.manager);
    }

    public void timerResetAnimation(Timer timer, float duration, TweenCallback callback) {
        Tween.set(timer, TimerAccessor.TIME_LEFT_TYPE).target(timer.getTimeLeft()).start(this.manager);
        Tween tween = Tween.to(timer, TimerAccessor.TIME_LEFT_TYPE, duration).target(timer.getCountdownTime());
        if (null != callback) {
            tween = tween.setCallback(callback);
        }
        tween.start(this.manager);
    }
}
