package eas.com;

import eas.com.controller.InventoryController;
import eas.com.controller.ShoppingController;
import eas.com.exception.QuickMartException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by eduardo on 12/12/2016.
 */
public class App {

    static private BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        String option = "";

        while (!option.equalsIgnoreCase("X")) {
            showMenu();

            System.out.print("Please selected an option: ");
            option = consoleIn.readLine().toUpperCase();

            switch (option) {
                case "0":
                    fillInventoryOption();
                    break;
                case "1":
                    viewInventoryOption();
                    break;
                case "2":
                    saleRewardsMemberOption();
                    break;
                case "3":
                    saleRegularMemberOption();
                    break;
                case "4":
                    addItemShoppingCartOption();
                    break;
                case "5":
                    removeItemShoppingCartOption();
                    break;
                case "6":
                    emptyShoppingCartOption();
                    break;
                case "7":
                    viewShoppingCartOption();
                    break;
                case "8":
                    checkoutAndPrintReceiptOption();
                    break;
                case "9":
                    cancelTransactionOption();
                    break;
                case "X":
                    System.out.println("Option Selected: Close Application");
                    System.out.println("Bye...");
                    break;
            }
        }
    }

    private static void showMenu() {
        System.out.println("------   Welcome to Quick Mart ---------------------------- ");
        System.out.println("----- 0. Inventory. Fill ---------------------------------- ");
        System.out.println("----- 1. Inventory. View ---------------------------------- ");
        System.out.println("----- 2. Sale. Rewards Member ----------------------------- ");
        System.out.println("----- 3. Sale. Regular Member ----------------------------- ");
        System.out.println("----- 4. Sopping Cart. Add Item --------------------------- ");
        System.out.println("----- 5. Sopping Cart. Remove Item ------------------------ ");
        System.out.println("----- 6. Sopping Cart. Empty ------------------------------ ");
        System.out.println("----- 7. Sopping Cart. View ------------------------------- ");
        System.out.println("----- 8. Sopping Cart. Checkout and Print receipt --------- ");
        System.out.println("----- 9. Sopping Cart. Cancel Transaction------------------ ");
        System.out.println("----- X. Close Application -------------------------------- ");
    }

    private static void fillInventoryOption() throws IOException {
        System.out.println("--------- Option Selected: 0. Inventory. Fill --------------");
        System.out.println();

       System.out.print("Please enter the file name for loading the inventory: ");
       String fileName = consoleIn.readLine();

        try {
            InventoryController.getCurrentInventoryController().loadDataFromFile(fileName);
            System.out.println("The data of the file is this: ");

            InventoryController.getCurrentInventoryController().viewInventoryTemporalData();

            System.out.print("Are you sure that you want to load this data into the inventory? (s) for confirm, other letter otherwise: ");
            String sureLoad = consoleIn.readLine();
            if (sureLoad.equalsIgnoreCase("s")) {
                InventoryController.getCurrentInventoryController().fillInventory();
                System.out.println("The inventory was filled");
            } else {
                InventoryController.getCurrentInventoryController().discardTempDataFromInventory();
                System.out.println("The data was discard");
            }


        } catch (IOException | QuickMartException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
    }

    private static void viewInventoryOption() {
        System.out.println("--------- Option Selected: 1. Inventory. View --------------");
        System.out.println();
        InventoryController.getCurrentInventoryController().viewInventory();
        System.out.println();
    }

    private static void saleRewardsMemberOption() {
        System.out.println("--------- Option Selected: 2. Sale. Rewards Member --------");

        try {
            ShoppingController.createNewShoppingControllerMemberCustomer();
            System.out.println("The operation was made correctly. New Customer/Shopping cart was created: ");
            ShoppingController.getCurrentShoppingController().viewShoppingCartBasicData();
        } catch (QuickMartException e) {
            System.out.println("In this moment you are taking care of the transaction:  " + ShoppingController.getCurrentShoppingController().getShoppingCart().getTransaction() + ". Please, finish it for create a new");
        }
    }

    private static void saleRegularMemberOption() {
        System.out.println("--------- Option Selected: 3. Sale. Regular Member --------");

        try {
            ShoppingController.createNewShoppingControllerRegularCustomer();
            System.out.println("The operation was made correctly. New Customer/Shopping cart was created: ");
            ShoppingController.getCurrentShoppingController().viewShoppingCartBasicData();
        } catch (QuickMartException e) {
            System.out.println("The operation is not valid. In this moment you are taking care of the transaction:  " + ShoppingController.getCurrentShoppingController().getShoppingCart().getTransaction() + ". Please, finish it for create a new");
        }

    }

    private static void addItemShoppingCartOption() throws IOException {
        System.out.println("--------- Option Selected: 4. Shopping Cart. Add Item -----");

        if (InventoryController.getCurrentInventoryController().isInventoryEmpty()) {
            System.out.println("The operation is not valid. The inventory is empty. Please, fill the inventory");
            return;
        }

        if (ShoppingController.getCurrentShoppingController() == null) {
            System.out.println("The operation is not valid. There is no a shopping cart created. Please, create member or regular customer");
            return;
        }

        InventoryController.getCurrentInventoryController().viewInventory();
        System.out.print("Please, select one item and its quantity from inventory. (For example: Milk,3) :");
        try {
            ShoppingController.getCurrentShoppingController().addItem(consoleIn.readLine());
            ShoppingController.getCurrentShoppingController().viewShoppingCart();
        } catch (QuickMartException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void removeItemShoppingCartOption() throws IOException {
        System.out.println("--------- Option Selected: 5. Shopping Cart. Add Item -----");

        if (ShoppingController.getCurrentShoppingController() == null) {
            System.out.println("The operation is not valid. There is no a shopping cart created. Please, create member or regular customer");
            return;
        }

        if (ShoppingController.getCurrentShoppingController().isShoppingCartEmpty()) {
            System.out.println("The operation is not valid. The Shopping Cart is empty. Please, fill the ShoppingCart");
            return;
        }

        ShoppingController.getCurrentShoppingController().viewShoppingCart();
        System.out.print("Please, select one item and its quantity from Shopping Cart. (For example: Milk,3) :");
        try {
            ShoppingController.getCurrentShoppingController().removeItem(consoleIn.readLine());
            ShoppingController.getCurrentShoppingController().viewShoppingCart();
        } catch (QuickMartException e) {
            System.out.println(e.getMessage());
        }

    }


    private static void emptyShoppingCartOption() throws IOException {
        System.out.println("--------- Option Selected: 6. Shopping Cart. Empty ------");

        if (ShoppingController.getCurrentShoppingController() == null) {
            System.out.println("The operation is not valid. There is no a shopping cart created. Please, create member or regular customer");
            return;
        }

        System.out.print("Are you sure that you want to empty the Shopping Cart? (s) for confirm, other letter otherwise: ");

        String sureEmpty = consoleIn.readLine();
        if (sureEmpty.equalsIgnoreCase("s")) {
            ShoppingController.getCurrentShoppingController().emptyShoppingCart();
            System.out.println("The Shopping Cart was emptied");
            ShoppingController.getCurrentShoppingController().viewShoppingCart();
        } else {
            System.out.println("Operation cancelled");
        }

    }

    private static void viewShoppingCartOption() {
        System.out.println("--------- Option Selected: 7. Shopping Cart. View -------");

        if (ShoppingController.getCurrentShoppingController() == null) {
            System.out.println("The operation is not valid. There is no a shopping cart created. Please, create member or regular customer");
            return;
        }

        ShoppingController.getCurrentShoppingController().viewShoppingCart();
    }

    private static void checkoutAndPrintReceiptOption() throws IOException {
        System.out.println("--------- Option Selected: 8. Checkout and Print receipt-");

        if (ShoppingController.getCurrentShoppingController() == null) {
            System.out.println("The operation is not valid. There is no a shopping cart created. Please, create member or regular customer");
            return;
        }


        String transaction = ShoppingController.getCurrentShoppingController().getShoppingCart().getTransaction();
        System.out.println("We are finalizing the transaction: " + transaction + ". Please enter the money: ");

        try {
            float cash = Float.valueOf(consoleIn.readLine());
            ShoppingController.getCurrentShoppingController().checkoutAndPrintReceipt(cash);
            System.out.println("The operation was made correctly. The transaction: " + transaction + " has finished");
        } catch (NumberFormatException e) {
            System.out.println("The value introduced is wrong");
        } catch (QuickMartException e) {
            System.out.println(e.getMessage());
        }


    }

    private static void cancelTransactionOption() throws IOException {
        System.out.println("--------- Option Selected: 9. Sopping Cart. Cancel Transaction-");

        if (ShoppingController.getCurrentShoppingController() == null) {
            System.out.println("The operation is not valid. There is no a shopping cart created. Please, create member or regular customer");
            return;
        }

        String transaction = ShoppingController.getCurrentShoppingController().getShoppingCart().getTransaction();

        System.out.print("Are you sure that you want to discard the transaction: " + transaction + " ? (s) for confirm, other letter otherwise: ");

        String sureDiscard = consoleIn.readLine();
        if (sureDiscard.equalsIgnoreCase("s")) {
            ShoppingController.getCurrentShoppingController().cancelTransaction();
            System.out.println("The transaction: " + transaction + " was cancelled");
        } else {
            System.out.println("Operation cancelled. The transaction is still open");
        }

    }


}
