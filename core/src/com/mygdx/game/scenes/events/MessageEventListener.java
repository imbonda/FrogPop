package com.mygdx.game.scenes.events;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

/**
 * Created by MichaelBond on 9/30/2016.
 */
public abstract class MessageEventListener implements EventListener{

    @Override
    public boolean handle(Event event) {
        if (event instanceof MessageEvent) {
            this.receivedMessage(((MessageEvent)event).getMessage(), event.getTarget());
        }
        return false;
    }

    public abstract void receivedMessage(int message, Actor actor);
}
