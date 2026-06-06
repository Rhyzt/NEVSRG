package nevsrg.parser;

import java.io.BufferedReader;

import com.badlogic.gdx.files.FileHandle;

import nevsrg.entidades.MetadataNivel;

public abstract class LectorMetadata {
	
	public final MetadataNivel extraerMetadata(FileHandle archivo, String extension) {
		// Le damos valores por defecto a la metadata
		MetadataNivel metadata = new MetadataNivel(null, null, archivo.nameWithoutExtension(), null);
        metadata.setExtension(extension);
		
        // Leemos el archivo
		try(BufferedReader reader = new BufferedReader(archivo.reader("UTF-8"))) {
			String linea;
				while ((linea = reader.readLine()) != null) {
                
                if (metadata.getTitulo().equals(archivo.nameWithoutExtension())) {
                    String t = buscarTituloEnLinea(linea);
                    if (t != null) metadata.setTitulo(t);
                }
                
                if (metadata.getArtista() == null) {
                    String a = buscarArtistaEnLinea(linea);
                    if (a != null) metadata.setArtista(a);
                }
                
                if (metadata.getMapper() == null) {
                    String m = buscarMapperEnLinea(linea);
                    if (m != null) metadata.setMapper(m);
                }
                
                if (metadata.getRutaAudio() == null) {
                    String au = buscarAudioEnLinea(linea);
                    if (au != null) {
                    	FileHandle rutaCharts = archivo.parent();
                    	FileHandle archivoAudio = rutaCharts.child(au);
                    	metadata.setRutaAudio(archivoAudio.path());
                    }
                }
                if (metadata.getArtista() != null &&
                        metadata.getTitulo() != null &&
                        metadata.getMapper() != null &&
                        metadata.getRutaAudio() != null) {
                        break;
                    }
            }
		} catch (Exception ex) {
			System.out.println("Error leyendo archivo: " + archivo.name());
		}
		return metadata;
	}

	
	protected abstract String buscarArtistaEnLinea(String linea);
	protected abstract String buscarTituloEnLinea(String linea); 
	protected abstract String buscarMapperEnLinea(String linea); 
	protected abstract String buscarAudioEnLinea(String linea); 
}
