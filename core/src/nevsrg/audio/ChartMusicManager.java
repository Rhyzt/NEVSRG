package nevsrg.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class ChartMusicManager implements IAudioManager {
	private static IAudioManager instancia;
	private Music cancionActual;
	private long tiempoUltimaActualizacion;
	private long tiempoAudioUltimaActualizacion;
	private long tiempoDesdeInicio;
	private boolean audioIniciado;
	
	private final static long OFFSET_INICIO = 1000l;
	
	private ChartMusicManager() {}
	
	// Patron Singleton, si existe una instancia la regresa, si no la crea.
	public static IAudioManager getInstancia() {
		if (instancia == null)
			instancia = new ChartMusicManager();
		return instancia;
	}
	
	// Reproduce la cancion guardada
	@Override
	public void reproducirCancion(String rutaMusica) {
		// Desechar cualquier cancion previa
		if (cancionActual != null) {
			cancionActual.stop();
			cancionActual.dispose();
			cancionActual = null;
		}	
		
		tiempoUltimaActualizacion = 0;
		tiempoAudioUltimaActualizacion = 0;
		Music musica = Gdx.audio.newMusic(Gdx.files.local(rutaMusica));
		cancionActual = musica;
		audioIniciado = false;
		tiempoDesdeInicio = System.currentTimeMillis();
	}	
	
	// Detiene la cancion guardada
	@Override
	public void dejarDeReproducirCancion() {
		if (cancionActual != null) {
			cancionActual.stop();
			cancionActual.dispose();
			cancionActual = null;
		}	
	}
	
	// Obtener el tiempo en ms como long
	@Override
	public long getTiempoMS() {
		long tiempo;
		if (cancionActual != null) {
			if (audioIniciado) {
				return (long) (cancionActual.getPosition() * 1000f);
			} else if ((tiempo = System.currentTimeMillis() - tiempoDesdeInicio) < OFFSET_INICIO) {
				return tiempo - OFFSET_INICIO;
			} else {
				cancionActual.play();
				audioIniciado = true;
			}
		}
		return 0;
	}
	
	// Interpolar el tiempo del audio para un movimiento fluido de las notas
	@Override
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
