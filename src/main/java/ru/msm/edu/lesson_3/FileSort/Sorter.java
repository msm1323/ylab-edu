package ru.msm.edu.lesson_3.FileSort;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;

public class Sorter {
    private int primaryBlocksSize = 60_000_000;     //60_000_000

    Sorter() {
    }

    Sorter(int primaryBlocksSize) {
        this.primaryBlocksSize = primaryBlocksSize;
    }

    public File sortFile(File dataFile) throws IOException {
        ArrayList<Long> block = new ArrayList<>();
        int primaryBlocksCounter = 0;

        try (Scanner scanner = new Scanner(Files.newInputStream(dataFile.toPath()))) {
            while (scanner.hasNextLong()) {
                for (int i = 0; i < primaryBlocksSize && scanner.hasNextLong(); i++) {
                    block.add(scanner.nextLong());
                }
                Collections.sort(block);

                File file = new File("_0_" + primaryBlocksCounter);
                try (PrintWriter pw = new PrintWriter(file)) {
                    for (Long l : block) {
                        pw.println(l);
                    }
                    pw.flush();
                }

                block.clear();
                primaryBlocksCounter++;
            }
        }

        return blocksMerging(0, primaryBlocksCounter);
    }

    private File blocksMerging(int lastIteration, int lastBlocksNum) throws IOException {
        for (int i = 0; i < lastBlocksNum / 2; i++) {
            File mergeBlockFile = new File("_" + (lastIteration + 1) + "_" + i);

            String block1stFileName = "_" + lastIteration + "_" + i * 2;
            String block2ndFileName = "_" + lastIteration + "_" + (i * 2 + 1);
            try (Scanner scanner1 = new Scanner(Files.newInputStream(new File(block1stFileName).toPath()));
                 Scanner scanner2 = new Scanner(Files.newInputStream(new File(block2ndFileName).toPath()))) {

                Long block1stDigit = scanner1.hasNextLong() ? scanner1.nextLong() : null;
                Long block2ndDigit = scanner2.hasNextLong() ? scanner2.nextLong() : null;
                try (PrintWriter pw = new PrintWriter(mergeBlockFile)) {
                    while (block1stDigit != null && block2ndDigit != null) {
                        if (block1stDigit < block2ndDigit) {
                            pw.println(block1stDigit);
                            block1stDigit = scanner1.hasNextLong() ? scanner1.nextLong() : null;
                        } else {
                            pw.println(block2ndDigit);
                            block2ndDigit = scanner2.hasNextLong() ? scanner2.nextLong() : null;
                        }
                    }
                    if (block1stDigit != null) {
                        pw.println(block1stDigit);
                        while (scanner1.hasNextLong()) {
                            pw.println(scanner1.nextLong());
                        }
                    } else if (block2ndDigit != null) {
                        pw.println(block2ndDigit);
                        while (scanner2.hasNextLong()) {
                            pw.println(scanner2.nextLong());
                        }
                    }
                    pw.flush();
                }
            }

            if (!new File(block1stFileName).delete()) {
                throw new IOException("Не удалось удалить файл " + block1stFileName);
            }
            if (!new File(block2ndFileName).delete()) {
                throw new IOException("Не удалось удалить файл " + block2ndFileName);
            }

        }

        int curBlocksNum = lastBlocksNum / 2 + lastBlocksNum % 2;

        if (lastBlocksNum % 2 == 1) {
            File file = new File("_" + lastIteration + "_" + (lastBlocksNum - 1));
            File newNamedFile = new File("_" + (lastIteration + 1) + "_" + (curBlocksNum - 1));
            if (!file.renameTo(newNamedFile)) {
                throw new IOException("Не удалось переименовать файл " + file.getPath() + " в " + newNamedFile.getPath());
            }
        }

        if (curBlocksNum != 1) {
            return blocksMerging(lastIteration + 1, curBlocksNum);
        } else {
            File file = new File("_" + (lastIteration + 1) + "_0");
            File newNamedFile = new File("sortedData" + (new Date()).getTime());
            if (!file.renameTo(newNamedFile)) {
                throw new IOException("Не удалось переименовать файл " + file.getPath() + " в " + newNamedFile.getPath());
            }
            return newNamedFile;
        }
    }


}
