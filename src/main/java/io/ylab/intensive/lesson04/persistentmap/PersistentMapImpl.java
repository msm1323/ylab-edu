package io.ylab.intensive.lesson04.persistentmap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class PersistentMapImpl implements PersistentMap {
    private DataSource dataSource;
    private String name;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) {
        this.name = name;
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        String containsKeyQuery = "SELECT * FROM persistent_map WHERE map_name = ? AND KEY = ?";
        boolean res;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(containsKeyQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, key);
            res = preparedStatement.executeQuery().next();
        }
        return res;
    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> keysList = new ArrayList<>();
        String getKeysQuery = "SELECT KEY FROM persistent_map WHERE map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getKeysQuery)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                keysList.add(rs.getString(1));
            }
        }
        return keysList;
    }

    @Override
    public String get(String key) throws SQLException {
        String getValueByKeyQuery = "SELECT value FROM persistent_map WHERE map_name = ? AND KEY = ?";
        String value;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getValueByKeyQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, key);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            value = rs.getString(1);
        }
        return value;
    }

    @Override
    public void remove(String key) throws SQLException {
        String deletePairQuery = "DELETE FROM persistent_map WHERE map_name = ? AND KEY = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deletePairQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        remove(key);
        String insertPairQuery = "INSERT INTO persistent_map VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertPairQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, key);
            preparedStatement.setString(3, value);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void clear() throws SQLException {
        String deletePairQuery = "DELETE FROM persistent_map WHERE map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deletePairQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        }
    }
}
