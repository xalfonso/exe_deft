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
    static private InventoryController inventoryController = new InventoryController();


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
                    saleRewardsMember();
                    break;
                case "3":
                    saleRegularMember();
                    break;
                case "4":
                    System.out.println("Option Selected: 4");
                    break;
                case "5":
                    System.out.println("Option Selected: 5");
                    break;
                case "6":
                    System.out.println("Option Selected: 6");
                    break;
                case "7":
                    System.out.println("Option Selected: 7");
                    break;
                case "8":
                    System.out.println("Option Selected: 8");
                    break;
                case "9":
                    System.out.println("Option Selected: 9");
                    break;
                case "X":
                    System.out.println("Option Selected: Close Application");
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
        System.out.println("----- 9. Sopping Cart. Cancel Transaction-------- --------- ");
        System.out.println("----- X. Close Application -------------------------------- ");
    }

    private static void fillInventoryOption() throws IOException {
        System.out.println("--------- Option Selected: 0. Inventory. Fill --------------");
        System.out.println();

        System.out.print("Please enter the file name for loading the inventory: ");
        String fileName = consoleIn.readLine();

        try {
            inventoryController.loadDataFromFile(fileName);
            System.out.println("The operation was made correctly. The data loaded was: ");

            inventoryController.showTempDataFromFile();

            System.out.print("Are you sure that you want to load this data into the inventory? (s) for confirm, other letter otherwise ");
            String sureLoad = consoleIn.readLine();
            if (sureLoad.equalsIgnoreCase("s")) {
                inventoryController.fillInventory();
                System.out.println("The inventory was filled");
            } else {
                inventoryController.discardTempDataFromInventory();
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
        inventoryController.show();
        System.out.println();
    }

    private static void saleRewardsMember() {
        System.out.println("--------- Option Selected: 2. Sale. Rewards Member --------");

        if (ShoppingController.getCurrentShoppingController() == null) {
            ShoppingController.createNewShoppingControllerMemberCustomer();

            System.out.println("The operation was made correctly. New Customer/Shopping cart was created: ");
            ShoppingController.getCurrentShoppingController().showBasicData();

        } else {
            System.out.println("In this moment you are taking care of the transaction:  " + ShoppingController.getCurrentShoppingController().getShoppingCart().getTransaction() + ". Please, finish it for create a new");
        }
    }

    private static void saleRegularMember() {
        System.out.println("--------- Option Selected: 3. Sale. Regular Member --------");

        if (ShoppingController.getCurrentShoppingController() == null) {
            ShoppingController.createNewShoppingControllerRegularCustomer();

            System.out.println("The operation was made correctly. New Customer/Shopping cart was created: ");
            ShoppingController.getCurrentShoppingController().showBasicData();

        } else {
            System.out.println("In this moment you are taking care of the transaction:  " + ShoppingController.getCurrentShoppingController().getShoppingCart().getTransaction() + ". Please, finish it for create a new");
        }
    }

}
