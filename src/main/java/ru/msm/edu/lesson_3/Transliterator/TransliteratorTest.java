package ru.msm.edu.lesson_3.Transliterator;

public class TransliteratorTest {

    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        String res = transliterator
                .transliterate("HELLO! ПРИВЕТ! Go, boy!");  // HELLO! PRIVET! Go, boy!
//                .transliterate("HELLO! ПРИВЕТ! кабанчик *-* ПРИВЕТ! Go, boy!");
        System.out.println(res);

    }

}
