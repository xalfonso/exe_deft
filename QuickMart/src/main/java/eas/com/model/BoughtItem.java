package eas.com.model;

import eas.com.exception.QuickMartException;

/**
 * Created by eduardo on 12/12/2016.
 */
public class BoughtItem {

    private Item item;
    private int quantity;

    public BoughtItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }



    /**
     *
     * @return true if the item is out, false otherwise
     */
    public boolean isItemOut(){
        return this.quantity == 0;
    }
    /**
     * @param demand for comparing if exist enough items
     * @return true is exist enough items, false otherwise
     */
    private boolean isThereEnough(int demand) {
        return this.quantity >= demand;
    }

    /**
     * Increase the number of this item kind
     *
     * @param value number of items for increasing
     */
    public void increaseQualityBy(int value) {
        this.quantity += value;
    }


    /**
     * Decrease the number of items
     * @param value number of items for removing
     * @return item
     * @throws QuickMartException if no exist enough item
     */
    public Item decreaseQuantity(int value) throws QuickMartException {
        if (!this.isThereEnough(value)) {
            throw new QuickMartException("There is not enough items of type: " + this.item.getName() + " in the shopping cart");
        }

        this.quantity -= value;
        return ((Item) this.item.clone());
    }


    /**
     * @return total price for regular customer
     */
    public float getTotalMemberPrice() {
        return this.quantity * this.item.getUnitMemberPrice();
    }

    /**
     * @return total price for member customer
     */
    public float getTotalRegularPrice() {
        return this.quantity * this.item.getUnitRegularPrice();
    }
}
