package com.nitsanmichael.popping_frog_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.config.metadata.HeroSpecMetaData;
import com.nitsanmichael.popping_frog_game.input.BackKeyInputProcessor;
import com.nitsanmichael.popping_frog_game.playservice.PlayServices;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButton;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButtonListener;
import com.nitsanmichael.popping_frog_game.scenes.events.MessageEventListener;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleBritishFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleMexicanFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleRegularFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleTurkishFrog;

import java.util.HashMap;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;


/**
 * Created by MichaelBond on 9/1/2016.
 */
public class ChooseHeroScreen extends FadingScreen {

    public static final int DEFAULT_HERO_INDEX = 0;

    private static final float FADE_OUT_TIME = 0.25f;
    private static final float FADE_IN_TIME = 0.25f;
    private static final String CHOOSE_HERO_TITLE = "My Frogish Hero";
    private static final Color DARK_SHADOW_COLOR = new Color(80/255f, 80/255f, 80/255f, 1);

    private PoppingFrog game;
    private Texture backgroundTexture;
    private Viewport backgroundViewport;
    private HashMap<Class<? extends Actor>, HeroSpecMetaData> heroesSpecMap;
    private Array<Actor> idleFrogs;
    private Actor idleHero;
    private int index;
    private int highestLevel;
    private BitmapFont font;
    private Stage stage;
    private Table heroesTable;



    public ChooseHeroScreen(final PoppingFrog game) {
        super(game.batch, game.tweenController);
        this.game = game;
        this.heroesSpecMap = this.game.config.heroesSpec;
        this.backgroundViewport = new StretchViewport(
                PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT);

        this.font = this.game.assetController.get(Assets.GAME_FONT);
        this.highestLevel = game.data.getHighScore(PlayServices.LeaderBoard.HIGHEST_LEVEL);

        // Go back button.
        Texture backIcon = this.game.assetController.get(Assets.BACK_ICON);
        Texture backPressedIcon = this.game.assetController.get(Assets.BACK_PRESSED_ICON);
        final ToggleButton backButton = new ToggleButton(
                new Image(backIcon), new Image(backPressedIcon));
        backButton.setSize(100, 100);
        backButton.setPosition(20, 400);
        backButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != backButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                backToMenu();
            }
        });

        // Next hero button.
        Texture nextIcon = this.game.assetController.get(Assets.NEXT_ICON);
        Texture nextPressedIcon = this.game.assetController.get(Assets.NEXT_PRESSED_ICON);
        final ToggleButton nextHeroButton = new ToggleButton(
                new Image(nextIcon), new Image(nextPressedIcon));
        nextHeroButton.setSize(80, 80);
        nextHeroButton.setPosition(550, 240);
        nextHeroButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != nextHeroButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                index = (index + 1) % idleFrogs.size;
                // Remove old hero from stage.
                idleHero.remove();
                idleHero = idleFrogs.get(index);

                HeroSpecMetaData meta = heroesSpecMap.get(idleHero.getClass());
                if(highestLevel >= meta.requiredLevel) {
                    game.data.setHeroIndex(meta.animationIndex);
                }
                setHeroRow();

            }
        });

        // Choose hero title.
        Label.LabelStyle labelStyle = new Label.LabelStyle(this.font, Color.LIME);
        Label chooseHeroTitle = new Label(CHOOSE_HERO_TITLE, labelStyle);
        chooseHeroTitle.setFontScale(0.5f);
        chooseHeroTitle.setPosition(220, 440);
        chooseHeroTitle.setHeight(50);

        initIdleFrogs();

        setStage(chooseHeroTitle, backButton, nextHeroButton);

        this.backgroundTexture = this.game.assetController.get(Assets.MENU_BACKGROUND);

        Gdx.input.setCatchBackKey(true);
        setInputProcessor();

        game.adsController.showBannerAd();
    }

    private void initIdleFrogs() {
        this.idleFrogs = new Array<Actor>();
        this.idleFrogs.add(new IdleRegularFrog(this.game.assetController,
                IdleRegularFrog.AnimationType.TONGUE, Vector2.Zero));
        this.idleFrogs.add(new IdleTurkishFrog(this.game.assetController, Vector2.Zero));
        this.idleFrogs.add(new IdleBritishFrog(this.game.assetController, Vector2.Zero));
        this.idleFrogs.add(new IdleMexicanFrog(this.game.assetController, Vector2.Zero));

        this.index = 0;
        for (int i = 0; i < this.idleFrogs.size; ++ i) {
            Actor hero = this.idleFrogs.get(i);
            if (this.heroesSpecMap.get(hero.getClass()).requiredLevel > this.highestLevel) {
                hero.setColor(80/255f, 80/255f, 80/255f, hero.getColor().a);
            }
            if (this.game.data.getHeroIndex() ==
                        this.heroesSpecMap.get(hero.getClass()).animationIndex) {
                this.index = i;
                this.idleHero = this.idleFrogs.get(i);
            }
        }
    }

    private void setStage(Label chooseHeroTitle, ToggleButton backButton,
                          ToggleButton nextHeroButton) {
        this.stage = new Stage(new ExtendViewport(
                PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT, new OrthographicCamera()),
                this.game.batch);
        this.stage.addActor(chooseHeroTitle);
        this.stage.addActor(backButton);
        this.heroesTable = new Table();
        this.heroesTable.setSize(600, 270);
        this.heroesTable.setPosition(100, 135);
        this.heroesTable.background(new TextureRegionDrawable(new TextureRegion(
                (Texture)this.game.assetController.get(Assets.CHOOSE_HERO_BACKGROUND))));
        this.stage.addActor(this.heroesTable);
        this.stage.addActor(nextHeroButton);
        setHeroRow();
    }

    private void setHeroRow() {
        HeroSpecMetaData meta = this.heroesSpecMap.get(this.idleHero.getClass());
        Color heroColor;
        Color descriptionFontColor;
        String extraHeroInformation;
        if (meta.requiredLevel > this.highestLevel) {
            heroColor = new Color(DARK_SHADOW_COLOR.r,DARK_SHADOW_COLOR.g, DARK_SHADOW_COLOR.b,
                        this.idleHero.getColor().a);
            descriptionFontColor = Color.DARK_GRAY;
            extraHeroInformation = meta.lockDescription;
        }
        else {
            heroColor = new Color(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b,
                        this.idleHero.getColor().a);
            descriptionFontColor = new Color(0xe7d9cfff);
            extraHeroInformation = meta.description;
        }

        // Clear children.
        this.heroesTable.clear();
        // Set new row.
        this.heroesTable.top();
        this.idleHero.setColor(heroColor);
        this.heroesTable.add(this.idleHero).padTop(10).expandY();
        Table descriptionTable = new Table();
        // Init nested description table.
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = this.font;
        style.fontColor = Color.BROWN;
        Label heroNameLabel = new Label(meta.name, style);
        heroNameLabel.setFontScale(0.3f);
        descriptionTable.add(heroNameLabel);
        // New row.
        descriptionTable.row();
        style = new Label.LabelStyle();
        style.font = this.font;
        style.fontColor = descriptionFontColor;
        Label heroDescriptionLabel = new Label(extraHeroInformation, style);
        heroDescriptionLabel.setFontScale(0.2f);
        descriptionTable.add(heroDescriptionLabel).padTop(20).padLeft(10);

        this.heroesTable.add(descriptionTable).padLeft(10);
    }

    private void setInputProcessor() {
        BackKeyInputProcessor backKeyInputProcessor = new BackKeyInputProcessor(
                new Runnable() {
                    @Override
                    public void run() {
                        backToMenu();
                    }
                }
        );
        Gdx.input.setInputProcessor(new InputMultiplexer(this.stage, backKeyInputProcessor));
    }

    private void backToMenu() {
        final PoppingFrog game = this.game;
        fadeOut(FADE_OUT_TIME, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                dispose();
                new MainMenuScreen(game).fadeIn(game, FADE_IN_TIME);
            }
        });
    }

    @Override
    public void setScreenColor(float r, float g, float b, float a) {
        super.setScreenColor(r, g, b, a);
        for (Actor actor : this.stage.getActors()) {
            actor.getColor().a = a;
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0/255f, 163/255f, 232/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.begin();
        this.game.batch.setProjectionMatrix(this.backgroundViewport.getCamera().combined);
        this.game.batch.draw(this.backgroundTexture, 0, 0);
        this.game.batch.end();
        this.game.batch.setProjectionMatrix(this.stage.getCamera().combined);
        this.stage.draw();
    }

    private void update(float deltaTime) {
        updateHighestLevel();
        this.stage.act(deltaTime);
    }

    private void updateHighestLevel() {
        this.highestLevel = this.game.data.getHighScore(PlayServices.LeaderBoard.HIGHEST_LEVEL);
    }

    @Override
    public void resize(int width, int height) {
        this.backgroundViewport.update(width, height, true);
        this.stage.getCamera().position.set(0,0,0);
        this.stage.getCamera().translate(
                PoppingFrog.VIRTUAL_WIDTH / 2,
                PoppingFrog.VIRTUAL_HEIGHT / 2,
                0);
        this.stage.getViewport().update(width, height, false);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        this.stage.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

}
