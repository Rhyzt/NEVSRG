package nevsrg.parser;

public class LectorMetadataFactory {
    public static LectorMetadata crear(String extension) {
        switch (extension) {
            case "osu":    return new MetadataOsu();
            case "nevsrg": return new MetadataNEVSRG();
            default:       return null;
        }
    }
}
