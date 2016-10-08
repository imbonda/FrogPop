package com.nitsanmichael.popping_frog_game.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;

/**
 * This class is responsible for loading the frogs-addition configuration from the corresponding -
 * configuration file.
 * A frog-addition configuration is for specifying the level range at which a new frog should -
 * be added to the game-screen.
 *
 * Created by MichaelBond on 9/18/2016.
 */
public class AddFrogsMetaDataLoader {

    public static void load(XmlReader xmlReader, Array<com.nitsanmichael.popping_frog_game.config.metadata.AddFrogMetaData> addFrogsMetaData) {
        Array<XmlReader.Element> addFrogElements = null;
        try {
            XmlReader.Element root = xmlReader.parse(Gdx.files.internal(
                        "config/frog_addition_levels.xml"));
            addFrogElements = root.getChildrenByName("addFrog");
        }
        catch (IOException e) {
            e.printStackTrace();
            // TODO (handle exception properly).
        }
        try {
            if (null != addFrogElements) {
                for (XmlReader.Element addFrog : addFrogElements) {
                    addFrogsMetaData.add(createAddFrogMetaData(addFrog));
                }
            }
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
            // TODO (handle exception properly).
        }
    }

    /**
     * This loads data from an xml-element object into its Java's representation object.
     *
     * @param addFrogElement    The xml-element to read from.
     * @return  A AddFrogMetaData object that holds the xml-element object's data.
     */
    private static com.nitsanmichael.popping_frog_game.config.metadata.AddFrogMetaData createAddFrogMetaData(XmlReader.Element addFrogElement) {
        if (null != addFrogElement.getAttribute("at_level", null)) {
            //..
            String atLevelString = addFrogElement.getAttribute("at_level");
            int atLevel = Integer.parseInt(atLevelString);
            return new com.nitsanmichael.popping_frog_game.config.metadata.AddFrogMetaData(atLevel);
        }
        else if (null != addFrogElement.getAttribute("min_level", null) &&
                null != addFrogElement.getAttribute("max_level", null)){
            String minLevelString = addFrogElement.getAttribute("min_level");
            String maxLevelString = addFrogElement.getAttribute("max_level");
            int minLevel = Integer.parseInt(minLevelString);
            int maxLevel = Integer.parseInt(maxLevelString);
            return new com.nitsanmichael.popping_frog_game.config.metadata.AddFrogMetaData(minLevel, maxLevel);
        }
        return null;
    }

}
