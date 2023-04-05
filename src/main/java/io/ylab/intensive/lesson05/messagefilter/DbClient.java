package io.ylab.intensive.lesson05.messagefilter;

import io.ylab.intensive.lesson05.DbUtil;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.util.Scanner;

public class DbClient {

    private DataSource dataSource;
    private String tableName;
    private String columnName;
    private File swearWordsFile;

    private int BATCH_SIZE = 290;

    public void setBATCH_SIZE(int BATCH_SIZE) {
        this.BATCH_SIZE = BATCH_SIZE;
    }

    public void setSwearWordsFile(File swearWordsFile) {
        this.swearWordsFile = swearWordsFile;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public DbClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean isTableExists() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData dbMetaData = connection.getMetaData();
            ResultSet rs = dbMetaData.getTables("", "public", tableName, new String[]{"TABLE"});
            if (rs.next()) {
                rs.close();
                return true;
            }
            rs.close();
        }
        return false;
    }

    public void createTable() throws SQLException {
        String ddl = "create table " + tableName
                + "(" + columnName + " varchar primary key)";
        DbUtil.applyDdl(ddl, dataSource);
    }

    public void cleanTable() throws SQLException {
        String ddl = "TRUNCATE " + tableName;
        DbUtil.applyDdl(ddl, dataSource);
    }

    public void fillTable() {
        String insertQuery = "INSERT INTO " + tableName + "  VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
             Scanner scanner = new Scanner(Files.newInputStream(swearWordsFile.toPath()))) {
            connection.setAutoCommit(false);
            while (scanner.hasNextLine()) {
                for (int i = 0; i < BATCH_SIZE && scanner.hasNextLine(); i++) {
                    preparedStatement.setString(1, scanner.nextLine());
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

    public boolean isWordSwear(String word) {
        word = word.replaceAll("_", "\\_");
        word = word.replaceAll("%", "\\%");
        String query = "SELECT * FROM " + tableName + " WHERE " + columnName + " ILIKE '" + word + "'";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                rs.close();
                return true;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return false;
    }

}
