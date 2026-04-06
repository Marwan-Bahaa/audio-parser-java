import java.nio.MappedByteBuffer;

public class MP3Format implements AudioFormat {

    private static final int FRAME_SYNC = 0xFF;
    private static final int FRAME_MASK = 0xE0;

    @Override
    public boolean matches(MappedByteBuffer buffer) {
        if (buffer.remaining() < 4) return false;

        MappedByteBuffer buf = buffer.duplicate();
        buf.position(0);

        byte b0 = buf.get();
        byte b1 = buf.get();
        byte b2 = buf.get();
        byte b3 = buf.get();

        final int FRAME_SYNC = 0xFF;
        final int FRAME_MASK = 0xE0;


        if ((b0 & 0xFF) == FRAME_SYNC && (b1 & FRAME_MASK) == FRAME_MASK) {

            int layer = (b1 >> 1) & 0x03;
            int bitrate = (b2 >> 4) & 0x0F;
            int sampling = (b2 >> 2) & 0x03;

            // Validation
            boolean validLayer = (layer >= 1 && layer <= 3);
            boolean validBitrate = (bitrate != 0 && bitrate != 15);
            boolean validSampling = (sampling != 3);

            return validLayer && validBitrate && validSampling;
        }

        return false;
    }

    @Override
    public String getType() {
        return "MP3";
    }

    @Override
    public AudioHeader parseHeader(MappedByteBuffer buffer) {
        MappedByteBuffer buf = buffer.duplicate();
        buf.position(0);

        boolean hasID3 = buf.get() == 'I' && buf.get() == 'D' && buf.get() == '3';

        return new MP3Header(hasID3);
    }


}