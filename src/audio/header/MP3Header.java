package audio.header;

public class MP3Header implements AudioHeader {

    private boolean hasID3;
    private int id3Size;

    private int bitrate;
    private int sampleRate;
    private String layer;
    private String version;

    public MP3Header(boolean hasID3, int id3Size,
                     int bitrate, int sampleRate,
                     String layer, String version) {

        this.hasID3 = hasID3;
        this.id3Size = id3Size;
        this.bitrate = bitrate;
        this.sampleRate = sampleRate;
        this.layer = layer;
        this.version = version;
    }

    @Override
    public String toString() {
        return "MP3 Header:\n" +
                "-------------------------\n" +
                "Has ID3 Tag : " + hasID3 + "\n" +
                "ID3 Size    : " + id3Size + " bytes\n\n" +

                "Version     : " + version + "\n" +
                "Layer       : " + layer + "\n" +
                "Bitrate     : " + bitrate + " kbps\n" +
                "SampleRate  : " + sampleRate + " Hz\n";
    }
}