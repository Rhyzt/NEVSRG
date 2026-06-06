package nevsrg.parser;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;

public class OsuParser extends BeatmapParser{
	private BufferedReader lector;
	
	// Offset entre motor LibGDX Y osu! que existira para la reproduccion de canciones
	/* Esta obtenido manualmente para medir el desfase de una nota y la musica.
	 */
	private static final long OFFSET_MS = 150l;
	
	public OsuParser(IBuilderChart builder) {
		super(builder);
	}
	
	@Override
	protected void abrirArchivo(FileHandle archivoMapa){  
	    // Se crea el lector
	    this.lector = archivoMapa.reader(8192, "UTF-8");
	}
	
	@Override
	protected void procesarNotas(){
		try {
			if (lector == null) {
		        System.out.println("Error: archivo no fue abierto correctamente");
		        return;
		    }
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
			while ((lineaNota = lector.readLine()) != null) { // Leer hasta el final del archivo
				String[] datosNotas = lineaNota.split(",");
				if (datosNotas.length < 4) continue; // Para notas normales
				if (datosNotas[3].equals("128") && datosNotas.length < 6) continue; // Para notas largas
				
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
			}
		} catch (IOException ex) {
			System.out.println("Ocurrio un error al procesar el mapa : " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	@Override
	protected void cerrarArchivo(){
		try {
			// Liberar memoria usada por el lector
			lector.close();
		} catch (IOException ex) {
			System.out.println("Ocurrio un error al cerrar el archivo : " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
