import Exeptions.MyInputException;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Scanner;

public class InputConsoleImpl implements InputConsole {

    private final int key;
    private Path path;
    private boolean typeEncrypt;
    private boolean modeEncrypt;
    private Scanner scanner = new Scanner(System.in);
    private EncryptAlphabet encryptAlphabet = EncryptAlphabet.getInstance();


    public InputConsoleImpl() {
        inputPath(scanner);
        inputTypeEncrypt(scanner);
        inputModeEncrypt(scanner);
        key = modeEncrypt ? 0 : inputKey(scanner);
    }


    public int getKey() {
        return key;
    }

    public Path getPath() {
        return path;
    }

    public boolean getTypeEncrypt() {
        return typeEncrypt;
    }

    public boolean getModeAutoEncrypt() {
        return modeEncrypt;
    }

    private void inputPath(Scanner sc) {
        String stringPath;
        int countPathAttempt = 3;
        do {
            System.out.println("Введите путь к существующему файлу с расширением .txt");
            stringPath = sc.nextLine();
            if (countPathAttempt != 0) {
                try {
                    path = Path.of(stringPath);
                } catch (InvalidPathException e) {
                    countPathAttempt--;
                    System.out.println("Ввели некорректный путь!!! Будьте внимательней! Осталось попыток ввода- " + countPathAttempt);
                }
            } else {
                throw new MyInputException();
            }
        }
        while (!(stringPath.endsWith(".txt") && Files.exists(path)));
    }

    private int inputKey(Scanner sc) {
        int keyResult = 0;
        int countKeyAttempt = 5;
        do {
            if (countKeyAttempt < 5) System.out.println("Осталось попыток ввода - " + countKeyAttempt);
            if (countKeyAttempt != 0) {
                System.out.println("Введите целочисленный ключ шифрования отличный от 0 и НЕ кратный длине алфавита шифрования.");
                System.out.printf("Длина алфавита - %d знак:  ", encryptAlphabet.getBaseCharList().size());
                String in = sc.nextLine();
                try {
                    countKeyAttempt--;
                    keyResult = Integer.parseInt(in);
                } catch (NumberFormatException ignore) {
                    System.out.println("Ввели не число - " + in);
                }
            } else {
                throw new MyInputException();
            }
        } while (keyResult == 0 || keyResult % encryptAlphabet.getBaseCharList().size() == 0);
        return keyResult;
    }

    private void inputTypeEncrypt(Scanner sc) {
        String type;
        do {
            System.out.println("Укажите РЕЖИМ шифрования файла:\n\" 1 \" - Шифрование \n\" 2 \" - Расшифровка");
            type = sc.nextLine();
            typeEncrypt = type.equals("1");
        } while (!(type.equals("1") || type.equals("2")));
    }

    private void inputModeEncrypt(Scanner sc) {
        String mode;
        do {
            System.out.println("Укажите СПОСОБ шифрования файла:\n\" a \" - Авто \n\" k \" - По ключу");
            modeEncrypt = (mode = sc.nextLine()).equals("a");
        } while (!(mode.equals("a") || mode.equals("k")));
    }


}
