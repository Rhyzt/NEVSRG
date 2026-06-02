package nevsrg.puntuacion;

public class JudgeJustice implements IStrategyJudge {
	
	@Override
	public TipoJudgement evaluarJudgement(long diferenciaMS) {
		if (diferenciaMS <= 4) return TipoJudgement.MARVELOUS;
		if (diferenciaMS <= 9) return TipoJudgement.PERFECT;
		if (diferenciaMS <= 18) return TipoJudgement.GREAT;
		if (diferenciaMS <= 27) return TipoJudgement.GOOD;
		if (diferenciaMS <= 36) return TipoJudgement.BAD;
		return TipoJudgement.MISS;
	}
	
	@Override
	public TipoJudgement evaluarJudgementRelease(long diferenciaMS) {
		if (diferenciaMS <= 4 * 1.3) return TipoJudgement.MARVELOUS;
		if (diferenciaMS <= 9 * 1.3) return TipoJudgement.PERFECT;
		if (diferenciaMS <= 27 * 1.3) return TipoJudgement.GREAT;
		if (diferenciaMS <= 36 * 1.3) return TipoJudgement.GOOD;
		return TipoJudgement.BAD;
	}
}
