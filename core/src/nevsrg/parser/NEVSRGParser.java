package nevsrg.parser;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;

public class NEVSRGParser extends BeatmapParser{
	private BufferedReader lector;
	
	public NEVSRGParser(IBuilderChart builder) {
		super(builder);
	}
	
	public void abrirArchivo(String rutaArchivo){
		// Creamos el lector con la informacion del nivel (.nevsrg)
		this.lector = Gdx.files.internal(rutaArchivo).reader(8192);
	}
	
	public void procesarCancion() {
		try {
			String linea = lector.readLine(); 
			
			//Buscamos la linea "[Cancion]" (deberia ser la primera)
			while (linea != null && !linea.equals("[Cancion]")) {
				linea = lector.readLine(); 
			}
			
			// La siguiente linea deberia ser la ruta del audio
			String rutaAudio = lector.readLine();
			if (rutaAudio != null) {
				builder.setRutaCancion(rutaAudio);
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
			
			// La siguiente linea deberian ser las notas
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
