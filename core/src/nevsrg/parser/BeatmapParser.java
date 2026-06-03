package nevsrg.parser;

import nevsrg.entidades.MetadataNivel;

public abstract class BeatmapParser { // Director del Builder, que tambien actua como parte del Template Method
	protected IBuilderChart builder;
	
	public BeatmapParser(IBuilderChart builder) {
		this.builder = builder;
	}
	
	/* El objetivo es que se pueda adaptar un parser a cualquier otro tipo de archivo
	 * como pueden ser .sm (stepmania), .osu (osu!) o .qua (quaver)
	 * todos son un .txt basicamente pero necesitan ser leidos de diferentes maneras
	*/
	public final void procesarMapa(String rutaArchivo, MetadataNivel metadata) {
		abrirArchivo(rutaArchivo);
		builder.setMetadata(metadata);
		procesarNotas();
		cerrarArchivo();
	}
	
	protected abstract void abrirArchivo(String rutaArchivo);
    protected abstract void procesarNotas();
    protected abstract void cerrarArchivo();
}
