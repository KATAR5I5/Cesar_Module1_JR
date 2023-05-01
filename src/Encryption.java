import java.util.*;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

//    List.indexOf(Obj) -> O(n),
//    List.contains(Obj) -> O(n)
//        Map.contains(key) -> O(1)
//        List.get(index) -> O(1)
//        Map.get(key) -> O(1)
public class Encryption {

    private final List<Character> baseCharList = EncryptAlphabet.getInstance().getBaseCharList();
    private final Map<Character, Integer> baseCharMap = EncryptAlphabet.getInstance().getBaseCharMap();
    private final List<String> baseListString;
    private final boolean typeEncryptionEncrypt;
    private int finalKeyEncrypt;

    public Encryption(List<String> baseListString, boolean typeEncryptionEncrypt, int keyEncrypt) {
        this.baseListString = baseListString;
        this.typeEncryptionEncrypt = typeEncryptionEncrypt;
        setModeAndFinalKey(keyEncrypt);
    }

    private void setModeAndFinalKey(int key) {
        finalKeyEncrypt = (key == 0)
                ? normalizeKey(key, typeEncryptionEncrypt, true)
                : normalizeKey(key, typeEncryptionEncrypt, false);
    }

    private String encryptionString(String string) {
        return string.chars()
                .mapToObj(e -> (char) e)
                .map(encryptUnEncrypt())
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private UnaryOperator<Character> encryptUnEncrypt() {
        return character -> {
            if (baseCharMap.containsKey(character)) {
                int keyMode = typeEncryptionEncrypt ? finalKeyEncrypt : -finalKeyEncrypt;
                int indexChEncrypt = baseCharMap.get(character) + keyMode;

                if (indexChEncrypt > baseCharList.size() - 1) {
                    indexChEncrypt -= baseCharList.size();
                    return baseCharList.get(indexChEncrypt);
                } else if (indexChEncrypt < 0) {
                    indexChEncrypt += baseCharList.size();
                    return baseCharList.get(indexChEncrypt);
                }
                return baseCharList.get(indexChEncrypt);
            }
            return character;
        };
    }

    public int getFinalKeyEncrypt() {
        return finalKeyEncrypt;
    }

    public List<String> getEncryptListStrings() {
        return baseListString.stream()
                .map(this::encryptionString)
                .collect(Collectors.toList());
    }

    private int normalizeKey(int key, boolean typeEncrypt, boolean modeEncrypt) {
        int resultKey = (Math.abs(key) > baseCharList.size()) ? key % baseCharList.size() : key;

        if (typeEncrypt) {
            if (modeEncrypt) {
                resultKey = new Random()
                        .ints(1, baseCharList.size() - 1)
                        .findFirst()
                        .getAsInt();
                System.out.println("Ключ для шифрования установлен - " + resultKey);
            }
        } else if (modeEncrypt) {
            int autokey = getKeyBruteForce();
            if (autokey != 0) {
                resultKey = autokey;
            } else {
                System.out.println("Ключ подобрать не удалось!");
                System.exit(0);
            }
        }

        return resultKey;
    }

    private int getKeyBruteForce() {
        List<Integer> deltaList = EncryptAlphabet.getInstance().getDeltaIndexChars();
        int indexSpase = baseCharMap.get(' ');
        for (String str : baseListString) {
//            System.out.println(str);
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length - 2; i++) {
                if (baseCharMap.containsKey(chars[i]) && baseCharMap.containsKey(chars[i + 1])) {
                    int indexPreChar = baseCharMap.get(chars[i]);
                    int indexFindSpase = baseCharMap.get(chars[i + 1]);
                    int deltaPreNext = indexPreChar - indexFindSpase;
                    if (deltaList.contains(deltaPreNext)) {
                        int tempKey = indexFindSpase - indexSpase;
                        if (verificationKey(tempKey)) {
                            System.out.println("Ключ подобран - " + tempKey);
                            return tempKey;
                        }
                    }
                }
            }
        }
        return 0;
    }

    int count = 1;

    private boolean verificationKey(int key) {
        System.out.printf("Попытка - %d, key - %d\n", count++, key);
        finalKeyEncrypt = key;
        Predicate<String> predicate = str -> {
            if (str != null && str.length() > 1) {
//            System.out.println(str.charAt(str.length() - 1));
                if (Character.isUpperCase(str.charAt(0)) && !Character.isLetterOrDigit(str.charAt(str.length() - 1))) {
                    return true;
                }
            }
            return false;
        };
        var result = baseListString.stream()
                .map(this::encryptionString)
                .filter(predicate)
                .findFirst();

        return result.isPresent();
    }
}

