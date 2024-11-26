package parks.inventorymanager.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import parks.inventorymanager.model.Workstation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Defines a workstation data access object. */
public abstract class WorkstationDAO {

    /** Retrieves all the workstations from the workstation table
     * @return A list of all workstations. */
    public static ObservableList<Workstation> selectAll() {
        ObservableList<Workstation> workstations = FXCollections.observableArrayList();

        try {
            String selectSQL = "SELECT * FROM workstation";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectSQL);
            ResultSet resultSet = selectPS.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Workstation_id");
                String workstation = resultSet.getString("Nomenclature");

                Workstation DBWorkstation = new Workstation(id, workstation);

                workstations.add(DBWorkstation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return workstations;
    }

    /** Select a workstation by id.
     * @return Requested workstation. */
    public static Workstation select(int workstationId) {
        Workstation workstation = null;

        try {
            String selectSQL = "SELECT * FROM workstation WHERE workstation_id = ?";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectSQL);
            selectPS.setInt(1, workstationId);
            ResultSet resultSet = selectPS.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Workstation_id");
                String name = resultSet.getString("Workstation");

                workstation = new Workstation(id, name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return workstation;
    }

    /** Select a workstation by nomenclature.
     * @param workstationName The nomenclature of the workstation.
     * @return The matching workstation. */
    public static Workstation select(String workstationName) {
        Workstation workstation = null;

        try {
            String selectSQL = "SELECT * FROM workstation WHERE nomenclature = ?";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectSQL);
            selectPS.setString(1, workstationName);
            ResultSet resultSet = selectPS.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Workstation_id");

                workstation = new Workstation(id, workstationName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return workstation;
    }

    /** Insert a new workstation into the workstation table.
     * @param workstation The name of the workstation.
     * @param user The user id of the user creating the workstation. */
    public static void insert(String workstation, int user) {

        try {
            String insertSQL = "INSERT INTO workstation (nomenclature, created_by, modified_by) VALUES (?, ?, ?)";
            PreparedStatement insertPS = JDBConnection.getConnection().prepareStatement(insertSQL);
            insertPS.setString(1, workstation);
            insertPS.setInt(2, user);
            insertPS.setInt(3, user);
            insertPS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /** Update a workstation.
     * @param workstationId The id of the workstation to update.
     * @param workstation The name of the workstation to update.
     * @param user The user making the update to the workstation. */
    public static void update(int workstationId, String workstation, int user) {

        try {
            String updateSQL = "UPDATE  workstation SET workstation = ?, modified_by = ? WHERE workstation_id = ?";
            PreparedStatement updatePS = JDBConnection.getConnection().prepareStatement(updateSQL);
            updatePS.setString(1, workstation);
            updatePS.setInt(2, user);
            updatePS.setInt(3, workstationId);
            updatePS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
