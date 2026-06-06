package nevsrg.screens;


import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import nevsrg.audio.ChartMusicManager;
import nevsrg.audio.IAudioManager;
import nevsrg.config.GameSettings;
import nevsrg.config.JudgeFactory;
import nevsrg.entidades.Carril;
import nevsrg.entidades.GameNEVSRG;
import nevsrg.entidades.MetadataNivel;
import nevsrg.entidades.Nivel;
import nevsrg.input.InputHandler;
import nevsrg.parser.BeatmapParser;
import nevsrg.parser.ChartBuilder;
import nevsrg.parser.ParserFactory;
import nevsrg.puntuacion.GestorPuntuacion;
import nevsrg.puntuacion.GestorVisualPuntuacion;
import nevsrg.puntuacion.IStrategyJudge;
import nevsrg.puntuacion.TipoJudgement;
import nevsrg.visual.Assets;
import nevsrg.visual.Recursos;

public class GameScreen implements Screen {
	private GameNEVSRG game;
	// Dibujado
	private SpriteBatch batch;
	private FitViewport viewport;
	private OrthographicCamera camara;
	private Texture pixelNegro;
	private float alphaFade = 0f;
	private boolean iniciandoFadeOut = false;

	
	// Informacion Nivel
	private Nivel nivel;
	private InputHandler inputHandler;
	private long tiempoUltimaNota = 0l;
	private float tiempoDesdeFin = 0f;
	private boolean nivelTerminado = false;
	private IAudioManager audio;

	// letras
    private BitmapFont letra;
    private BitmapFont fontFPS;
    
    // Referentes al Score y Judgements
    private GestorPuntuacion gestorMatematico;
    private GestorVisualPuntuacion gestorGrafico;
	
	public GameScreen(GameNEVSRG game, String rutaMapa, MetadataNivel metadata) {
		this.game = game;
		// Relacionados al dibujado
		this.camara = new OrthographicCamera();
		viewport = new FitViewport(1280, 720, camara);
		this.batch = new SpriteBatch();
		this.fontFPS = new BitmapFont();
		
		// Pixel negro para Fade Out
		Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pm.setColor(1, 1, 1, 1);
		pm.fill();
		pixelNegro = new Texture(pm);
		pm.dispose();
		
		// Manager del audio para niveles
		audio = ChartMusicManager.getInstancia();
		
		
		// Iniciar configuraciones
		GameSettings settings = GameSettings.getInstancia();
		
		// Crear Judge
		IStrategyJudge judge = JudgeFactory.crear(GameSettings.getInstancia().getJudge());
		
		// Generar Carriles
		Carril[] carriles = new Carril[4];
		float anchoCarril = 90f;
		float margenIzquierdo = 460f;
        float receptorY = 70f;
        float scrollSpeed = settings.getScrollSpeedNormalizada();	
        
        Texture texturaReceptorUp = Assets.getInstancia().get(Recursos.RECEPTOR_UP);
        Texture texturaReceptorDown = Assets.getInstancia().get(Recursos.RECEPTOR_DOWN);
        for (int i = 0 ; i < carriles.length ; i++) {
            float posicionX = margenIzquierdo + (i * anchoCarril);
            carriles[i] = new Carril(posicionX, receptorY, scrollSpeed, judge, texturaReceptorUp, texturaReceptorDown);
        }
        
        // Procesado del mapa
        Texture normal = Assets.getInstancia().get(Recursos.NOTA_NORMAL);
        Texture cuerpo = Assets.getInstancia().get(Recursos.NOTA_CUERPO);
        Texture larga = Assets.getInstancia().get(Recursos.NOTA_COLA);
        ChartBuilder builder = new ChartBuilder(carriles, audio, normal, cuerpo, larga);
        
        String ext = metadata.getExtension();
        BeatmapParser parser = ParserFactory.crear(ext, builder);
        
        if (parser == null) {
        	System.err.println("Error: Formato de archivo no soportado: " + ext);
        	game.setScreen(new SelectionScreen(game));
        	return;
        }
        parser.procesarMapa(metadata, Gdx.files.local(rutaMapa));
        this.nivel = builder.obtenerNivelTerminado();
        
        // Buscar tiempo fin
        tiempoUltimaNota = nivel.tiempoUltimaNota();
        
        // Cargar la clase que maneje los inputs
        inputHandler = new InputHandler(this.nivel, settings.copiarTeclas());
        
        // Instanciar los observers
        gestorMatematico = new GestorPuntuacion();
        letra = new BitmapFont();
        
        // Inicializar el gestor visual de la puntuacion
        Map<TipoJudgement, Texture> texturasJudges = new EnumMap<>(TipoJudgement.class);
        texturasJudges.put(TipoJudgement.MARVELOUS, Assets.getInstancia().get(Recursos.JUDGE_MARVELOUS));
        texturasJudges.put(TipoJudgement.PERFECT, Assets.getInstancia().get(Recursos.JUDGE_PERFECT));
        texturasJudges.put(TipoJudgement.GREAT, Assets.getInstancia().get(Recursos.JUDGE_GREAT));
        texturasJudges.put(TipoJudgement.GOOD, Assets.getInstancia().get(Recursos.JUDGE_GOOD));
        texturasJudges.put(TipoJudgement.BAD, Assets.getInstancia().get(Recursos.JUDGE_BAD));
        texturasJudges.put(TipoJudgement.MISS, Assets.getInstancia().get(Recursos.JUDGE_MISS));
        gestorGrafico = new GestorVisualPuntuacion(letra, texturasJudges, gestorMatematico);
        
        // Suscribir los observers a los carriles
        for (int i = 0 ; i < 4 ; i++) {
        	carriles[i].agregarObservador(gestorMatematico);
        	carriles[i].agregarObservador(gestorGrafico);
        }
        
        // Cargar y Reproducir la Musica (Ultimo Paso)
        audio.reproducirCancion(this.nivel.getRutaAudio());
	}
	
	
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}
	
	@Override
	public void render(float delta) {
		//Limpiar pantalla
		ScreenUtils.clear(0, 0, 0, 1);
		
		// Obtener el tiempo actual de la cancion que sera utilizado para renderizar
		long tiempoActual = audio.getTiempoMS();
		
		// Checkear si termino el nivel
		if (tiempoActual >= tiempoUltimaNota) {
			nivelTerminado = true;
		}
		
		if (nivelTerminado) {
			tiempoDesdeFin += delta;
			
			if (tiempoDesdeFin >= 2 && !iniciandoFadeOut) {
				iniciandoFadeOut = true;
			}
			
		    if (iniciandoFadeOut) {
		        alphaFade = (tiempoDesdeFin - 2f) / 2f; 
		        alphaFade = Math.min(alphaFade, 1f);
		    }
		    
			if (tiempoDesdeFin >= 4) {
				audio.dejarDeReproducirCancion();
				game.setScreen(new ResultScreen(game, gestorMatematico));
			}
			 
		}
		
		// Renderizado
		camara.update();
		batch.setProjectionMatrix(camara.combined);
		batch.begin();
		nivel.renderizar(batch, delta);
		gestorGrafico.renderizar(batch);
		
		// Dibujar fps
		int fps = Gdx.graphics.getFramesPerSecond();
		fontFPS.draw(batch, "FPS: " + fps, 10, 710);
		
		// Si es que se inicio el fade out
		if (iniciandoFadeOut) {
            batch.setColor(0, 0, 0, alphaFade);
            batch.draw(pixelNegro, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.setColor(1, 1, 1, 1);
        }
		batch.end();
	}
	
	public void dispose() {
		batch.dispose();
	    fontFPS.dispose();
	    letra.dispose();
	    pixelNegro.dispose();
	}
	
	@Override public void show() {
		// Para tener mas de un input handler a la vez
		InputMultiplexer multiplexer = new InputMultiplexer();
		// Input handler original para presionar las notas
		multiplexer.addProcessor(inputHandler); 
		// Se a;ade el segundo input handler para controlar el esc y poder salir del nivel
		multiplexer.addProcessor(new InputAdapter() { 
			private int contadorEsc = 0;
			private long tiempoUltimoEsc = 0l;
			private static final long LIMITE_MS = 1000l;
		    
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.ESCAPE) {
					long tiempoActual = TimeUtils.millis(); // Se obtiene el segundo de la primera pulsacion
					 
					if (contadorEsc == 1 && (tiempoActual - tiempoUltimoEsc) <= LIMITE_MS) { 
						// Segunda pulsacion fue dentro del tiempo de 1s
						contadorEsc = 0;
						audio.dejarDeReproducirCancion();
						game.setScreen(new SelectionScreen(game));
					} else {
						contadorEsc = 1;
						tiempoUltimoEsc = tiempoActual;
					}
					return true;	
				}
				return false;
			}
				 
		});
		Gdx.input.setInputProcessor(multiplexer);
	}
    @Override public void pause() {}
    @Override public void resume() {}
    
    @Override public void hide() {
    	Gdx.input.setInputProcessor(null);
    }
}
