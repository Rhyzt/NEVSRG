package nevsrg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

public class SelectionScreen implements Screen {
	
	private Stage stage;
	private final GameNEVSRG game;
	private Skin skin;
	
	
	public SelectionScreen(GameNEVSRG game) {
		this.game = game;
	}
	
	@Override
	public void show() {
        // Cargar el canvas para colocar la tabla
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); // Habilitar la deteccion de clicks

        // Cargar estilos visuales
        skin = new Skin(Gdx.files.internal("uiskin.json")); 

        // Cargar tabla para poder colocar todos los elementos necesarios dentro de ella
        Table tablaPrincipal = new Table();
        tablaPrincipal.setFillParent(true);
        // tablaPrincipal.setDebug(true); // Ver lineas de la tabla

        // Cargar tabla de canciones
        Table tablaCanciones = new Table();

        // Se cargan los mapas encontrados en la carpeta destinada a estos
        cargarMapas(tablaCanciones);

        // el ScrollPane	
        ScrollPane scrollPane = new ScrollPane(tablaCanciones, skin);
        scrollPane.setFadeScrollBars(false); // Para que la barra de desplazamiento siempre se vea
        
        // Se coloca el ScrollPane en la tabla principal, ocupando todo el espacio
        tablaPrincipal.add(scrollPane).expand().fill();

        // Se sube la tabla principal al canvas (stage)
        stage.addActor(tablaPrincipal);
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
    
    private void cargarMapas(Table tabla) {
    	FileHandle carpetaCharts = Gdx.files.internal("charts");
    	
    	if (carpetaCharts.exists() && carpetaCharts.isDirectory()) { 
            for (FileHandle subCarpeta : carpetaCharts.list()) { // Iterar por todas las carpetas dentro de charts
            	if (subCarpeta.isDirectory()) {
            		FileHandle archivoMapa = null;

            		for (FileHandle archivo : subCarpeta.list()) {
            			if (archivo.extension().equals("osu") || archivo.extension().equals("nevsrg")) {
            				archivoMapa = archivo;
            				break;
            			}
            		}
            		// Si se encuentra un mapa, se instancia el lector respectivo
            		LectorMetadata lector = null;
                	if (archivoMapa != null) {
                		String extension = archivoMapa.extension();
                    	if (extension.equals("osu")) {
                    		lector = new MetadataOsu();
                    	} else if (extension.equals("nevsrg")) {
                    		lector = new MetadataNEVSRG();
                    	}
                    }
                	// Se extraen los datos
                	if (lector != null) {
                		final MetadataNivel metadata = lector.extraerMetadata(archivoMapa, archivoMapa.extension());
                		String textoVisual = metadata.getArtista() + " - " + metadata.getTitulo() + "\n"
                		+ "Mapper: " + metadata.getMapper();
                		final String rutaDelMapa = archivoMapa.path();
                		
                		// Se crea el boton
                		TextButton botonCancion = new TextButton(textoVisual, skin);
                		botonCancion.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                            	// Se pasan la informacion necesaria para hacer el cambio de pantalla
                            	game.setScreen(new GameScreen(game, rutaDelMapa, metadata));
                            }
                        });
                		// Se a;ade el boton a la tabla
                		tabla.add(botonCancion).fillX().padBottom(10).row();
                 	}
            	}
            }
    	 } else {
    		 // Si no existe la carpeta
	         tabla.add(new TextButton("No se encontro la carpeta /charts/", skin)).row();
    	 }
    }
}
