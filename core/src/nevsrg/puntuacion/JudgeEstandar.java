package nevsrg.puntuacion;

public class JudgeEstandar implements IStrategyJudge {
	
	@Override
	public TipoJudgement evaluarJudgement(long diferenciaMS) {
		if (diferenciaMS <= 18) return TipoJudgement.MARVELOUS;
		if (diferenciaMS <= 38) return TipoJudgement.PERFECT;
		if (diferenciaMS <= 76) return TipoJudgement.GREAT;
		if (diferenciaMS <= 113) return TipoJudgement.GOOD;
		if (diferenciaMS <= 151) return TipoJudgement.BAD;
		return TipoJudgement.MISS;
	}
	
	@Override
	public TipoJudgement evaluarJudgementRelease(long diferenciaMS) {
		if (diferenciaMS <= 18 * 1.3) return TipoJudgement.MARVELOUS;
		if (diferenciaMS <= 38 * 1.3) return TipoJudgement.PERFECT;
		if (diferenciaMS <= 76 * 1.3) return TipoJudgement.GREAT;
		if (diferenciaMS <= 113 * 1.3) return TipoJudgement.GOOD;
		return TipoJudgement.BAD;
	}
}
