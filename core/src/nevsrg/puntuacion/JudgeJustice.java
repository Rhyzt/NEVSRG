package nevsrg.puntuacion;

public class JudgeJustice implements IStrategyJudge {
	
	@Override
	public TipoJudgement evaluarJudgement(long diferenciaMS) {
		if (diferenciaMS <= 11) return TipoJudgement.MARVELOUS;
		if (diferenciaMS <= 23) return TipoJudgement.PERFECT;
		if (diferenciaMS <= 45) return TipoJudgement.GREAT;
		if (diferenciaMS <= 68) return TipoJudgement.GOOD;
		if (diferenciaMS <= 90) return TipoJudgement.BAD;
		return TipoJudgement.MISS;
	}
	
	@Override
	public TipoJudgement evaluarJudgementRelease(long diferenciaMS) {
		if (diferenciaMS <= 11 * 1.3) return TipoJudgement.MARVELOUS;
		if (diferenciaMS <= 23 * 1.3) return TipoJudgement.PERFECT;
		if (diferenciaMS <= 45 * 1.3) return TipoJudgement.GREAT;
		if (diferenciaMS <= 68 * 1.3) return TipoJudgement.GOOD;
		return TipoJudgement.BAD;
	}
}
