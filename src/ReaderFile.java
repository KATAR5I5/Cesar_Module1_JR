import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReaderFile {
    private List<String> listReadStrings;
    private final Path pathForRead;

    public ReaderFile(Path pathForRead) {
        this.pathForRead = pathForRead;
    }

      private void readFileInList(Path path) {
        try {
            listReadStrings = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getListReadStrings() {
        readFileInList(pathForRead);
        return listReadStrings;
    }
}


