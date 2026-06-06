package nevsrg.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import nevsrg.audio.IAudioManager;
import nevsrg.input.IControlCarril;

public class Nivel implements IControlCarril {
	private Carril[] carriles;
	private MetadataNivel metadata;
	private IAudioManager audio;
	
	public Nivel(Carril[] carriles, MetadataNivel metadata, IAudioManager audio) {
		this.carriles = carriles;
		this.metadata = metadata;
		this.audio = audio;
	}
	
	
	public String getRutaAudio() { return metadata.getRutaAudio(); }
	public String getArtista() { return metadata.getArtista(); }
	public String getTitulo() { return metadata.getTitulo(); }
	public String getMapper() { return metadata.getMapper(); }
	
	public void setRutaAudio(String ruta) { metadata.setRutaAudio(ruta); }
	public void setArtista(String artista) { metadata.setArtista(artista); }
	public void setTitulo(String titulo) { metadata.setTitulo(titulo); }
	public void setMapper(String mapper) { metadata.setMapper(mapper); }
	
	private Carril getCarril(int indiceCarril) {
		if (indiceCarril >= 0 && indiceCarril < carriles.length)
			return carriles[indiceCarril];
		return null;
	}
	
	// Pulsacion en un carril
	@Override
	public void presionarCarril(int indiceCarril) {
		long tiempoAudioActual = audio.getTiempoInterpoladoMS();
		Carril carril = getCarril(indiceCarril);
		if (carril != null)
			carril.evaluarHit(tiempoAudioActual);
	}
	
	// Soltar un carril
	@Override
	public void soltarCarril(int indiceCarril) {
		long tiempoAudioActual = audio.getTiempoInterpoladoMS();
		Carril carril = getCarril(indiceCarril);
		if (carril != null)
			carril.evaluarRelease(tiempoAudioActual);
	}
	
	// Renderizar las lista de notas del carril
	public void renderizar(SpriteBatch batch, float delta) {
		//Obtenemos el ms a dibujar 
		long tiempoAudioActual = audio.getTiempoInterpoladoMS();
		
		// Dibujar cada carril
		for(Carril carril : carriles) {
			if (carril != null) {
				carril.renderizar(batch, tiempoAudioActual, delta);
			}
		}
	}
	
	// Obtener el tiempo en el cual aparece la ultima nota (en ms)
	public long tiempoUltimaNota() {
		long masTarde = 0;
		for (Carril carril : carriles) {
			if (carril != null) {
				long tiempo = carril.tiempoUltimaNota();
				if (tiempo > masTarde) {
					masTarde = tiempo;
				}
			}
		}
		return masTarde;
	}
}
