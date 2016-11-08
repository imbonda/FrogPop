package com.nitsanmichael.popping_frog_game.data;

/**
 * Created by MichaelBond on 11/8/2016.
 */
public class CachedVariable {

    private Integer intValue;
    private Float floatValue;


    public CachedVariable() {
        this.intValue = null;
        this.floatValue = null;
    }

    public void set(int value) {
        this.intValue = value;
    }

    public void set(float value) {
        this.floatValue = value;
    }

    public Integer intValue() {
        return this.intValue;
    }

    public Float floatValue() {
        return this.floatValue;
    }

}
