package parks.inventorymanager.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import parks.inventorymanager.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Executes queries on the User table of the client schedule database. */
public abstract class UserQuery {

    /** Select all users. */
    public static ObservableList<User> selectAll() {
        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            String selectAllSql = "SELECT * FROM users";
            PreparedStatement selectAllPs = JDBConnection.getConnection().prepareStatement(selectAllSql);
            ResultSet resultSet = selectAllPs.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                User user = new User(userId, userName, null);
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error with the select all users method: " + e.getMessage());
        }

        return users;
    }

    /** Selects a user from the user table that matches a User_Name and Password.
     * @param name The User_Name for the query.
     * @param password The Password for the query. */
    public static User select(String name, String password) {
        String selectSql = "SELECT * FROM users WHERE User_Name=? AND Password=?";
        User authUser = null;

        try {
            PreparedStatement selectPs = JDBConnection.getConnection().prepareStatement(selectSql);
            selectPs.setString(1, name);
            selectPs.setString(2, password);
            ResultSet resultSet = selectPs.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                String userPassword = resultSet.getString("Password");
                authUser = new User(id, userName, userPassword);
            }

        } catch (SQLException e) {
            System.out.println("Error with the select user query: " + e.getMessage());
        }
        return authUser;
    }

    /** Sects a user from the user table that matches a user id.
     * @param userId The user id. */
    public static User select(int userId) {
        String selectSql = "SELECT * FROM users WHERE User_ID=?";
        User user = null;

        try {
            PreparedStatement selectPs = JDBConnection.getConnection().prepareStatement(selectSql);
            selectPs.setInt(1, userId);
            ResultSet resultSet = selectPs.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                String userPassword = resultSet.getString("Password");
                user = new User(id, userName, userPassword);
            }

        } catch (SQLException e) {
            System.out.println("Error with the select user by id query: " + e.getMessage());
        }
        return user;
    }

}
