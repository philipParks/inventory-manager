package parks.inventorymanager.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import parks.inventorymanager.model.Distributor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Defines a distributor data access object. */
public abstract class DistributorDAO {

    /** Retrieves all the distributors from the distributor table.
     * @return A list of all distributors.*/
    public static ObservableList<Distributor> selectAll() {
        ObservableList<Distributor> distributors = FXCollections.observableArrayList();

        try {
            String selectSQL = "SELECT * FROM distributor";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectSQL);
            ResultSet resultSet = selectPS.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Distributor_id");
                String distributor = resultSet.getString("Distributor");
                String street = resultSet.getString("Street");
                String city = resultSet.getString("City");
                String state = resultSet.getString("State");
                String zipcode = resultSet.getString("Zipcode");
                String contact = resultSet.getString("Contact_name");
                String phone = resultSet.getString("Phone");
                String email = resultSet.getString("Contact_email");

                Distributor DBDistributor = new Distributor(id, distributor, street, city, state, zipcode, contact,
                        phone, email);

                distributors.add(DBDistributor);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return distributors;
    }

    /** Select a distributor by id.
     * @return Requested distributor. */
    public static Distributor select(int distributorId) {
        Distributor distributor = null;

        try {
            String selectSQL = "SELECT * FROM distributors WHERE distributor_id = ?";
            PreparedStatement selectPS = JDBConnection.getConnection().prepareStatement(selectSQL);
            selectPS.setInt(1, distributorId);
            ResultSet resultSet = selectPS.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Distributor_id");
                String distributorName = resultSet.getString("Distributor");
                String street = resultSet.getString("Street");
                String city = resultSet.getString("City");
                String state = resultSet.getString("State");
                String zipcode = resultSet.getString("Zipcode");
                String contact = resultSet.getString("Contact_name");
                String phone = resultSet.getString("Phone");
                String email = resultSet.getString("Contact_email");

                distributor = new Distributor(id, distributorName, street, city, state, zipcode, contact,
                        phone, email);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return distributor;
    }

    /** Insert a new distributor into the distributor table.
     * @param distributor The name of the distributor.
     * @param street The street name and building number of the distributor.
     * @param city The distributor city.
     * @param state The distributor state.
     * @param zipcode The distributor zipcode.
     * @param contact The contact at the distributor.
     * @param phone The contacts phone number.
     * @param email The contacts email address.*/
    public static void insert(String distributor, String street, String city, String state, String zipcode,
                              String contact, String phone, String email, int user) {

        try {
            String insertSQL = "INSERT INTO distributor (distributor, street, city, state, zipcode, contact_name, phone," +
                    "contact_email, created_by, modified_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertPS = JDBConnection.getConnection().prepareStatement(insertSQL);
            insertPS.setString(1, distributor);
            insertPS.setString(2, street);
            insertPS.setString(3, city);
            insertPS.setString(4, state);
            insertPS.setString(5, zipcode);
            insertPS.setString(6, contact);
            insertPS.setString(7, phone);
            insertPS.setString(8, email);
            insertPS.setInt(9, user);
            insertPS.setInt(10, user);
            insertPS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**  Update a distributor.
     * @param distributorId The distributor id.
     * @param distributor The name of the distributor.
     * @param street The street name and building number of the distributor.
     * @param city The distributor city.
     * @param state The distributor state.
     * @param zipcode The distributor zipcode.
     * @param contact The contact at the distributor.
     * @param phone The contacts phone number.
     * @param email The contacts email address.
     * @param user The user performing the update. */
    public static void update(int distributorId, String distributor, String street, String city, String state,
                              String zipcode, String contact, String phone, String email, int user) {

        try {
            String updateSQL = "UPDATE distributor SET distributor = ?, street = ?, city = ?, state = ?, zipcode = ?, " +
                    "contact_name = ?, phone = ?, contact_email = ?, modified_by = ? WHERE distributor_id = ?";
            PreparedStatement updatePS = JDBConnection.getConnection().prepareStatement(updateSQL);
            updatePS.setString(1, distributor);
            updatePS.setString(2, street);
            updatePS.setString(3, city);
            updatePS.setString(4, state);
            updatePS.setString(5, zipcode);
            updatePS.setString(6, contact);
            updatePS.setString(7, phone);
            updatePS.setString(8, email);
            updatePS.setInt(9, user);
            updatePS.setInt(10, distributorId);
            updatePS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Removes a distributor from the distributor table.
     * @param distributorId The id of the distributor to delete. */
    public static void delete(int distributorId) {

        try {
            String deleteSQL = "DELETE FROM distributor WHERE distributor_id = ?";
            PreparedStatement deletePS = JDBConnection.getConnection().prepareStatement(deleteSQL);
            deletePS.setInt(1, distributorId);
            deletePS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
