package com.nitsanmichael.frog_pop_game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.nitsanmichael.frog_pop_game.assets.AssetController;
import com.nitsanmichael.frog_pop_game.assets.Assets;

/**
 * Created by MichaelBond on 8/25/2016.
 */
public class Hole extends Sprite {

    private Texture holeTexture;
    private Vector2 position;

    public Hole(AssetController assetController, float xCord, float yCord) {
        this.holeTexture = assetController.get(Assets.HOLE);
        this.position = new Vector2(xCord, yCord);
    }

    public Vector2 getPosition() {
        return position;
    }

    /**
     * This method is responsible for drawing the hole properly.
     */
    @Override
    public void draw(Batch batch) {
        batch.draw(this.holeTexture, this.position.x, this.position.y);
    }

}
