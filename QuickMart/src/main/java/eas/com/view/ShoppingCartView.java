package eas.com.view;

import eas.com.model.ShoppingCart;

/**
 * View for the Shopping Cart
 *
 * Created by eduardo on 12/12/2016.
 */
public class ShoppingCartView {

    /**
     * Represent the model
     */
    private ShoppingCart shoppingCart;

    /**
     *
     * @param shoppingCart data
     */
    public ShoppingCartView(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * Show the Shopping Cart
     */
    public void show() {
        System.out.println("ITEM          QUANTITY          UNIT          PRICE          TOTAL");
    }

    /**
     * Show the basic data from the shopping cart
     */
    public void showBasicData(){
        System.out.println(this.shoppingCart.getTypeCustomer());
        System.out.println(this.shoppingCart.getLocalDate());
        System.out.println("TRANSACTION: " + this.shoppingCart.getTransaction());
    }
}
