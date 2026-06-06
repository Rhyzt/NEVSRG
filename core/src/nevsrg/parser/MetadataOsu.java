package nevsrg.parser;

public class MetadataOsu extends LectorMetadata {
	
	@Override
	public String buscarMapperEnLinea(String linea) {
		String[] lineaSeparada = linea.split(":", 2);
		if (lineaSeparada.length == 2 && lineaSeparada[0].toLowerCase().equals("creator")) {
			return lineaSeparada[1].trim();
		}
		return null;
	}
	
	@Override
	public String buscarTituloEnLinea(String linea) {
		String[] lineaSeparada = linea.split(":", 2);
		if (lineaSeparada.length == 2 && lineaSeparada[0].toLowerCase().equals("title")) {
			return lineaSeparada[1].trim();
		}
		return null;
	}
	
	@Override
	public String buscarAudioEnLinea(String linea) {
		String[] lineaSeparada = linea.split(":", 2);
		if (lineaSeparada.length == 2 && lineaSeparada[0].toLowerCase().equals("audiofilename")) {
			return lineaSeparada[1].trim();
		}
		return null;
	}
	
	@Override
	public String buscarArtistaEnLinea(String linea){
		String[] lineaSeparada = linea.split(":", 2);
		if (lineaSeparada.length == 2 && lineaSeparada[0].toLowerCase().equals("artist")) {
			return lineaSeparada[1].trim();
		}
		return null;
	}
}
