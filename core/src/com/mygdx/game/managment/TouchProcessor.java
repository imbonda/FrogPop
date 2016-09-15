package com.mygdx.game.managment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.sprites.frogs.Frog;

/**
 * This class is responsible for all the touch events happening during the game runtime.
 *
 * Created by MichaelBond on 9/10/2016.
 */
public class TouchProcessor implements InputProcessor {

    private Viewport viewport;
    private FrogManager frogManager;
    private Hud hud;


    public TouchProcessor(Viewport viewport) {
        this.viewport = viewport;
        this.frogManager = FrogManager.getInstance();
        this.hud = Hud.getInstance();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 touchVector = new Vector2(screenX,screenY);
        touchVector = this.viewport.unproject(touchVector);

        for (Frog frog: this.frogManager.activeFrogs) {
            if (frog.isFrogTouched(touchVector) && !frog.isLifeTimeExpired()) {
                this.hud.getScoreCounter().addScore(frog.getProfitValue());
                frog.applyAbilityOnTouch();
                frog.setKilled();
                return true;
            }
        }
        Gdx.input.vibrate(500);
        this.hud.getLifeCounter().addLife(-1);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
