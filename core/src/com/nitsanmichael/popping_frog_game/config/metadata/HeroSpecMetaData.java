package com.nitsanmichael.popping_frog_game.config.metadata;


import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by MichaelBond on 11/6/2016.
 */
public class HeroSpecMetaData {

    public Class<? extends Actor> heroClass;
    public int animationIndex;
    public int requiredLevel;
    public String name;
    public String description;


    public HeroSpecMetaData(Class<? extends Actor> heroClass, int animationIndex,
                                Integer requiredLevel, String name, String description) {
        this.heroClass = heroClass;
        this.animationIndex = animationIndex;
        if (null == requiredLevel) {
            this.requiredLevel = 0;
        }
        else {
            this.requiredLevel = requiredLevel;
        }
        this.name = name;
        this.description = description;
    }

}
