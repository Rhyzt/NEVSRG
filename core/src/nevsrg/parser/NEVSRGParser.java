package nevsrg.parser;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class NEVSRGParser extends BeatmapParser{
	private BufferedReader lector;
	private FileHandle directorioBase;
	
	public NEVSRGParser(IBuilderChart builder) {
		super(builder);
	}
	
	public void abrirArchivo(String rutaArchivo){
		// Creamos el lector con la informacion del nivel (.nevsrg)
		FileHandle archivoMapa = Gdx.files.internal(rutaArchivo);
		
		this.directorioBase = archivoMapa.parent();
		this.lector = archivoMapa.reader(8192);
				
	}
	
	public void procesarDatosCancion() {
		try {
			String linea = lector.readLine(); 
			
			// Buscamos la linea de los datos de la cancion (deberia ser la primera)
			while (linea != null && !linea.equals("[Datos]")) {
				linea = lector.readLine(); 
			}
			linea = lector.readLine(); // Saltamos "[Datos]"
			
			// Aca estaran el Artista, Titulo de la Cancion y Mapper del nivel (en ese orden)
			while (linea != null && linea.contains(":")) {
				String[] partes = linea.split(":", 2); // Separamos la primera palabra con el resto del string
				String clave = partes[0].trim().toLowerCase();
				String valor = partes[1].trim();
				
				switch (clave) {
					case "artista":
						builder.setArtista(valor);
						break;
					case "titulo":
						builder.setTitulo(valor);
						break;
					case "mapper":
						builder.setMapper(valor);
						break;
					case "audio":
						String rutaCompletaAudio = directorioBase.child(valor).path();
						builder.setRutaCancion(rutaCompletaAudio);
						break;
					default:
						break;
				}
				linea = lector.readLine(); 
			}
		} catch (IOException ex) {
			System.out.println("Ocurrio un error al procesar la cancion: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void procesarNotas(){
		try {
			String linea = lector.readLine();
			
			//Buscamos la linea "[Notas]" (deberiamos estar en ella)
			while (linea != null && !linea.equals("[Notas]")) {
				linea = lector.readLine(); 
			}
			
			// Las siguientes lineas deberian ser las notas
			String lineaNota;
			lineaNota = lector.readLine(); 
			while (lineaNota != null) { // Leer hasta el final del archivo
				String[] datosNotas = lineaNota.split(",");
				
				int carril = Integer.parseInt(datosNotas[0]);
				long tiempoHit = Long.parseLong(datosNotas[1]);
				long duracion = Long.parseLong(datosNotas[2]);
				duracion = duracion - tiempoHit;
				// Revisamos si es una nota larga o nota normal
				if (datosNotas[2].equals("0")) {
					// Es una Nota Normal
					builder.agregarNotaNormal(carril, tiempoHit);
				} else {
					// Es una Nota Larga
					builder.agregarNotaLarga(carril, tiempoHit, duracion);
				}
				lineaNota = lector.readLine(); 
			}
		} catch (IOException ex) {
			System.out.println("Ocurrio un error al procesar el mapa : " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void cerrarArchivo(){
		try {
			// Liberar memoria usada por el lector
			lector.close();
		} catch (IOException ex) {
			System.out.println("Ocurrio un error al cerrar el archivo : " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
