import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EncryptAlphabet {
    private static EncryptAlphabet encryptAlphabet;
    private final List<Character> baseCharList;
    private final Map<Character, Integer> baseCharMap;
    private final List<Integer> deltaIndexChars;

    private EncryptAlphabet() {
        baseCharList = baseCharList();
        baseCharMap = baseCharMap(baseCharList);
        deltaIndexChars = deltaIndexChars();
    }
    public static EncryptAlphabet getInstance() {
        if (encryptAlphabet == null) {
            encryptAlphabet = new EncryptAlphabet();
        }
        return encryptAlphabet;
    }

    private Map<Character, Integer> baseCharMap(List<Character> list) {
        return list.stream()
                .collect(Collectors.toMap(Function.identity(), e -> list.indexOf(e)));
    }
    private List<Integer> deltaIndexChars() {

        List<Integer> deltaIndexChars = new ArrayList<>();
        int indexSpase = baseCharMap.get(' ');
        int deltaDotSpase = baseCharMap.get('.') - indexSpase;
        int deltaCommaSpase = baseCharMap.get(',') - indexSpase;
        int deltaQuestionSpase = baseCharMap.get('?') - indexSpase;
        int deltaColonSpase = baseCharMap.get(':') - indexSpase;
        int deltaExclamationSpase = baseCharMap.get('!') - indexSpase;
        int deltaDashSpase = baseCharMap.get('-') - indexSpase;
        int deltaQuotesSpase = baseCharMap.get('"') - indexSpase;
        deltaIndexChars = List.of(indexSpase, deltaDotSpase, deltaColonSpase, deltaDashSpase, deltaCommaSpase,deltaQuotesSpase,deltaQuestionSpase,deltaExclamationSpase);

        return deltaIndexChars;
    }

    public List<Integer> getDeltaIndexChars() {
        return deltaIndexChars;
    }


    public List<Character> getBaseCharList() {
        return baseCharList;
    }

    public Map<Character, Integer> getBaseCharMap() {
        return baseCharMap;
    }

    private IntPredicate intPredicate = e ->
            ((e > 31 && e < 35) || (e > 43 && e < 47) || (e > 1039 && e < 1104) || e == 58 || e == 63);

    private List<Character> baseCharList() {
        IntStream intStream1 = IntStream.rangeClosed(1, 63);
        IntStream intStream2 = IntStream.rangeClosed(1040, 1103);

        return IntStream.concat(intStream1, intStream2)
                .filter(intPredicate)
                .mapToObj(e -> (char) e)
                .sorted()
                .collect(Collectors.toList());
    }

    public void printAlphabetMap() {
        System.out.println("Char - Index");
        baseCharMap.entrySet()
                .stream()
                .sorted((a, b) -> Character.compare(a.getKey(), b.getKey()))
                .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));
    }
}
