import java.nio.MappedByteBuffer;

public interface AudioFormat {
    boolean matches(MappedByteBuffer buffer);
    String getType();
}