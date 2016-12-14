package eas.com.model;

import eas.com.exception.QuickMartException;

import java.text.DecimalFormat;

/**
 * Created by eduardo on 12/14/2016.
 */
public class ItemQuantity {

    private Item item;
    private int quantity;


    public ItemQuantity(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }


    /**
     * @return true if the item is sold out, false otherwise
     */
    public boolean isItemSoldOut() {
        return this.quantity == 0;
    }


    /**
     * Increase the quantity of the item
     *
     * @param itemQuantity to increase
     */
    public void increase(ItemQuantity itemQuantity) {
        this.quantity += itemQuantity.getQuantity();
    }


    /**
     * Decrease the quantity of the item
     *
     * @param value to increase
     */
    public ItemQuantity decrease(int value) throws QuickMartException {
        if (value > this.quantity) {
            throw new QuickMartException("There is not enough items of type: " + this.item.getName() + " in the inventory");
        }

        this.quantity -= value;
        return new ItemQuantity(this.item, value);
    }

    /**
     * @param isMemberCustomer if the customer is member or not
     * @return total price depending on type of customer
     */
    public float getTotalPrice(boolean isMemberCustomer) {
        return this.quantity * this.getItem().getUnitPrice(isMemberCustomer);
    }

    /**
     * @param isMemberCustomer if the customer is member or not
     * @return total tax depending on type of customer
     */
    public float getTotalTax(boolean isMemberCustomer){
        return this.getItem().isTaxable() ? this.getTotalPrice(isMemberCustomer) * 0.065f : 0;
    }


    /**
     *
     * @param isMemberCustomer if the customer is member or not
     * @return saved money
     */
    public float getSavedMoney(boolean isMemberCustomer){
        return isMemberCustomer ? this.getItem().differenceMemberRegularPrice() * this.quantity : 0;
    }


    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public String toStringFormatShoppingCart(boolean isMemberCustomer) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String quantityString = String.valueOf(this.quantity);
        String unitPriceString = String.valueOf(this.getItem().getUnitPrice(isMemberCustomer));
        String totalPriceString = String.valueOf(Float.valueOf(decimalFormat.format(this.getTotalPrice(isMemberCustomer))));

        return this.getItem().getName()
                + String.format("%" + (24 - this.getItem().getName().length()) + "s", "")
                + quantityString
                + String.format("%" + (28 - quantityString.length()) + "s", "")
                + unitPriceString
                + String.format("%" + (30 - unitPriceString.length()) + "s", "")
                + totalPriceString;
    }
}
