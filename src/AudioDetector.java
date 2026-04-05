import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class AudioDetector {

    private List<AudioFormat> formats;

    public AudioDetector() {
        formats = new ArrayList<>();
        formats.add(new MP3Format());
        formats.add(new WAVFormat());
    }

    public String detect(MappedByteBuffer buffer) {
        for (AudioFormat format : formats) {
            if (format.matches(buffer)) {
                return format.getType();
            }
        }
        return "Unknown";
    }
}