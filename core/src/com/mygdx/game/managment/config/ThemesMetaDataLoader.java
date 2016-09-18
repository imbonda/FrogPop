package com.mygdx.game.managment.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.game.managment.metadata.ThemeMetaData;
import com.mygdx.game.managment.themes.Theme;

import java.io.IOException;

/**
 * This class is responsible for loading the themes configuration from the corresponding -
 * configuration file.
 * A theme has it's own background and music, as well of the frog's dressing-code.
 *
 * Created by MichaelBond on 9/18/2016.
 */
public class ThemesMetaDataLoader {

    public static void load(XmlReader xmlReader, Array<ThemeMetaData> themesMetaData) {
        Array<XmlReader.Element> themeElements = null;
        try {
            XmlReader.Element root = xmlReader.parse(Gdx.files.internal(
                        "config/level_themes.xml"));
            themeElements = root.getChildrenByName("theme");
        }
        catch (IOException e) {
            e.printStackTrace();
            // TODO (handle exception properly).
        }
        try {
            if (null != themeElements) {
                for (XmlReader.Element theme : themeElements) {
                    themesMetaData.add(createThemeMetaData(theme));
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            // TODO (handle exception properly).
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
            // TODO (handle exception properly).
        }
    }

    /**
     * This loads data from an xml-element object into its Java's representation object.
     *
     * @param themeElement  The xml-element to read from.
     * @return  A ThemeMetaData object that holds the xml-element object's data.
     * @throws ClassNotFoundException   In case no class was found for one of the -
     *  frogs used in that level.
     * @throws IllegalStateException    In case of one or more malformed attributes -
     *  inside this element or one of it's children.
     */
    private static ThemeMetaData createThemeMetaData(XmlReader.Element themeElement)
                throws ClassNotFoundException, IllegalStateException {
        try {
            String className = themeElement.getAttribute("class");
            String levelString = themeElement.getAttribute("starting_level");
            int startingLevel = Integer.parseInt(levelString);
            return new ThemeMetaData(getThemeClassByName(className), startingLevel);
        }
        catch (GdxRuntimeException e) {
            throw new IllegalStateException("A 'theme' element does not contain " +
                        "one ore more crucial attributes.");
        }
        catch (NumberFormatException e) {
            throw new IllegalStateException("Attribute is of the wrong type.\n" +
                        e.getMessage());
        }
    }

    private static Class<? extends Theme> getThemeClassByName(String className)
            throws ClassNotFoundException{
        return ClassForName.getThemeClassByName(className);
    }

}
