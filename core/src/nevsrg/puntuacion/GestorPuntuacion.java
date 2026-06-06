package nevsrg.puntuacion;

import java.util.EnumMap;
import java.util.Map;

public class GestorPuntuacion implements IObserverJudge, ILectorPuntuacion{ 
	private int comboActual;
	private int comboMaximo;
	private float precision;
	private int notasTotales;
	private Map<TipoJudgement, Integer> conteoJudges;
	
	public GestorPuntuacion() {
		this.comboActual = 0;
		this.comboMaximo = 0;
		this.precision = 100f;
		this.notasTotales = 0;
		
		// Se inicializa el mapa de los judges 
		this.conteoJudges = new EnumMap<>(TipoJudgement.class);
		
		// Se llena con ceros como valor por defecto
		for (TipoJudgement tipo : TipoJudgement.values()) { // Se recorre la lista de valores que tiene el Enum
            conteoJudges.put(tipo, 0);
        }
	}
	
	@Override
	public int getCantidadJudges(TipoJudgement judge) {
		return conteoJudges.get(judge);
	}
	
	@Override
	public int getComboActual() { return comboActual; }
	@Override
	public int getComboMaximo() { return comboMaximo; }
	@Override
	public float getPrecision() { return precision; }
	@Override
	public int getNotasTotales() { return notasTotales; }
	
	// Sera activada en cada pulsacion o release de nota larga hecho
	@Override
	public void onJudgeEvaluado(TipoJudgement resultado) {
		int cantidadActual = conteoJudges.get(resultado);
		conteoJudges.put(resultado, cantidadActual + 1);
		
		if (resultado.esComboBreak()) {
			// Si es un combo break, se reinicia el combo.
			comboActual = 0;
		} else {
			// Si no es un combo break, se aumenta el combo
			comboActual += 1;
		}
		// Si el combo actual supera al maximo, se actualiza
		if (comboActual > comboMaximo) {
	        comboMaximo = comboActual;
	    }
		// Se a;ade la nota y se recalcula la precision
		notasTotales += 1;
		precision = calcularPrecision();
	}
	
	/*
	 * Formula de la precision (ponderacion):
	 * MARVELOUS: 100%
	 * PERFECT: 98%
	 * GREAT: 70%
	 * GOOD: 35%
	 * BAD: 10%
	 * MISS: 0%
	 * 
	 * Estan en TipoJudgement
	 * Se multiplican por su %, sumadas todas y luego se dividen por el total de notas multiplicado por 100
	 */
	private float calcularPrecision() {
		if (notasTotales == 0) return 100f;
		
		int sumaWeights = 0;
		
		int maxScore =  100 * notasTotales;
		for (TipoJudgement tipo : TipoJudgement.values()) {
	        sumaWeights += tipo.getPeso() * conteoJudges.get(tipo);
	    }
		
		return ((float) sumaWeights) / maxScore * 100f;
	}
}