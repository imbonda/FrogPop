package com.nitsanmichael.popping_frog_game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nitsanmichael.popping_frog_game.PoppingFrog;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1366;
		config.height = 768;
		config.title = PoppingFrog.GAME_TITLE;
		new LwjglApplication(new PoppingFrog(new DummyAdsController(), new DummyPlayServices()),
					config);
	}
}
