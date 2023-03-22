package ru.msm.edu.lesson_3.Transliterator;

import java.util.*;

public class TransliteratorImpl implements Transliterator {

    private Map<Character, String> transliterationTable;

    {
        transliterationTable = new HashMap<>(33);
        transliterationTable.put('А', "A");
        transliterationTable.put('Б', "B");
        transliterationTable.put('В', "V");
        transliterationTable.put('Г', "G");
        transliterationTable.put('Д', "D");
        transliterationTable.put('Е', "E");
        transliterationTable.put('Ё', "E");
        transliterationTable.put('Ж', "ZH");
        transliterationTable.put('З', "Z");
        transliterationTable.put('И', "I");
        transliterationTable.put('Й', "I");
        transliterationTable.put('К', "K");
        transliterationTable.put('Л', "L");
        transliterationTable.put('М', "M");
        transliterationTable.put('Н', "N");
        transliterationTable.put('О', "O");
        transliterationTable.put('П', "P");
        transliterationTable.put('Р', "R");
        transliterationTable.put('С', "S");
        transliterationTable.put('Т', "T");
        transliterationTable.put('У', "U");
        transliterationTable.put('Ф', "F");
        transliterationTable.put('Х', "KH");
        transliterationTable.put('Ц', "TS");
        transliterationTable.put('Ч', "CH");
        transliterationTable.put('Ш', "SH");
        transliterationTable.put('Щ', "SHCH");
        transliterationTable.put('Ъ', "IE");
        transliterationTable.put('Ы', "Y");
        transliterationTable.put('Ь', "");
        transliterationTable.put('Э', "E");
        transliterationTable.put('Ю', "IU");
        transliterationTable.put('Я', "IA");
    }

    @Override
    public String transliterate(String source) {

        for (Map.Entry<Character, String> p : transliterationTable.entrySet()) {
            source = source.replace(String.valueOf(p.getKey()), p.getValue());
        }

        return source;
    }

    //alternative variant
    public String transliterate_(String source) {
        Set<Character> ruCharacters = new HashSet<>();
        for (char c : source.toCharArray()) {
            if (ruCharacters.size() == transliterationTable.size()) {
                break;
            }
            if (c <= 1071 && c >= 1040) {
                ruCharacters.add(c);
            }
        }

        for (Character c : ruCharacters) {
            source = source.replace(c.toString(), transliterationTable.get(c));
        }

        return source;
    }

}