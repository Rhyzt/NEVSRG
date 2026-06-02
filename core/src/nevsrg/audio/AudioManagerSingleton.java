package nevsrg.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManagerSingleton {
	private static AudioManagerSingleton instancia;
	private Music cancionActual;
	
	private AudioManagerSingleton(){}
	
	public static AudioManagerSingleton getInstancia() {
		if (instancia == null)
			instancia = new AudioManagerSingleton();
		return instancia;
	}
	
	public void reproducirCancion(String rutaMusica) {
		Music musica = Gdx.audio.newMusic(Gdx.files.internal(rutaMusica));
		this.cancionActual = musica;
		this.cancionActual.play();
	}
	
	public void dejarDeReproducirCancion() {
		if (cancionActual != null) {
			this.cancionActual.stop();
			this.cancionActual.dispose();
			cancionActual = null;	
		}
	}
	
	public long getTiempoMS() {
		if (cancionActual != null) {
			return (long) (cancionActual.getPosition() * 1000f);
		}
		return 0;
	}
	
	public float getTiempoFloatMS() {
		if (cancionActual != null) {
			return (float) (cancionActual.getPosition() * 1000f);
		}
		return 0;
	}
}
