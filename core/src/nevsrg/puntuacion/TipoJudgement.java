package nevsrg.puntuacion;

// Que tan bien se presiono la nota, marvelous es muy preciso, bad es el peor de todos y miss es un 0%
// Se toma como Combo Break (Accion de perder todo el combo obtenido, tambien se le conoce como CB) MISS, BAD Y GOOD
public enum TipoJudgement {
	MARVELOUS, 
	PERFECT,
	GREAT,
	GOOD,
	BAD,
	MISS
}
