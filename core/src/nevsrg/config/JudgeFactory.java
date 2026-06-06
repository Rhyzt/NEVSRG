package nevsrg.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import nevsrg.puntuacion.IStrategyJudge;
import nevsrg.puntuacion.JudgeDificil;
import nevsrg.puntuacion.JudgeEstandar;
import nevsrg.puntuacion.JudgeFacil;
import nevsrg.puntuacion.JudgeJustice;

public class JudgeFactory implements IJudgeValidator {
	private static final Map<String, Supplier<IStrategyJudge>> JUDGES = new LinkedHashMap<>();
	static {
	    JUDGES.put("Facil",    JudgeFacil::new);
	    JUDGES.put("Estandar", JudgeEstandar::new);
	    JUDGES.put("Dificil",  JudgeDificil::new);
	    JUDGES.put("Justice",  JudgeJustice::new);
	}
	
	@Override
	public String[] getJudgesDisponibles() {
	    return JUDGES.keySet().toArray(new String[0]);
	}
	
	@Override
	public boolean esJudgeValido(String nombre) {
		return nombre != null && JUDGES.containsKey(nombre);
	}
	
	public static IStrategyJudge crear(String nombre) {
		Supplier<IStrategyJudge> supplier = JUDGES.getOrDefault(nombre, JudgeEstandar::new);
		return supplier.get();
	}
}
