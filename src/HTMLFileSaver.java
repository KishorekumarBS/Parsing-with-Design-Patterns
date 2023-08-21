import java.io.FileWriter;
import java.io.IOException;

public class HTMLFileSaver implements FileSaver {
    private final String filePath;

    public HTMLFileSaver(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(String content) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
        }
    }
}
