package com.mygdx.game.managment.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.game.managment.metadata.AddFrogMetaData;
import com.mygdx.game.managment.metadata.FrogMetaData;
import com.mygdx.game.managment.metadata.LevelMetaData;
import com.mygdx.game.sprites.frogs.Frog;

import java.io.IOException;

import javax.management.InvalidAttributeValueException;

/**
 * This class is responsible for loading all the data related to the levels configurations.
 *
 * Created by MichaelBond on 9/11/2016.
 */
public class LevelMapLoader {


    public static void loadLevelMap(XmlReader xmlReader,
                                        Array<LevelMetaData> levelsMetaData,
                                        Array<AddFrogMetaData> addFrogsMetaData) {
        Array<XmlReader.Element> levelElements = null;
        Array<XmlReader.Element> addFrogElements = null;
        try {
            XmlReader.Element levelMapRoot = xmlReader.parse(Gdx.files.internal("config/frogs_level_map.xml"));
            levelElements = levelMapRoot.getChildrenByName("level");
            addFrogElements = levelMapRoot.getChildrenByName("addFrog");
        }
        catch (IOException e) {
            e.printStackTrace();
            // TODO (handle exception properly).
        }
        try {
            if (null != levelElements) {
                for (XmlReader.Element level : levelElements) {
                    levelsMetaData.add(createLevelMetaData(level));
                }
            }
            if (null != addFrogElements) {
                for (XmlReader.Element addFrog : addFrogElements) {
                    addFrogsMetaData.add(createAddFrogMetaData(addFrog));
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
     * @param levelElement  The xml-element to read from.
     * @return  A LevelMetaData object that holds the xml-element object's data.
     * @throws ClassNotFoundException   In case no class was found for one of the -
     *  frogs used in that level.
     * @throws InvalidAttributeValueException   In case of one or more malformed attributes -
     *  inside this element or one of it's children.
     */
    private static LevelMetaData createLevelMetaData(XmlReader.Element levelElement)
            throws ClassNotFoundException, IllegalStateException{
        String idString = levelElement.getAttribute("id");
        int levelId = Integer.parseInt(idString);

        Array<FrogMetaData> frogsMetaData = new Array<FrogMetaData>();
        for (XmlReader.Element frog : levelElement.getChildrenByName("frog")) {
            frogsMetaData.add(createFrogMetaData(frog));
        }
        return new LevelMetaData(levelId, frogsMetaData);
    }

    private static FrogMetaData createFrogMetaData(XmlReader.Element frogElement)
            throws ClassNotFoundException, IllegalStateException {
        try {
            String className = frogElement.getAttribute("class");
            String spawnProbString = frogElement.getAttribute("spawn_prob");
            String maxAllowedString = frogElement.getAttribute("max_allowed", null);
            float spawnProbability = Float.parseFloat(spawnProbString);
            if (null == maxAllowedString) {
                return new FrogMetaData(getFrogClassByName(className), spawnProbability);
            }
            else {
                int maxAllowed = Integer.parseInt(maxAllowedString);
                return new FrogMetaData(getFrogClassByName(className), spawnProbability, maxAllowed);
            }
        }
        catch (GdxRuntimeException e) {
            throw new IllegalStateException("A 'Level' element does not contain "+
                    "one ore more crucial attributes.");
        }
        catch (NumberFormatException e) {
            throw new IllegalStateException("Attribute is of the wrong type.\n" +
                    e.getMessage());
        }
    }

    private static Class<? extends Frog> getFrogClassByName(String className)
            throws ClassNotFoundException{
        return ClassForName.getFrogClassByName(className);
    }

    /**
     * This loads data from an xml-element object into its Java's representation object.
     *
     * @param addFrogElement    The xml-element to read from.
     * @return  A AddFrogMetaData object that holds the xml-element object's data.
     */
    public static AddFrogMetaData createAddFrogMetaData(XmlReader.Element addFrogElement) {
        if (null != addFrogElement.getAttribute("at_level", null)) {
            //..
            String atLevelString = addFrogElement.getAttribute("at_level");
            int atLevel = Integer.parseInt(atLevelString);
            return new AddFrogMetaData(atLevel);
        }
        else if (null != addFrogElement.getAttribute("min_level", null) &&
                null != addFrogElement.getAttribute("max_level", null)){
            String minLevelString = addFrogElement.getAttribute("min_level");
            String maxLevelString = addFrogElement.getAttribute("max_level");
            int minLevel = Integer.parseInt(minLevelString);
            int maxLevel = Integer.parseInt(maxLevelString);
            return new AddFrogMetaData(minLevel, maxLevel);
        }
        return null;
    }

}
