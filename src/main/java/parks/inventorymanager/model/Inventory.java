package parks.inventorymanager.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import parks.inventorymanager.controller.addProductViewController;
import parks.inventorymanager.controller.mainViewController;
import parks.inventorymanager.controller.modifyPartViewController;

import java.io.IOException;
import java.util.Optional;
/** This class creates and manages lists of Parts and Products. */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Adds a Part to the allParts list.
     * LOGIC ERROR Unable to generate a unique Part ID. Corrected by sorting allParts by Part ID's with a bubble sort
     * and finding the highest Part ID then assigning a new Part an ID that is 1 more than the highest ID.
     * @param newPart The new Part to add to allParts.
     * */
    public static void addPart(Part newPart) {
        int newPartId;
        String newPartName = newPart.getName();
        double newPartPrice = newPart.getPrice();
        int newPartStock = newPart.getStock();
        int newPartMin = newPart.getMin();
        int newPartMax = newPart.getMax();

        if (allParts.isEmpty()) {
            newPartId = 1;
        }
        else {
            int n = allParts.size();
            int highestId = 1;
            for (int i = 0; i < n - 1; i++) {
                Part part1 = allParts.get(i);
                int part1Id = part1.getId();
                for (int j = 0; j < n - i - 1; j++) {
                    Part part2 = allParts.get(j);
                    int part2Id = part2.getId();
                    if (part2Id > part1Id) {
                        int temp = part1Id;
                        part1Id = part2Id;
                        part2Id = temp;
                    }
                    highestId = part1Id;
                }
                highestId++;
            }
            newPartId = highestId + 1;
        }

        if (newPart instanceof InHouse) {
            int machineId = ((InHouse) newPart).getMachineId();
            newPart = new InHouse(newPartId, newPartName, newPartPrice, newPartStock, newPartMin, newPartMax, machineId);
        }
        if (newPart instanceof Outsourced) {
            String company = ((Outsourced) newPart).getCompanyName();
            newPart = new Outsourced(newPartId, newPartName, newPartPrice, newPartStock, newPartMin, newPartMax, company);
        }

        allParts.add(newPart);
    }

    /** Adds a Product to the allProducts list.
     * @param newProduct The new Product to add to allProducts.
     * */
    public static void addProduct(Product newProduct) {
        int newProductId;
        String newProductName = newProduct.getName();
        double newProductPrice = newProduct.getPrice();
        int newProductStock = newProduct.getStock();
        int newProductMin = newProduct.getMin();
        int newProductMax = newProduct.getMax();
        ObservableList<Part> associatedParts = newProduct.getAllAssociatedParts();

        if (allProducts.isEmpty()) {
            newProductId = 1;
        }
        else {
            int n = allProducts.size();
            int highestId = 1;
            for (int i = 0; i < n - 1; i++) {
                Product product1 = allProducts.get(i);
                int product1Id = product1.getId();
                for (int j = 0; j < n - i - 1; j++) {
                    Product product2 = allProducts.get(j);
                    int product2Id = product2.getId();
                    if (product2Id > product1Id) {
                        int temp = product1Id;
                        product1Id = product2Id;
                        product2Id = temp;
                    }
                    highestId = product1Id;
                }
                highestId++;
            }
            newProductId = highestId + 1;
        }

        newProduct = new Product(newProductId, newProductName, newProductPrice, newProductStock, newProductMin, newProductMax);

        for (Part part : associatedParts) {
            newProduct.addAssociatedPart(part);
        }

        allProducts.add(newProduct);
    }

    /** Looks for a Part in the allParts list by its ID.
     * @param partId The Part ID that the user is searching for.
     * @return The Part in allParts that matches partId.
     * */
    public static Part lookUpPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /** Looks for a Product in the allProducts list by its ID.
     * @param productId The Product ID that the user is searching for.
     * @return The Product in allProducts that matches productId.
     * */
    public static Product lookUpProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /** Looks for a Part in the allParts list by its Name.
     * @param partName The Part Name that the user is searching for.
     * @return A list of Parts that matches the text entered into the search text field.
     * */
    public static ObservableList<Part> lookUpPart(String partName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                namedParts.add(part);
            }
        }
        return namedParts;
    }

    /** Looks for a Product in the allProducts list by its Name.
     * @param productName The Product Name that the user is searching for.
     * @return A list of Products that matches the text entered into the search text field.
     * */
    public static ObservableList<Product> lookUpProduct(String productName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allNamedProducts = Inventory.getAllProducts();

        for (Product product : allNamedProducts) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                namedProducts.add(product);
            }
        }
        return namedProducts;
    }

    /** Updates a Part in the allParts list.
     * @param index The index of the Part being modified.
     * @param selectedPart The Part that has been selected to modify.
     * */
    public static void updatePart(int index, Part selectedPart) {
        for (Part part : allParts) {
            if (allParts.indexOf(part) == index) {
                allParts.set(index, selectedPart);
            }
        }
    }

    /** Updates a Product in the allProducts list.
     * @param index The index of the Product being modified.
     * @param newProduct The Product that has been selected to modify.
     * */
    public static void updateProduct(int index, Product newProduct) {
        for (Product product : allProducts) {
            if (allProducts.indexOf(product) == index) {
                allProducts.set(index, newProduct);
            }
        }
    }

    /** Removes a Part from the allParts list.
     * @param selectedPart The Part that is being removed.
     * @return Removal state of selectedPart.
     * */
    public static boolean deletePart(Part selectedPart) {
        Alert partDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        partDeleteAlert.setTitle("Part Delete Dialog");
        partDeleteAlert.setHeaderText("Do you want to remove " + "\"" + selectedPart.getName() + "\" ?");
        partDeleteAlert.setContentText("Deleting \"" + selectedPart.getName() + "\" cannot be undone\n" +
                "Click \"OK\" to delete\nClick \"Cancel\" to undo this action\n");
        Optional<ButtonType> result = partDeleteAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            return allParts.remove(selectedPart);
        }
        return false;

    }

    /** Removes a Product from the allProducts list.
     * @param selectedProduct The Product that is being removed.
     * @return Removal state of selectedProduct.
     * */
    public static boolean deleteProduct(Product selectedProduct) {
        Alert partDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        partDeleteAlert.setTitle("Product Delete Dialog");
        partDeleteAlert.setHeaderText("Do you want to remove " + "\"" + selectedProduct.getName() + "\" ?");
        partDeleteAlert.setContentText("Deleting \"" + selectedProduct.getName() + "\" cannot be undone\n" +
                "Click \"OK\" to delete\nClick \"Cancel\" to undo this action\n");
        Optional<ButtonType> result = partDeleteAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            return allProducts.remove(selectedProduct);
        }

        return false;
    }

    /** Accesses the Parts in allParts.
     * @return All Parts.
     * */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** Accesses the Products in allProducts.
     * @return All Products.
     * */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
