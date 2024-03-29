package com.nitsanmichael.popping_frog_game.states;

import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.scenes.PopupDrawer;
import com.nitsanmichael.popping_frog_game.screens.PlayScreen;


/**
 * Created by MichaelBond on 10/23/2016.
 */
public class CountdownState implements State {

    private static final float TIMER_RESET_ANIMATION_DURATION = 2.5f;

    private PlayScreen playScreen;


    public CountdownState(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.playScreen.popupDrawer.register(PopupDrawer.PopupType.COUNTDOWN);
        playScreen.levelController.reset();
        playScreen.holesManager.reset();
        playScreen.game.tweenController.timerResetAnimation(playScreen.timer,
                    TIMER_RESET_ANIMATION_DURATION, null);
        // Play music.
        this.playScreen.game.media.stopSound(Assets.GAME_OVER_SOUND);
        playScreen.game.media.playMusic(Assets.GAME_PLAY_MUSIC);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        this.playScreen.draw();
    }

    private void update(float deltaTime) {
        this.playScreen.themeController.update(deltaTime, playScreen.runtimeInfo.gameLevel);
        this.playScreen.holesManager.update(deltaTime);
        this.playScreen.hud.update();
    }

    @Override
    public void dispose() {
        // Nothing to dispose.
    }
}
