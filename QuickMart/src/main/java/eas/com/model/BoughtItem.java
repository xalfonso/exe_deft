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
     * @return true if the item is out, false otherwise
     */
    public boolean isItemOut() {
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
     *
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
     * @return total price depending on type of customer
     */
    public float getTotalPrice(boolean isMemberCustomer) {
        return this.quantity * this.getItem().getUnitPrice(isMemberCustomer);
    }

    /**
     *
     * @return total tax depending on type of customer
     */
    public float getTotalTax(boolean isMemberCustomer){
        return this.getItem().isTaxable() ? this.getTotalPrice(isMemberCustomer) * 0.065f : 0;
    }

    public float getSavedMoney(boolean isMemberCustomer){
        return isMemberCustomer ? this.getItem().diferenceMemberRegularPrice() * this.quantity : 0;
    }



    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }


    public String toStringFormat(boolean isMemeberCustomer) {
        String quantityString = String.valueOf(this.quantity);
        String unitPriceString = String.valueOf(this.getItem().getUnitPrice(isMemeberCustomer));
        String totalPriceString = String.valueOf(this.getTotalPrice(isMemeberCustomer));

        return this.getItem().getName()
                + String.format("%" + (24 - this.getItem().getName().length()) + "s", "")
                + quantityString
                + String.format("%" + (28 - quantityString.length()) + "s", "")
                + unitPriceString
                + String.format("%" + (30 - unitPriceString.length()) + "s", "")
                + totalPriceString;
    }
}
