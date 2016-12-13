package eas.com.controller;

import eas.com.exception.QuickMartException;
import eas.com.model.Inventory;
import eas.com.model.InventoryItem;
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


    public InventoryController() {
        this.inventory = new Inventory();
        this.inventoryView = new InventoryView(this.inventory);
    }

    public void show() {
        this.inventoryView.show();
    }


    public void loadDataFromFile(String file) throws IOException, QuickMartException {
        this.inventoryItemsTemp = this.inventoryView.loadDataFromFile(file);
    }

    public void showTempDataFromFile() throws QuickMartException {
        if (this.inventoryItemsTemp == null)
            throw new QuickMartException("There is not temporal data from file inventory leaded");

        this.inventoryView.showTemporalData(this.inventoryItemsTemp);
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


}
