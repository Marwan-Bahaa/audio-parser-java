package audio.core;

import audio.format.*;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class AudioDetector {

    private final List<AudioFormat> formats = new ArrayList<>();

    public AudioDetector() {
        // Add supported formats
        formats.add(new MP3Format());
        formats.add(new WAVFormat());
    }

    /**
     * Detects the first matching audio.format.AudioFormat.
     */
    public AudioFormat detect(MappedByteBuffer buffer) {
        for (AudioFormat format : formats) {
            if (format.matches(buffer)) return format;
        }
        return null; // Unknown
    }

    /**
     * Add new format dynamically.
     */
    public void addFormat(AudioFormat format) {
        formats.add(format);
    }
}