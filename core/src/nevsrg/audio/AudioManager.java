package nevsrg.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager {
	private static AudioManager instancia;
	private Music cancionActual;
	
	private AudioManager(){}
	
	// Patron Singleton, si existe una instancia la regresa, si no la crea.
	public static AudioManager getInstancia() {
		if (instancia == null)
			instancia = new AudioManager();
		return instancia;
	}
	
	// Reproduce la cancion guardada 
	public void reproducirCancion(String rutaMusica) {
		Music musica = Gdx.audio.newMusic(Gdx.files.internal(rutaMusica));
		this.cancionActual = musica;
		this.cancionActual.play();
	}
	
	// Detiene la cancion guardada
	public void dejarDeReproducirCancion() {
		if (cancionActual != null) {
			this.cancionActual.stop();
			this.cancionActual.dispose();
			cancionActual = null;	
		}
	}
	
	// Obtener el tiempo en ms como long
	public long getTiempoMS() {
		if (cancionActual != null) {
			return (long) (cancionActual.getPosition() * 1000f);
		}
		return 0;
	}
	
	// Obtener el tiempo en ms como float
	public float getTiempoFloatMS() {
		if (cancionActual != null) {
			return (float) (cancionActual.getPosition() * 1000f);
		}
		return 0;
	}
}
