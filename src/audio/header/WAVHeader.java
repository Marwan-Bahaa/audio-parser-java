package audio.header;

public class WAVHeader implements AudioHeader {

    // 4 bytes → String
    private String chunkId;        // "RIFF"
    private String format;         // "WAVE"
    private String subchunk1Id;    // "fmt "
    private String subchunk2Id;    // "data"

    // 4 bytes unsigned → int
    private int chunkSize;
    private int subchunk1Size;
    private int sampleRate;
    private int byteRate;
    private int subchunk2Size;

    // 2 bytes → short
    private short audioFormat;
    private short numChannels;
    private short blockAlign;
    private short bitsPerSample;

    public WAVHeader(String chunkId, String format, String subchunk1Id, String subchunk2Id,
                     int chunkSize, int subchunk1Size, int sampleRate, int byteRate,
                     int subchunk2Size, short audioFormat, short numChannels, short blockAlign, short bitsPerSample) {
        this.chunkId = chunkId;
        this.format = format;
        this.subchunk1Id = subchunk1Id;
        this.subchunk2Id = subchunk2Id;
        this.chunkSize = chunkSize;
        this.subchunk1Size = subchunk1Size;
        this.sampleRate = sampleRate;
        this.byteRate = byteRate;
        this.subchunk2Size = subchunk2Size;
        this.audioFormat = audioFormat;
        this.numChannels = numChannels;
        this.blockAlign = blockAlign;
        this.bitsPerSample = bitsPerSample;
    }


    public double getDuration() {
        if (byteRate == 0) return 0;
        return (double) subchunk2Size / byteRate;
    }

    @Override
    public String toString() {
        return "WAV Header:\n" +
                "-------------------------\n" +
                "ChunkID        : " + chunkId + "\n" +
                "ChunkSize      : " + chunkSize + "\n" +
                "Format         : " + format + "\n" +
                "\n" +
                "Subchunk1ID    : " + subchunk1Id + "\n" +
                "Subchunk1Size  : " + subchunk1Size + "\n" +
                "audio.format.AudioFormat    : " + audioFormat + (audioFormat == 1 ? " (PCM)" : "") + "\n" +
                "NumChannels    : " + numChannels + (numChannels == 1 ? " (Mono)" : numChannels == 2 ? " (Stereo)" : "") + "\n" +
                "SampleRate     : " + sampleRate + " Hz\n" +
                "ByteRate       : " + byteRate + " B/s\n" +
                "BlockAlign     : " + blockAlign + "\n" +
                "BitsPerSample  : " + bitsPerSample + " bits\n" +
                "\n" +
                "Subchunk2ID    : " + subchunk2Id + "\n" +
                "Subchunk2Size  : " + subchunk2Size + " bytes\n" +
                "\n" +
                "Duration       : " + getDuration() + " sec\n";
    }


}