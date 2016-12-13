package eas.com.model;

import eas.com.exception.QuickMartException;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for simulating the inventory of item
 *
 * Created by eduardo on 12/12/2016.
 */
public class Inventory {

    private Map<String, InventoryItem> inventoryItemMap;


    public Inventory() {
        this.inventoryItemMap = new HashMap<>();
    }

    /**
     * Remove a count of item from inventory
     * @param nameItem name of item
     * @param quantity count of items
     * @return item
     * @throws QuickMartException if do not exist the item or the count is not enough
     */
    public Item removeItemQuantity(String nameItem, int quantity) throws QuickMartException {
        InventoryItem inventoryItem;
        if ((inventoryItem = this.inventoryItemMap.get(nameItem)) == null) {
            throw new QuickMartException("There is not the item: " + nameItem + " in the inventory");
        }

        Item item = inventoryItem.decreaseQuantity(quantity);
        if (inventoryItem.isItemSoldOut())
            this.inventoryItemMap.remove(nameItem);

        return item;
    }


    /**
     * If there are more of one line with the same item (same name)
     * Only the latter item remains
     *
     * @param inventoryItem to save in the inventory
     */
    public void addItemQuantity(InventoryItem inventoryItem){
        this.inventoryItemMap.put(inventoryItem.getItem().getName(), inventoryItem);
    }

    /**
     *
     * @return true is the inventory is empty, false otherwise
     */
    public boolean isEmpty(){
        return this.inventoryItemMap.isEmpty();
    }


    public Map<String, InventoryItem> getInventoryItemMap() {
        return inventoryItemMap;
    }
}
