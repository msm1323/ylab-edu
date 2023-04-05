package io.ylab.intensive.lesson05.sqlquerybuilder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLQueryBuilderImpl implements SQLQueryBuilder {

    DataSource dataSource;

    SQLQueryBuilderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        StringBuilder resultQuery;
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData dbMetaData = connection.getMetaData();
            ResultSet rs = dbMetaData.getColumns(null, null, tableName, "%");
            if (rs.next()) {
                resultQuery = new StringBuilder("SELECT ").append(rs.getString(4));
            } else {
                return null;
            }
            while (rs.next()) {
                resultQuery.append(", ").append(rs.getString(4));
            }
            resultQuery.append(" FROM ").append(tableName);
            rs.close();
        }
        return resultQuery.toString();
    }
// todo закрыть везде resultSet и Connection

    @Override
    public List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData dbMetaData = connection.getMetaData();
           ResultSet rs = dbMetaData.getTables("", "public", null, new String[]{"TABLE"});
//            ResultSet rs = dbMetaData.getTables(null, null, null, null);
            while (rs.next()){
                tables.add(rs.getString(3));
            }
            rs.close();
        }
        return tables;
    }
}
