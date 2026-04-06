package audio.format;

import audio.header.AudioHeader;

import java.nio.MappedByteBuffer;

public interface AudioFormat {
    boolean matches(MappedByteBuffer buffer);
    String getType();
    AudioHeader parseHeader(MappedByteBuffer buffer);
}