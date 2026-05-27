package nevsrg.puntuacion;

public interface IStrategyJudge {
	/**
     * Evalúa la precisión de una pulsacion.
     * @param diferenciaMilisegundos El valor absoluto entre
     * el tiempo de pulsar la nota y el tiempo actual de la cancion.
     * @return El juicio correspondiente (MARVELOUS, PERFECT, etc.)
     */
	TipoJudgement evaluarJudgement(long diferenciaMS);
	
	/**
     * Evalúa la precisión de un release.
     * @param diferenciaMilisegundos El valor absoluto entre
     * el tiempo de soltar la nota y el tiempo actual de la cancion.
     * @return El juicio correspondiente (MARVELOUS, PERFECT, etc.)
     */
	TipoJudgement evaluarJudgementRelease(long diferenciaMS);
}
