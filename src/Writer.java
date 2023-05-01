import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Writer {

    private String fileNameToWrite;
    private Path pathToWrite;

    public Writer() {
    }

    void writeToFile(List<String> list, Path path, boolean encrypt, int key) {
        fileNameToWrite = createFileName(path, encrypt);
        pathToWrite = path.getParent().resolve(fileNameToWrite);

        try {
            if (Files.notExists(pathToWrite)) {
                Files.createFile(pathToWrite);
            }
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(pathToWrite)) {
                for (String s : list) {
                    bufferedWriter.write(s + "\n");
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        verificationWriteFile(key, encrypt);
    }

    private String createFileName(Path path, boolean encrypt) {
        String subStringNewFileName;
        String shortFileName;
        String fullFileName = path.getFileName().toString();
//      add subName to end fileName in Path

        subStringNewFileName = encrypt ? "_Encrypt" : "_Unencrypt";

        int indexLast = fullFileName.lastIndexOf(".");

        shortFileName = (indexLast != -1) ? fullFileName.substring(0, indexLast) : fullFileName;

        String typeFile = fullFileName.substring(indexLast);

        if (shortFileName.contains("_Encrypt")) {
            shortFileName = shortFileName.replace("_Encrypt", subStringNewFileName);
            return shortFileName + typeFile;

        } else if (shortFileName.contains("_Unencrypt")) {
            shortFileName = shortFileName.replace("_Unencrypt", subStringNewFileName);
            return shortFileName + typeFile;
        }
        return shortFileName + subStringNewFileName + typeFile;
    }

    private void verificationWriteFile(int key, boolean encrypt) {
        StringBuilder sb = new StringBuilder();
        String encryption = encrypt ? "шифрованным" : "расшифрованным";
        if (Files.exists(pathToWrite)) {
            sb.append("-----------------------------------------\n");
            sb.append(String.format("Файл с %s текстом создан.\nИмя файла:  %s\nПуть к файлу:  %s\n", encryption, fileNameToWrite, pathToWrite));
            if (encrypt) sb.append("Ключ для дешифрования:  " + key + "\n");
            sb.append("-----------------------------------------");
        } else {
            System.out.println("А где файл??? Что-то пошло не так");
        }
        System.out.println(sb);
    }
}
