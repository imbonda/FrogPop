package com.nitsanmichael.popping_frog_game.config.metadata;


/**
 * Created by MichaelBond on 11/6/2016.
 */
public class HeroSpecMetaData {

    public Class<?> heroClass;
    public int animationIndex;
    public int requiredLevel;


    public HeroSpecMetaData(Class<?> heroClass, int animationIndex, Integer requiredLevel) {
        this.heroClass = heroClass;
        this.animationIndex = animationIndex;
        if (null == requiredLevel) {
            this.requiredLevel = 0;
        }
        else {
            this.requiredLevel = requiredLevel;
        }
    }

}
