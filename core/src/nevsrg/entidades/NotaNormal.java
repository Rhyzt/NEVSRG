package nevsrg.entidades;

import java.util.Deque;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import nevsrg.puntuacion.IObserverJudge;
import nevsrg.puntuacion.IStrategyJudge;
import nevsrg.puntuacion.TipoJudgement;

public class NotaNormal extends Nota {
	private Texture textura;
	
	public NotaNormal(long hitTime, Texture textura) {
		super(hitTime);
		this.textura= textura;
	}
	
	@Override
	public long getTiempoFin() {
		return this.getHitTime();
	}
	
	@Override
	public void dibujar(SpriteBatch batch, float x, float receptorY, float tiempoAudioActual, float scrollSpeed) {
		// Se calcula la posicion exacta de la nota segun el tiempo de la cancion
		float tiempoRestante = getHitTime() - tiempoAudioActual;
		float y = receptorY + (tiempoRestante * scrollSpeed);
		
		batch.draw(textura, x, y);
	}
	
	@Override
	public boolean debeLimpiarse(long tiempoAudioActual, List<IObserverJudge> observadores) {
		// Si la nota normal ya pasó el receptor por más de 180ms, es MISS 
		if (tiempoAudioActual - getHitTime() > 180) {
            notificarObservadores(TipoJudgement.MISS, observadores);
            return true;
        } else
            return false; 
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
		cola.poll();
	}
	
	@Override 
	public void alSoltar(Deque<Nota> cola, long tiempoAudioActual, IStrategyJudge judge, List<IObserverJudge> observadores) {
		// Las notas normales no tienen release
	}
}
