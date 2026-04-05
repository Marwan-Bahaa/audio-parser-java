import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileReader {
    public static MappedByteBuffer mapFile(String path) {
        try (RandomAccessFile file = new RandomAccessFile(path, "r");
             FileChannel channel = file.getChannel()) {

            long fileSize = channel.size();
            if (fileSize == 0) {
                System.out.println("File is empty");
                return null;
            }

            // Map the entire file into memory (read-only)
            MappedByteBuffer mappedBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);
            return mappedBuffer;

        } catch (IOException e) {
            System.out.println("Error mapping file: " + e.getMessage());
            return null;
        }
    }
}