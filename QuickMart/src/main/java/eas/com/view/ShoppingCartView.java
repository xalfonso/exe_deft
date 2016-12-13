package eas.com.view;

import eas.com.exception.QuickMartException;
import eas.com.model.BoughtItem;
import eas.com.model.ShoppingCart;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * View for the Shopping Cart
 * <p>
 * Created by eduardo on 12/12/2016.
 */
public class ShoppingCartView {

    /**
     * Represent the model
     */
    private ShoppingCart shoppingCart;

    /**
     * @param shoppingCart data
     */
    public ShoppingCartView(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * Show the Shopping Cart
     */
    public void generateViewShoppingCart(float cash) {
        this.showBasicData();
        String padding = String.format("%20s", "");
        System.out.println("ITEM" + padding + "QUANTITY" + padding + "UNIT PRICE" + padding + "TOTAL");
        int totalItem = 0;
        float subTotalPrice = 0;
        float totalTax = 0;
        float savedMoney = 0;

        for (Map.Entry<String, BoughtItem> entry : this.shoppingCart.getBoughtItemMap().entrySet()) {
            System.out.println(entry.getValue().toStringFormat(this.shoppingCart.isMemberCustomer()));
            totalItem += entry.getValue().getQuantity();
            subTotalPrice += entry.getValue().getTotalPrice(this.shoppingCart.isMemberCustomer());
            totalTax += entry.getValue().getTotalTax(this.shoppingCart.isMemberCustomer());
            savedMoney += entry.getValue().getSavedMoney(this.shoppingCart.isMemberCustomer());
        }

        System.out.println("******************************");
        System.out.println("TOTAL NUMBER OF ITEMS SOLD: " + totalItem);
        System.out.println("SUB-TOTAL: $" + subTotalPrice);
        System.out.println("TAX (6.5%): $" + totalTax);
        System.out.println("TOTAL : $" + (subTotalPrice + totalTax));

        if (cash != 0) {
            System.out.println("CASH : $" + cash);
            System.out.println("CHANGE : $" + (cash - (subTotalPrice + totalTax)));
        }

        if (this.shoppingCart.isMemberCustomer())
            System.out.println("YOU SAVED : $" + savedMoney + "!");


    }

    /**
     * Show the basic data from the shopping cart
     */
    public void showBasicData() {
        System.out.println(this.shoppingCart.getTypeCustomer());
        System.out.println(this.shoppingCart.getLocalDate());
        System.out.println("TRANSACTION: " + this.shoppingCart.getTransaction());
    }

    /**
     * @param itemNameQuantity item name and quantity. eg: Milk,9
     * @return [0] the name of the item, [1] the quantity of the item
     * @throws QuickMartException different errors found in the parsing
     */
    public Object[] readItem(String itemNameQuantity) throws QuickMartException {
        String[] parts = itemNameQuantity.split(",");
        if (parts.length == 1)
            throw new QuickMartException("The name and the quantity of the item is not defined correctly the item, missing the symbol (,) {Data: " + itemNameQuantity + "}");

        /*item Name*/
        if (parts[0].isEmpty())
            throw new QuickMartException("The name of the item is empty {Data: " + itemNameQuantity + "}");

        String itemName = parts[0];

        /*item quantity*/
        if (parts[1].isEmpty())
            throw new QuickMartException("The quantity of the item is empty {Data: " + itemNameQuantity + "}");


        Integer quantity;
        try {
            quantity = Integer.valueOf(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new QuickMartException("The quantity of the item is wrong {Data: " + itemNameQuantity + "}");
        }

        return new Object[]{itemName, quantity};

    }


}
