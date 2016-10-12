package com.nitsanmichael.popping_frog_game.scenes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.scenes.popups.CountdownPopup;
import com.nitsanmichael.popping_frog_game.scenes.popups.LevelUpPopup;
import com.nitsanmichael.popping_frog_game.tweens.TweenController;


/**
 * Created by MichaelBond on 10/9/2016.
 */
public class PopupDrawer {

    public enum PopupType { LEVEL_UP, COUNTDOWN }

    private LevelUpPopup levelUpPopup;
    private CountdownPopup countdownPopup;
    private Stage stage;


    public PopupDrawer(Viewport viewport, Batch batch, AssetController assetController,
                        TweenController tweenController, RuntimeInfo runtimeInfo) {
        this.stage = new Stage(viewport, batch);
        BitmapFont font = assetController.get(Assets.GAME_FONT);
        this.levelUpPopup = new LevelUpPopup(this.stage, tweenController, font);
        this.countdownPopup = new CountdownPopup(tweenController, runtimeInfo, this.stage, font);
    }

    public void register(PopupType popup) {
        switch (popup) {
            case LEVEL_UP:
                this.levelUpPopup.perform();
                break;
            case COUNTDOWN:
                this.countdownPopup.perform();
                break;
            default:
                break;
        }
    }

    public void drawPopups() {
        stage.draw();
    }
}
