import java.nio.MappedByteBuffer;

public class WAVFormat implements AudioFormat {

    @Override
    public boolean matches(MappedByteBuffer buffer) {
        if (buffer.remaining() < 12) return false;

        buffer.position(0);
        return buffer.get() == 'R' && buffer.get() == 'I' &&
                buffer.get() == 'F' && buffer.get() == 'F' &&
                buffer.get() == 0 && buffer.get() == 0 && buffer.get() == 0 && buffer.get() == 0 &&
                buffer.get() == 'W' && buffer.get() == 'A' &&
                buffer.get() == 'V' && buffer.get() == 'E';
    }

    @Override
    public String getType() {
        return "WAV";
    }
}