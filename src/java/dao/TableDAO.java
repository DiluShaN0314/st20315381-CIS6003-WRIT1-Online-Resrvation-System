package dao;

import model.Table;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {
    private Connection connection;

    public TableDAO() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void createTable(Table table) throws SQLException {
        String sql = "INSERT INTO tables (capacity, status) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, table.getCapacity());
        statement.setString(2, table.getStatus());
        statement.executeUpdate();
    }

    public Table getTable(int id) throws SQLException {
        String sql = "SELECT * FROM tables WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Table table = new Table();
            table.setId(resultSet.getInt("id"));
            table.setCapacity(resultSet.getInt("capacity"));
            table.setStatus(resultSet.getString("status"));
            return table;
        }
        return null;
    }

    public List<Table> getAllTables() throws SQLException {
        String sql = "SELECT * FROM tables";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Table> tables = new ArrayList<>();

        while (resultSet.next()) {
            Table table = new Table();
            table.setId(resultSet.getInt("id"));
            table.setCapacity(resultSet.getInt("capacity"));
            table.setStatus(resultSet.getString("status"));
            tables.add(table);
        }
        return tables;
    }

    public void updateTable(Table table) throws SQLException {
        String sql = "UPDATE tables SET capacity = ?, status = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, table.getCapacity());
        statement.setString(2, table.getStatus());
        statement.setInt(3, table.getId());
        statement.executeUpdate();
    }

    public void deleteTable(int id) throws SQLException {
        String sql = "DELETE FROM tables WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
