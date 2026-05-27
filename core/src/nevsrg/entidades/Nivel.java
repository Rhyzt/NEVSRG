package nevsrg.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import nevsrg.audio.AudioManager;

public class Nivel {
	private Carril[] carriles;
	private String rutaAudio;
	
	public Nivel(Carril[] carriles, String rutaAudio) {
		this.carriles = carriles;
		this.rutaAudio = rutaAudio;
	}
	
	public String getRutaAudio() { return rutaAudio; }
	public void setRutaAudio(String ruta) { rutaAudio = ruta; }
	
	private Carril getCarril(int indiceCarril) { return carriles[indiceCarril]; }
	
	public void presionarCarril(int indiceCarril) {
		long tiempoAudioActual = AudioManager.getInstancia().getTiempoMS();
		Carril carril = getCarril(indiceCarril);
		if (carril != null)
			carril.evaluarHit(tiempoAudioActual);
	}
	
	public void soltarCarril(int indiceCarril) {
		long tiempoAudioActual = AudioManager.getInstancia().getTiempoMS();
		Carril carril = getCarril(indiceCarril);
		if (carril != null)
			carril.evaluarRelease(tiempoAudioActual);
	}
	
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
}
