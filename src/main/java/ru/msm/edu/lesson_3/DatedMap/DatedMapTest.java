package ru.msm.edu.lesson_3.DatedMap;

public class DatedMapTest {

    public static void main(String[] args) throws InterruptedException {
        DatedMapImpl datedMap = new DatedMapImpl();
        datedMap.put("Aaa", "Ccc");
        datedMap.put("First", "1th");

//        Thread.sleep(2000);

        datedMap.put("Second", "2th");

        System.out.println(datedMap.get("First"));
        System.out.println(datedMap.getKeyLastInsertionDate("First"));
        System.out.println(datedMap.getKeyLastInsertionDate("First"));
        System.out.println();

        System.out.println(datedMap.containsKey("Aaa"));
        System.out.println(datedMap.getKeyLastInsertionDate("Aaa"));
        datedMap.remove("Aaa");
        System.out.println(datedMap.containsKey("Aaa"));
        System.out.println();

        System.out.println(datedMap.getKeyLastInsertionDate("Second"));
        System.out.println(datedMap.get("Thirst"));
        System.out.println(datedMap.getKeyLastInsertionDate("Thirst"));
        System.out.println();

        System.out.println(datedMap.keySet());

    }

}
