package com.mygdx.game.managment;

import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by MichaelBond on 9/27/2016.
 */
public class AssetController {

    private static AssetController ourInstance = new AssetController();

    /**
     * Singleton implementation.
     *
     * @return  The singleton object.
     */
    public static AssetController getInstance() {
        return ourInstance;
    }

    /**
     * Singleton private constructor.
     */
    private AssetController() {
        this.manager = new AssetManager();
    }

    private AssetManager manager;


    public void loadAll() {
        //..
    }

    public void unload(String fileName) {
        this.manager.unload(fileName);
    }

    public void clearAll() {
        this.manager.clear();
    }
}
