package dao;

import model.Menu;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    private Connection connection;

    public MenuDAO() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void createMenu(Menu menu) throws SQLException {
        String sql = "INSERT INTO products (Name, Description, Price, Category, Image) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, menu.getName());
        statement.setString(2, menu.getDescription());
        statement.setDouble(3, menu.getPrice());
        statement.setString(4, menu.getCategory());
        statement.setString(5, menu.getImagePath());
        statement.executeUpdate();
    }

    public Menu getMenu(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE ProductID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Menu menu = new Menu();
            menu.setId(resultSet.getInt("ProductID"));
            menu.setName(resultSet.getString("Name"));
            menu.setCategory(resultSet.getString("Category"));
            menu.setDescription(resultSet.getString("Description"));
            menu.setImagePath(resultSet.getString("Image"));
            menu.setPrice(resultSet.getDouble("Price"));
            return menu;
        }
        return null;
    }

    public List<Menu> getAllMenus() throws SQLException {
        String sql = "SELECT * FROM products";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Menu> menus = new ArrayList<>();

        while (resultSet.next()) {
            Menu menu = new Menu();
            menu.setId(resultSet.getInt("ProductID"));
            menu.setName(resultSet.getString("Name"));
            menu.setCategory(resultSet.getString("Category"));
            menu.setDescription(resultSet.getString("Description"));
            menu.setImagePath(resultSet.getString("Image"));
            menu.setPrice(resultSet.getDouble("Price"));
            menus.add(menu);
        }
        return menus;
    }

    public void updateMenu(Menu menu) throws SQLException {
        String sql;
        PreparedStatement statement;

        // Check if the image path is provided
        if (menu.getImagePath() != null && !menu.getImagePath().isEmpty()) {
            // Update with the image path
            sql = "UPDATE products SET Name = ?, Description = ?, Price = ?, Category = ?, Image = ? WHERE ProductID = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, menu.getName());
            statement.setString(2, menu.getDescription());
            statement.setDouble(3, menu.getPrice());
            statement.setString(4, menu.getCategory());
            statement.setString(5, menu.getImagePath());
            statement.setInt(6, menu.getId());
        } else {
            // Update without the image path
            sql = "UPDATE products SET Name = ?, Description = ?, Price = ?, Category = ? WHERE ProductID = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, menu.getName());
            statement.setString(2, menu.getDescription());
            statement.setDouble(3, menu.getPrice());
            statement.setString(4, menu.getCategory());
            statement.setInt(5, menu.getId());
        }

        // Execute the update
        statement.executeUpdate();
    }

    public void deleteMenu(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE ProductID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
