package nevsrg.entidades;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import nevsrg.puntuacion.IStrategyJudge;
import nevsrg.puntuacion.IObserverJudge;
import nevsrg.puntuacion.TipoJudgement;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.LinkedList;

public class Carril {
	private Texture texturaReceptor;
	private IStrategyJudge judge;
	private Deque<Nota> colaNotas;
	private float posicionX;
    private float receptorY;
	private float scrollSpeed;
	private List<IObserverJudge> observadores;
	
	   
	public Carril(float posicionX, float receptorY, float scrollSpeed, IStrategyJudge judge, Texture texturaReceptor) {
        this.colaNotas = new LinkedList<>();
        this.posicionX = posicionX;
        this.receptorY = receptorY;
        this.scrollSpeed = scrollSpeed; 
        this.judge = judge;
        this.texturaReceptor = texturaReceptor;
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
	public void renderizar(SpriteBatch batch, float tiempoAudioActual) {
		// Limpieza de notas perdidas
		while (!colaNotas.isEmpty()) {
			Nota notaP = colaNotas.peek();
			
			if (notaP instanceof NotaNormal) {
	            // Si la nota normal ya pasó el receptor por más de 180ms, es MISS 
	            if (tiempoAudioActual - notaP.getHitTime() > 180) {
	                notificarObservadores(TipoJudgement.MISS); 
	                colaNotas.poll();
	            } else {
	                break; 
	            }
	        } else if (notaP instanceof NotaLarga) {
	        	NotaLarga notaLarga = (NotaLarga) notaP;
	        	if (!notaLarga.getSiendoPresionada()) {
	        		// Si se ignoro la cabeza por mas de 180ms, es MISS
	        		if (tiempoAudioActual - notaLarga.getHitTime() > 180) {
	                    notificarObservadores(TipoJudgement.MISS); // Miss de la cabeza
	                    notificarObservadores(TipoJudgement.MISS); // Miss de la cola
	                    colaNotas.poll(); 		
	        		} else {
	        			break; // La cabeza aun no llega cerca de los receptores
	        		}
	        	} else { // Si es que la nota se esta presionando
	        		long tiempoFin = notaLarga.getHitTime() + notaLarga.getDuracionMS();
	        		
	        		if (tiempoAudioActual - tiempoFin > 180) { // Si la nota nunca se solto
	        			notificarObservadores(TipoJudgement.BAD); // BAD de la Cola
	        			colaNotas.poll();
	        		} else {
	        			break; // Se esta manteniendo correctamente la nota
	        		}
	        	}
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
		batch.draw(texturaReceptor, posicionX, receptorY);
	}
	
	/**
	 * Evalua el Judgement de una pulsacion
	 * @param tiempoAudioActual punto en el que actualmente se encuentra la cancion (en ms)
	 */
	public void evaluarHit(long tiempoAudioActual) {
		if (colaNotas.isEmpty())
			return;
		
		Nota nota = colaNotas.peek();
		
		long diferencia = nota.getHitTime() - tiempoAudioActual;
		if (Math.abs(diferencia) > 180) { // Evitar el miss por muy temprano 
	        return; 
	    }
			
		TipoJudgement resultado = judge.evaluarJudgement(Math.abs(diferencia));
		
		notificarObservadores(resultado);
		if(nota instanceof NotaNormal)
			colaNotas.poll();
		else {
			NotaLarga notaLarga = (NotaLarga) nota;
			notaLarga.presionar();
		}
	}	
	
	/**
	 * Evalua el Judgement de un release
	 * @param tiempoAudioActual punto en el que actualmente se encuentra la cancion (en ms)
	 */
	public void evaluarRelease(long tiempoAudioActual) {
		if (colaNotas.isEmpty())
			return;
		
		Nota nota = colaNotas.peek();
	    
	    if (!(nota instanceof NotaLarga)) {
	        return;
	    }
	    NotaLarga notaLarga = (NotaLarga) nota;
	    
	    if(!(notaLarga.getSiendoPresionada())) {
	    	return;
	    }
	    
	    long tiempoFinNotaLarga = notaLarga.getDuracionMS() + nota.getHitTime();
		long diferencia = tiempoFinNotaLarga - tiempoAudioActual;
		
		TipoJudgement resultado = judge.evaluarJudgementRelease(Math.abs(diferencia));
		notificarObservadores(resultado);
		colaNotas.poll(); 
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
	
	/**
	 * Notifica a los observadores del resultado de la pulsacion
	 * @param resultado
	 */
	private void notificarObservadores(TipoJudgement resultado) {
		for (IObserverJudge observador : observadores) {
			observador.onJudgeEvaluado(resultado);
		}
	}
	
	public void agregarNota(Nota nota) {
		colaNotas.add(nota);
	}
	
	public long tiempoUltimaNota() {
		if (colaNotas.isEmpty()) return 0l;
		return colaNotas.getLast().getTiempoFin();
	}
}
