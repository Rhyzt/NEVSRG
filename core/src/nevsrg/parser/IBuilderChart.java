package nevsrg.parser;

import nevsrg.entidades.Nivel;

public interface IBuilderChart {
	public void setRutaCancion(String ruta);
	public void setArtista(String artista);
	public void setTitulo(String titulo);
	public void setMapper(String mapper);
	
	public void agregarNotaNormal(int numeroCarril, long hitTime);
	public void agregarNotaLarga(int numeroCarril, long hitTime, long duracionMS);
	
	public Nivel obtenerNivelTerminado();
}
	