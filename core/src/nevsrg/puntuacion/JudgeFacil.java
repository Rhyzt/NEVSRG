package nevsrg.puntuacion;

public class JudgeFacil implements IStrategyJudge {
	
	@Override
	public TipoJudgement evaluarJudgement(long diferenciaMS) {
		if (diferenciaMS <= 22) return TipoJudgement.MARVELOUS;
		if (diferenciaMS <= 45) return TipoJudgement.PERFECT;
		if (diferenciaMS <= 90) return TipoJudgement.GREAT;
		if (diferenciaMS <= 135) return TipoJudgement.GOOD;
		if (diferenciaMS <= 180) return TipoJudgement.BAD;
		return TipoJudgement.MISS;
	}
	
	@Override
	public TipoJudgement evaluarJudgementRelease(long diferenciaMS) {
		if (diferenciaMS <= 22 * 1.3) return TipoJudgement.MARVELOUS;
		if (diferenciaMS <= 45 * 1.3) return TipoJudgement.PERFECT;
		if (diferenciaMS <= 90 * 1.3) return TipoJudgement.GREAT;
		if (diferenciaMS <= 135 * 1.3) return TipoJudgement.GOOD;
		return TipoJudgement.BAD;
	}
}
