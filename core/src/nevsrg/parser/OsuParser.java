package nevsrg.parser;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class OsuParser extends BeatmapParser{
	private BufferedReader lector;
	
	private static final long OFFSET_MS = 150l;
	
	public OsuParser(IBuilderChart builder) {
		super(builder);
	}
	
	public void abrirArchivo(String rutaArchivo){
		// Se carga el archivo
	    FileHandle archivoMapa = Gdx.files.internal(rutaArchivo);
	    
	    // Se crea el lector
	    this.lector = archivoMapa.reader(8192, "UTF-8");
	}
	
	public void procesarNotas(){
		try {
			String linea = lector.readLine();
			
			//Buscamos la linea "[HitObjects]"
			while (linea != null && !linea.equals("[HitObjects]")) {
				linea = lector.readLine(); 
			}
			
			// Las siguientes lineas deberian ser las notas
			// Tienen un formato diferente asi que tendremos que aplicar conversiones
			
			/* Columnas (1er dato):
			 * 1: 64
			 * 2: 192 (+128)
			 * 3: 320
			 * 4: 448
			 * divion entera / 128 da la columna correspondiente
			 */
			
			/* tiempoPulsacion (3er dato):
			 * en ms, ningun cambio
			 */
			
			/* tiempoRelease (6to dato):
			 * en ms, ningun cambio
			 */
			
			String lineaNota;
			lineaNota = lector.readLine(); 
			while (lineaNota != null) { // Leer hasta el final del archivo
				String[] datosNotas = lineaNota.split(",");
				
				int carril = Integer.parseInt(datosNotas[0]) / 128;
				long tiempoHit = Long.parseLong(datosNotas[2]);
				// Revisamos si es una nota larga o nota normal
				if (!datosNotas[3].equals("128")) {
					// Es una Nota Normal
					builder.agregarNotaNormal(carril, tiempoHit + OFFSET_MS);
				} else {
					// Es una Nota Larga
					String[] extra = datosNotas[5].split(":", 2);
					long tiempoRelease = Long.parseLong(extra[0]);
					long duracion = tiempoRelease - tiempoHit;
					builder.agregarNotaLarga(carril, tiempoHit + OFFSET_MS, duracion);
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
