package com.nitsanmichael.popping_frog_game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;

import java.util.HashMap;

/**
 * Created by MichaelBond on 10/9/2016.
 */
public class PopupDrawer {

    public enum Popup { LEVEL_UP }

    private interface PopupPerformer {

        void addToStage();

        void removeFromStage();
    }

    private class LevelUpPopup implements PopupPerformer {

        private Label levelUpLabel;

        public LevelUpPopup(BitmapFont font) {
            Label.LabelStyle style = new Label.LabelStyle();
            style.font = font;
            style.fontColor = Color.FIREBRICK;
            this.levelUpLabel = new Label("Level up ! ", style);
            this.levelUpLabel.setFontScale(0.3f);
            this.levelUpLabel.setPosition(320, 440);
        }

        @Override
        public void addToStage() {
            stage.addActor(this.levelUpLabel);
        }

        @Override
        public void removeFromStage() {
            this.levelUpLabel.remove();
        }
    }

    private LevelUpPopup levelUpPopup;
    private HashMap<Popup, PopupPerformer> popupToPerformerMap;
    private Array<Popup> registeredPopups;
    private Stage stage;


    public PopupDrawer(Viewport viewport, Batch batch, AssetController assetController) {
        this.stage = new Stage(viewport, batch);
        BitmapFont font = assetController.get(Assets.GAME_FONT);
        this.levelUpPopup = new LevelUpPopup(font);
        initPopupsToPerformersMap();
        this.registeredPopups = new Array<Popup>();
    }

    private void initPopupsToPerformersMap() {
        this.popupToPerformerMap = new HashMap<Popup, PopupPerformer>();
        this.popupToPerformerMap.put(Popup.LEVEL_UP, this.levelUpPopup);
    }

    public void register(Popup popup) {
        if (!this.registeredPopups.contains(Popup.LEVEL_UP, true)) {
            this.registeredPopups.add(popup);
            this.popupToPerformerMap.get(popup).addToStage();
        }
    }

    public void unregister(Popup popup) {
        if (this.registeredPopups.contains(Popup.LEVEL_UP, true)) {
            this.registeredPopups.removeValue(popup, true);
            this.popupToPerformerMap.get(popup).removeFromStage();
        }
    }

    public void drawPopups() {
        stage.draw();
    }
}
