package nevsrg.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager {
	private static AudioManager instancia;
	private Music cancionActual;
	private long tiempoUltimaActualizacion = 0;
	private long tiempoAudioUltimaActualizacion = 0;
	
	
	private AudioManager(){}
	
	// Patron Singleton, si existe una instancia la regresa, si no la crea.
	public static AudioManager getInstancia() {
		if (instancia == null)
			instancia = new AudioManager();
		return instancia;
	}
	
	// Reproduce la cancion guardada 
	public void reproducirCancion(String rutaMusica) {
		tiempoUltimaActualizacion = 0;
		tiempoAudioUltimaActualizacion = 0;
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
	
	// Interpolar el tiempo del audio para un movimiento fluido de las notas
	public long getTiempoInterpoladoMS() {
		// Obtenemos el tiempo actual tanto de la cancion como del sistema
		long tiempoAudio = getTiempoMS();
		long ahora = System.currentTimeMillis();
		
		// Se revisa si la diferencia entre la posicion normal y la interpolada no es minima
		if (tiempoAudio - tiempoAudioUltimaActualizacion >= 1 ) {
			tiempoAudioUltimaActualizacion = tiempoAudio; // Se actualiza el audio por si cambia
			tiempoUltimaActualizacion = ahora; // Sera responsable de agregar los ms para interpolacion
		}
		
		// Se calcula la diferencia entre las updates del sistema
		long msPasados = ahora - tiempoUltimaActualizacion;
		msPasados =  Math.min(msPasados, 20l);
		return tiempoAudio + msPasados; 
	}
}
