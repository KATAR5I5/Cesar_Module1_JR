import java.nio.file.Path;
import java.util.Date;
import java.util.List;

public class MainEncryption {


    public static void main(String[] args) {
        EncryptAlphabet encryptAlphabet = EncryptAlphabet.getInstance();
//        encryptAlphabet.printAlphabetMap();

//        Input metaData

        InputConsole inputConsole = new InputConsoleImpl();
        Path inputConsolePath = inputConsole.getPath();
        int inputConsoleKey = inputConsole.getKey();
        boolean inputConsoleTypeEncrypt = inputConsole.getTypeEncrypt(); // encrypt - true, unEncrypt - false
        boolean inputConsoleModeEncrypt = inputConsole.getModeAutoEncrypt(); // auto - true, key - false
//        read file
        long start = new Date().getTime();

        ReaderFile reader = new ReaderFile(inputConsolePath);
        List<String> listReadStrings = reader.getListReadStrings();

//        Encryption/ UnEncryption

        Encryption encryption = new Encryption(listReadStrings, inputConsoleTypeEncrypt, inputConsoleKey);
        List<String> encryptList = encryption.getEncryptListStrings();
        int encryptionKey = encryption.getFinalKeyEncrypt();


//        write to file
        Writer writer = new Writer();
        writer.writeToFile(encryptList, inputConsolePath, inputConsoleTypeEncrypt, encryptionKey);


        long end = new Date().getTime();
        System.out.println("Время выполнения млс:  " + (end - start));
    }
}
/*

F:\TestTextFile\JR_Modul_1\1.txt
F:\TestTextFile\JR_Modul_1\1_Encrypt.txt
F:\TestTextFile\JR_Modul_1\1_Unencrypt.txt

F:\TestTextFile\10KB.txt
F:\TestTextFile\100KB.txt

F:\TestTextFile\1MB.txt
F:\TestTextFile\10MB.txt
F:\TestTextFile\100MB.txt

F:\TestTextFile\10MB_Encrypt.txt

 */

