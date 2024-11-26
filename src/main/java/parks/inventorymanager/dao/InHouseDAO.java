package parks.inventorymanager.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import parks.inventorymanager.model.Inhouse;
import parks.inventorymanager.model.Workstation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Defines an in house part data access object. */
public abstract class InHouseDAO {

    /** Retrieves all the in house parts from the in house table.
     * @return A list of in house parts. */
    public static ObservableList<Inhouse> selectAll() {
        ObservableList<Inhouse> inHouseParts = FXCollections.observableArrayList();
        String workstation = null;
        try {
            String selectInhouseSQL = "SELECT * FROM inhouse_part";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectInhouseSQL);
            ResultSet inhouseResultSet = selectPS.executeQuery();

            while (inhouseResultSet.next()) {
                int workstationId = inhouseResultSet.getInt("Workstation_id");
                int partId = inhouseResultSet.getInt("Part_id");

                try {
                    String selectWorkstation = "SELECT nomenclature FROM workstation WHERE workstation_id = ?";
                    PreparedStatement workstationPS = JDBConnection.getConnection().prepareStatement(selectWorkstation);
                    workstationPS.setInt(1, workstationId);
                    ResultSet workstationRS = workstationPS.executeQuery();

                    while (workstationRS.next()) {
                        workstation = workstationRS.getString("Nomenclature");
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                try {
                    String selectPartSQL = "SELECT * FROM part WHERE part_id = ?";
                    PreparedStatement selectPartPS = JDBConnection.getConnection().prepareStatement(selectPartSQL);
                    selectPartPS.setInt(1, partId);
                    ResultSet partResultSet = selectPartPS.executeQuery();

                    while (partResultSet.next()) {
                        String part = partResultSet.getString("Part");
                        String location = partResultSet.getString("Location");

                        Inhouse DBInhousePart = new Inhouse(partId, part, location, workstation);

                        inHouseParts.add(DBInhousePart);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return inHouseParts;
    }

    /** Retrieves all the in house parts from the in house table that match a workstation.
     * @param workstationId The id of the workstation to match. */
    public static ObservableList<Inhouse> selectAll(int workstationId) {
        ObservableList<Inhouse> inhouseParts = FXCollections.observableArrayList();
        String workstation = null;
        try {
            String selectSQL = "SELECT * FROM inhouse_part WHERE workstation_id = ?";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectSQL);
            selectPS.setInt(1, workstationId);
            ResultSet inhouseResultSet = selectPS.executeQuery();

            while (inhouseResultSet.next()) {
                int partId = inhouseResultSet.getInt("Part_id");

                try {
                    String selectWorkstation = "SELECT nomenclature FROM workstation WHERE workstation_id = ?";
                    PreparedStatement workstationPS = JDBConnection.getConnection().prepareStatement(selectWorkstation);
                    workstationPS.setInt(1, workstationId);
                    ResultSet workstationRS = workstationPS.executeQuery();

                    while (workstationRS.next()) {
                        workstation = workstationRS.getString("Nomenclature");
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                try {
                    String selectPartSQL = "SELECT * FROM part WHERE part_id = ?";
                    PreparedStatement selectPartPS = JDBConnection.getConnection().prepareStatement(selectPartSQL);
                    selectPartPS.setInt(1, partId);
                    ResultSet partResultSet = selectPartPS.executeQuery();

                    while (partResultSet.next()) {
                        String part = partResultSet.getString("Part");
                        String location = partResultSet.getString("Location");

                        Inhouse DBInhousePart = new Inhouse(partId, part, location, workstation);

                        inhouseParts.add(DBInhousePart);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return inhouseParts;
    }

    /** Select an in house part by its id.
     * @return Requested engine. */
    public static Inhouse select(int partId) {
        Inhouse part = null;

        try {
            String selectSQL = "SELECT * FROM inhouse_part WHERE part_id = ?";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectSQL);
            selectPS.setInt(1, partId);
            ResultSet resultSet = selectPS.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Part_id");
                String name = resultSet.getString("Part");
                String location = resultSet.getString("Location");
                String workstation = resultSet.getString("Workstation");

                part = new Inhouse(id, name, location, workstation);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return part;
    }

    /** Insert a new in house part into the part and in house table.
     * @param workstationId The id of the workstation where the part was made.
     * @param partId The id of the part.
     * @param user The user to input the part. */
    public static void insert(int workstationId, int partId, int user) {

        try {

            String insertInhousePart = "INSERT INTO inhouse_part (workstation_id, part_id, created_by, modified_by)" +
                    " VALUES (?, ?, ?, ?)";
            PreparedStatement inhousePartPS = JDBConnection.getConnection().prepareStatement(insertInhousePart);
            inhousePartPS.setInt(1, workstationId);
            inhousePartPS.setInt(2, partId);
            inhousePartPS.setInt(3, user);
            inhousePartPS.setInt(4, user);
            inhousePartPS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Updates an in house part record.
     * @param partId The part id.
     * @param workstation The workstation where the part was made.
     * @param part The name of the part.
     * @param location The physical location of the part. */
    public static void update(int partId, String workstation, String part, String location, int user) {

        try {
            String updatePartSQL = "UPDATE part SET part = ?, location = ?, modified_by = ? WHERE part_id = ?";
            PreparedStatement updatePartPS = JDBConnection.getConnection().prepareStatement(updatePartSQL);
            updatePartPS.setString(1, part);
            updatePartPS.setString(2, location);
            updatePartPS.setInt(3, user);
            updatePartPS.setInt(4, partId);
            updatePartPS.executeUpdate();

            String updateInhouseSQL = "UPDATE inhouse_part SET workstation = ? modified_by = ? WHERE part_id = ?";
            PreparedStatement updateInhousePS = JDBConnection.getConnection().prepareStatement(updateInhouseSQL);
            updateInhousePS.setString(1, workstation);
            updateInhousePS.setInt(2, user);
            updatePartPS.setInt(3, partId);
            updateInhousePS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /** Removes an in house part from the part table and the in house part table.
     * @param partId The id of the part to remove. */
    public static void delete(int partId) {

        try {
            String deletePart = "DELETE FROM inhouse_part WHERE part_id = ?";
            PreparedStatement deletePS = JDBConnection.getConnection().prepareStatement(deletePart);
            deletePS.setInt(1, partId);
            deletePS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        PartDAO.delete(partId);

    }

}
