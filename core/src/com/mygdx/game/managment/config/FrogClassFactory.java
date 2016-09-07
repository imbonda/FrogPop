package com.mygdx.game.managment.config;

import com.mygdx.game.sprites.frogs.Frog;
import com.mygdx.game.sprites.frogs.RegularFrog;

/**
 * Created by MichaelBond on 9/8/2016.
 */
public class FrogClassFactory {

    // Frog's classes names.
    private static final String REGULAR_FROG = RegularFrog.class.getCanonicalName();


    public static Class<? extends Frog> getFrogClassByName(String classNme)
                throws ClassNotFoundException{
        if (REGULAR_FROG.equals(classNme)) {
            return Frog.class;
        }

        throw new ClassNotFoundException("Could not find a frog class with the given name: " + classNme);
    }
}
