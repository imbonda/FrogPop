package com.nitsanmichael.popping_frog_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.PoppingFrog;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.input.BackKeyInputProcessor;
import com.nitsanmichael.popping_frog_game.scenes.Manual;
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
    // Produced by.
    private static final String PRODUCED_BY = "Producers :\t" +
                "Michael Bondarevsky and Nitsan Levy.";


    private PoppingFrog game;
    private Viewport backgroundViewport;
    private Stage stage;
    private Sprite background;
    private Manual manual;


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
        Label.LabelStyle style = new Label.LabelStyle(font, Color.LIME);
        Label chooseHeroTitle = new Label(MANUAL_TITLE, style);
        chooseHeroTitle.setFontScale(0.6f);
        chooseHeroTitle.setPosition(300, 440);
        chooseHeroTitle.setHeight(50);

        // Produced by.
        style = new Label.LabelStyle(font, new Color(0x42000b9f));
        Label producedByLabel = new Label(PRODUCED_BY, style);
        producedByLabel.setFontScale(0.2f);
        producedByLabel.setPosition(150, 360);
        producedByLabel.setHeight(50);

        this.manual = new Manual(this.game.assetController);
        setStage(chooseHeroTitle, producedByLabel, backButton);

        this.background = new Sprite((Texture) this.game.assetController.get(Assets.MENU_BACKGROUND));

        Gdx.input.setCatchBackKey(true);
        setInputProcessor();

        game.adsController.showBannerAd();
    }

    private void setStage(Label chooseHeroTitle, Label producedByLabel, ToggleButton backButton) {
        this.stage = new Stage(new ExtendViewport(
                PoppingFrog.VIRTUAL_WIDTH, PoppingFrog.VIRTUAL_HEIGHT, new OrthographicCamera()),
                this.game.batch);
        this.stage.addActor(chooseHeroTitle);
        this.stage.addActor(producedByLabel);
        this.stage.addActor(backButton);

        this.stage.addActor(this.manual);
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
