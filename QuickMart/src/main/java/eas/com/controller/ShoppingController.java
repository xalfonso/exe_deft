package eas.com.controller;

import eas.com.model.ShoppingCart;
import eas.com.view.ShoppingCartView;

/**
 * Class for managing the Shopping Cart
 * <p>
 * Created by eduardo on 12/12/2016.
 */
public class ShoppingController {

    /**
     * Number of all transaction
     */
    private static int transaction;

    /**
     * Current Shopping Controller
     * <p>
     * Represent the Shopping cart that is being management (controller) by the system in each time
     */
    private static ShoppingController currentShoppingController;

    /**
     * Represent the model
     */
    private ShoppingCart shoppingCart;

    /**
     * Represent the view
     */
    private ShoppingCartView shoppingCartView;


    private ShoppingController(boolean memberCustomer, String transaction) {
        this.shoppingCart = new ShoppingCart(memberCustomer, transaction);
        this.shoppingCartView = new ShoppingCartView(this.shoppingCart);
    }

    public void showBasicData(){
        this.shoppingCartView.showBasicData();
    }


    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    private static String generateIdTransaction(){
        transaction ++;
        return String.format("%06d", transaction);
    }

    public static void createNewShoppingControllerMemberCustomer() {
        currentShoppingController = new ShoppingController(true, generateIdTransaction());
    }

    public static void createNewShoppingControllerRegularCustomer() {
        currentShoppingController = new ShoppingController(false, generateIdTransaction());
    }


    public static ShoppingController getCurrentShoppingController() {
        return currentShoppingController;
    }


}
