package parks.inventorymanager.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
/** This class creates an instance of a Product. */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    /** This method is a constructor for the Product class.
     * @param id The unique number assigned to the Product.
     * @param name The name of the Product.
     * @param price The monetary value assigned to the Product.
     * @param stock The current quantity of the Product.
     * @param min The minimum allowable inventory to retain.
     * @param max The maximum allowable inventory to retain.
     * */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Product ID mutator. This number is automatically assigned and should not be modified.
     * @param id The unique number assigned to the Product.
     * */
    public void setId(int id) {
        this.id = id;
    }

    /** Product ID accessor.
     * @return Product ID. */
    public int getId() {
        return id;
    }

    /** Product name mutator.
     * @param name The name of the Product.
     * */
    public void setName(String name) {
        this.name = name;
    }

    /** Product name accessor.
     * @return Product name.
     * */
    public String getName() {
        return name;
    }

    /** Product price mutator.
     * @param price The monetary value assigned to the Product.
     * */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Product price accessor.
     * @return Product price.
     * */
    public double getPrice() {
        return price;
    }

    /** Product stock mutator.
     * @param stock The current quantity of the Product.
     * */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Product stock accessor.
     * @return Current inventory.
     * */
    public int getStock() {
        return stock;
    }

    /** Product min mutator.
     * @param min The minimum allowable inventory to retain.
     * */
    public void setMin(int min) {
        this.min = min;
    }

    /** Product min accessor.
     * @return Minimum allowable inventory.
     * */
    public int getMin() {
        return min;
    }

    /** Product max mutator.
     * @param max The maximum allowable inventory to retain.
     * */
    public void setMax(int max) {
        this.max = max;
    }

    /** Product max accessor.
     * @return Maximum allowable inventory.
     * */
    public int getMax() {
        return max;
    }

    /** This method adds a Part to associatedParts.
     * @param part The Part to add to associatedParts.
     * */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** This method removes a Part from associatedParts.
     * @param selectedAssociatedPart The Part that has been selected for removal from associatedParts.
     * */
    public void deleteAssociatedPart(Part selectedAssociatedPart) {
        Alert partDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        partDeleteAlert.setTitle("Part Delete Dialog");
        partDeleteAlert.setHeaderText("Do you want to remove " + "\"" + selectedAssociatedPart.getName() + "\" ?");
        partDeleteAlert.setContentText("Deleting \"" + selectedAssociatedPart.getName() + "\" cannot be undone\n" +
                "Click \"OK\" to delete\nClick \"Cancel\" to undo this action\n");
        Optional<ButtonType> result = partDeleteAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            associatedParts.remove(selectedAssociatedPart);
        }
    }

    /** Product associatedParts accessor.
     * @return A list of the Parts associated with the Product.
     * */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
