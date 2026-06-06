package nevsrg.audio;


public interface IAudioManager {
	
	public void reproducirCancion(String rutaMusica);
	public void dejarDeReproducirCancion();
	public long getTiempoMS();
	public long getTiempoInterpoladoMS();
}
