/** Welcome to the Inventory Manager.
* */
module parks.inventorymanager {
    requires javafx.controls;
    requires javafx.fxml;


    exports parks.inventorymanager.controller;
    opens parks.inventorymanager.controller to javafx.fxml;
    exports parks.inventorymanager.model;
    opens parks.inventorymanager.model to javafx.base;
    exports parks.inventorymanager;
    opens parks.inventorymanager to javafx.fxml;
}