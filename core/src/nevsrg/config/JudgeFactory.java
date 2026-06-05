package nevsrg.config;

import nevsrg.puntuacion.IStrategyJudge;
import nevsrg.puntuacion.JudgeDificil;
import nevsrg.puntuacion.JudgeEstandar;
import nevsrg.puntuacion.JudgeFacil;
import nevsrg.puntuacion.JudgeJustice;

public class JudgeFactory {
	private static final String[] NOMBRES = { "Facil", "Estandar", "Dificil", "Justice" };
	
	public static String[] getNombres() {
		return NOMBRES;
	}
	
	public static boolean esJudgeValido(String nombre) {
		if (nombre == null) return false;
		
		for (String actual : NOMBRES) {
			if (actual.equals(nombre)) return true;
		}
		return false;
	}
	
	public static IStrategyJudge crear(String nombre) {
		if ("Facil".equals(nombre)) return new JudgeFacil();
		if ("Dificil".equals(nombre)) return new JudgeDificil();
		if ("Justice".equals(nombre)) return new JudgeJustice();
		return new JudgeEstandar();
	}
}
