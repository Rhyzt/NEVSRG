package nevsrg.parser;

import nevsrg.entidades.MetadataNivel;

public interface IBuilderChart {
	public void setMetadata(MetadataNivel metadata);
	
	public void agregarNotaNormal(int numeroCarril, long hitTime);
	public void agregarNotaLarga(int numeroCarril, long hitTime, long duracionMS);

}
	