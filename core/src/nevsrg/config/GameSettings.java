package nevsrg.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.SerializationException;

public class GameSettings {
	private static final String ARCHIVO_SETTINGS = "settings.json";
	public static final int SCROLL_MINIMO = 1;
	public static final int SCROLL_MAXIMO = 40;
	public static final int SCROLL_DEFECTO = 12;
	public static final String JUDGE_DEFECTO = "Estandar";
	
	private static GameSettings instancia;
	
	public int scrollSpeedX;
	public int[] teclas;
	public String judge;
	
	public GameSettings() {
		aplicarDefectos();
	}
	
	public static GameSettings getInstancia() {
		if (instancia == null) {
			instancia = cargar();
		}
		return instancia;
	}
	
	private static GameSettings cargar() {
		FileHandle archivo = Gdx.files.local(ARCHIVO_SETTINGS);
		Json json = new Json();
		
		if (archivo.exists()) {
			try {
				GameSettings settings = json.fromJson(GameSettings.class, archivo);
				settings.validar();
				return settings;
			} catch (SerializationException ex) {
				System.out.println("No se pudo leer settings.json, usando valores por defecto.");
			}
		}
		
		GameSettings settings = new GameSettings();
		settings.guardar();
		return settings;
	}
	
	public void guardar() {
		validar();
		Json json = new Json();
		json.setOutputType(JsonWriter.OutputType.json);
		Gdx.files.local(ARCHIVO_SETTINGS).writeString(json.prettyPrint(this), false, "UTF-8");
	}
	
	public void aplicarDefectos() {
		scrollSpeedX = SCROLL_DEFECTO;
		teclas = new int[] { Keys.D, Keys.F, Keys.J, Keys.K };
		judge = JUDGE_DEFECTO;
	}
	
	public float getScrollSpeed() {
		return scrollSpeedX / 20f + 0.05f;
	}
	
	public int[] copiarTeclas() {
		validar();
		return new int[] { teclas[0], teclas[1], teclas[2], teclas[3] };
	}
	
	private void validar() {
		if (scrollSpeedX < SCROLL_MINIMO) scrollSpeedX = SCROLL_MINIMO;
		if (scrollSpeedX > SCROLL_MAXIMO) scrollSpeedX = SCROLL_MAXIMO;
		
		if (teclas == null || teclas.length != 4) {
			teclas = new int[] { Keys.D, Keys.F, Keys.J, Keys.K };
		}
		
		if (!JudgeFactory.esJudgeValido(judge)) {
			judge = JUDGE_DEFECTO;
		}
	}
}
