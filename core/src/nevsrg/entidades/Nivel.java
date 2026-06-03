package nevsrg.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import nevsrg.audio.AudioManager;

public class Nivel {
	private Carril[] carriles;
	private MetadataNivel metadata;
	
	public Nivel(Carril[] carriles, MetadataNivel metadata) {
		this.carriles = carriles;
		this.metadata = metadata;
	}
	
	
	public String getRutaAudio() { return metadata.getRutaAudio(); }
	public String getArtista() { return metadata.getArtista(); }
	public String getTitulo() { return metadata.getTitulo(); }
	public String getMapper() { return metadata.getMapper(); }
	
	public void setRutaAudio(String ruta) { metadata.setRutaAudio(ruta); }
	public void setArtista(String artista) { metadata.setArtista(artista); }
	public void setTitulo(String titulo) { metadata.setTitulo(titulo); }
	public void setMapper(String mapper) { metadata.setMapper(mapper); }
	
	
	private Carril getCarril(int indiceCarril) { return carriles[indiceCarril]; }
	
	
	// Pulsacion en un carril
	public void presionarCarril(int indiceCarril) {
		long tiempoAudioActual = AudioManager.getInstancia().getTiempoMS();
		Carril carril = getCarril(indiceCarril);
		if (carril != null)
			carril.evaluarHit(tiempoAudioActual);
	}
	
	// Soltar un carril
	public void soltarCarril(int indiceCarril) {
		long tiempoAudioActual = AudioManager.getInstancia().getTiempoMS();
		Carril carril = getCarril(indiceCarril);
		if (carril != null)
			carril.evaluarRelease(tiempoAudioActual);
	}
	
	// Renderizar las lista de notas del carril
	public void renderizar(SpriteBatch batch) {
		//Obtenemos el ms a dibujar 
		long tiempoAudioActual = AudioManager.getInstancia().getTiempoMS();
		
		// Dibujar cada carril
		for(Carril carril : carriles) {
			if (carril != null) {
				carril.renderizar(batch, tiempoAudioActual);
			}
		}
	}
	
	// Obtener el tiempo en el cual aparece la ultima nota (en ms)
	public long tiempoUltimaNota( ) {
		long masTarde = 0;
		for (Carril carril : carriles) {
			long tiempo = carril.tiempoUltimaNota();
			if (tiempo > masTarde) {
				masTarde = tiempo;
			}
		}
		return masTarde;
	}
}
