package eas.com.controller;

import eas.com.exception.QuickMartException;
import eas.com.model.Item;
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

    public void updateViewBasicData() {
        this.shoppingCartView.printBasicDataToStandardOutput();
    }

    public void updateViewCompleteData() {
        this.shoppingCartView.printCompleteDataToStandardOutput(0);
    }

    public void addItem(String itemNameQuantity) throws QuickMartException {
        Object[] itemNameAndQuantity = this.shoppingCartView.readItem(itemNameQuantity);
        Item item = InventoryController.getCurrentInventoryController().removeFromInventory((String) itemNameAndQuantity[0], (Integer) itemNameAndQuantity[1]);
        currentShoppingController.getShoppingCart().addItem(item, (Integer) itemNameAndQuantity[1]);
    }


    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    private static String generateIdTransaction() {
        transaction++;
        return String.format("%06d", transaction);
    }

    /**
     * Singleton instance. Only exist on shopping cart controller in each time
     *
     * @throws QuickMartException if already exist one instance of ShoppingController class
     */
    public static void createNewShoppingControllerMemberCustomer() throws QuickMartException {
        if (currentShoppingController != null)
            throw new QuickMartException("Already exist one instance of class: " + ShoppingController.class.getName());
        currentShoppingController = new ShoppingController(true, generateIdTransaction());
    }


    /**
     * Singleton instance. Only exist on shopping cart controller in each time
     *
     * @throws QuickMartException if already exist one instance of ShoppingController class
     */
    public static void createNewShoppingControllerRegularCustomer() throws QuickMartException {
        if (currentShoppingController != null)
            throw new QuickMartException("Already exist one instance of class: " + ShoppingController.class.getName());
        currentShoppingController = new ShoppingController(false, generateIdTransaction());
    }

    /**
     * This method must be called before create other instance of class removeCurrentShoppingCartController
     */
    public static void removeCurrentShoppingCartController() {
        currentShoppingController = null;
    }


    public static ShoppingController getCurrentShoppingController() {
        return currentShoppingController;
    }


}
