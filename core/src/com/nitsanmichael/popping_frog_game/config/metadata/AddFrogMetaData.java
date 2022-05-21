package com.nitsanmichael.popping_frog_game.config.metadata;

import java.util.Random;

/**
 * Created by MichaelBond on 9/11/2016.
 */
public class AddFrogMetaData {

    private int minLevel;
    private int maxLevel;
    private Random random;

    public AddFrogMetaData(int atLevel) {
        this.minLevel = atLevel;
        this.maxLevel = atLevel;
        this.random = new Random();
    }

    public AddFrogMetaData(int minLevel, int maxLevel) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.random = new Random();
    }

    public int getLevelToAddFrog() {
        return this.minLevel + random.nextInt(this.maxLevel - this.minLevel + 1);
    }
}
