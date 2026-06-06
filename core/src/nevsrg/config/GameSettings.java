package nevsrg.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.SerializationException;

public class GameSettings {
	private static final String ARCHIVO_SETTINGS = "settings.json";
	private static final int SCROLL_MINIMO = 1;
	private static final int SCROLL_MAXIMO = 40;
	private static final int SCROLL_DEFECTO = 12;
	private static final String JUDGE_DEFECTO = "Estandar";
	
	private static GameSettings instancia;
	
	private int scrollSpeed;
	private int[] teclas;
	private String judge;
	private static IJudgeValidator validador;
	
	private GameSettings() {
		aplicarDefectos();
	}
	
	public static GameSettings getInstancia() {
		if (validador == null)
            throw new IllegalStateException("No se inicializo el validador.");
        if (instancia == null)
            instancia = cargar();
        return instancia;
	}
	
	public float getScrollSpeedNormalizada() { return scrollSpeed / 20f + 0.05f; }
	public int getScrollSpeed() { return scrollSpeed; }
	public static int getScrollMinimo() { return SCROLL_MINIMO; }
	public static int getScrollMaximo() { return SCROLL_MAXIMO; }
	public String getJudge() { return judge; }
	public int getKeybind(int carril) {
		if (carril >= 0 && carril < teclas.length)
			return teclas[carril];
		return Keys.UNKNOWN;
	}
	
	public void setScrollSpeed(int valor) {
		if (valor < SCROLL_MINIMO) valor = SCROLL_MINIMO;
	    if (valor > SCROLL_MAXIMO) valor = SCROLL_MAXIMO;
	    this.scrollSpeed = valor;
	}
	
	public void setJudge(String judge) {
		if (validador.esJudgeValido(judge))
			this.judge = judge;
	}
	
	public void setKeybind(int carril, int keycode) {
		if (carril >= 0 && carril < teclas.length)
			teclas[carril] = keycode;
	}
	
	public static void inicializar(IJudgeValidator v) {
        validador = v;
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
	
	private void aplicarDefectos() {
		scrollSpeed = SCROLL_DEFECTO;
		teclas = new int[] { Keys.D, Keys.F, Keys.J, Keys.K };
		judge = JUDGE_DEFECTO;
	}
	
	public int[] copiarTeclas() {
		validar();
		return new int[] { teclas[0], teclas[1], teclas[2], teclas[3] };
	}
	
	private void validar() {
		if (scrollSpeed < SCROLL_MINIMO) scrollSpeed = SCROLL_MINIMO;
		if (scrollSpeed > SCROLL_MAXIMO) scrollSpeed = SCROLL_MAXIMO;
		
		if (teclas == null || teclas.length != 4) {
			teclas = new int[] { Keys.D, Keys.F, Keys.J, Keys.K };
		}
		
		if (!validador.esJudgeValido(judge)) {
			judge = JUDGE_DEFECTO;
		}
	}
	
	public String[] getJudgesDisponibles() {
	    return validador.getJudgesDisponibles();
	}
}
