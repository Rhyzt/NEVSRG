package nevsrg.visual;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
	private static Assets instancia;
	private AssetManager assets;
	
	
	private Assets() {
		this.assets = new AssetManager();
	}
	
	public static Assets getInstancia() {
		if (instancia == null) {
			instancia = new Assets();
		}
		return instancia;
	}
	
	public void cargarRecursos() {
	    assets.load(Recursos.NOTA_NORMAL, Texture.class);
	    assets.load(Recursos.NOTA_CUERPO, Texture.class);
	    assets.load(Recursos.NOTA_COLA, Texture.class);
	    assets.load(Recursos.RECEPTOR, Texture.class);
	    assets.load(Recursos.JUDGE_MARVELOUS, Texture.class);
	    assets.load(Recursos.JUDGE_PERFECT, Texture.class);
	    assets.load(Recursos.JUDGE_GREAT, Texture.class);
	    assets.load(Recursos.JUDGE_GOOD, Texture.class);
	    assets.load(Recursos.JUDGE_BAD, Texture.class);
	    assets.load(Recursos.JUDGE_MISS, Texture.class);
	    assets.finishLoading();
	}
	
	// Para obtener cualquier textura
	 public Texture get(String ruta) {
	        return assets.get(ruta, Texture.class);
	    }
	
}
