package com.nitsanmichael.popping_frog_game.managment;

import com.badlogic.gdx.math.Vector2;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.sprites.Hole;
import com.nitsanmichael.popping_frog_game.sprites.SpritesDrawer;


/**
 * Created by MichaelBond on 10/11/2016.
 */
public class HolesManager {

    private static class HoleConfig {

        public Vector2 origin;
        public Vector2 boxBottomLeft;
        public Vector2 boxTopRight;

        public HoleConfig(Vector2 origin, Vector2 boxBottomLeft, Vector2 boxTopRight) {
            this.origin = origin;
            this.boxBottomLeft = boxBottomLeft;
            this.boxTopRight = boxTopRight;
        }
    }

    private static final HoleConfig HOLE_CONFIGS [] = {
            new HoleConfig(new Vector2(50, 10), new Vector2(5, 5), new Vector2(295, 125)),
            new HoleConfig(new Vector2(300, 10), new Vector2(295, 5), new Vector2(545, 125)),
            new HoleConfig(new Vector2(550, 10), new Vector2(545, 5), new Vector2(795, 125)),
            new HoleConfig(new Vector2(50, 130), new Vector2(5, 125), new Vector2(295, 245)),
            new HoleConfig(new Vector2(300, 130), new Vector2(295, 125), new Vector2(545, 245)),
            new HoleConfig(new Vector2(550, 130), new Vector2(545, 125), new Vector2(795, 245)),
            new HoleConfig(new Vector2(50, 250), new Vector2(5, 245), new Vector2(295, 350)),
            new HoleConfig(new Vector2(300, 250), new Vector2(295, 245), new Vector2(545, 350)),
            new HoleConfig(new Vector2(550, 250), new Vector2(545, 245), new Vector2(795, 350))
    };

    private RuntimeInfo runtimeInfo;


    public HolesManager(AssetController assetController, SpritesDrawer spritesDrawer,
                            RuntimeInfo runtimeInfo) {
        this.runtimeInfo = runtimeInfo;
        for (HoleConfig config : HOLE_CONFIGS) {
            Hole hole = new Hole(assetController, config.origin.x, config.origin.y);
            hole.setBox(config.boxBottomLeft, config.boxTopRight);
            this.runtimeInfo.holes.add(hole);
        }
        spritesDrawer.addSprites(this.runtimeInfo.holes);
    }

    public void update(float deltaTime) {
        for (Hole hole : this.runtimeInfo.holes) {
            hole.update(deltaTime);
        }
    }

    public void reset() {
        for (Hole hole : this.runtimeInfo.holes) {
            hole.shuffleOff(true);
        }
    }
}
