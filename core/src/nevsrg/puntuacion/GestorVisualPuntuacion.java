package nevsrg.puntuacion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Map;

import nevsrg.audio.AudioManager;



public class GestorVisualPuntuacion implements IObserverJudge{
	private Texture judgeActual;
	private GestorPuntuacion gestor;
	private long tiempoAparicion;
	private long duracionMaxima;
	private BitmapFont letra;
	
	// Texturas de Judgements
	private Map<TipoJudgement, Texture> texturasJudges;
	
	public GestorVisualPuntuacion(BitmapFont letra, Map<TipoJudgement, Texture> texturasJudges, GestorPuntuacion gestor) {
		this.gestor = gestor;
		judgeActual = null;
		tiempoAparicion = 0;
		// La textura de los judges durara 1 segundo en pantalla, a menos que sea interrumpido por otro
		duracionMaxima = 1000;
		this.letra = letra;
		this.texturasJudges = texturasJudges;
	}
	
	public void onJudgeEvaluado(TipoJudgement resultado) {
		judgeActual = texturasJudges.get(resultado);
		tiempoAparicion = AudioManager.getInstancia().getTiempoMS();
	}
	
	public void renderizar(SpriteBatch batch, long tiempoAudioActual) {
		
		
		// Si aun no ha aparecido ningun judge
		if (judgeActual == null) return;
		
		// Coordenadas de centro 
		float posXCombo = 640; 
		float posYCombo = 500;
		
		// Coordenadas del Accuracy
		float posXAccuracy = 900; 
		float posYAccuracy = 500;
		
		
		// Se dibuja el combo
		if (letra != null && gestor.getComboActual() >= 5) { // Solo se mostrara el combo si es mayor a 5
			letra.draw(batch, String.valueOf(gestor.getComboActual()), posXCombo, posYCombo + 40); 
	    }
		
		// Se dibuja la precision
		if (letra != null) { 
			letra.draw(batch, String.format("%.2f", gestor.getPrecision()) + "%", posXAccuracy, posYAccuracy); 
	    }
		
		// Se dibuja el conteo de judgements
		if (letra != null) { 
			letra.draw(batch, "Marvelous: " + String.valueOf(gestor.getCantidadJudges(TipoJudgement.MARVELOUS)), posXAccuracy, posYAccuracy - 20);
			letra.draw(batch, "Perfect: " + String.valueOf(gestor.getCantidadJudges(TipoJudgement.PERFECT)), posXAccuracy, posYAccuracy - 40);
			letra.draw(batch, "Great: " + String.valueOf(gestor.getCantidadJudges(TipoJudgement.GREAT)), posXAccuracy, posYAccuracy - 60);
			letra.draw(batch, "Good: " + String.valueOf(gestor.getCantidadJudges(TipoJudgement.GOOD)), posXAccuracy, posYAccuracy - 80);
			letra.draw(batch, "Bad: " + String.valueOf(gestor.getCantidadJudges(TipoJudgement.BAD)), posXAccuracy, posYAccuracy - 100);
			letra.draw(batch, "Miss: " + String.valueOf(gestor.getCantidadJudges(TipoJudgement.MISS)), posXAccuracy, posYAccuracy - 120);
	    }
		
		// Se calcula la edad para ver si se necesita renderizar o no
		long edad = tiempoAudioActual - tiempoAparicion;
		if (edad > duracionMaxima) {
			return;
		}
		// Se calcula el porcentaje de duracionMaxima que lleva
		float progreso = (float) edad / duracionMaxima;
		
		//Fade Out
		float alpha = 1f;
		if (progreso > 0.75) {
			alpha = (1f - (progreso - 0.75f) * 4f);
		}
		batch.setColor(1f, 1f, 1f, alpha);
		
		//Scaling (Para darle animacion de rebote al momento que aparezca)
		float scale = 1f;
		if (progreso < 0.1f) { 
			// Obtenemos el progreso del rebote
	        float popProgress = progreso / 0.1f; 
	        
	        // Empieza con 130% de su tamaño y se devuelve a 100%
	        scale = 1.3f - (0.3f * popProgress); 
	    }
		
		scale = scale * 0.75f;
		
		// Centro de origen de la imagen para el scaling	
		float originX = judgeActual.getWidth() / 2f;
	    float originY = judgeActual.getHeight() / 2f;
		
		//Dibujar
		batch.draw(judgeActual, // Textura del judge
				posXCombo - originX, posYCombo - originY, // Centro donde se ubicara la textura
				originX, originY, // Punto de origen para el escalado
				judgeActual.getWidth(), judgeActual.getHeight(), // alto y largo de la textura
				scale, scale, // Escalado (usado para el efecto de rebote)
				0, // Rotacion 0
				0, 0, judgeActual.getWidth(), judgeActual.getHeight(),
				false, false);
		
		batch.setColor(1, 1, 1, 1);
	}
	
}
