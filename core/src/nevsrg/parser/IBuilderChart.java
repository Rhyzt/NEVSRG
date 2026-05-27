package nevsrg.parser;

import nevsrg.entidades.Nivel;

public interface IBuilderChart {
	public void setRutaCancion(String ruta);
	
	public void agregarNotaNormal(int numeroCarril, long hitTime);
	public void agregarNotaLarga(int numeroCarril, long hitTime, long duracionMS);
	
	public Nivel obtenerNivelTerminado();
}
	