package com.mygdx.nevsrg;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import nevsrg.entidades.GameNEVSRG;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		config.useVsync(false);
		config.setForegroundFPS(1000);
		config.setTitle("NEVSRG");
		
		config.setWindowedMode(1280, 720);
		
		new Lwjgl3Application(new GameNEVSRG(), config);
	}
}
	