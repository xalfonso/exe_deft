package eas.com.model;

import eas.com.exception.QuickMartException;

/**
 * Class for simulating the relation between the item ant the quantity that exist in the inventory
 *
 * Created by eduardo on 12/12/2016.
 */
public class InventoryItem {

    private Item item;
    private int quantity;

    public InventoryItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }


    /**
     * @param demand for comparing if exist enough items
     * @return true is exist enough items, false otherwise
     */
    private boolean isThereEnough(int demand) {
        return this.quantity >= demand;
    }

    /**
     *
     * @return true if the item is sold out, false otherwise
     */
    public boolean isItemSoldOut(){
        return this.quantity == 0;
    }


    /**
     * Decrease the number of items
     *
     * @param value number of items for removing
     * @return item
     * @throws QuickMartException if no exist enough item
     */
    public Item decreaseQuantity(int value) throws QuickMartException {
        if (!this.isThereEnough(value)) {
            throw new QuickMartException("There is not enough items of type: " + this.item.getName() + " in the inventory");
        }

        this.quantity -= value;
        return ((Item) this.item.clone());
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
