package nevsrg.entidades;

import java.util.Deque;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import nevsrg.puntuacion.IObserverJudge;
import nevsrg.puntuacion.IStrategyJudge;
import nevsrg.puntuacion.TipoJudgement;

public class NotaLarga extends Nota {
	private Texture texturaCabeza;
	private Texture texturaCuerpo;
	private Texture texturaCola;
	private long duracionMS;
	private boolean siendoPresionada;
	private final int OFFSET;
	
	
	
	public NotaLarga(long hitTime, Texture texturaCabeza, Texture texturaCuerpo, Texture texturaCola, long duracionMS) {
		super(hitTime);
		this.texturaCabeza = texturaCabeza;
		this.texturaCuerpo = texturaCuerpo;
		this.texturaCola = texturaCola;
		this.duracionMS = duracionMS;
		this.siendoPresionada = false;
		this.OFFSET = texturaCabeza.getHeight() / 2;
	}
	
	public long getDuracionMS() { return duracionMS; }
	public boolean getSiendoPresionada() { return siendoPresionada; }
	
	@Override
	public long getTiempoFin() {
		return this.getHitTime() + this.getDuracionMS();
	}
	
	private void presionar() { siendoPresionada = true; }
	public void dejarDePresionar() { siendoPresionada = false; }
	
	@Override
	public void dibujar(SpriteBatch batch, float x, float receptorY, float tiempoAudioActual, float scrollSpeed) {
		// Calculo de posicion Y de la cabeza
		float tiempoRestanteCabeza = getHitTime() - tiempoAudioActual;
		float yCabeza = receptorY + (tiempoRestanteCabeza * scrollSpeed);
		
		// Calculo de posicion Y de la cola
		float tiempoRestanteCola = (getHitTime() + duracionMS) - tiempoAudioActual;
		float yCola = receptorY + (tiempoRestanteCola * scrollSpeed);

		// Anclar la cabeza (mientras se mantiene la nota)
		if (siendoPresionada) {
	        yCabeza = receptorY;
	    }
		//Calcular el tamaño del cuerpo
		float alturaCuerpo = Math.max(0, yCola - yCabeza);
		
		// Orden de dibujado: Cola, Cuerpo, Cabeza.
		if (yCola > receptorY) {
	        batch.draw(texturaCola, x, yCola); // Se dibuja la cola del Noodle 
	    } 

		// Aplica offset  para que aparezca en el medio de la nota y no se vea por los costados inferiores
		if (alturaCuerpo > OFFSET) {
	        batch.draw(texturaCuerpo, x, yCabeza + OFFSET, texturaCabeza.getWidth(), alturaCuerpo - OFFSET); 
	    } 

		batch.draw(texturaCabeza, x, yCabeza); // Se dibuja la cabeza del Noodle
	}
	
	@Override
	public boolean debeLimpiarse(long tiempoAudioActual, List<IObserverJudge> observadores) {
		if (!getSiendoPresionada()) {
    		// Si se ignoro la cabeza por mas de 180ms, es MISS
    		if (tiempoAudioActual - getHitTime() > 180) {
                notificarObservadores(TipoJudgement.MISS, observadores); // Miss de la cabeza
                notificarObservadores(TipoJudgement.MISS, observadores); // Miss de la cola		
                return true;
    		} else {
    			return false; // La cabeza aun no llega cerca de los receptores
    		}
    	} else { // Si es que la nota se esta presionando
    		long tiempoFin = getHitTime() + getDuracionMS();
    		
    		if (tiempoAudioActual - tiempoFin > 180) { // Si la nota nunca se solto
    			notificarObservadores(TipoJudgement.BAD, observadores); // BAD de la Cola
    			return true;
    		} else {
    			return false; // Se esta manteniendo correctamente la nota
    		}
    	}
	}
	
	@Override
	public void alPresionar(Deque<Nota> cola, long tiempoAudioActual, IStrategyJudge judge, List<IObserverJudge> observadores) {
		long diferencia = getHitTime() - tiempoAudioActual;
		if (Math.abs(diferencia) > 180) { // Evitar el miss por muy temprano 
	        return; 
	    }
		
		// Se calcula el resultado obtenido
		TipoJudgement resultado = judge.evaluarJudgement(Math.abs(diferencia));
		
		notificarObservadores(resultado, observadores);
		presionar();
	}
	
	@Override
	public void alSoltar(Deque<Nota> cola, long tiempoAudioActual, IStrategyJudge judge, List<IObserverJudge> observadores) {
	    if(!getSiendoPresionada()) {
	    	return;
	    }
	    
	    long tiempoFinNotaLarga = getDuracionMS() + getHitTime();
		long diferencia = tiempoFinNotaLarga - tiempoAudioActual;
		
		TipoJudgement resultado = judge.evaluarJudgementRelease(Math.abs(diferencia));
		notificarObservadores(resultado, observadores);
		cola.poll(); 
	}
	
	
}
