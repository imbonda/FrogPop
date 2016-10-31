package com.nitsanmichael.popping_frog_game.runtime;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by MichaelBond on 11/1/2016.
 */
public class ScreenInfo {

    private Viewport viewport;
    private Vector2 bottomLeft;
    private Vector2 topRight;


    public ScreenInfo(Viewport viewport) {
        this.viewport = viewport;
        this.bottomLeft = new Vector2();
        this.topRight = new Vector2();
    }

    public void resize(int width, int height) {
        this.bottomLeft.set(0, height);
        this.bottomLeft.set(this.viewport.unproject(this.bottomLeft));
        this.topRight.set(width, 0);
        this.topRight.set(this.viewport.unproject(this.topRight));
    }

    public Vector2 getScreenBottomLeft() {
        return this.bottomLeft;
    }

    public Vector2 getScreenTopRight() {
        return this.topRight;
    }

}
