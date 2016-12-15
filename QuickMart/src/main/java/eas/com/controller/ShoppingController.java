package eas.com.controller;

import eas.com.exception.QuickMartException;
import eas.com.model.ItemQuantity;
import eas.com.model.ShoppingCart;
import eas.com.view.ShoppingCartView;

import java.util.Iterator;
import java.util.Map;

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
     * Represent the Shopping cart controller that is being management by the system in each time
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

    /**
     * Create Singleton instance for Member Customer
     *
     * @throws QuickMartException if already exist one instance of ShoppingController class
     */
    public static void createNewShoppingControllerMemberCustomer() throws QuickMartException {
        if (currentShoppingController != null)
            throw new QuickMartException("Already exist one instance of class: " + ShoppingController.class.getName());
        currentShoppingController = new ShoppingController(true, generateIdTransaction());
    }


    /**
     * Create Singleton instance for Regular member.
     *
     * @throws QuickMartException if already exist one instance of ShoppingController class
     */
    public static void createNewShoppingControllerRegularCustomer() throws QuickMartException {
        if (currentShoppingController != null)
            throw new QuickMartException("Already exist one instance of class: " + ShoppingController.class.getName());
        currentShoppingController = new ShoppingController(false, generateIdTransaction());
    }

    /**
     * Get the singleton instance
     *
     * @return instance of ShoppingController
     */
    public static ShoppingController getCurrentShoppingController() {
        return currentShoppingController;
    }

    /**
     * Add item
     *
     * @param itemNameQuantity to add
     * @throws QuickMartException different errors found
     */
    public void addItem(String itemNameQuantity) throws QuickMartException {
        Object[] itemNameAndQuantity = this.shoppingCartView.readItem(itemNameQuantity);
        ItemQuantity itemQuantity = InventoryController.getCurrentInventoryController().removeFromInventory((String) itemNameAndQuantity[0], (Integer) itemNameAndQuantity[1]);
        currentShoppingController.getShoppingCart().addItemQuantity(itemQuantity);
    }

    /**
     * Remove item
     *
     * @param itemNameQuantity to remove
     * @throws QuickMartException different errors found
     */
    public void removeItem(String itemNameQuantity) throws QuickMartException {
        Object[] itemNameAndQuantity = this.shoppingCartView.readItem(itemNameQuantity);
        ItemQuantity itemQuantity = currentShoppingController.getShoppingCart().removeItemQuantity((String) itemNameAndQuantity[0], (Integer) itemNameAndQuantity[1]);
        InventoryController.getCurrentInventoryController().addItemToInventory(itemQuantity);
    }

    /**
     * Checkout and Print receipt
     *
     * @param cash paid by customer
     * @throws QuickMartException different errors found
     */
    public void checkoutAndPrintReceipt(float cash) throws QuickMartException {
        String fileName = this.shoppingCartView.printInvoice(cash);
        this.shoppingCartView.printCompleteDataToStandardOutput(cash, fileName);
        currentShoppingController = null;
    }

    /**
     * Cancel the current transaction
     * The current ShoppingController is put on null
     */
    public void cancelTransaction() {
        this.emptyShoppingCart();
        currentShoppingController = null;
    }

    /**
     * View basic data of the shopping cart in standard output (console)
     */
    public void viewShoppingCartBasicData() {
        this.shoppingCartView.printBasicDataToStandardOutput();
    }

    /**
     * View complete data of the shopping cart in standard output (console)
     */
    public void viewShoppingCart() {
        this.shoppingCartView.printCompleteDataToStandardOutput(0, "Pre-View");
    }


    /**
     * Empty the shopping cart
     */
    public void emptyShoppingCart() {
        Iterator<Map.Entry<String, ItemQuantity>> iterator = this.getShoppingCart().getBoughtItemMap().entrySet().iterator();

        while (iterator.hasNext()) {
            InventoryController.getCurrentInventoryController().addItemToInventory(iterator.next().getValue());
            iterator.remove();
        }
    }


    public boolean isShoppingCartEmpty() {
        return this.shoppingCart.isEmpty();
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    /**
     * Generate new Id transaction
     * @return id transaction
     */
    private static String generateIdTransaction() {
        transaction++;
        return String.format("%06d", transaction);
    }
}
