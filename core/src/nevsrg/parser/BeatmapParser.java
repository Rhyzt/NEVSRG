package nevsrg.parser;

import com.badlogic.gdx.files.FileHandle;

import nevsrg.entidades.MetadataNivel;

public abstract class BeatmapParser { // Director del Builder, que tambien actua como parte del Template Method
	protected final IBuilderChart builder;
	
	public BeatmapParser(IBuilderChart builder) {
		this.builder = builder;
	}
	
	/* El objetivo es que se pueda adaptar un parser a cualquier otro tipo de archivo
	 * como pueden ser .sm (stepmania), .osu (osu!) o .qua (quaver)
	 * todos son un .txt basicamente pero necesitan ser leidos de diferentes maneras
	*/
	public final void procesarMapa(MetadataNivel metadata, FileHandle archivoMapa) {
		abrirArchivo(archivoMapa);
		builder.setMetadata(metadata);
		procesarNotas();
		cerrarArchivo();
	}
	
	protected abstract void abrirArchivo(FileHandle archivoMapa);
    protected abstract void procesarNotas();
    protected abstract void cerrarArchivo();
}
