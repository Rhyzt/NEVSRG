package nevsrg.parser;

import com.badlogic.gdx.graphics.Texture;

import nevsrg.entidades.Carril;
import nevsrg.entidades.MetadataNivel;
import nevsrg.entidades.Nivel;
import nevsrg.entidades.NotaLarga;
import nevsrg.entidades.NotaNormal;
import nevsrg.visual.Assets;
import nevsrg.visual.Recursos;

public class ChartBuilder implements IBuilderChart {
	private Carril[] carriles;
	
	private MetadataNivel metadata;
	
	private Texture texturaNotaNormal;
    private Texture texturaCuerpoLarga;
    private Texture texturaColaLarga;

    public ChartBuilder(Carril[] carriles) {
    	this.carriles = carriles;
    	
    	this.texturaNotaNormal = Assets.getInstancia().get(Recursos.NOTA_NORMAL);
    	this.texturaCuerpoLarga = Assets.getInstancia().get(Recursos.NOTA_CUERPO);
    	this.texturaColaLarga = Assets.getInstancia().get(Recursos.NOTA_COLA);
    }
	
	public void setMetadata(MetadataNivel metadata) { this.metadata = metadata; }
	
	public void agregarNotaNormal(int numeroCarril, long hitTime) {
		NotaNormal nota = new NotaNormal(hitTime, texturaNotaNormal);
		carriles[numeroCarril].agregarNota(nota);
	}
	public void agregarNotaLarga(int numeroCarril, long hitTime, long duracionMS) {
		NotaLarga nota = new NotaLarga(hitTime, texturaNotaNormal, texturaCuerpoLarga, texturaColaLarga, duracionMS);
		carriles[numeroCarril].agregarNota(nota);
	}
	
	public Nivel obtenerNivelTerminado() {
		Nivel nivel = new Nivel(carriles, metadata);
		return nivel;
	}
}
