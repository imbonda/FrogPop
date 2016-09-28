package com.mygdx.game.managment.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.game.managment.metadata.FrogMetaData;
import com.mygdx.game.managment.metadata.LevelMetaData;
import com.mygdx.game.sprites.frogs.Frog;

import java.io.IOException;

/**
 * This class is responsible for loading the levels configuration from the corresponding -
 * configuration file.
 * A level configuration consists of the frogs related to the level and their spawn probability.
 *
 * Created by MichaelBond on 9/18/2016.
 */
public class LevelsMetaDataLoader {

    public static void load(XmlReader xmlReader, Array<LevelMetaData> levelsMetaData) {
        Array<XmlReader.Element> levelElements = null;
        try {
            XmlReader.Element root = xmlReader.parse(Gdx.files.internal(
                        "config/frogs_level_distribution.xml"));
            levelElements = root.getChildrenByName("level");
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
     * @throws IllegalStateException   In case of one or more malformed attributes -
     *  inside this element or one of it's children.
     */
    private static LevelMetaData createLevelMetaData(XmlReader.Element levelElement)
                throws ClassNotFoundException, IllegalStateException {
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
            String maxParallelString = frogElement.getAttribute("max_parallel", null);
            Float spawnProbability = Float.parseFloat(spawnProbString);
            Integer maxAllowed = null;
            Integer maxParallel = null;
            if (null != maxAllowedString) {
                maxAllowed = Integer.parseInt(maxAllowedString);
            }
            if (null != maxParallelString) {
                maxParallel = Integer.parseInt(maxParallelString);
            }
            return new FrogMetaData(
                        getFrogClassByName(className), spawnProbability, maxAllowed, maxParallel);
        }
        catch (GdxRuntimeException e) {
            throw new IllegalStateException("A 'Level' element does not contain " +
                        "one ore more crucial attributes.");
        }
        catch (NumberFormatException e) {
            throw new IllegalStateException("Attribute is of the wrong type.\n" +
                        e.getMessage());
        }
    }

    private static Class<? extends Frog> getFrogClassByName(String className)
                throws ClassNotFoundException {
        return ClassForName.getFrogClassByName(className);
    }

}
