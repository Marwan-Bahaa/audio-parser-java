import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileReader {

    public static MappedByteBuffer mapFile(String path) throws IOException {
        RandomAccessFile file = new RandomAccessFile(path, "r");
        FileChannel channel = file.getChannel();
        long fileSize = channel.size();
        if (fileSize == 0) throw new IOException("File is empty");

        return channel.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);
    }
}