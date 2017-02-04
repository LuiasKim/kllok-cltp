package com.korea.univ.luias.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.korea.univ.luias.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 600;
		config.height = 900;
		config.resizable = false;
		
		
		new LwjglApplication(new Main(), config);
	}
}
