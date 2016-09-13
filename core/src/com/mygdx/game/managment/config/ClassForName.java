package com.mygdx.game.managment.config;

import com.mygdx.game.sprites.frogs.Frog;
import com.mygdx.game.sprites.frogs.RedFrog;
import com.mygdx.game.sprites.frogs.RegularFrog;
import com.mygdx.game.sprites.frogs.YellowFrog;

/**
 * This class is used to translate a given class-name to it's corresponding class-object.
 *
 * Created by MichaelBond on 9/10/2016.
 */
public class ClassForName {
    // Frog's classes names.
    private static final String REGULAR_FROG = RegularFrog.class.getCanonicalName();
    private static final String RED_FROG = RedFrog.class.getCanonicalName();
    private static final String YELLOW_FROG = YellowFrog.class.getCanonicalName();


    /**
     * Translates a class-name to it's corresponding object.
     *
     * @param classNme  The name of tha class we wish to get back.
     * @return  The class object with the given class-name.
     * @throws ClassNotFoundException   In case no class was found for the given class-name.
     */
    public static Class<? extends Frog> getFrogClassByName(String classNme)
            throws ClassNotFoundException{

        if (REGULAR_FROG.equals(classNme)) {
            return RegularFrog.class;
        }
        else if (RED_FROG.equals(classNme)) {
            return RedFrog.class;
        }
        else if (YELLOW_FROG.equals(classNme)) {
            return YellowFrog.class;
        }

        throw new ClassNotFoundException("Could not find a frog class with the given name: " + classNme);
    }
}
