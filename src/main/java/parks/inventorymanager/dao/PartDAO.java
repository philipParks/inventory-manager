package parks.inventorymanager.dao;

import parks.inventorymanager.model.Part;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Defines a part data access object. */
public abstract class PartDAO {

    /** Insert a part into the part table.
     * @param name The name of the part.
     * @param location The physical location of the part.
     * @param user The user inserting the part. */
    public static void insert(String name, String location, int user) {

        try {
            String insertSQL = "INSERT INTO part (part, location, created_by, modified_by) VALUES (?, ?, ?, ?)";
            PreparedStatement insertPS = JDBConnection.getConnection().prepareStatement(insertSQL);
            insertPS.setString(1, name);
            insertPS.setString(2, location);
            insertPS.setInt(3, user);
            insertPS.setInt(4, user);
            insertPS.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert part error: " + e.getMessage());
        }

    }

    /** Selects a part id by the name, location and user that created the part.
     * @param name The name of the part.
     * @param location The physical location of the part.
     * @param user The user that created the part.
     * @return The id of the part that matches the parameters. */
    public static int select(String name, String location, int user) {

        int partId = 0;

        try {
            String selectPartId = "SELECT part_id FROM part WHERE part = ? AND location = ? AND created_by = ?";
            PreparedStatement selectPartIdPS = JDBConnection.getConnection().prepareStatement(selectPartId);
            selectPartIdPS.setString(1, name);
            selectPartIdPS.setString(2, location);
            selectPartIdPS.setInt(3, user);
            ResultSet resultSet = selectPartIdPS.executeQuery();

            while (resultSet.next()) {
                partId = resultSet.getInt("part_id");
            }
        } catch (SQLException e) {
            System.out.println("Select part id error: " + e.getMessage());
        }
        return partId;
    }

    /** Removes a part from the part table.
     * @param partId The id of the part to remove. */
    public static void delete(int partId) {

        try {
            String deletePart = "DELETE FROM part WHERE part_id = ?";
            PreparedStatement deletePS = JDBConnection.getConnection().prepareStatement(deletePart);
            deletePS.setInt(1, partId);
            deletePS.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete part error: " + e.getMessage());
        }

    }

}
