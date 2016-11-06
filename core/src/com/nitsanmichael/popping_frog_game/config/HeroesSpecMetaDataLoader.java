package com.nitsanmichael.popping_frog_game.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.nitsanmichael.popping_frog_game.config.metadata.HeroSpecMetaData;

import java.io.IOException;
import java.util.HashMap;


/**
 * Created by MichaelBond on 11/6/2016.
 */
public class HeroesSpecMetaDataLoader {

    public static void load(XmlReader xmlReader, HashMap<Class<? extends Actor>, HeroSpecMetaData> heroesSpec) {
        Array<XmlReader.Element> heroElement = null;
        try {
            XmlReader.Element root = xmlReader.parse(Gdx.files.internal(
                    "config/heroes_specifications.xml"));
            heroElement = root.getChildrenByName("hero");
        }
        catch (IOException e) {
            e.printStackTrace();
            // TODO (handle exception properly).
        }
        try {
            if (null != heroElement) {
                for (XmlReader.Element hero : heroElement) {
                    HeroSpecMetaData heroSpecMetaData = createHeroSpecMetaData(hero);
                    heroesSpec.put(heroSpecMetaData.heroClass, heroSpecMetaData);
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
     * @param heroElement  The xml-element to read from.
     * @return  A HeroSpecMetaData object that holds the xml-element object's data.
     * @throws ClassNotFoundException   In case no class was found for one of the -
     *  frogs used in that level.
     * @throws IllegalStateException   In case of one or more malformed attributes -
     *  inside this element or one of it's children.
     */
    private static HeroSpecMetaData createHeroSpecMetaData(XmlReader.Element heroElement)
            throws ClassNotFoundException, IllegalStateException {
        try {
            String className = heroElement.getAttribute("class");
            String animationIndexString = heroElement.getAttribute("animationIndex");
            String requiredLevelString = heroElement.getAttribute("requiredLevel", null);
            String heroName = heroElement.getAttribute("name");
            String heroDescription = heroElement.getAttribute("description");
            Integer animationIndex = Integer.parseInt(animationIndexString);
            Integer requiredLevel = null;
            if (null != requiredLevelString) {
                requiredLevel = Integer.parseInt(requiredLevelString);
            }
            return new HeroSpecMetaData(
                    getHeroClassByName(className), animationIndex, requiredLevel,
                        heroName, heroDescription);
        }
        catch (GdxRuntimeException e) {
            throw new IllegalStateException("A 'hero' element does not contain " +
                    "one ore more crucial attributes.");
        }
        catch (NumberFormatException e) {
            throw new IllegalStateException("Attribute is of the wrong type.\n" +
                    e.getMessage());
        }
    }

    private static Class<? extends Actor> getHeroClassByName(String className)
            throws ClassNotFoundException {
        return ClassForName.getHeroClassByName(className);
    }

}
