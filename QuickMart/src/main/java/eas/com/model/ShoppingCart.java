package eas.com.model;

import eas.com.exception.QuickMartException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for simulating the shopping cart
 * Created by eduardo on 12/12/2016.
 */
public class ShoppingCart {

    /**
     * Key: Name of Item
     * Value: Relation Item-Quantity @see {@link ItemQuantity}
     */
    private Map<String, ItemQuantity> boughtItemMap;

    /**
     * False: Regular Customer
     * True: Member Customer
     */
    private boolean memberCustomer;


    /**
     * The id transaction of the shopping cart
     */
    private String transaction;

    /**
     * The date that is created the Shopping Cart
     */
    private LocalDate localDate;

    public ShoppingCart(boolean memberCustomer, String transaction) {
        this.boughtItemMap = new HashMap<>();
        this.memberCustomer = memberCustomer;
        this.transaction = transaction;
        this.localDate = LocalDate.now();
    }

    /**
     * Remove a quantity of item from shopping cart
     * @param nameItem name of item
     * @param quantity count of items
     * @return ItemQuantity
     * @throws QuickMartException if do not exist the item or the count is not enough
     */
    public ItemQuantity removeItemQuantity(String nameItem, int quantity) throws QuickMartException {
        ItemQuantity boughtItem;
        if ((boughtItem = this.boughtItemMap.get(nameItem)) == null) {
            throw new QuickMartException("There is not the item: " + nameItem + " in the shopping cart");
        }

     ItemQuantity itemQuantityDecrement = boughtItem.decrease(quantity);

        if (boughtItem.isItemSoldOut())
            this.boughtItemMap.remove(nameItem);

        return itemQuantityDecrement;
    }

    /**
     * Add an item to shopping cart
     *
     * @param itemQuantity for adding
     */
    public void addItemQuantity(ItemQuantity itemQuantity) {
        if (!this.boughtItemMap.containsKey(itemQuantity.getItem().getName())) {
            this.boughtItemMap.put(itemQuantity.getItem().getName(), itemQuantity);
        } else {
            this.boughtItemMap.get(itemQuantity.getItem().getName()).increase(itemQuantity);
        }
    }



    public boolean isMemberCustomer() {
        return memberCustomer;
    }

    public String getTypeCustomer(){
        return this.memberCustomer ? "Rewards Member" : "Regular customer";
    }

    public String getTransaction() {
        return transaction;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public Map<String, ItemQuantity> getBoughtItemMap() {
        return boughtItemMap;
    }

    public boolean isEmpty(){
        return this.boughtItemMap.isEmpty();
    }
}
