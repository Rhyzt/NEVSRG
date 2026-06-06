package nevsrg.parser;

public class ParserFactory {
	
    public static BeatmapParser crear(String extension, IBuilderChart builder) {
        switch (extension) {
            case "osu":
            	return new OsuParser(builder);
            case "nevsrg":
            	return new NEVSRGParser(builder);
            default:
            	return null;
        }
    }
}
