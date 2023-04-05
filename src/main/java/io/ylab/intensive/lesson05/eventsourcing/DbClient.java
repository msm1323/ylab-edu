package io.ylab.intensive.lesson05.eventsourcing;

import io.ylab.intensive.lesson05.DbUtil;

import javax.sql.DataSource;
import java.sql.*;

public class DbClient {

    private DataSource dataSource;

    public DbClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public void executeUpdate(String query) {
        try {
            DbUtil.applyDdl(query, dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
