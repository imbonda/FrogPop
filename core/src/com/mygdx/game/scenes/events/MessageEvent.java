package com.mygdx.game.scenes.events;

import com.badlogic.gdx.scenes.scene2d.Event;

/**
 * Created by MichaelBond on 9/30/2016.
 */
public class MessageEvent extends Event {

    private int message;
    public int emptyMessageValue;

    public MessageEvent(int message, int emptyMessageValue) {
        this.message = message;
        this.emptyMessageValue = emptyMessageValue;
        // None bubbling event.
        this.setBubbles(false);
    }

    public int getMessage() {
        return this.message;
    }

    @Override
    public void reset() {
        super.reset();
        this.message = this.emptyMessageValue;
    }
}
