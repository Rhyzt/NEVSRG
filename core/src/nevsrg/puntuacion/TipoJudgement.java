package nevsrg.puntuacion;

import nevsrg.visual.Recursos;

// Que tan bien se presiono la nota, marvelous es muy preciso, bad es el peor de todos y miss es un 0%
// Se toma como Combo Break (Accion de perder todo el combo obtenido, tambien se le conoce como CB) MISS, BAD Y GOOD
public enum TipoJudgement {
	MARVELOUS(100, false, Recursos.JUDGE_MARVELOUS),
    PERFECT  (98,  false, Recursos.JUDGE_PERFECT),
    GREAT    (70,  false, Recursos.JUDGE_GREAT),
    GOOD     (35,  true, Recursos.JUDGE_GOOD),
    BAD      (10,  true, Recursos.JUDGE_BAD),
    MISS     (0,   true, Recursos.JUDGE_MISS);
	
	private final int peso;
	private final boolean esComboBreak;
	private final String recurso;
	
	TipoJudgement(int peso, boolean esComboBreak, String recurso) {
        this.peso = peso;
        this.esComboBreak = esComboBreak;
        this.recurso = recurso;
    }
	
	public int getPeso() { return peso; }
    public boolean esComboBreak() { return esComboBreak; }
    public String getRecurso() { return recurso; }
}
