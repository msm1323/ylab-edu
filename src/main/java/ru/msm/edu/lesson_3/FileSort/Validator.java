package ru.msm.edu.lesson_3.FileSort;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Scanner;

public class Validator {

    private File file;
    private BigDecimal hashSum = new BigDecimal(0);

    public BigDecimal getHashSum() {
        return hashSum;
    }

    public Validator(File file) {
        this.file = file;
    }

    public void calculateHashSum() {
        try (Scanner scanner = new Scanner(Files.newInputStream(file.toPath()))) {
            while (scanner.hasNextLine()) {
                hashSum = hashSum.add(new BigDecimal(scanner.nextLine().hashCode()));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isSorted() {
        try (Scanner scanner = new Scanner(Files.newInputStream(file.toPath()))) {
            long prev = Long.MIN_VALUE;
            while (scanner.hasNextLong()) {
                long current = scanner.nextLong();
                if (current < prev) {
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


}
