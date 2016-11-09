package com.nitsanmichael.popping_frog_game.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleBritishFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleMexicanFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleRegularFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleTurkishFrog;
import com.nitsanmichael.popping_frog_game.themes.AutumnTheme;
import com.nitsanmichael.popping_frog_game.themes.SpringTheme;
import com.nitsanmichael.popping_frog_game.themes.Theme;
import com.nitsanmichael.popping_frog_game.themes.WinterTheme;
import com.nitsanmichael.popping_frog_game.sprites.frogs.FreezeFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.Frog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.IllusionFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.HealthFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.RegularFrog;
import com.nitsanmichael.popping_frog_game.sprites.frogs.EvilFrog;

/**
 * This class is used to translate a given class-name to it's corresponding class-object.
 *
 * Created by MichaelBond on 9/10/2016.
 */
public class ClassForName {

    // Frog's class names.
    private static final String REGULAR_FROG = RegularFrog.class.getCanonicalName();
    private static final String HEALTH_FROG = HealthFrog.class.getCanonicalName();
    private static final String EVIL_FROG = EvilFrog.class.getCanonicalName();
    private static final String FREEZE_FROG = FreezeFrog.class.getCanonicalName();
    private static final String ILLUSION_FROG = IllusionFrog.class.getCanonicalName();
    // Heroes' class names.
    private static final String REGULAR_HERO = IdleRegularFrog.class.getCanonicalName();
    private static final String BRITISH_HERO = IdleBritishFrog.class.getCanonicalName();
    private static final String MEXICAN_HERO = IdleMexicanFrog.class.getCanonicalName();
    private static final String TURKISH_HERO = IdleTurkishFrog.class.getCanonicalName();
    // Theme's class names.
    private static final String SPRING_THEME = SpringTheme.class.getCanonicalName();
    private static final String AUTUMN_THEME = AutumnTheme.class.getCanonicalName();
    private static final String SUMMER_THEME = com.nitsanmichael.popping_frog_game.themes.SummerTheme.class.getCanonicalName();
    private static final String WINTER_THEME = WinterTheme.class.getCanonicalName();


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
        else if (EVIL_FROG.equals(classNme)) {
            return EvilFrog.class;
        }
        else if (FREEZE_FROG.equals(classNme)) {
            return FreezeFrog.class;
        }
        else if (ILLUSION_FROG.equals(classNme)) {
            return IllusionFrog.class;
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
    public static Class<? extends Actor> getHeroClassByName(String classNme)
            throws ClassNotFoundException {
        if (REGULAR_HERO.equals(classNme)) {
            return IdleRegularFrog.class;
        }
        else if (BRITISH_HERO.equals(classNme)) {
            return IdleBritishFrog.class;
        }
        else if (MEXICAN_HERO.equals(classNme)) {
            return IdleMexicanFrog.class;
        }
        else if (TURKISH_HERO.equals(classNme)) {
            return IdleTurkishFrog.class;
        }
        throw new ClassNotFoundException("Could not find a hero class with the given name: " +
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
        if (SPRING_THEME.equals(classNme)) {
            return SpringTheme.class;
        }
        else if (AUTUMN_THEME.equals(classNme)) {
            return AutumnTheme.class;
        }
        else if (SUMMER_THEME.equals(classNme)) {
            return com.nitsanmichael.popping_frog_game.themes.SummerTheme.class;
        }
        else if (WINTER_THEME.equals(classNme)) {
            return WinterTheme.class;
        }
        throw new ClassNotFoundException("Could not find a theme class with the given name: " +
                    classNme);
    }

}
