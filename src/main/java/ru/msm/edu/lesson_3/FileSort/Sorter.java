package ru.msm.edu.lesson_3.FileSort;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;

public class Sorter {
    private int N = 60_000_000;     //60_000_000

    Sorter() {
    }

    Sorter(int N) {
        this.N = N;
    }

    public File sortFile(File dataFile) throws IOException {
        ArrayList<Long> block = new ArrayList<>();
        int blocksCounter = 0;

        //разбитие на первичные блоки размера N
        try (Scanner scanner = new Scanner(Files.newInputStream(dataFile.toPath()))) {
            while (scanner.hasNextLong()) {
                for (int i = 0; i < N && scanner.hasNextLong(); i++) {
                    block.add(scanner.nextLong());
                }
                Collections.sort(block);

                File file = new File("_0_" + blocksCounter);
                try (PrintWriter pw = new PrintWriter(file)) {
                    for (Long l : block) {
                        pw.println(l);
                    }
                    pw.flush();
                }

                block.clear();
                blocksCounter++;
            }
        }

        return blocksMerging(0, blocksCounter);
    }

    private File blocksMerging(int lastIteration, int lastBlocksNum) throws IOException {
        for (int i = 0; i < lastBlocksNum / 2; i++) {
            String block1stFileName = "_" + lastIteration + "_" + i * 2;
            String block2ndFileName = "_" + lastIteration + "_" + (i * 2 + 1);
            try (Scanner scanner1 = new Scanner(Files.newInputStream(new File(block1stFileName).toPath()));
                 Scanner scanner2 = new Scanner(Files.newInputStream(new File(block2ndFileName).toPath()))) {

                File mergeBlockFile = new File("_" + (lastIteration + 1) + "_" + i);
                Long l1 = scanner1.hasNextLong() ? scanner1.nextLong() : null;
                Long l2 = scanner2.hasNextLong() ? scanner2.nextLong() : null;
                try (PrintWriter pw = new PrintWriter(mergeBlockFile)) {
                    while (true) {
                        if (l1 != null && l2 != null) {
                            if (l1 < l2) {
                                pw.println(l1);
                                l1 = scanner1.hasNextLong() ? scanner1.nextLong() : null;
                            } else {
                                pw.println(l2);
                                l2 = scanner2.hasNextLong() ? scanner2.nextLong() : null;
                            }
                        } else if (l1 != null) {
                            pw.println(l1);
                            while (scanner1.hasNextLong()) {
                                pw.println(scanner1.nextLong());
                            }
                            break;
                        } else if (l2 != null) {
                            pw.println(l2);
                            while (scanner2.hasNextLong()) {
                                pw.println(scanner2.nextLong());
                            }
                            break;
                        } else {
                            break;
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

        int curBlocksCounter = lastBlocksNum / 2 + lastBlocksNum % 2;

        if (lastBlocksNum % 2 == 1) {
            File file = new File("_" + lastIteration + "_" + (lastBlocksNum - 1));
            File newNamedFile = new File("_" + (lastIteration + 1) + "_" + (curBlocksCounter - 1));
            if (!file.renameTo(newNamedFile)) {
                throw new IOException("Не удалось переименовать файл " + file.getPath() + " в " + newNamedFile.getPath());
            }
        }

        if (curBlocksCounter != 1) {
            return blocksMerging(lastIteration + 1, curBlocksCounter);
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
