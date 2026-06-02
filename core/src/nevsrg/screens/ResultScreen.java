package nevsrg.screens;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import nevsrg.entidades.GameNEVSRG;
import nevsrg.entidades.MetadataNivel;
import nevsrg.parser.LectorMetadata;
import nevsrg.parser.MetadataNEVSRG;
import nevsrg.parser.MetadataOsu;
import nevsrg.puntuacion.GestorPuntuacion;
import nevsrg.puntuacion.TipoJudgement;

public class ResultScreen implements Screen {
	
	private Stage stage;
	private final GameNEVSRG game;
	private Skin skin;
	private GestorPuntuacion informacionPuntuacion;
	private Map<TipoJudgement, Texture> mapaTexturas;
	
	
	public ResultScreen(GameNEVSRG game, GestorPuntuacion gestor) {
		this.game = game;
		this.informacionPuntuacion = gestor;
	}
	
	@Override
	public void show() {
        // Cargar el canvas para colocar la tabla
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); // Habilitar la deteccion de clicks

        // Cargar estilos visuales
        skin = new Skin(Gdx.files.internal("uiskin.json")); 

        // Titulo
        Label titulo = new Label("RESULTADO", skin);
        titulo.setFontScale(2f);
        titulo.pack();
        titulo.setPosition(640 - titulo.getWidth() / 2f, 620);
        
        // Acuraccy
        String accTexto = String.format("Accuracy: %.2f%%", informacionPuntuacion.getPrecision());
        Label accuracy = new Label(accTexto, skin);
        accuracy.pack();
        accuracy.setPosition(640 - accuracy.getWidth() / 2f, 560);
        
        // Combo
        String comboTexto = String.format("Combo %d", informacionPuntuacion.getComboMaximo());
        Label combo = new Label(comboTexto, skin);
        combo.pack();
        combo.setPosition(640 - combo.getWidth() / 2f, 510);
        
        // Conteo de Judges
        Table tablaJudges = new Table();
        tablaJudges.setPosition(440,200);
        for (TipoJudgement judge : TipoJudgement.values()) {
        	//Imagen del judge
        	Image imagenJudge = new Image()
        }

        // Se sube la tabla principal al canvas (stage)
        //stage.addActor();
    }

    @Override
    public void render(float delta) {
    	// Limpieza
        ScreenUtils.clear(0.15f, 0.15f, 0.15f, 1f);

        // Se dibuja todo
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Se ajustan las dimensiones en caso de que la ventana sufra un cambio de tama;o
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        
        // Se libera la memoria al cambiar pantalla
        this.dispose();
    }

    @Override
    public void dispose() {
	    if (skin != null) skin.dispose();
	    if (stage != null) stage.dispose();
	    }
}
