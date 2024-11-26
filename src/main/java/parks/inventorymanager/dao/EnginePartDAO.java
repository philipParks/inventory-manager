package parks.inventorymanager.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import parks.inventorymanager.model.EnginePart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Defines an engine part data access object. */
public abstract class EnginePartDAO {

    /** Selects all parts associated with an engine.
     * @param engineId The id of the engine. */
    public static ObservableList<EnginePart> selectAssociatedParts(int engineId) {
        ObservableList<EnginePart> engineParts = FXCollections.observableArrayList();

        try {
            String selectEngineParts = "SELECT * FROM engine_part WHERE engine_id = ?";
            PreparedStatement selectPartsPS = JDBConnection.getConnection().prepareStatement(selectEngineParts);
            selectPartsPS.setInt(1, engineId);
            ResultSet resultSet = selectPartsPS.executeQuery();

            while (resultSet.next()) {
                int enginePartId = resultSet.getInt("Id");
                int partId = resultSet.getInt("Part_id");

                EnginePart DBEnginePart = new EnginePart(enginePartId, engineId, partId);

                engineParts.add(DBEnginePart);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return engineParts;
    }

    /** Inserts an engine part.
     * @param engineId The id of the engine the part belongs to.
     * @param partId The id of the part that is associated with the engine.
     * @param user The user adding the part to the engine. */
    public static void insert(int engineId, int partId, int user) {

        try {
            String insertSQL = "INSERT INTO engine_part (engine_id, part_id, created_by, modified_by) VALUES (?, ?, ?, ?)";
            PreparedStatement insertPS = JDBConnection.getConnection().prepareStatement(insertSQL);
            insertPS.setInt(1, engineId);
            insertPS.setInt(2, partId);
            insertPS.setInt(3, user);
            insertPS.setInt(4, user);
            insertPS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Removes a record from the engine part table.
     * @param partId The id of the part to remove from the engine part table. */
    public static void delete(int partId) {

        try {
            String deleteSQL = "DELETE FROM engine_part WHERE part_id =  ?";
            PreparedStatement deletePS = JDBConnection.getConnection().prepareStatement(deleteSQL);
            deletePS.setInt(1, partId);
            deletePS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
