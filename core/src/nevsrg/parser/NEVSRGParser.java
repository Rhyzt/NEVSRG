package nevsrg.parser;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;

public class NEVSRGParser extends BeatmapParser{
	private BufferedReader lector;
	
	public NEVSRGParser(IBuilderChart builder) {
		super(builder);
	}
	
	@Override
	protected void abrirArchivo(FileHandle archivoMapa){
		// Creamos el lector con la informacion del nivel (.nevsrg)
		this.lector = archivoMapa.reader(8192, "UTF-8");
	}
	
	@Override
	protected void procesarNotas(){
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
				if (datosNotas.length < 3) continue;
				
				int carril = Integer.parseInt(datosNotas[0]);
				long tiempoHit = Long.parseLong(datosNotas[1]);
				long tiempoFin = Long.parseLong(datosNotas[2]);
				
				// Revisamos si es una nota larga o nota normal
				if (tiempoFin == 0) {
					// Es una Nota Normal
					builder.agregarNotaNormal(carril, tiempoHit);
				} else {
					// Es una Nota Larga
					builder.agregarNotaLarga(carril, tiempoHit, tiempoFin - tiempoHit);
				}
				lineaNota = lector.readLine(); 
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
