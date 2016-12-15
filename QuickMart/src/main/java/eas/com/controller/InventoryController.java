package eas.com.controller;

import eas.com.exception.QuickMartException;
import eas.com.model.Inventory;
import eas.com.model.ItemQuantity;
import eas.com.view.InventoryView;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Class for managing the Inventory
 * <p>
 * Created by eduardo on 12/12/2016.
 */
public class InventoryController {


    /**
     * Represent the unique Inventory controller of the system
     */
    private static InventoryController currentInventoryController;


    /**
     * Represent the Model
     */
    private Inventory inventory;

    /**
     * Represent the view
     */
    private InventoryView inventoryView;


    /**
     * For temporal data, the user need to confirm
     * for saving in the inventory
     */
    private List<ItemQuantity> inventoryItemsTemp;


    private InventoryController() {
        this.inventory = new Inventory();
        this.inventoryView = new InventoryView(this.inventory);
    }

    /**
     * Singleton instance of the inventory controller
     *
     * @return current inventory controller
     */
    public static InventoryController getCurrentInventoryController() {
        if (currentInventoryController == null) {
            currentInventoryController = new InventoryController();
        }
        return currentInventoryController;
    }

    /**
     * Load data from File
     *
     * @param file path of file
     * @throws IOException        if happen error reading the file
     * @throws QuickMartException different errors found
     */
    public void loadDataFromFile(String file) throws IOException, QuickMartException {
        this.inventoryItemsTemp = this.inventoryView.readDataFromFile(file);
    }

    /**
     * View the items of the inventory (Temporal Data) in the standard output (console)
     *
     * @throws QuickMartException if there is not temporal data loaded
     */
    public void viewInventoryTemporalData() throws QuickMartException {
        if (this.inventoryItemsTemp == null)
            throw new QuickMartException("There is not temporal data from file inventory leaded");

        this.inventoryView.printTemporalDataToStandardOutput(this.inventoryItemsTemp);
    }

    /**
     * Move the items from temporal data to Inventory
     */
    public void fillInventory() {
        Iterator<ItemQuantity> it = this.inventoryItemsTemp.iterator();
        while (it.hasNext()) {
            this.inventory.addItemQuantity(it.next());
            it.remove();
        }

    }

    /**
     * Discard items from temporal data
     */
    public void discardTempDataFromInventory() {
        this.inventoryItemsTemp.clear();
    }

    /**
     * Remove item from inventory
     *
     * @param nameItem name of item
     * @param quantity of the item
     * @return ItemQuantity
     * @throws QuickMartException different errors found
     */
    public ItemQuantity removeFromInventory(String nameItem, int quantity) throws QuickMartException {
        return this.inventory.removeItemQuantity(nameItem, quantity);
    }

    /**
     * Add items to the inventory
     *
     * @param inventoryItem to add
     */
    public void addItemToInventory(ItemQuantity inventoryItem) {
        this.inventory.addItemQuantity(inventoryItem);
    }

    /**
     * View the items of the inventory in the standard output (console)
     */
    public void viewInventory() {
        this.inventoryView.printToStandardOutput();
    }


    /**
     * @return true if the inventory is empty, false otherwise
     */
    public boolean isInventoryEmpty() {
        return this.inventory.isEmpty();
    }


    public Inventory getInventory() {
        return inventory;
    }
}
