package com.mygdx.game.managment.config;

import com.mygdx.game.themes.AllTheme;
import com.mygdx.game.themes.BlueTheme;
import com.mygdx.game.themes.DefaultTheme;
import com.mygdx.game.themes.PinkTheme;
import com.mygdx.game.themes.Theme;
import com.mygdx.game.themes.YellowTheme;
import com.mygdx.game.sprites.frogs.FreezeFrog;
import com.mygdx.game.sprites.frogs.ColorfullFrog;
import com.mygdx.game.sprites.frogs.Frog;
import com.mygdx.game.sprites.frogs.IllusionFrog;
import com.mygdx.game.sprites.frogs.HealthFrog;
import com.mygdx.game.sprites.frogs.RegularFrog;
import com.mygdx.game.sprites.frogs.PoisonFrog;

/**
 * This class is used to translate a given class-name to it's corresponding class-object.
 *
 * Created by MichaelBond on 9/10/2016.
 */
public class ClassForName {

    // Frog's class names.
    private static final String REGULAR_FROG = RegularFrog.class.getCanonicalName();
    private static final String HEALTH_FROG = HealthFrog.class.getCanonicalName();
    private static final String POISON_FROG = PoisonFrog.class.getCanonicalName();
    private static final String FREEZE_FROG = FreezeFrog.class.getCanonicalName();
    private static final String ILLUSION_FROG = IllusionFrog.class.getCanonicalName();
    private static final String COLORFUL_FROG = ColorfullFrog.class.getCanonicalName();
    // Theme's class names.
    private static final String DEFAULT_THEME = DefaultTheme.class.getCanonicalName();
    private static final String BLUE_THEME = BlueTheme.class.getCanonicalName();
    private static final String YELLOW_THEME = YellowTheme.class.getCanonicalName();
    private static final String PINK_THEME = PinkTheme.class.getCanonicalName();
    private static final String All_THEME = AllTheme.class.getCanonicalName();


    /**
     * Translates a class-name to it's corresponding object.
     *
     * @param classNme  The name of tha class we wish to get back.
     * @return  The class object with the given class-name.
     * @throws ClassNotFoundException   In case no class was found for the given class-name.
     */
    public static Class<? extends Frog> getFrogClassByName(String classNme)
                throws ClassNotFoundException {
        if (REGULAR_FROG.equals(classNme)) {
            return RegularFrog.class;
        }
        else if (HEALTH_FROG.equals(classNme)) {
            return HealthFrog.class;
        }
        else if (POISON_FROG.equals(classNme)) {
            return PoisonFrog.class;
        }
        else if (FREEZE_FROG.equals(classNme)) {
            return FreezeFrog.class;
        }
        else if (ILLUSION_FROG.equals(classNme)) {
            return IllusionFrog.class;
        }
        else if (COLORFUL_FROG.equals(classNme)) {
            return ColorfullFrog.class;
        }
        throw new ClassNotFoundException("Could not find a frog class with the given name: " +
                    classNme);
    }

    /**
     * Translates a class-name to it's corresponding object.
     *
     * @param classNme  The name of tha class we wish to get back.
     * @return  The class object with the given class-name.
     * @throws ClassNotFoundException   In case no class was found for the given class-name.
     */
    public static Class<? extends Theme> getThemeClassByName(String classNme)
            throws ClassNotFoundException {
        if (DEFAULT_THEME.equals(classNme)) {
            return DefaultTheme.class;
        }
        else if (BLUE_THEME.equals(classNme)) {
            return BlueTheme.class;
        }
        else if (YELLOW_THEME.equals(classNme)) {
            return YellowTheme.class;
        }
        else if (PINK_THEME.equals(classNme)) {
            return PinkTheme.class;
        }
        else if (All_THEME.equals(classNme)) {
            return AllTheme.class;
        }
        throw new ClassNotFoundException("Could not find a theme class with the given name: " +
                    classNme);
    }

}
