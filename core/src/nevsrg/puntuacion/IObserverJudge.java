package nevsrg.puntuacion;

/*
 *  Se usara con el Observer Pattern, con el fin
 *  de ir avisando de las puntuaciones que el jugador
 *  vaya obteniendo para asi actualizar cosas como el 
 *  combo o la precision.
 */
public interface IObserverJudge {
	
	// Para cada cosa que quiera observar cuando se evalue una pulsacion
	public void onJudgeEvaluado(TipoJudgement resultado);
}
