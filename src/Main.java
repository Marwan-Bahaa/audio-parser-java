import java.io.IOException;
import java.nio.MappedByteBuffer;

public class Main {
    public static void main(String[] args) {

        String filePath0 = "C:\\Users\\Marwan_Bahaa\\Downloads\\file_example_WAV_1MG.wav";
        String filePath1 = "D:\\Downloads\\sonican-orchestral-joy-30-sec-423312.mp3";

        try {
            // Map the file into memory
            MappedByteBuffer buffer = FileReader.mapFile(filePath0);

            // Create the detector
            AudioDetector detector = new AudioDetector();

            // Detect the file format
            AudioFormat format = detector.detect(buffer);

            // Print the file type
            System.out.println("File type: " + (format != null ? format.getType() : "Unknown"));

            //print header
              AudioHeader header = format.parseHeader(buffer);
              SimpleFormatter simpleFormatter = new SimpleFormatter();
              String h = simpleFormatter.format(header);
              System.out.println(h);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}