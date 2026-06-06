package nevsrg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import nevsrg.config.GameSettings;
import nevsrg.entidades.GameNEVSRG;
import nevsrg.visual.Recursos;

public class SettingsScreen implements Screen {
	private Stage stage;
	private Skin skin;
	private final GameNEVSRG game;
	private final GameSettings settings;
	private TextButton[] botonesTeclas;
	private int teclaEnEspera = -1;
	
	
	public SettingsScreen(GameNEVSRG game) {
		this.game = game;
		this.settings = GameSettings.getInstancia();
	}
	 
	@Override
	public void show() {
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal(Recursos.UI_SKIN));
		botonesTeclas = new TextButton[4];
		
		Table tabla = new Table();
		tabla.setFillParent(true);
		tabla.pad(40);
		stage.addActor(tabla);
		
		Label titulo = new Label("CONFIGURACION", skin);
		titulo.setFontScale(2f);
		tabla.add(titulo).colspan(2).padBottom(35).row();
			
		int scrollSpeed = settings.getScrollSpeed();
		final Label valorScroll = new Label(textoScroll(), skin);
		final Slider sliderScroll = new Slider(GameSettings.getScrollMinimo(), GameSettings.getScrollMaximo(), 1, false, skin);
		sliderScroll.setValue(scrollSpeed);
		sliderScroll.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				settings.setScrollSpeed((int) sliderScroll.getValue());
				valorScroll.setText(textoScroll());
			}
		});
		
		tabla.add(new Label("Scroll Speed", skin)).left().padRight(30).padBottom(18);
		tabla.add(valorScroll).left().padBottom(18).row();
		tabla.add(sliderScroll).colspan(2).width(520).padBottom(32).row();
		
		tabla.add(new Label("Teclas", skin)).left().padRight(30).padBottom(12);
		Table tablaTeclas = new Table();
		for (int i = 0; i < 4; i++) {
			final int indice = i;
			botonesTeclas[i] = new TextButton(textoBotonTecla(i), skin);
			botonesTeclas[i].addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					teclaEnEspera = indice;
					actualizarBotonesTeclas();
				}
			});
			tablaTeclas.add(botonesTeclas[i]).width(115).height(45).padRight(10);
		}
		tabla.add(tablaTeclas).left().padBottom(32).row();
		
		String judge = settings.getJudge();
		final SelectBox<String> selectorJudge = new SelectBox<String>(skin);
		selectorJudge.setItems(settings.getJudgesDisponibles());
		selectorJudge.setSelected(judge);
		selectorJudge.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				settings.setJudge(selectorJudge.getSelected());
			}
		});
		
		tabla.add(new Label("Judge", skin)).left().padRight(30).padBottom(32);
		tabla.add(selectorJudge).width(240).left().padBottom(32).row();
		
		TextButton botonGuardarYVolver = new TextButton("Guardar y volver", skin);
		botonGuardarYVolver.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				settings.guardar();
				game.setScreen(new SelectionScreen(game));
			}
		});
		
		Table tablaBotones = new Table();
		tablaBotones.add(botonGuardarYVolver).width(160).height(50);
		tabla.add(tablaBotones).colspan(2).padTop(10).row();
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				if (teclaEnEspera >= 0) {
					if (keycode != Input.Keys.ESCAPE) {
						settings.setKeybind(teclaEnEspera, keycode);
					}
					teclaEnEspera = -1;
					actualizarBotonesTeclas();
					return true;
				}
				
				if (keycode == Input.Keys.ESCAPE) {
					settings.guardar();
					game.setScreen(new SelectionScreen(game));
					return true;
				}
				return false;
			}
		});
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	private String textoScroll() {
		return String.valueOf(settings.getScrollSpeed());
	}
	
	private String textoBotonTecla(int indice) {
		return "Carril " + (indice + 1) + ": " + Keys.toString(settings.getKeybind(indice));
	}
	
	private void actualizarBotonesTeclas() {
		for (int i = 0; i < botonesTeclas.length; i++) {
			if (i == teclaEnEspera) {
				botonesTeclas[i].setText("Presiona...");
			} else {
				botonesTeclas[i].setText(textoBotonTecla(i));
			}
		}
	}
	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(0.15f, 0.15f, 0.15f, 1f);
		stage.act(delta);
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override public void pause() {}
	@Override public void resume() {}
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		dispose();
	}
	
	@Override
	public void dispose() {
		if (skin != null) skin.dispose();
		if (stage != null) stage.dispose();
	}
}
