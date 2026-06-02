package nevsrg.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import nevsrg.entidades.GameNEVSRG;
import nevsrg.puntuacion.GestorPuntuacion;
import nevsrg.puntuacion.TipoJudgement;
import nevsrg.visual.Assets;
import nevsrg.visual.Recursos;

public class ResultScreen implements Screen {

	private Stage stage;
	private final GameNEVSRG game;
	private Skin skin;
	private GestorPuntuacion informacionPuntuacion;

	public ResultScreen(GameNEVSRG game, GestorPuntuacion gestor) {
		this.game = game;
		this.informacionPuntuacion = gestor;
	}

	@Override
	public void show() {
		// Cargar el canvas para colocar la tabla
		stage = new Stage(new ScreenViewport());

		// Cargar estilos visuales
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		// Titulo
		Label titulo = new Label("RESULTADO", skin);
		titulo.setFontScale(2f);
		titulo.pack();
		titulo.setPosition(640 - titulo.getWidth() / 2f, 620);
		stage.addActor(titulo);

		// Acuraccy
		String accTexto = String.format("Accuracy: %.2f%%", informacionPuntuacion.getPrecision());
		Label accuracy = new Label(accTexto, skin);
		accuracy.pack();
		accuracy.setPosition(640 - accuracy.getWidth() / 2f, 560);
		stage.addActor(accuracy);

		// Combo
		String comboTexto = String.format("Combo %d", informacionPuntuacion.getComboMaximo());
		Label combo = new Label(comboTexto, skin);
		combo.pack();
		combo.setPosition(640 - combo.getWidth() / 2f, 510);
		stage.addActor(combo);

		// Conteo de Judges
		Table tablaJudges = new Table();
		tablaJudges.setPosition(630, 330);
		for (TipoJudgement tipo : TipoJudgement.values()) {

			Texture textura;
			switch (tipo) {
				case MARVELOUS:
					textura = Assets.getInstancia().get(Recursos.JUDGE_MARVELOUS);
					break;
				case PERFECT:
					textura = Assets.getInstancia().get(Recursos.JUDGE_PERFECT);
					break;
				case GREAT:
					textura = Assets.getInstancia().get(Recursos.JUDGE_GREAT);
					break;
				case GOOD:
					textura = Assets.getInstancia().get(Recursos.JUDGE_GOOD);
					break;
				case BAD:
					textura = Assets.getInstancia().get(Recursos.JUDGE_BAD);
					break;
				case MISS:
					textura = Assets.getInstancia().get(Recursos.JUDGE_MISS);
					break;
				default:
					textura = null;
					break;
			};

			// Imagen del judge
			Image imagenJudge = new Image(textura);
			tablaJudges.add(imagenJudge).width(120).height(40).left();

			// Cantidad
			Label cantidad = new Label(String.valueOf(informacionPuntuacion.getCantidadJudges(tipo)), skin);
			tablaJudges.add(cantidad).width(80).right();
			tablaJudges.row().padTop(8);
		}
		// Se sube la tabla de judges al canvas (stage)
		stage.addActor(tablaJudges);

		// Boton para volver
		TextButton botonVolver = new TextButton("Volver al menu", skin);
		botonVolver.setSize(200, 50);
		botonVolver.setPosition(640 - 100, 60);
		botonVolver.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new SelectionScreen(game));
			}
		});
		stage.addActor(botonVolver);

		// Para tener mas de un input handler a la vez
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.ESCAPE) {
					game.setScreen(new SelectionScreen(game));
					return true;
				}
				return false;
			}
		});
		Gdx.input.setInputProcessor(multiplexer);
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
		// Se ajustan las dimensiones en caso de que la ventana sufra un cambio de
		// tama;o
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);

		// Se libera la memoria al cambiar pantalla
		this.dispose();
	}

	@Override
	public void dispose() {
		if (skin != null)
			skin.dispose();
		if (stage != null)
			stage.dispose();
	}
}
