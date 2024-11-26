package parks.inventorymanager.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import parks.inventorymanager.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Defines a user data access object.
 * @author Philip Parks */
public abstract class UserDAO {

    /** Select all users.
     * @return List of users. */
    public static ObservableList<User> selectAll() {
        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            String selectAllSql = "SELECT * FROM user";
            PreparedStatement selectAllPs = JDBConnection.getConnection().prepareStatement(selectAllSql);
            ResultSet resultSet = selectAllPs.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("User_id");
                String userName = resultSet.getString("User_name");
                boolean isAdmin = resultSet.getBoolean("Admin_account");
                User user = new User(userId, userName, null, isAdmin);
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
        String selectSql = "SELECT * FROM user WHERE user_name=? AND password=?";
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
                boolean isAdmin = resultSet.getBoolean("Admin_account");
                authUser = new User(id, userName, userPassword, isAdmin);
            }

        } catch (SQLException e) {
            System.out.println("Error with the select user query: " + e.getMessage());
        }
        return authUser;
    }

    /** Selects a user from the user table that matches a user id.
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
                boolean isAdmin = resultSet.getBoolean("Admin_account");
                user = new User(id, userName, userPassword, isAdmin);
            }

        } catch (SQLException e) {
            System.out.println("Error with the select user by id query: " + e.getMessage());
        }
        return user;
    }

    /** Inserts a user into the database.
     * @param userName The username of the user.
     * @param password The password of the user.
     * @param isAdmin The admin privileges of the user. */
    public static void insert(String userName, String password, boolean isAdmin) {

        try {
            String insertSQL = "INSERT INTO user (user_name, password, admin_account) VALUES " +
                    "(?, ?, ?)";
            PreparedStatement insertPS = JDBConnection.getConnection().prepareStatement(insertSQL);
            insertPS.setString(1, userName);
            insertPS.setString(2, password);
            insertPS.setBoolean(3, isAdmin);
            insertPS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
