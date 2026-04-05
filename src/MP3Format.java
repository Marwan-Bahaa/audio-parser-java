import java.nio.MappedByteBuffer;

public class MP3Format implements AudioFormat {

    @Override
    public boolean matches(MappedByteBuffer buffer) {
        if (buffer.remaining() < 4) return false;

        buffer.position(0);
        byte b0 = buffer.get();
        byte b1 = buffer.get();
        byte b2 = buffer.get();
        byte b3 = buffer.get();

        // Check ID3
        if (b0 == 'I' && b1 == 'D' && b2 == '3') {
            return true;
        }

        // Check Frame Sync
        if ((b0 & 0xFF) == 0xFF && (b1 & 0xE0) == 0xE0) {
            int layer = (b1 >> 1) & 0x03;
            int bitrate = (b2 >> 4) & 0x0F;
            int sampling = (b2 >> 2) & 0x03;

            return layer == 1 && bitrate != 0 && bitrate != 15 && sampling != 3;
        }

        return false;
    }

    @Override
    public String getType() {
        return "MP3";
    }
}