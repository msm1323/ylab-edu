package io.ylab.intensive.lesson04.filesort;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Scanner;

public class Validator {

    private static BigDecimal hashSum;

    public static BigDecimal calculateHashSum(File file) {
        hashSum = new BigDecimal(0);
        try (Scanner scanner = new Scanner(Files.newInputStream(file.toPath()))) {
            while (scanner.hasNextLine()) {
                hashSum = hashSum.add(new BigDecimal(scanner.nextLine().hashCode()));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return hashSum;
    }

    private static boolean isSorted(File file, boolean isAscendingOrder) {
        try (Scanner scanner = new Scanner(Files.newInputStream(file.toPath()))) {
            long prev = isAscendingOrder ? Long.MIN_VALUE : Long.MAX_VALUE;
            while (scanner.hasNextLong()) {
                long current = scanner.nextLong();

                if (isAscendingOrder && current < prev) {
                    return false;
                } else if (!isAscendingOrder && current > prev) {
                    return false;
                } else {
                    prev = current;
                }

            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean isSortedASC(File file) {
        return isSorted(file, true);
    }

    public static boolean isSortedDESC(File file) {
        return isSorted(file, false);
    }


}
