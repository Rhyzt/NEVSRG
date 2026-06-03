package nevsrg.puntuacion;

import java.util.EnumMap;
import java.util.Map;

public class GestorPuntuacion implements IObserverJudge { 
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
	
	public int getCantidadJudges(TipoJudgement judge) {
		return conteoJudges.get(judge);
	}
	public int getComboActual() { return comboActual; }
	public int getComboMaximo() { return comboMaximo; }
	public float getPrecision() { return precision; }
	public int getNotasTotales() { return notasTotales; }
	
	// Sera activada en cada pulsacion o release de nota larga hecho
	@Override
	public void onJudgeEvaluado(TipoJudgement resultado) {
		int cantidadActual = conteoJudges.get(resultado);
		conteoJudges.put(resultado, cantidadActual + 1);
		
		if (resultado == TipoJudgement.MISS || resultado == TipoJudgement.BAD || resultado == TipoJudgement.GOOD) {
			// Si es un combo break, se reinicia el combo.
			if (comboActual > comboMaximo)
				comboMaximo = comboActual;
			comboActual = 0;
		} else {
			// Si no es un combo break, se aumenta el combo y se actualiza el maximo de ser necesario
			comboActual += 1;
			if (comboActual > comboMaximo) {
		        comboMaximo = comboActual;
		    }
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
	 * Se multiplican por su %, sumadas todas y luego se dividen por el total de notas multiplicado por 100
	 */
	private float calcularPrecision() {
		if (notasTotales == 0) return 100f;
		
		int maxScore =  100 * notasTotales;
		int marvelousWeight = 100 * conteoJudges.get(TipoJudgement.MARVELOUS);
		int perfectWeight = 98 * conteoJudges.get(TipoJudgement.PERFECT);
		int greatWeight = 70 * conteoJudges.get(TipoJudgement.GREAT);
		int goodWeight = 35 * conteoJudges.get(TipoJudgement.GOOD);
		int badWeight = 10 * conteoJudges.get(TipoJudgement.BAD);
		
		int sumaWeights = marvelousWeight + perfectWeight + greatWeight + goodWeight + badWeight;
		
		return ((float) sumaWeights) / maxScore * 100f;
	}
}