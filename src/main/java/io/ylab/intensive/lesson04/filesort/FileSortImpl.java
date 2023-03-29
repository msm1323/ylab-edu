package io.ylab.intensive.lesson04.filesort;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.*;
import java.util.Date;
import java.util.Scanner;
import javax.sql.DataSource;

public class FileSortImpl implements FileSorter {
    private DataSource dataSource;
    private final int BATCH_SIZE = 290;

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) {
        Date start = new Date();
        dataBatchInsertion(data);       //dataInsertion(data);
        Date end = new Date();
        long insertionTime = (end.getTime() - start.getTime())/1000;
        System.out.println(insertionTime / 60 + "min " + insertionTime % 60 + "sec ");  //
        String sortQuery = "SELECT * FROM numbers ORDER BY val DESC";
        File sortedFile = new File("sortedData_" + new Date().getTime());
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             PrintWriter pw = new PrintWriter(sortedFile)) {
            statement.executeQuery(sortQuery);
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                pw.println(rs.getLong(1));
                pw.flush();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return sortedFile;
    }

    private void dataBatchInsertion(File data) {
        String insertQuery = "INSERT INTO numbers (val) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
             Scanner scanner = new Scanner(Files.newInputStream(data.toPath()))) {
            connection.setAutoCommit(false);
            while (scanner.hasNextLong()) {
                for (int i = 0; i < BATCH_SIZE && scanner.hasNextLong(); i++) {
                    preparedStatement.setLong(1, scanner.nextLong());
                    preparedStatement.addBatch();
                }
                try {
                    preparedStatement.executeBatch();
                    connection.commit();
                } catch (BatchUpdateException ex) {
                    connection.rollback();
                    ex.printStackTrace();
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void dataInsertion(File data) {
        String insertQuery = "INSERT INTO numbers (val) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
             Scanner scanner = new Scanner(Files.newInputStream(data.toPath()))) {
            while (scanner.hasNextLong()) {
                preparedStatement.setLong(1, scanner.nextLong());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
