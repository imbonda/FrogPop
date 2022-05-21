package com.nitsanmichael.popping_frog_game.data;

/**
 * Created by MichaelBond on 11/8/2016.
 */
public class CachedVariable {

    private Integer intValue;
    private Float floatValue;
    private Boolean booleanValue;


    public CachedVariable() {
        this.intValue = null;
        this.floatValue = null;
        this.booleanValue = null;
    }

    public void set(int value) {
        this.intValue = value;
    }

    public void set(float value) {
        this.floatValue = value;
    }

    public void set(Boolean value) {
        this.booleanValue = value;
    }

    public Integer intValue() {
        return this.intValue;
    }

    public Float floatValue() {
        return this.floatValue;
    }

    public Boolean booleanValue() {
        return this.booleanValue;
    }

}
