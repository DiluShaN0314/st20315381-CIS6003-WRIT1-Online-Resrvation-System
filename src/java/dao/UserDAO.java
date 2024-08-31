package dao;

import model.User;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (Username, Password, Email, RoleID, Name, ContactInfo, Image) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getEmail());
        //statement.setInt(4, user.getRoleId());
        statement.setInt(4, user.getRoleId() != 0 ? user.getRoleId() : 3);
        statement.setString(5, user.getName());
        statement.setString(6, user.getContactInfo());
        statement.setString(7, user.getImagePath());
        statement.executeUpdate();
    }

    public User getUser(int id) throws SQLException {
        String sql = "SELECT u.* , r.RoleName FROM users u LEFT JOIN roles r ON r.RoleID = u.RoleID WHERE u.UserID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("UserID"));
            user.setUsername(resultSet.getString("Username"));
            user.setPassword(resultSet.getString("Password"));
            user.setName(resultSet.getString("Name"));
            user.setEmail(resultSet.getString("Email"));
            user.setRole(resultSet.getString("RoleName"));
            user.setRoleId(resultSet.getInt("RoleID"));
            user.setContactInfo(resultSet.getString("ContactInfo"));
            user.setImagePath(resultSet.getString("Image"));
            System.out.println("getImagePath: " + resultSet.getString("Image"));
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT u.* , r.RoleName FROM users u LEFT JOIN roles r ON r.RoleID = u.RoleID";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("UserID"));
            user.setUsername(resultSet.getString("Username"));
            user.setPassword(resultSet.getString("Password"));
            user.setEmail(resultSet.getString("Email"));
            user.setRoleId(resultSet.getInt("RoleID"));
            user.setName(resultSet.getString("Name"));
            user.setContactInfo(resultSet.getString("ContactInfo"));
            user.setImagePath(resultSet.getString("Image"));
            user.setRole(resultSet.getString("RoleName"));
            users.add(user);
        }
        return users;
    }

    public void updateUser(User user) throws SQLException {
        String sql;
        PreparedStatement statement;

        // Check if the image path is provided
        if (user.getImagePath() != null && !user.getImagePath().isEmpty()) {
            sql = "UPDATE users SET Username = ?, Password = ?, Email = ?, RoleID = ?, Name = ?, ContactInfo = ?, Image = ? WHERE UserID = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getRoleId());
            statement.setString(5, user.getName());
            statement.setString(6, user.getContactInfo());
            statement.setString(7, user.getImagePath());
            statement.setInt(8, user.getId());        
        } else {
            sql = "UPDATE users SET Username = ?, Password = ?, Email = ?, RoleID = ?, Name = ?, ContactInfo = ? WHERE UserID = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getRoleId());
            statement.setString(5, user.getName());
            statement.setString(6, user.getContactInfo());
            statement.setInt(7, user.getId());       
        }
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("User updated successfully!");
        }
    }

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE UserId = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("UserID"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRoleId(resultSet.getInt("RoleID"));
                user.setEmail(resultSet.getString("Email"));
                user.setName(resultSet.getString("Name"));
                user.setContactInfo(resultSet.getString("ContactInfo"));
                user.setImagePath(resultSet.getString("Image"));
                //user.setRole(resultSet.getString("RoleName"));
                // Set other user fields if needed
                return user;
            }
        }
        return null;
    }

    public List<User> getAllStaff() throws SQLException {
        String sql = "SELECT u.* , r.RoleName FROM users u LEFT JOIN roles r ON r.RoleID = u.RoleID WHERE u.RoleID != 3";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("UserID"));
            user.setUsername(resultSet.getString("Username"));
            user.setPassword(resultSet.getString("Password"));
            user.setEmail(resultSet.getString("Email"));
            user.setRoleId(resultSet.getInt("RoleID"));
            user.setName(resultSet.getString("Name"));
            user.setContactInfo(resultSet.getString("ContactInfo"));
            user.setImagePath(resultSet.getString("Image"));
            user.setRole(resultSet.getString("RoleName"));
            users.add(user);
        }
        return users;
    }

    public List<User> getAllCustomer() throws SQLException {
        String sql = "SELECT u.* , r.RoleName FROM users u LEFT JOIN roles r ON r.RoleID = u.RoleID WHERE u.RoleID = 3";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("UserID"));
            user.setUsername(resultSet.getString("Username"));
            user.setPassword(resultSet.getString("Password"));
            user.setEmail(resultSet.getString("Email"));
            user.setRoleId(resultSet.getInt("RoleID"));
            user.setName(resultSet.getString("Name"));
            user.setContactInfo(resultSet.getString("ContactInfo"));
            user.setImagePath(resultSet.getString("Image"));
            user.setRole(resultSet.getString("RoleName"));
            users.add(user);
        }
        return users;
    }
}
