package eas.com.model;

import eas.com.exception.QuickMartException;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for simulating the inventory of item
 * <p>
 * Created by eduardo on 12/12/2016.
 */
public class Inventory {

    private Map<String, ItemQuantity> inventoryItemMap;


    public Inventory() {
        this.inventoryItemMap = new HashMap<>();
    }

    /**
     * Remove a count of item from inventory
     *
     * @param nameItem name of item
     * @param quantity count of items
     * @return ItemQuantity
     * @throws QuickMartException if do not exist the item or the count is not enough
     */
    public ItemQuantity removeItemQuantity(String nameItem, int quantity) throws QuickMartException {
        ItemQuantity inventoryItem;
        if ((inventoryItem = this.inventoryItemMap.get(nameItem)) == null) {
            throw new QuickMartException("There is not the item: " + nameItem + " in the inventory");
        }

        ItemQuantity itemQuantityDecrement = inventoryItem.decrease(quantity);
        if (inventoryItem.isItemSoldOut())
            this.inventoryItemMap.remove(nameItem);

        return itemQuantityDecrement;
    }

    /**
     * Add item quantity
     *
     * @param itemQuantity to save in the inventory
     */
    public void addItemQuantity(ItemQuantity itemQuantity) {
        if (this.inventoryItemMap.containsKey(itemQuantity.getItem().getName())) {
            this.inventoryItemMap.get(itemQuantity.getItem().getName()).increase(itemQuantity);
        } else {
            this.inventoryItemMap.put(itemQuantity.getItem().getName(), itemQuantity);
        }
    }

    /**
     * @return true is the inventory is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.inventoryItemMap.isEmpty();
    }


    public Map<String, ItemQuantity> getInventoryItemMap() {
        return inventoryItemMap;
    }
}
