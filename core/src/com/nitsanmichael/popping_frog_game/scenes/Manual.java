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

    // Descriptions.
    private static final String MISPLACED_TAP_DESCRIPTION =
            "If you tap the screen and miss all the frogs ,\nyou lose 1 life !";
    private static final String HERO_DESCRIPTION =
            "Tap it to increase the score +1,\notherwise you will lose a life.";
    private static final String DEVIL_DESCRIPTION =
            "Do not tap it,\nyou will lose a life if you do.";
    private static final String ANGEL_DESCRIPTION =
            "Tap it to get an extra life.\nYou won't lose any if you don't -\n catch it.";
    private static final String GENIE_DESCRIPTION =
            "Oh no ! it makes all the holes move.\nTap it to increase the score + 2,\n" +
                    "otherwise you will lose a life.";
    private static final String POLICE_DESCRIPTION =
            "Careful it's the police.\nIt slows all the frogs down.\n" +
                    "Tap it to increase the score + 1,\notherwise you will lose a life.";


    public Manual(AssetController assetController) {
        initFrogsManual(assetController);
    }

    private void initFrogsManual(AssetController assetController) {
        Table table = new Table();
        Table container = new Table();
        Skin scrollPaneSkin = assetController.get(Assets.SLIDER_SKIN);
        ScrollPane scrollPane = new ScrollPane(table, scrollPaneSkin);
        scrollPane.getStyle().background = new TextureRegionDrawable(new TextureRegion(
                (Texture)assetController.get(Assets.SCROLL_BACKGROUND)));
        container.add(scrollPane);
        container.setSize(700, 450);
        container.setPosition(100, 50);
        container.setFillParent(false);

        BitmapFont font = assetController.get(Assets.GAME_FONT);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.BROWN);

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
        Label heroDescriptionLabel = new Label(HERO_DESCRIPTION, style);
        heroDescriptionLabel.setFontScale(0.2f);
        table.add(heroDescriptionLabel).padTop(20).padLeft(20).padRight(20);
        table.row();
        // Devil.
        table.add(new IdleEvilFrog(assetController, Vector2.Zero)).padTop(20).padLeft(20);
        Label devilLabel = new Label("Devil", style);
        devilLabel.setFontScale(0.2f);
        table.add(devilLabel).padTop(20).padLeft(20);
        Label devilDescriptionLabel = new Label(DEVIL_DESCRIPTION, style);
        devilDescriptionLabel.setFontScale(0.2f);
        table.add(devilDescriptionLabel).padTop(20).padLeft(20).padRight(20);
        table.row();
        // Angel.
        table.add(new IdleHealthFrog(assetController, Vector2.Zero)).padTop(20).padLeft(20);
        Label angelLabel = new Label("Angel", style);
        angelLabel.setFontScale(0.2f);
        table.add(angelLabel).padTop(20).padLeft(20);
        Label angelDescriptionLabel = new Label(ANGEL_DESCRIPTION, style);
        angelDescriptionLabel.setFontScale(0.2f);
        table.add(angelDescriptionLabel).padTop(20).padLeft(20).padRight(20);
        table.row();
        // Genie.
        table.add(new IdleIllusionFrog(assetController, Vector2.Zero)).padTop(20).padLeft(20);
        Label genieLabel = new Label("Genie", style);
        genieLabel.setFontScale(0.2f);
        table.add(genieLabel).padTop(20).padLeft(20);
        Label genieDescriptionLabel = new Label(GENIE_DESCRIPTION, style);
        genieDescriptionLabel.setFontScale(0.2f);
        table.add(genieDescriptionLabel).padTop(20).padLeft(20).padRight(20);
        table.row();
        // Police.
        table.add(new IdleFreezeFrog(assetController, IdleFreezeFrog.AnimationType.NORMAL,
                Vector2.Zero)).padTop(20).padLeft(20).padBottom(20);
        Label policeLabel = new Label("Police", style);
        policeLabel.setFontScale(0.2f);
        table.add(policeLabel).padTop(20).padLeft(20).padBottom(20);
        Label policeDescriptionLabel = new Label(POLICE_DESCRIPTION, style);
        policeDescriptionLabel.setFontScale(0.2f);
        table.add(policeDescriptionLabel).padTop(20).padLeft(20).padRight(20).padBottom(20);

        addActor(container);
    }

}
