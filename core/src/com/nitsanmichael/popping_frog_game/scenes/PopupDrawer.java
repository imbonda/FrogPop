package com.nitsanmichael.popping_frog_game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

/**
 * Created by MichaelBond on 10/9/2016.
 */
public class PopupDrawer {

    public enum Popup { LEVEL_UP }

    private interface PopupPerformer {
        void draw(Batch batch);
    }

    private class LevelUpPopup implements PopupPerformer {

        private BitmapFont levelUpPopup;

        public LevelUpPopup() {
            this.levelUpPopup = new BitmapFont(Gdx.files.internal("font.fnt"));
            this.levelUpPopup.getData().setScale(0.3f);
            this.levelUpPopup.setColor(Color.FIREBRICK);
        }

        @Override
        public void draw(Batch batch) {
            this.levelUpPopup.draw(batch, "Level up ! ",320,440);
        }
    }

    private LevelUpPopup levelUpPopup;
    private HashMap<Popup, PopupPerformer> popupToPerformerMap;
    private Array<Popup> registeredPopups;


    public PopupDrawer() {
        this.levelUpPopup = new LevelUpPopup();
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
        }
    }

    public void unregister(Popup popup) {
        this.registeredPopups.removeValue(popup, true);
    }

    public void drawPopups(Batch batch) {
        for (Popup popup : this.registeredPopups) {
            this.popupToPerformerMap.get(popup).draw(batch);
        }
    }
}
