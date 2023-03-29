package ru.msm.edu.lesson_3.FileSort;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws IOException {
        File dataFile = new Generator().generate("data.txt", 100_000_000);  //375_000_000   //100_000_000
//        System.out.println(new Date() + " - Generation");

        Validator dataValidator = new Validator(dataFile);
//        dataValidator.calculateHashSum();
        System.out.println(dataValidator.isSorted()); // false
//        Date firstValidate = new Date();
//        System.out.println(firstValidate + " - Validation before sorting");

        File sortedFile = new Sorter().sortFile(dataFile);
//        Date sorted = new Date();
//        System.out.println(sorted + " - Sorting");
//        long sortTime = sorted.getTime() - firstValidate.getTime();
//        System.out.println("sortingTime = " + sortTime / 60000 + "," + sortTime % 60000);

        Validator sortedValidator = new Validator(sortedFile);
//        sortedValidator.calculateHashSum();
        System.out.println(sortedValidator.isSorted()); // true
//        System.out.println(new Date() + " - Validation after sorting");
//        System.out.println(dataValidator.getHashSum().equals(sortedValidator.getHashSum()));

    }

}
