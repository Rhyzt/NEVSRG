package nevsrg.entidades;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import nevsrg.puntuacion.IStrategyJudge;
import nevsrg.puntuacion.IObserverJudge;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.LinkedList;

public class Carril {
	private Texture texturaReceptorUp;
	private Texture texturaReceptorDown;
	private IStrategyJudge judge;
	private Deque<Nota> colaNotas;
	private float posicionX;
    private float receptorY;
	private float scrollSpeed;
	private List<IObserverJudge> observadores;
	
	private float tiempoFlash = 0f;
	private final float DURACION_FLASH = 0.15f; 
	
	   
	public Carril(float posicionX, float receptorY, float scrollSpeed, IStrategyJudge judge, Texture texturaReceptorUp, Texture texturaReceptorDown) {
        this.colaNotas = new LinkedList<>();
        this.posicionX = posicionX;
        this.receptorY = receptorY;
        this.scrollSpeed = scrollSpeed; 
        this.judge = judge;
        this.texturaReceptorUp = texturaReceptorUp;
        this.texturaReceptorDown = texturaReceptorDown;
        this.observadores = new ArrayList<>();
    }
	
	public float getPosicionX() { return posicionX; }
	public float getReceptorY() { return receptorY; }
	public float getScrollSpeed() { return scrollSpeed; }
	
	public void setPosicionX(float n) { posicionX = n; }
	public void setReceptorY(float n) { receptorY = n; } 
	public void setScrollSpeed(float n) { scrollSpeed = n; }
	
	/**
	 * Renderiza las notas en pantalla
	 * @param batch
	 * @param tiempoAudioActual
	 */
	public void renderizar(SpriteBatch batch, long tiempoAudioActual, float delta) {
		// Limpieza de notas perdidas
		while (!colaNotas.isEmpty()) {
			Nota nota = colaNotas.peek();
			
            // Se verifica si debe limpiarse la nota
            if (nota.debeLimpiarse(tiempoAudioActual, observadores)) {
                colaNotas.poll();
            } else {
                break; 
            }
        }
		// Dibujado de las notas que quedan en la cola
		for (Nota nota : colaNotas) {
			float posY = receptorY + ((nota.getHitTime() - tiempoAudioActual) * scrollSpeed);  
			
			if (posY > 720) {
				break;
			}
			nota.dibujar(batch, posicionX, receptorY, tiempoAudioActual, scrollSpeed);
		}
		
		// Para efecto flash en el receptor al presionarlo
		if (tiempoFlash > 0) {
	        tiempoFlash -= delta;
	        batch.draw(texturaReceptorDown, posicionX, receptorY);

	    } else {
	        // Se dibuja el receptor normal 
	        batch.draw(texturaReceptorUp, posicionX, receptorY);
	    }
	}
	
	/**
	 * Evalua el Judgement de una pulsacion
	 * @param tiempoAudioActual punto en el que actualmente se encuentra la cancion (en ms)
	 */
	public void evaluarHit(long tiempoAudioActual) {
		// Efecto de flash en el receptor
		tiempoFlash = DURACION_FLASH;
		
		if (colaNotas.isEmpty())
			return;
		
		Nota nota = colaNotas.peek();
		nota.alPresionar(colaNotas, tiempoAudioActual, judge, observadores);
	}	
	
	/**
	 * Evalua el Judgement de un release
	 * @param tiempoAudioActual punto en el que actualmente se encuentra la cancion (en ms)
	 */
	public void evaluarRelease(long tiempoAudioActual) {
		if (colaNotas.isEmpty())
			return;
		
		Nota nota = colaNotas.peek();
	    nota.alSoltar(colaNotas, tiempoAudioActual, judge, observadores);
	}
	
	/**
	 * Agrega a la lista de observadores a los objetos que necesiten
	 * obtener informacion proporcionada por este objeto
	 * Corresponde al Pattern Design Observer
	 * @param observador el observador que se quiere agregar
	 */
	public void agregarObservador(IObserverJudge observador) {
		observadores.add(observador);
	}

	public void agregarNota(Nota nota) {
		colaNotas.add(nota);
	}
	
	public long tiempoUltimaNota() {
		if (colaNotas.isEmpty()) return 0l;
		return colaNotas.getLast().getTiempoFin();
	}
}
