package com.mygdx.game.config.metadata;

import java.util.Random;

/**
 * Created by MichaelBond on 9/11/2016.
 */
public class AddFrogMetaData {

    private static final Random random = new Random();

    private int minLevel;
    private int maxLevel;

    public AddFrogMetaData(int atLevel) {
        this.minLevel = atLevel;
        this.maxLevel = atLevel;
    }

    public AddFrogMetaData(int minLevel, int maxLevel) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    public int getLevelToAddFrog() {
        return this.minLevel + random.nextInt(this.maxLevel - this.minLevel + 1);
    }
}
