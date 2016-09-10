package com.mygdx.game.managment.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.mygdx.game.managment.metadata.FrogMetaData;
import com.mygdx.game.managment.metadata.LevelMetaData;
import com.mygdx.game.sprites.frogs.Frog;

import java.io.IOException;

import javax.management.InvalidAttributeValueException;

/**
 * Created by MichaelBond on 9/8/2016.
 */
public class Config {

    public static Array<LevelMetaData> levelsMetaData = new Array<LevelMetaData>();
    private static XmlReader xmlReader = new XmlReader();

    static {
        loadConfig();
    }

    private static void loadConfig() {
        loadLevelMap();
    }

    private static void loadLevelMap() {
        Array<Element> levelElements = null;
        try {
            Element levelMapRoot = xmlReader.parse(Gdx.files.internal("config/frogs_level_map.xml"));
            levelElements = levelMapRoot.getChildrenByName("level");
        }
        catch (IOException e) {
            e.printStackTrace();
            // TODO (handle exception properly).
        }
        try {
            if (null != levelElements) {
                for (Element level : levelElements) {
                    levelsMetaData.add(createLevelMetaData(level));
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            // TODO (handle exception properly).
        }
        catch (InvalidAttributeValueException e) {
            e.printStackTrace();
            // TODO (handle exception properly).
        }
    }

    private static LevelMetaData createLevelMetaData(Element levelElement)
                throws ClassNotFoundException, InvalidAttributeValueException{
        String idString = levelElement.getAttribute("id");
        int levelId = Integer.parseInt(idString);

        Array<FrogMetaData> frogsMetaData = new Array<FrogMetaData>();
        for (Element frog : levelElement.getChildrenByName("frog")) {
            frogsMetaData.add(createFrogMetaData(frog));
        }
        return new LevelMetaData(levelId, frogsMetaData);
    }

    private static FrogMetaData createFrogMetaData(Element frogElement)
                throws ClassNotFoundException, InvalidAttributeValueException {
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
            throw new InvalidAttributeValueException("A 'Level' element does not contain "+
                    "one ore more crucial attributes.");
        }
        catch (NumberFormatException e) {
            throw new InvalidAttributeValueException("Attribute is of the wrong type.\n" +
                        e.getMessage());
        }
    }

    private static Class<? extends Frog> getFrogClassByName(String className)
                throws ClassNotFoundException{
        return FrogClassFactory.getFrogClassByName(className);
    }

}
