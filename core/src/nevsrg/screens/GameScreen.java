package nevsrg.screens;

import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import nevsrg.audio.AudioManager;
import nevsrg.entidades.Carril;
import nevsrg.entidades.Nivel;
import nevsrg.input.InputHandler;
import nevsrg.parser.BeatmapParser;
import nevsrg.parser.ChartBuilder;
import nevsrg.parser.NEVSRGParser;
import nevsrg.parser.OsuParser;
import nevsrg.puntuacion.GestorPuntuacion;
import nevsrg.puntuacion.IStrategyJudge;
import nevsrg.puntuacion.JudgeEstandar;
import nevsrg.puntuacion.TipoJudgement;
import nevsrg.visual.GestorVisualJudges;

public class GameScreen implements Screen {
	// Dibujado
	private SpriteBatch batch;
	FitViewport viewport;
	private OrthographicCamera camara;
	
	// Informacion Nivel
	private Nivel nivel;
	private String rutaArchivo;
	
	// Texturas
	private Texture texturaNotaNormal;
    private Texture texturaCuerpoLarga;
    private Texture texturaColaLarga;
    private Texture texturaReceptores;
    private BitmapFont letra;
    
    // Referentes al Score y Judgements
    private GestorPuntuacion gestorMatematico;
    private GestorVisualJudges gestorGrafico;
	
	public GameScreen() {
		// Relacionados al dibujado
		this.camara = new OrthographicCamera();
		viewport = new FitViewport(1280, 720, camara);
		this.batch = new SpriteBatch();
		
		// Texturas
		texturaNotaNormal = new Texture("notes/nota.png");
		texturaCuerpoLarga = new Texture("notes/cuerpoLN.png");
		texturaColaLarga = new Texture("notes/colaLN.png");
		texturaReceptores = new Texture("notes/receptor.png");
		
		// Instanciar Judge
		IStrategyJudge judge = new JudgeEstandar();
		
		// Generar Carriles
		Carril[] carriles = new Carril[4];
		float anchoCarril = 90f;
		float margenIzquierdo = 460f;
        float receptorY = 70f;
        float scrollSpeed = 1.4f;	
        
        for (int i = 0; i < 4; i++) {
            float posicionX = margenIzquierdo + (i * anchoCarril);
            carriles[i] = new Carril(posicionX, receptorY, scrollSpeed, judge, texturaReceptores);
        }
        
        ChartBuilder builder = new ChartBuilder(carriles, texturaNotaNormal, texturaCuerpoLarga, texturaColaLarga);
        BeatmapParser parser = new OsuParser(builder);
        parser.procesarMapa("charts/tuyu/TUYU - Loneliness and the Future (nyawaa) [There is Nothing].osu");
        this.nivel = builder.obtenerNivelTerminado();
        
        // Cargar la clase que maneje los inputs
        InputHandler inputHandler = new InputHandler(this.nivel);
        Gdx.input.setInputProcessor(inputHandler);
        
        // Cargar las imagenes de los Judgements
        Map<TipoJudgement, Texture> texturasJudges = new EnumMap<>(TipoJudgement.class);
        texturasJudges.put(TipoJudgement.MARVELOUS, new Texture("judgements/marvelous.png"));
        texturasJudges.put(TipoJudgement.PERFECT, new Texture("judgements/perfect.png"));
        texturasJudges.put(TipoJudgement.GREAT, new Texture("judgements/great.png"));
        texturasJudges.put(TipoJudgement.GOOD, new Texture("judgements/good.png"));
        texturasJudges.put(TipoJudgement.BAD, new Texture("judgements/bad.png"));
        texturasJudges.put(TipoJudgement.MISS, new Texture("judgements/miss.png"));
        
        // Instanciar los observers
        gestorMatematico = new GestorPuntuacion();
        letra = new BitmapFont();
        gestorGrafico = new GestorVisualJudges(letra, texturasJudges);
        
        // Suscribir los observers a los carriles
        for (int i = 0 ; i < 4 ; i++) {
        	carriles[i].agregarObservador(gestorMatematico);
        	carriles[i].agregarObservador(gestorGrafico);
        }
        
        
        // Cargar la Musica (Ultimo Paso)
        AudioManager.getInstancia().reproducirCancion(this.nivel.getRutaAudio());
	}
	
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}
	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		camara.update();
		batch.setProjectionMatrix(camara.combined);
		batch.begin();
		nivel.renderizar(batch);
		gestorGrafico.renderizar(batch, AudioManager.getInstancia().getTiempoMS());
		batch.end();
	}
	
	public void dispose() {
		batch.dispose();
		texturaNotaNormal.dispose();
        texturaCuerpoLarga.dispose();
        texturaColaLarga.dispose();
        texturaReceptores.dispose();
	}
	
	@Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
