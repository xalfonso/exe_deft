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

    private Map<String, BoughtItem> boughtItemMap;

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
        this.boughtItemMap = new HashMap<String, BoughtItem>();
        this.memberCustomer = memberCustomer;
        this.transaction = transaction;
        this.localDate = LocalDate.now();
    }

    /**
     * Remove a count of item from shopping cart
     * @param nameItem name of item
     * @param quantity count of items
     * @return item
     * @throws QuickMartException if do not exist the item or the count is not enough
     */
    public Item removeItem(String nameItem, int quantity) throws QuickMartException {
        BoughtItem boughtItem;

        if ((boughtItem = this.boughtItemMap.get(nameItem)) == null) {
            throw new QuickMartException("There is not the item: " + nameItem + " in the shopping cart");
        }

        Item item = boughtItem.decreaseQuantity(quantity);

        if (boughtItem.isItemOut())
            this.boughtItemMap.remove(nameItem);

        return item;
    }

    /**
     * Add an item to shopping cart
     *
     * @param item for adding
     * @param quantity count of item for adding
     */
    public void addItem(Item item, int quantity) {
        if (!this.boughtItemMap.containsKey(item.getName())) {
            BoughtItem boughtItem = new BoughtItem(item, quantity);
            this.boughtItemMap.put(item.getName(), boughtItem);
        } else {
            this.boughtItemMap.get(item.getName()).increaseQualityBy(quantity);
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
}
