package com.nitsanmichael.popping_frog_game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleEvilFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleFreezeFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleHealthFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleIllusionFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleRegularFrog;


/**
 * Created by MichaelBond on 11/6/2016.
 */
public class Manual extends Group {

    // Hit.
    private static final String HIT = "Hit:";
    // Hit.
    private static final String MISS = "Miss:";

    // Descriptions.
    private static final String MISPLACED_TAP_DESCRIPTION =
            "If you tap the screen and miss all the frogs ,\nyou lose 1 life !";
    // Hero.
    private static final String HERO_HIT = "\tscore +1.";
    private static final String HERO_MISS = "\tlives -1.";
    // Devil.
    private static final String DEVIL_DESCRIPTION = "Do not hit it,\n It is bad for your health!";
    private static final String DEVIL_HIT = "lives -1,\tscore -5.";
    // Angel.
    private static final String ANGEL_DESCRIPTION = "It will give you bonus lives!";
    private static final String ANGEL_HIT = "lives +1,\tscore +2.";
    // Genie.
    private static final String GENIE_DESCRIPTION = "Oh no !\nIt makes all the holes move.";
    private static final String GENIE_HIT = "score +2.";
    private static final String GENIE_MISS = "lives -1.";
    // Police.
    private static final String POLICE_DESCRIPTION =
            "Careful, it's the police.\nIt slows all the frogs down.";
    private static final String POLICE_HIT = "score + 1.";
    private static final String POLICE_MISS = "lives -1.";


    public Manual(AssetController assetController) {
        initFrogsManual(assetController);
    }

    private void initFrogsManual(AssetController assetController) {
        Table table = new Table();
        Table container = new Table();
        Table descriptionTable;
        Skin scrollPaneSkin = assetController.get(Assets.SLIDER_SKIN);
        ScrollPane scrollPane = new ScrollPane(table, scrollPaneSkin);
        scrollPane.getStyle().background = new TextureRegionDrawable(new TextureRegion(
                (Texture)assetController.get(Assets.SCROLL_BACKGROUND)));
        container.add(scrollPane);
        container.setSize(700, 430);
        container.setPosition(100, 70);
        container.setFillParent(false);

        BitmapFont font = assetController.get(Assets.GAME_FONT);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.BROWN);
        Label.LabelStyle positiveStyle = new Label.LabelStyle(font, new Color(0x246d14ff));
        Label.LabelStyle negativeStyle = new Label.LabelStyle(font, Color.FIREBRICK);

        // Adding frogs to manual.
        table.top();
        Label misplacedTapDescription = new Label(MISPLACED_TAP_DESCRIPTION, style);
        misplacedTapDescription.setFontScale(0.23f);
        table.add(misplacedTapDescription).colspan(3).padTop(20).padLeft(20).padRight(20);
        table.row();
        // Hero.
        table.add(new IdleRegularFrog(assetController,
                IdleRegularFrog.AnimationType.TONGUE, Vector2.Zero)).padTop(20).padLeft(20);
        Label heroLabel = new Label("Hero", style);
        heroLabel.setFontScale(0.2f);
        table.add(heroLabel).padTop(20).padLeft(20);
        descriptionTable = getDescriptionTable(style, positiveStyle, negativeStyle, null, HERO_HIT, HERO_MISS);
        table.add(descriptionTable).padTop(20).padLeft(20).padRight(20).align(Align.left);
        table.row();
        // Devil.
        table.add(new IdleEvilFrog(assetController, Vector2.Zero)).padTop(20).padLeft(20);
        Label devilLabel = new Label("Devil", style);
        devilLabel.setFontScale(0.2f);
        table.add(devilLabel).padTop(20).padLeft(20);
        descriptionTable = getDescriptionTable(style, positiveStyle, negativeStyle, DEVIL_DESCRIPTION, DEVIL_HIT, null);
        table.add(descriptionTable).padTop(20).padLeft(20).padRight(20).align(Align.left);
        table.row();
        // Angel.
        table.add(new IdleHealthFrog(assetController, Vector2.Zero)).padTop(20).padLeft(20);
        Label angelLabel = new Label("Angel", style);
        angelLabel.setFontScale(0.2f);
        table.add(angelLabel).padTop(20).padLeft(20);
        descriptionTable = getDescriptionTable(style, positiveStyle, negativeStyle, ANGEL_DESCRIPTION, ANGEL_HIT, null);
        table.add(descriptionTable).padTop(20).padLeft(20).padRight(20).align(Align.left);
        table.row();
        // Genie.
        table.add(new IdleIllusionFrog(assetController, Vector2.Zero)).padTop(20).padLeft(20);
        Label genieLabel = new Label("Genie", style);
        genieLabel.setFontScale(0.2f);
        table.add(genieLabel).padTop(20).padLeft(20);
        descriptionTable = getDescriptionTable(style, positiveStyle, negativeStyle, GENIE_DESCRIPTION, GENIE_HIT, GENIE_MISS);
        table.add(descriptionTable).padTop(20).padLeft(20).padRight(20).align(Align.left);
        table.row();
        // Police.
        table.add(new IdleFreezeFrog(assetController, IdleFreezeFrog.AnimationType.NORMAL,
                Vector2.Zero)).padTop(20).padLeft(20).padBottom(20);
        Label policeLabel = new Label("Police", style);
        policeLabel.setFontScale(0.2f);
        table.add(policeLabel).padTop(20).padLeft(20).padBottom(20);
        descriptionTable = getDescriptionTable(style, positiveStyle, negativeStyle, POLICE_DESCRIPTION, POLICE_HIT, POLICE_MISS);
        table.add(descriptionTable).padTop(20).padLeft(20).padRight(20).padBottom(20).align(Align.left);

        addActor(container);
    }

    private Table getDescriptionTable(Label.LabelStyle style, Label.LabelStyle hitStyle,
                                      Label.LabelStyle missStyle, String description, String hit,
                                      String miss) {
        Table table = new Table();

        if (null != description) {
            Label descriptionLabel = new Label(description, style);
            descriptionLabel.setFontScale(0.2f);
            table.add(descriptionLabel).colspan(2).align(Align.left);
            table.row();
        }

        if (null != hit) {
            Label hitLabel = new Label(HIT, hitStyle);
            hitLabel.setFontScale(0.2f);
            table.add(hitLabel).align(Align.left);
            hitLabel = new Label(hit, style);
            hitLabel.setFontScale(0.2f);
            table.add(hitLabel).align(Align.left);
            table.row();
        }

        if (null != miss) {
            Label missLabel = new Label(MISS, missStyle);
            missLabel.setFontScale(0.2f);
            table.add(missLabel).align(Align.left);
            missLabel = new Label(miss, style);
            missLabel.setFontScale(0.2f);
            table.add(missLabel).align(Align.left);
            table.row();
        }

        return table;
    }

}
