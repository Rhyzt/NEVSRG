package nevsrg.entidades;

public class MetadataNivel {
	private String rutaAudio;
	private String artista;
	private String titulo;
	private String mapper;
	private String extension;
	
	public MetadataNivel(String rutaAudio, String artista, String titulo, String mapper) {
		this.rutaAudio = rutaAudio;
		this.artista = artista;
		this.titulo = titulo;
		this.mapper = mapper;
		this.extension = null;
	}
	
	public String getRutaAudio() { return rutaAudio; }
	public String getArtista() { return artista; }
	public String getTitulo() { return titulo; }
	public String getMapper() { return mapper; }
	public String getExtension() { return extension; }
	
	public void setRutaAudio(String ruta) { rutaAudio = ruta; }
	public void setArtista(String artista) { this.artista = artista; }
	public void setTitulo(String titulo) { this.titulo = titulo; }
	public void setMapper(String mapper) { this.mapper = mapper; }
	public void setExtension(String ext) { this.extension = ext; }
}
