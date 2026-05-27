package nevsrg.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager {
	private static AudioManager instancia;
	private Music cancionActual;
	
	private AudioManager(){}
	
	public static AudioManager getInstancia() {
		if (instancia == null)
			instancia = new AudioManager();
		return instancia;
	}
	
	public void reproducirCancion(String rutaMusica) {
		Music musica = Gdx.audio.newMusic(Gdx.files.internal(rutaMusica));
		this.cancionActual = musica;
		this.cancionActual.play();
	}
	
	public long getTiempoMS() {
		if (cancionActual != null) {
			return (long) (cancionActual.getPosition() * 1000f);
		}
		return 0;
	}
}
