package com.nitsanmichael.frog_pop_game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nitsanmichael.frog_pop_game.FrogPop;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=1366;
		config.height=768;
		new LwjglApplication(new FrogPop(new DummyAdsController()), config);
	}
}
