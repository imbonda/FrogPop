package com.nitsanmichael.popping_frog_game.scenes.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nitsanmichael.popping_frog_game.assets.AssetController;
import com.nitsanmichael.popping_frog_game.assets.Assets;
import com.nitsanmichael.popping_frog_game.data.Data;
import com.nitsanmichael.popping_frog_game.media.Media;
import com.nitsanmichael.popping_frog_game.runtime.RuntimeInfo;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButton;
import com.nitsanmichael.popping_frog_game.scenes.ToggleButtonListener;
import com.nitsanmichael.popping_frog_game.scenes.events.MessageEventListener;
import com.nitsanmichael.popping_frog_game.states.StateTracker;


/**
 * Created by MichaelBond on 10/17/2016.
 */
public class PauseDialog implements Disposable {

    private static final String DIALOG_TITLE = "PAUSE";

    private Texture background;
    private ToggleButton muteButton;
    private ToggleButton unmuteButton;
    private Stage stage;

    public PauseDialog(AssetController assetController, final Data data, final Media media,
                       Viewport viewport, Batch batch, final RuntimeInfo runtimeInfo) {
        BitmapFont font = assetController.get(Assets.GAME_FONT);
        this.background = assetController.get(Assets.PAUSE_BACKGROUND);

        // Play button.
        Texture playIcon = assetController.get(Assets.PLAY_ICON);
        Texture playPressedIcon = assetController.get(Assets.PLAY_PRESSED_ICON);
        final ToggleButton playButton = new ToggleButton(
                new Image(playIcon), new Image(playPressedIcon));
        playButton.setSize(130, 130);
        playButton.setPosition(325, 230);
        playButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != playButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                runtimeInfo.stateTracker.setState(StateTracker.GameState.PLAY);
            }
        });

        // Mute button.
        Texture muteIcon = assetController.get(Assets.MUTE_ICON);
        Texture mutePressedIcon = assetController.get(Assets.MUTE_PRESSED_ICON);
        this.muteButton = new ToggleButton(new Image(muteIcon), new Image(mutePressedIcon));
        this.muteButton.setSize(87, 87);
        this.muteButton.setPosition(278, 100);
        this.muteButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != muteButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                // Set mute.
                data.setMute(true);
                media.updateSoundVolume(0);
                media.updateMusicVolume(0);
                media.pauseMusic(Assets.GAME_PLAY_MUSIC);
                // Switch to unmute button.
                muteButton.remove();
                stage.addActor(unmuteButton);
            }
        });

        // Unmute button.
        Texture unmuteIcon = assetController.get(Assets.UNMUTE_ICON);
        Texture unmutePressedIcon = assetController.get(Assets.UNMUTE_PRESSED_ICON);
        this.unmuteButton = new ToggleButton(new Image(unmuteIcon), new Image(unmutePressedIcon));
        this.unmuteButton.setSize(87, 87);
        this.unmuteButton.setPosition(278, 100);
        this.unmuteButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != unmuteButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                // Set unmute.
                data.setMute(false);
                if (data.getSoundVolume() == 0 && data.getMusicVolume() == 0) {
                    data.setSoundVolume(Media.DEFAULT_SOUND_VOLUME);
                    data.setMusicVolume(Media.DEFAULT_MUSIC_VOLUME);
                }
                media.updateSoundVolume(data.getSoundVolume());
                media.updateMusicVolume(data.getMusicVolume());
                media.playMusic(Assets.GAME_PLAY_MUSIC);
                // todo
                // Switch to mute button.
                unmuteButton.remove();
                stage.addActor(muteButton);
            }
        });

        // Home button.
        Texture homeIcon = assetController.get(Assets.HOME_ICON);
        Texture homePressedIcon = assetController.get(Assets.HOME_PRESSED_ICON);
        final ToggleButton homeButton = new ToggleButton(new Image(homeIcon), new Image(homePressedIcon));
        homeButton.setSize(80, 80);
        homeButton.setPosition(420, 100);
        homeButton.addListener(new MessageEventListener() {
            @Override
            public void receivedMessage(int message, Actor actor) {
                if (actor != homeButton || message != ToggleButtonListener.ON_TOUCH_UP) {
                    return;
                }
                runtimeInfo.stateTracker.setState(StateTracker.GameState.BACK_TO_MENU);
            }
        });

        Label titleLabel = new Label(DIALOG_TITLE, new Label.LabelStyle(font, Color.WHITE));
        titleLabel.setFontScale(0.4f);
        titleLabel.setPosition(310, 400);

        ToggleButton noiseButton =
                    (data.isMute() || (data.getSoundVolume() == 0 && data.getMusicVolume() == 0)) ?
                                (unmuteButton) : (muteButton);
        setStage(viewport, batch, playButton, homeButton, noiseButton, titleLabel);
    }

    private void setStage(Viewport viewport, Batch batch,
                            ToggleButton homeButton, ToggleButton playButton,
                            ToggleButton noiseButton, Label title) {
        this.stage = new Stage(viewport, batch);
        this.stage.addActor(title);
        this.stage.addActor(playButton);
        this.stage.addActor(homeButton);
        this.stage.addActor(noiseButton);
        InputProcessor processor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(new InputMultiplexer(this.stage, processor));
    }

    public void draw() {
        Batch batch = this.stage.getBatch();
        batch.begin();
        batch.draw(this.background, 0, 0);
        batch.end();
        this.stage.draw();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }
}
