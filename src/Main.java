import java.nio.MappedByteBuffer;

public class Main {
    public static void main(String[] args) {

        String filePath = "audio.file";

        MappedByteBuffer buffer = FileReader.mapFile(filePath);

        if (buffer != null) {

            // Detect file type
            AudioDetector detector = new AudioDetector();
            String type = detector.detect(buffer);

            System.out.println("File type: " + type);

            // Display first 16 bytes as header
            buffer.position(0);
            int headerSize = Math.min(16, buffer.remaining());
            System.out.print("Header: ");
            for (int i = 0; i < headerSize; i++) {
                System.out.printf("%02X ", buffer.get());
            }
            System.out.println();

        } else {
            System.out.println("Failed to map file.");
        }
    }
}