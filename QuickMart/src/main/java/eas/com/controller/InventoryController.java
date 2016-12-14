package eas.com.controller;

import eas.com.exception.QuickMartException;
import eas.com.model.Inventory;
import eas.com.model.InventoryItem;
import eas.com.model.Item;
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
     */
    private List<InventoryItem> inventoryItemsTemp;


    private InventoryController() {
        this.inventory = new Inventory();
        this.inventoryView = new InventoryView(this.inventory);
    }

    /**
     *
     * @return true if the inventory is empty, false otherwise
     */
    public boolean isInventoryEmpty(){
        return this.inventory.isEmpty();
    }


    public void updateView() {
        this.inventoryView.printToStandardOutput();
    }


    public void loadDataFromFile(String file) throws IOException, QuickMartException {
        this.inventoryItemsTemp = this.inventoryView.readDataFromFile(file);
    }

    public void showTempDataFromFile() throws QuickMartException {
        if (this.inventoryItemsTemp == null)
            throw new QuickMartException("There is not temporal data from file inventory leaded");

        this.inventoryView.printTemporalDataToStandardOutput(this.inventoryItemsTemp);
    }

    public void fillInventory() {
        Iterator<InventoryItem> it = this.inventoryItemsTemp.iterator();
        while (it.hasNext()) {
            this.inventory.addItemQuantity(it.next());
            it.remove();
        }

    }

    public void discardTempDataFromInventory() {
        this.inventoryItemsTemp.clear();
    }


    public Item removeFromInventory(String nameItem, int quantity) throws QuickMartException {
        return this.inventory.removeItemQuantity(nameItem, quantity);
    }

    /**
     * Singleton instance of the inventory controller
     * @return current inventory controller
     */
    public static InventoryController getCurrentInventoryController() {
        if(currentInventoryController == null){
            currentInventoryController = new InventoryController();
        }
        return currentInventoryController;
    }
}
