package nevsrg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import nevsrg.puntuacion.IStrategyJudge;
import nevsrg.puntuacion.JudgeEstandar;

public class GameScreen implements Screen {
	private SpriteBatch batch;
	FitViewport viewport;
	private OrthographicCamera camara;
	private Nivel nivel;
	
	private Texture texturaNotaNormal;
    private Texture texturaCuerpoLarga;
    private Texture texturaColaLarga;
    private Texture texturaReceptores;
	
	public GameScreen() {
		// Relacionados al dibujado
		this.camara = new OrthographicCamera();
		viewport = new FitViewport(1280, 720, camara);
		this.batch = new SpriteBatch();
		
		// Texturas
		texturaNotaNormal = new Texture("nota.png");
		texturaCuerpoLarga = new Texture("cuerpoLN.png");
		texturaColaLarga = new Texture("colaLN.png");
		texturaReceptores = new Texture("receptor.png");
		
		// Instanciar Judge
		IStrategyJudge judge = new JudgeEstandar();
		
		// Generar Carriles
		Carril[] carriles = new Carril[4];
		float anchoCarril = 128f;
		float margenIzquierdo = 384f;   
        float receptorY = 100f;         
        float scrollSpeed = 1.2f;
        
        for (int i = 0; i < 4; i++) {
            float posicionX = margenIzquierdo + (i * anchoCarril);
            carriles[i] = new Carril(posicionX, receptorY, scrollSpeed, judge, texturaReceptores);
        }
        
        ChartBuilder builder = new ChartBuilder(carriles, texturaNotaNormal, texturaCuerpoLarga, texturaColaLarga);
        BeatmapParser parser = new NEVSRGParser(builder);
        parser.procesarMapa("charts/mapa/si.nevsrg");
        this.nivel = builder.obtenerNivelTerminado();
        
        // Configurar el manejador de inputs
        InputHandler inputHandler = new InputHandler(this.nivel);
        Gdx.input.setInputProcessor(inputHandler);
        
        // Configurar la Musica
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
