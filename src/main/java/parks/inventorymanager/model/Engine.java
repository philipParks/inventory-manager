package parks.inventorymanager.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/** Defines an engine. */
public class Engine {

    private int id;
    private String nomenclature;
    private String location;
    private String status;
    private String fuelType;
    private String serialNumber;
    private String cylinders;

    /** Constructs an engine.
     * @param id The unique number assigned to the engine.
     * @param nomenclature The name of the engine.
     * @param location The physical location of the engine.
     * @param status The status of the engine.
     * @param fuelType The fuel type of the engine.
     * @param serialNumber The serial number of the engine.
     * @param cylinders The cylinder count and configuration of the engine.*/
    public Engine(int id, String nomenclature, String location, String status,
                  String fuelType, String serialNumber, String cylinders) {
        this.id = id;
        this.nomenclature = nomenclature;
        this.location = location;
        this.status = status;
        this.fuelType = fuelType;
        this.serialNumber = serialNumber;
        this.cylinders = cylinders;
    }

    /** Sets the engine id.
     * @param id The engine id.*/
    public void setId(int id) {
        this.id = id;
    }

    /** Gets the engine id.
     * @return The engine id. */
    public int getId() {
        return id;
    }

    /** Sets the engine nomenclature.
     * @param nomenclature The engine nomenclature.*/
    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }

    /** Gets the engine nomenclature.
     * @return The engine nomenclature. */
    public String getNomenclature() {
        return nomenclature;
    }

    /** Sets the engine location.
     * @param location The physical location of the engine.*/
    public void setLocation(String location) {
        this.location = location;
    }

    /** Gets the engine location.
     * @return The physical location of the engine. */
    public String getLocation() {
        return location;
    }

    /** Sets the status of the engine.
     * @param status The status of the engine.*/
    public void setStatus(String status) {
        this.status = status;
    }

    /** Gets the status of the engine.
     * @return The status of the engine. */
    public String getStatus() {
        return status;
    }

    /** Sets the engine fuel type.
     * @param fuelType The engine fuel type.*/
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    /** Gets the engine fuel type.
     * @return The engine fuel type. */
    public String getFuelType() {
        return fuelType;
    }

    /** Sets the engine serial number.
     * @param serialNumber The engine serial number.*/
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /** Gets the engine serial number.
     * @return The engine serial number. */
    public String getSerialNumber() {
        return serialNumber;
    }

    /** Sets the cylinder count and configuration of the engine.
     * @param cylinders The cylinder count and configuration of the engine.*/
    public void setCylinders(String cylinders) {
        this.cylinders = cylinders;
    }

    /** Gets the cylinder count and configuration of the engine.
     * @return The cylinder count and configuration of the engine. */
    public String getCylinders() {
        return cylinders;
    }

}
