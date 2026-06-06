package nevsrg.entidades;

import java.util.Deque;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import nevsrg.puntuacion.IObserverJudge;
import nevsrg.puntuacion.IStrategyJudge;
import nevsrg.puntuacion.TipoJudgement;

public abstract class Nota {
	private long hitTime;
	
	   
	public Nota(long hitTime) {
		this.hitTime = hitTime;
	}
	
	public abstract void dibujar(SpriteBatch batch, float x, float receptorY, float tiempoAudioActual, float scrollSpeed);
	public abstract long getTiempoFin();
	public abstract boolean debeLimpiarse(long tiempoAudioActual, List<IObserverJudge> observadores);
	public abstract void alPresionar(Deque<Nota> cola, long tiempoAudioActual, IStrategyJudge judge, List<IObserverJudge> observadores);
	public abstract void alSoltar(Deque<Nota> cola, long tiempoAudioActual, IStrategyJudge judge, List<IObserverJudge> observadores);
	
	public long getHitTime() { return hitTime; }
	
	protected void notificarObservadores(TipoJudgement resultado, List<IObserverJudge> observadores) {
		for (IObserverJudge observador : observadores) {
			observador.onJudgeEvaluado(resultado);
		}
	}
}
