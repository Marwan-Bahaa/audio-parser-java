package audio.format;

import audio.header.AudioHeader;
import audio.header.MP3Header;
import java.nio.MappedByteBuffer;

public class MP3Format implements AudioFormat {

    private static final int FRAME_SYNC = 0xFF;
    private static final int FRAME_MASK = 0xE0;
    private static final int BIT_SYNC_SAFE = 0x7F;

    @Override
    public boolean matches(MappedByteBuffer buffer) {
        if (buffer.remaining() < 4) return false;

        MappedByteBuffer buf = buffer.duplicate();
        buf.position(0);

        byte b0 = buf.get();
        byte b1 = buf.get();
        byte b2 = buf.get();
        byte b3 = buf.get();


        if ((b0 & FRAME_SYNC) == FRAME_SYNC && (b1 & FRAME_MASK) == FRAME_MASK) {

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

        boolean hasID3 = false;
        int id3Size = 0;


        if (buf.remaining() >= 10 &&
                buf.get(0) == 'I' &&
                buf.get(1) == 'D' &&
                buf.get(2) == '3') {

            hasID3 = true;

            id3Size = ((buf.get(6) & BIT_SYNC_SAFE) << 21) |
                    ((buf.get(7) & BIT_SYNC_SAFE) << 14) |
                    ((buf.get(8) & BIT_SYNC_SAFE) << 7)  |
                    (buf.get(9) & BIT_SYNC_SAFE);

            id3Size += 10;
            buf.position(id3Size);
        }

        if (buf.remaining() < 4) return null;

        int b0 = buf.get() & FRAME_SYNC;
        int b1 = buf.get() & FRAME_SYNC;
        int b2 = buf.get() & FRAME_SYNC;


        if (b0 != FRAME_SYNC || (b1 & FRAME_MASK) != FRAME_MASK) return null;


        int versionBits = (b1 >> 3) & 0x03;
        int layerBits   = (b1 >> 1) & 0x03;
        int bitrateIndex = (b2 >> 4) & 0x0F;
        int sampleIndex  = (b2 >> 2) & 0x03;


        //INVALID CHECK
        if (versionBits == 1 || layerBits == 0 || bitrateIndex == 15 || sampleIndex == 3) {
            return null;
        }


        int[][][] BITRATE_TABLE = {
                {   // MPEG 2.5
                        {},
                        {0,8,16,24,32,40,48,56,64,80,96,112,128,144,160,0},
                        {0,8,16,24,32,40,48,56,64,80,96,112,128,144,160,0},
                        {0,32,48,56,64,80,96,112,128,144,160,176,192,224,256,0}
                },
                {},
                {   // MPEG 2
                        {},
                        {0,8,16,24,32,40,48,56,64,80,96,112,128,144,160,0},
                        {0,8,16,24,32,40,48,56,64,80,96,112,128,144,160,0},
                        {0,32,48,56,64,80,96,112,128,144,160,176,192,224,256,0}
                },
                {   // MPEG 1
                        {},
                        {0,32,40,48,56,64,80,96,112,128,160,192,224,256,320,0},
                        {0,32,48,56,64,80,96,112,128,160,192,224,256,320,384,0},
                        {0,32,64,96,128,160,192,224,256,288,320,352,384,416,448,0}
                }
        };

        int[][] SAMPLE_RATE_TABLE = {
                {11025, 12000, 8000, 0},
                {},
                {22050, 24000, 16000, 0},
                {44100, 48000, 32000, 0}
        };


        int bitrate = BITRATE_TABLE[versionBits][layerBits][bitrateIndex];
        int sampleRate = SAMPLE_RATE_TABLE[versionBits][sampleIndex];

        if (bitrate == 0 || sampleRate == 0) return null;


        String version = switch (versionBits) {
            case 0 -> "MPEG 2.5";
            case 2 -> "MPEG 2";
            case 3 -> "MPEG 1";
            default -> "Reserved";
        };

        String layer = switch (layerBits) {
            case 1 -> "Layer III";
            case 2 -> "Layer II";
            case 3 -> "Layer I";
            default -> "Unknown";
        };

        return new MP3Header(
                hasID3,
                id3Size,
                bitrate,
                sampleRate,
                layer,
                version
        );
    }

}