package nevsrg.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import nevsrg.audio.AudioManager;

public class Nivel {
	private Carril[] carriles;
	private String rutaAudio;
	private String artista;
	private String titulo;
	private String mapper;
	
	public Nivel(Carril[] carriles, String rutaAudio, String artista, String titulo, String mapper) {
		this.carriles = carriles;
		this.rutaAudio = rutaAudio;
		this.artista = artista;
		this.titulo = titulo;
		this.mapper = mapper;
	}
	
	public String getRutaAudio() { return rutaAudio; }
	public String getArtista() { return artista; }
	public String getTitulo() { return titulo; }
	public String getMapper() { return mapper; }
	
	public void setRutaAudio(String ruta) { rutaAudio = ruta; }
	public void setArtista(String artista) { this.artista = artista; }
	public void setTitulo(String titulo) { this.titulo = titulo; }
	public void setMapper(String mapper) { this.mapper = mapper; }
	
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
