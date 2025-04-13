package parks.inventorymanager.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import parks.inventorymanager.model.Engine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Defines an engine data access object. */
public abstract class EngineDAO {

    /** Retrieves all the engines from the engine table.
     * @return A list of all engines. */
    public static ObservableList<Engine> selectAll() {
        ObservableList<Engine> engines = FXCollections.observableArrayList();

        try {
            String selectSQL = "SELECT * FROM engine";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectSQL);
            ResultSet resultSet = selectPS.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Engine_id");
                String engine = resultSet.getString("Engine");
                String location = resultSet.getString("Location");
                String status = resultSet.getString("Status");
                String fuelType = resultSet.getString("Fuel_type");
                String serialNumber = resultSet.getString("Serial_number");
                String cylinder = resultSet.getString("Cylinder");

                Engine DBEngine = new Engine(id, engine, location, status, fuelType, serialNumber,
                        cylinder);

                engines.add(DBEngine);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return engines;
    }

    /** Select all engines with a specific status.
     * @param status The status of the engine.
     * @return Engines with the parameter status. */
    public static ObservableList<Engine> selectAll(String status) {
        ObservableList<Engine> engines = FXCollections.observableArrayList();

        try {
            String selectSQL = "SELECT * FROM engine WHERE status = ?";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectSQL);
            selectPS.setString(1, status);
            ResultSet resultSet = selectPS.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Engine_id");
                String engine = resultSet.getString("Engine");
                String location = resultSet.getString("Location");
                String fuelType = resultSet.getString("Fuel_type");
                String serialNumber = resultSet.getString("Serial_number");
                String cylinder = resultSet.getString("Cylinder");

                Engine DBEngine = new Engine(id, engine, location, status, fuelType, serialNumber,
                        cylinder);

                engines.add(DBEngine);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return engines;
    }

    /** Select an engine by its id.
     * @param engineId The id of the engine.
     * @return Requested engine. */
    public static Engine select(int engineId) {
        Engine engine = null;

        try {
            String selectSQL = "SELECT * FROM engine WHERE engine_id = ?";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectSQL);
            selectPS.setInt(1, engineId);
            ResultSet resultSet = selectPS.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Engine_id");
                String nomenclature = resultSet.getString("Engine");
                String location = resultSet.getString("Location");
                String status = resultSet.getString("Status");
                String fuelType = resultSet.getString("Fuel_type");
                String serialNumber = resultSet.getString("Serial_number");
                String cylinder = resultSet.getString("Cylinder");

                engine = new Engine(id, nomenclature, location, status, fuelType, serialNumber,
                        cylinder);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return engine;
    }

    /** Select an engine by its name.
     * @param engineName The nomenclature of the engine.
     * @return Requested engine. */
    public static Engine select(String engineName) {
        Engine engine = null;

        try {
            String selectSQL = "SELECT * FROM engine WHERE nomenclature = ?";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectSQL);
            selectPS.setString(1, engineName);
            ResultSet resultSet = selectPS.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Engine_id");
                String nomenclature = resultSet.getString("Engine");
                String location = resultSet.getString("Location");
                String status = resultSet.getString("Status");
                String fuelType = resultSet.getString("Fuel_type");
                String serialNumber = resultSet.getString("Serial_number");
                String cylinder = resultSet.getString("Cylinder");

                engine = new Engine(id, nomenclature, location, status, fuelType, serialNumber,
                        cylinder);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return engine;
    }

    /** Gets the unique status strings from the engine table.
     * @return A list of unique status'. */
    public static ObservableList<String> getStatus() {
        ObservableList<String> statusList = FXCollections.observableArrayList();

        try {
            String getStatus = "SELECT status FROM engine";
            PreparedStatement getStatusPS = JDBConnection.getConnection().prepareStatement(getStatus);
            ResultSet resultSet = getStatusPS.executeQuery();

            while (resultSet.next()) {
                String status = resultSet.getString("Status");

                statusList.add(status);
            }

            for (String status : statusList) {
                if (!statusList.contains(status)) {
                    statusList.add(status);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return statusList;
    }

    /** Insert a new engine into the engine table.
     * @param name The nomenclature for the engine.
     * @param location The physical location of the engine.
     * @param status The status of the engine.
     * @param fuelType The fuel type of the engine.
     * @param serialNumber The serial number of the engine.
     * @param cylinder The cylinder count and configuration of the engine.*/
    public static void insert(String name, String location, String status, String fuelType,
                              String serialNumber, String cylinder, int user) {

        try {
            String insertSQL = "INSERT INTO engine (engine, location, status, fuel_type, " +
                    "serial_number, cylinder, created_by, modified_by)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertPS = JDBConnection.getConnection().prepareStatement(insertSQL);
            insertPS.setString(1, name);
            insertPS.setString(2, location);
            insertPS.setString(3, status);
            insertPS.setString(4, fuelType);
            insertPS.setString(5, serialNumber);
            insertPS.setString(6, cylinder);
            insertPS.setInt(7, user);
            insertPS.setInt(8, user);
            insertPS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Updates an engine record.
     * @param engineId The id of the engine to update.
     * @param name The nomenclature for the engine.
     * @param location The physical location of the engine.
     * @param status The status of the engine.
     * @param fuelType The fuel type of the engine.
     * @param serialNumber The serial number of the engine.
     * @param cylinder The cylinder count and configuration of the engine.*/
    public static void update(int engineId, String name, String location, String status,
                              String fuelType, String serialNumber, String cylinder, int user) {

        try {
            String updateSQL = "UPDATE engine SET engine = ?, location = ?, status = ?, fuel_type = ?, " +
                    "serial_number = ?, cylinder = ?, modified_by = ? WHERE engine_id = ?";
            PreparedStatement updatePS = JDBConnection.getConnection().prepareStatement(updateSQL);
            updatePS.setString(1, name);
            updatePS.setString(2, location);
            updatePS.setString(3, status);
            updatePS.setString(4, fuelType);
            updatePS.setString(5, serialNumber);
            updatePS.setString(6, cylinder);
            updatePS.setInt(7, user);
            updatePS.setInt(8, engineId);
            updatePS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Removes an engine from the engine table.
     * @param engineId The id of the engine to delete. */
    public static void delete(int engineId) {

        try {
            String deleteSQL = "DELETE FROM engine WHERE engine_id = ?";
            PreparedStatement deletePS = JDBConnection.getConnection().prepareStatement(deleteSQL);
            deletePS.setInt(1, engineId);
            deletePS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
