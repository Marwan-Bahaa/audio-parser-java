import java.nio.MappedByteBuffer;

public class WAVFormat implements AudioFormat {

    @Override
    public boolean matches(MappedByteBuffer buffer) {
        if (buffer.remaining() < 12) return false;

        MappedByteBuffer buf = buffer.duplicate();
        buf.position(0);

        // Check "RIFF"
        if (buf.get() != 'R' || buf.get() != 'I' || buf.get() != 'F' || buf.get() != 'F')
            return false;
        //Ignore chunkSize
        buf.position(8);
        // Check "WAVE"
        return buf.get() == 'W' && buf.get() == 'A' && buf.get() == 'V' && buf.get() == 'E';
    }

    @Override
    public String getType() {
        return "WAV";
    }

    @Override
    public AudioHeader parseHeader(MappedByteBuffer buffer) {

        MappedByteBuffer buf = buffer.duplicate();
        buf.position(0);
        buf.order(java.nio.ByteOrder.LITTLE_ENDIAN);

        // --- RIFF HEADER ---
        if (buf.remaining() < 12) return null;

        byte[] riffBytes = new byte[4];
        buf.get(riffBytes);
        String chunkId = new String(riffBytes);

        int chunkSize = buf.getInt();

        byte[] waveBytes = new byte[4];
        buf.get(waveBytes);
        String format = new String(waveBytes);

        // --- Variables ---
        String subchunk1Id = null;
        int subchunk1Size = 0;
        short audioFormat = 0;
        short numChannels = 0;
        int sampleRate = 0;
        int byteRate = 0;
        short blockAlign = 0;
        short bitsPerSample = 0;

        String subchunk2Id = null;
        int subchunk2Size = 0;

        // --- LOOP over chunks ---
        while (buf.remaining() >= 8) {

            // اقرأ ID
            byte[] idBytes = new byte[4];
            buf.get(idBytes);
            String id = new String(idBytes);

            int size = buf.getInt();

            // 🛑 Bounds check
            if (size < 0 || size > buf.remaining()) {
                break; // corrupted file
            }

            if (id.equals("fmt ")) {

                subchunk1Id = id;
                subchunk1Size = size;

                if (size < 16) break; // invalid fmt

                audioFormat = buf.getShort();
                numChannels = buf.getShort();
                sampleRate = buf.getInt();
                byteRate = buf.getInt();
                blockAlign = buf.getShort();
                bitsPerSample = buf.getShort();

                // skip extra bytes
                int remainingFmt = size - 16;
                if (remainingFmt > 0 && remainingFmt <= buf.remaining()) {
                    buf.position(buf.position() + remainingFmt);
                }

            } else if (id.equals("data")) {

                subchunk2Id = id;
                subchunk2Size = size;

                break; // reach to data group

            } else {
                // skip unknown chunk safely
                buf.position(buf.position() + size);
            }

            // Alignment fix (padding byte)
            if (size % 2 != 0 && buf.remaining() > 0) {
                buf.get(); // skip padding byte
            }
        }

        return new WAVHeader(chunkId, format, subchunk1Id, subchunk1Id,
                chunkSize, subchunk1Size, sampleRate, byteRate, subchunk2Size,
                audioFormat, numChannels, blockAlign, bitsPerSample);
    }


}