package nevsrg.parser;

public class MetadataNEVSRG extends LectorMetadata {
	
	@Override
	public String buscarMapperEnLinea(String linea) {
		String[] lineaSeparada = linea.split(":", 2);
		if (lineaSeparada.length == 2 && lineaSeparada[0].toLowerCase().equals("mapper")) {
			return lineaSeparada[1].trim();
		}
		return null;
	}
	
	@Override
	public String buscarTituloEnLinea(String linea) {
		String[] lineaSeparada = linea.split(":", 2);
		if (lineaSeparada.length == 2 && lineaSeparada[0].toLowerCase().equals("titulo")) {
			return lineaSeparada[1].trim();
		}
		return null;
	}
	
	@Override
	public String buscarAudioEnLinea(String linea) {
		String[] lineaSeparada = linea.split(":", 2);
		if (lineaSeparada.length == 2 && lineaSeparada[0].toLowerCase().equals("audio")) {
			return lineaSeparada[1].trim();
		}
		return null;
	}
	
	@Override
	public String buscarArtistaEnLinea(String linea){
		String[] lineaSeparada = linea.split(":", 2);
		if (lineaSeparada.length == 2 && lineaSeparada[0].toLowerCase().equals("artista")) {
			return lineaSeparada[1].trim();
		}
		return null;
	}
}

