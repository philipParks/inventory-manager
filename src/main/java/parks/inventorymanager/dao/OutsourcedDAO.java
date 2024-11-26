package parks.inventorymanager.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import parks.inventorymanager.model.Engine;
import parks.inventorymanager.model.Outsourced;
import parks.inventorymanager.model.Part;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Defines an outsourced part data access object. */
public abstract class OutsourcedDAO {

    /** Retrieves all the outsourced parts from the part table.
     * @return A list of all outsourced parts. */
    public static ObservableList<Outsourced> selectAll() {
        ObservableList<Outsourced> outsourcedParts = FXCollections.observableArrayList();
        String distributor = null;

        try {
            String selectSQL = "SELECT * FROM outsource_part";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectSQL);
            ResultSet outsourcedResultSet = selectPS.executeQuery();

            while (outsourcedResultSet.next()) {
                int distributorId = outsourcedResultSet.getInt("Distributor_id");
                int partId = outsourcedResultSet.getInt("Part_id");

                try {
                    String selectDistributor = "SELECT distributor FROM distributor WHERE distributor_id = ?";
                    PreparedStatement distributorPS = JDBConnection.getConnection().prepareStatement(selectDistributor);
                    distributorPS.setInt(1, distributorId);
                    ResultSet distributorRS = distributorPS.executeQuery();

                    while (distributorRS.next()) {
                        distributor = distributorRS.getString("Distributor");
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

                        Outsourced DBOutsourcedPart = new Outsourced(partId, part, location, distributor);

                        outsourcedParts.add(DBOutsourcedPart);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return outsourcedParts;
    }

    /** Retrieves all the outsourced parts from the part table that match a distributor id.
     * @param distributorId The distributor id to match. */
    public static ObservableList<Outsourced> selectAll(int distributorId) {
        ObservableList<Outsourced> outsourcedParts = FXCollections.observableArrayList();
        String distributor = null;

        try {
            String selectSQL = "SELECT * FROM outsource_part WHERE distributor_id = ?";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectSQL);
            selectPS.setInt(1, distributorId);
            ResultSet outsourcedResultSet = selectPS.executeQuery();

            while (outsourcedResultSet.next()) {
                int partId = outsourcedResultSet.getInt("Part_id");

                try {
                    String selectDistributor = "SELECT distributor FROM distributor WHERE distributor_id = ?";
                    PreparedStatement distributorPS = JDBConnection.getConnection().prepareStatement(selectDistributor);
                    distributorPS.setInt(1, distributorId);
                    ResultSet distributorRS = distributorPS.executeQuery();

                    while (distributorRS.next()) {
                        distributor = distributorRS.getString("Distributor");
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

                        Outsourced DBOutsourcedPart = new Outsourced(partId, part, location, distributor);

                        outsourcedParts.add(DBOutsourcedPart);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return outsourcedParts;
    }

    /** Select an engine by its id.
     * @return Requested engine. */
    public static Outsourced select(int partId) {
        Outsourced part = null;

        try {
            String selectSQL = "SELECT * FROM outsource_part WHERE part_id = ?";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectSQL);
            selectPS.setInt(1, partId);
            ResultSet resultSet = selectPS.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Engine_id");
                String name = resultSet.getString("Engine");
                String location = resultSet.getString("Location");
                String distributor = resultSet.getString("Distributor");

                part = new Outsourced(id, name, location, distributor);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return part;
    }

    /** Insert a new outsourced part into the part and outsourced table.
     * @param distributorId The distributor id if the distributor.
     * @param partId The id of the part.
     * @param user The physical location of the part.*/
    public static void insert(int distributorId, int partId, int user) {

        try {

            String insertOutsourcedPart = "INSERT INTO outsource_part (distributor_id, part_id, created_by, " +
                    "modified_by) VALUES (?, ?, ?, ?)";
            PreparedStatement outsourcedPartPS = JDBConnection.getConnection().prepareStatement(insertOutsourcedPart);
            outsourcedPartPS.setInt(1, distributorId);
            outsourcedPartPS.setInt(2, partId);
            outsourcedPartPS.setInt(3, user);
            outsourcedPartPS.setInt(4, user);
            outsourcedPartPS.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /** Updates an in house part record.
     * @param partId The part id.
     * @param distributor The distributor where the part was acquired.
     * @param part The name of the part.
     * @param location The physical location of the part. */
    public static void update(int partId, String distributor, String part, String location, int user) {

        try {
            String updatePartSQL = "UPDATE part SET part = ?, location = ?, modified_by = ? WHERE part_id = ?";
            PreparedStatement updatePartPS = JDBConnection.getConnection().prepareStatement(updatePartSQL);
            updatePartPS.setString(1, part);
            updatePartPS.setString(2, location);
            updatePartPS.setInt(3, user);
            updatePartPS.setInt(4, partId);
            updatePartPS.executeUpdate();

            String updateInhouseSQL = "UPDATE outsource_part SET distributor = ? modified_by = ? WHERE part_id = ?";
            PreparedStatement updateInhousePS = JDBConnection.getConnection().prepareStatement(updateInhouseSQL);
            updateInhousePS.setString(1, distributor);
            updateInhousePS.setInt(2, user);
            updatePartPS.setInt(3, partId);
            updateInhousePS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /** Removes an outsourced part from the part table and the outsourced part table.
     * @param partId The id of the part to remove. */
    public static void delete(int partId) {

        try {
            String deletePart = "DELETE FROM outsource_part WHERE part_id = ?";
            PreparedStatement deletePS = JDBConnection.getConnection().prepareStatement(deletePart);
            deletePS.setInt(1, partId);
            deletePS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        PartDAO.delete(partId);

    }

}
