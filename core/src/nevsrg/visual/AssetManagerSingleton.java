package nevsrg.visual;

import com.badlogic.gdx.assets.AssetManager;

public class AssetManagerSingleton {
	private static AssetManagerSingleton instancia;
	private AssetManager assets;
	
	private AssetManagerSingleton() {
		this.assets = new AssetManager();
	}
	
	public static AssetManagerSingleton getInstancia() {
		if (instancia == null) {
			instancia = new AssetManagerSingleton();
		}
		return instancia;
	}
	
}
