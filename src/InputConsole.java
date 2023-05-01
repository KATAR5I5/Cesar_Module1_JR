import java.nio.file.Path;

public interface InputConsole {
    Path getPath();
    boolean getTypeEncrypt();
    boolean getModeAutoEncrypt();
    int getKey();
}
