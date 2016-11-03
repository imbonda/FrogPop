package com.nitsanmichael.popping_frog_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.input.BackKeyInputProcessor;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButton;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButtonListener;
import com.nitsanmichael.popping_frog_game.scenes.events.MessageEventListener;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleEvilFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleFreezeFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleHealthFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleIllusionFrog;
import com.nitsanmichael.popping_frog_game.scenes.idlefrogs.IdleRegularFrog;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;


/**
 * Created by MichaelBond on 11/2/2016.
 */
public class ManualScreen extends FadingScreen {

    private static final float FADE_OUT_TIME = 0.25f;
    private static final float FADE_IN_TIME = 0.25f;
    private static final String MANUAL_TITLE = "Manual";
    // Descriptions.
    private static final String HERO_DESCRIPTION =
                "Tap it to increase score +1,\notherwise you will lose a life.";
    private static final String DEVIL_DESCRIPTION =
                "Do not tap it,\nyou will lose a life if you do.";
    private static final String ANGEL_DESCRIPTION =
                "Tap it to get an extra life.\nYou won't lose any if you miss it.";
    private static final String GENIE_DESCRIPTION =
                "Oh no ! he is making all the holes move.\nTap it to increase the score + 3,\n" +
                "otherwise you will lose a life.";
    private static final String POLICE_DESCRIPTION =
                "Careful it is the police.\nIt is slowing all the other frogs down.\n" +
                "Tap it to increase the score + 1\notherwise you will lose a life.";


    private Sprite background;
    private PoppingFrog game;
    private Stage stage;
    private Viewport backgroundViewport;


    public ManualScreen(final PoppingFrog game) {
        super(game.batch, game.tweenController);
        this.game = game;
        this.backgroundViewport = new StretchViewport(
                    PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT);

        BitmapFont font = this.game.assetController.get(Assets.GAME_FONT);

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

        // Choose hero title.
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.LIME);
        Label chooseHeroTitle = new Label(MANUAL_TITLE, labelStyle);
        chooseHeroTitle.setFontScale(0.6f);
        chooseHeroTitle.setPosition(300, 440);
        chooseHeroTitle.setHeight(50);

        setStage(chooseHeroTitle, backButton);

        this.background = new Sprite((Texture) this.game.assetController.get(Assets.MENU_BACKGROUND));

        Gdx.input.setCatchBackKey(true);
        setInputProcessor();

        game.adsController.showBannerAd();
    }

    private void setStage(Label chooseHeroTitle, ToggleButton backButton) {
        this.stage = new Stage(new ExtendViewport(
                PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT, new OrthographicCamera()),
                this.game.batch);
        this.stage.addActor(chooseHeroTitle);
        this.stage.addActor(backButton);
        addIdleFrogsToStage();
    }

    private void addIdleFrogsToStage() {
        Table table = new Table();
        Table container = new Table();
        Skin scrollPaneSkin = this.game.assetController.get(Assets.SLIDER_SKIN);
        ScrollPane scrollPane = new ScrollPane(table, scrollPaneSkin);
        container.add(scrollPane);
        container.setSize(700, 350);
        container.setPosition(50, 20);
        container.setFillParent(false);

        BitmapFont font = this.game.assetController.get(Assets.GAME_FONT);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.BROWN);

        // Adding frogs to manual.
        table.top();
        // Hero.
        table.add(new IdleRegularFrog(this.game.assetController,
                    IdleRegularFrog.AnimationType.TONGUE, Vector2.Zero)).padRight(20);
        Label heroLabel = new Label("Hero", style);
        heroLabel.setFontScale(0.2f);
        table.add(heroLabel).padRight(20);
        Label heroDescriptionLabel = new Label(HERO_DESCRIPTION, style);
        heroDescriptionLabel.setFontScale(0.2f);
        table.add(heroDescriptionLabel).padRight(20);
        table.row();
        // Devil.
        table.add(new IdleEvilFrog(this.game.assetController, Vector2.Zero)).padTop(20);
        Label devilLabel = new Label("Devil", style);
        devilLabel.setFontScale(0.2f);
        table.add(devilLabel).padRight(20).padTop(20);
        Label devilDescriptionLabel = new Label(DEVIL_DESCRIPTION, style);
        devilDescriptionLabel.setFontScale(0.2f);
        table.add(devilDescriptionLabel).padRight(20).padTop(20);
        table.row();
        // Angel.
        table.add(new IdleHealthFrog(this.game.assetController, Vector2.Zero)).padTop(20);
        Label angelLabel = new Label("Angel", style);
        angelLabel.setFontScale(0.2f);
        table.add(angelLabel).padRight(20).padTop(20);
        Label angelDescriptionLabel = new Label(ANGEL_DESCRIPTION, style);
        angelDescriptionLabel.setFontScale(0.2f);
        table.add(angelDescriptionLabel).padRight(20).padTop(20);
        table.row();
        // Genie.
        table.add(new IdleIllusionFrog(this.game.assetController, Vector2.Zero)).padTop(20);
        Label genieLabel = new Label("Genie", style);
        genieLabel.setFontScale(0.2f);
        table.add(genieLabel).padRight(20).padTop(20);
        Label genieDescriptionLabel = new Label(GENIE_DESCRIPTION, style);
        genieDescriptionLabel.setFontScale(0.2f);
        table.add(genieDescriptionLabel).padRight(20).padTop(20);
        table.row();
        // Police.
        table.add(new IdleFreezeFrog(this.game.assetController, IdleFreezeFrog.AnimationType.NORMAL,
                    Vector2.Zero)).padTop(20);
        Label policeLabel = new Label("Police", style);
        policeLabel.setFontScale(0.2f);
        table.add(policeLabel).padRight(20).padTop(20);
        Label policeDescriptionLabel = new Label(POLICE_DESCRIPTION, style);
        policeDescriptionLabel.setFontScale(0.2f);
        table.add(policeDescriptionLabel).padRight(20).padTop(20);
        this.stage.addActor(container);
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
        this.background.draw(this.game.batch);
        this.game.batch.end();
        this.game.batch.setProjectionMatrix(this.stage.getCamera().combined);
        this.stage.draw();
    }

    public void update(float deltaTime) {
        this.stage.act(deltaTime);
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
