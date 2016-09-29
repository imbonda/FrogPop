package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Date;

/**
 * This class is responsible for maintaining the game saved data and preferences.
 *
 * Created by MichaelBond on 9/26/2016.
 */
public class DataManager {

    private static final String VALUE_KEY_ID = "t";
    private static final String TIME_KEY_ID = "z";
    private static final String ENC_KEY_ID = "d";
    private static final int INT_VALUE_OFFSET = 18;

    private Preferences prefs;


    public DataManager(String prefName) {
        this.prefs = Gdx.app.getPreferences(prefName);
    }

    /**
     * Stores the given integer value as association to the given key.
     *
     * @param key   The key string to associate the integer value to.
     * @param value The integer value to associate with the given key.
     */
    public void saveInt(String key, int value) {
        saveEncryptedInt(key, value);
    }

    private void saveEncryptedInt(String key, int value) {
        String valueKey = key + VALUE_KEY_ID;
        String timeKey = key + TIME_KEY_ID;
        String encKey = key + ENC_KEY_ID;
        value *= INT_VALUE_OFFSET;
        long time = (new Date()).getTime();
        String encryption = Encryptor.encrypt(value, time);
        this.prefs.putInteger(valueKey, value);
        this.prefs.putLong(timeKey, time);
        this.prefs.putString(encKey, encryption);
        this.prefs.flush();
    }

    /**
     * Returns the integer value association with the given key.
     *
     * @param key   The key string to use in search of an associated integer value.
     * @param defaultValue   A default value to use in case no associated value has been found.
     * @return  The integer value associated with the given key, or the given default-value
     *  in case no association has been found.
     */
    public int getInt(String key, int defaultValue) {
        return readEncryptedInt(key, defaultValue);
    }

    private int readEncryptedInt(String key, int defaultValue) {
        if (!this.prefs.contains(key + VALUE_KEY_ID)) {
            return defaultValue;
        }
        int value = this.prefs.getInteger(key + VALUE_KEY_ID);
        long time = this.prefs.getLong(key + TIME_KEY_ID);
        String encryption = this.prefs.getString(key + ENC_KEY_ID);
        String digest = Encryptor.encrypt(value, time);
        if (digest.equals(encryption)) {
            return value / INT_VALUE_OFFSET;
        }
        return defaultValue;
    }

    /**
     * Stores the given boolean value as association to the given key.
     *
     * @param key   The key string to associate the boolean value to.
     * @param value The boolean value to associate with the given key.
     */
    public void saveBoolean(String key, boolean value) {
        this.prefs.putBoolean(key, value);
        this.prefs.flush();
    }

    /**
     * Returns the boolean value association with the given key.
     *
     * @param key   The key string to use in search of an associated boolean value.
     * @param defaultValue   A default value to use in case no associated value has been found.
     * @return  The boolean value associated with the given key, or the given default-value
     *  in case no association has been found.
     */
    public boolean getBoolean(String key, boolean defaultValue){
        return this.prefs.getBoolean(key, defaultValue);
    }
}
