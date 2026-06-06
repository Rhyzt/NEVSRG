package nevsrg.puntuacion;

public class JudgeDificil implements IStrategyJudge {
	 private static final long MARVELOUS_MS = 11;
	 private static final long PERFECT_MS   = 23;
	 private static final long GREAT_MS     = 45;
	 private static final long GOOD_MS      = 68;
	 private static final long BAD_MS       = 90;
	 private static final double FACTOR_RELEASE = 1.3;
	
	
	@Override
	public TipoJudgement evaluarJudgement(long diferenciaMS) {
		if (diferenciaMS <= MARVELOUS_MS) return TipoJudgement.MARVELOUS;
		if (diferenciaMS <= PERFECT_MS) return TipoJudgement.PERFECT;
		if (diferenciaMS <= GREAT_MS) return TipoJudgement.GREAT;
		if (diferenciaMS <= GOOD_MS) return TipoJudgement.GOOD;
		if (diferenciaMS <= BAD_MS) return TipoJudgement.BAD;
		return TipoJudgement.MISS;
	}
	
	@Override
	public TipoJudgement evaluarJudgementRelease(long diferenciaMS) {
		if (diferenciaMS <= MARVELOUS_MS * FACTOR_RELEASE) return TipoJudgement.MARVELOUS;
		if (diferenciaMS <= PERFECT_MS * FACTOR_RELEASE) return TipoJudgement.PERFECT;
		if (diferenciaMS <= GREAT_MS * FACTOR_RELEASE) return TipoJudgement.GREAT;
		if (diferenciaMS <= GOOD_MS * FACTOR_RELEASE) return TipoJudgement.GOOD;
		return TipoJudgement.BAD;
	}
}
