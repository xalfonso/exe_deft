package eas.com.view;

import eas.com.exception.QuickMartException;
import eas.com.model.Inventory;
import eas.com.model.InventoryItem;
import eas.com.model.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 12/12/2016.
 */
public class InventoryView {

    private Inventory inventory;

    private BufferedReader fileReaderInventory;

    private PrintStream terminalShowInventory;

    /**
     * @param inventory data
     */
    public InventoryView(Inventory inventory) {
        this.inventory = inventory;
        this.terminalShowInventory = System.out;
    }

    public List<InventoryItem> loadDataFromFile(String fileInventory) throws IOException, QuickMartException {
        List<InventoryItem> items = new ArrayList<>();
        try {
            this.fileReaderInventory = new BufferedReader(new FileReader(fileInventory));

            String line;
            int linePos = 1;
            while ((line = this.fileReaderInventory.readLine()) != null) {
                InventoryItem inventoryItem = this.parseDataFile(line, linePos);
                items.add(inventoryItem);
                linePos++;
            }
        } catch (FileNotFoundException e) {
            throw new QuickMartException("The file: " + fileInventory + " for loading the inventory do not exist");
        } finally {
            this.fileReaderInventory.close();
        }


        return items;
    }

    /**
     * Parse the data of each item defined in the file of the inventory
     *
     * @param line of the file
     * @param linePos pos os the line in the file
     * @return InventoryItem
     * @throws QuickMartException different errors found in the line parsing
     */
    private InventoryItem parseDataFile(String line, int linePos) throws QuickMartException {
        String[] nameAndDetail = line.split(":");
        if (nameAndDetail.length == 1)
            throw new QuickMartException("In the line: " + linePos + " is not defined correctly the item, missing the symbol (:) ");

        if (nameAndDetail[1].isEmpty())
            throw new QuickMartException("In the line: " + linePos + " the data (quantity, prices, tax) of the item is empty");


        /*Item Name*/
        if (nameAndDetail[0].isEmpty())
            throw new QuickMartException("In the line: " + linePos + " the name of the item is empty");

        String itemName = nameAndDetail[0].trim();


        String[] partsDetail = nameAndDetail[1].split(",");

        if (partsDetail.length < 4)
            throw new QuickMartException("In the line: " + linePos + " is not defined correctly the data (quantity, prices, tax) of the item, missing the symbol (,) in some position");


        if (partsDetail.length > 4)
            throw new QuickMartException("In the line: " + linePos + " is not defined correctly the data (quantity, prices, tax) of the item, too many symbols (,) ");


        /*Quantity*/
        if (partsDetail[0].isEmpty())
            throw new QuickMartException("In the line: " + linePos + " the quantity of the item is empty");

        int quantity;
        try {
            quantity = Integer.valueOf(partsDetail[0].trim());
        } catch (NumberFormatException e) {
            throw new QuickMartException("In the line: " + linePos + " the quantity of the item is wrong");
        }

        /*regular price*/
        if (partsDetail[1].isEmpty())
            throw new QuickMartException("In the line: " + linePos + " the regular price of the item is empty");

        int regularPrice;
        try {
            regularPrice = Integer.valueOf(partsDetail[1].trim().replace("$", ""));
        } catch (NumberFormatException e) {
            throw new QuickMartException("In the line: " + linePos + " the regular price of the item is wrong");
        }

        /*member price*/
        if (partsDetail[2].isEmpty())
            throw new QuickMartException("In the line: " + linePos + " the member price of the item is empty");

        int memberPrice;
        try {
            memberPrice = Integer.valueOf(partsDetail[2].trim().replace("$", ""));
        } catch (NumberFormatException e) {
            throw new QuickMartException("In the line: " + linePos + " the member price of the item is wrong");
        }

        /*taxable*/
        if (partsDetail[3].isEmpty())
            throw new QuickMartException("In the line: " + linePos + " the taxable value of the item is empty");

        boolean taxable;
        String taxString = partsDetail[3].trim();
        if (taxString.equalsIgnoreCase("Tax-Exempt")) {
            taxable = true;
        } else if (taxString.equalsIgnoreCase("Taxable")) {
            taxable = false;
        } else {
            throw new QuickMartException("In the line: " + linePos + " the taxable value of the item is wrong. Must be [Tax-Exempt or Taxable]");
        }

        return new InventoryItem(new Item(itemName, regularPrice, memberPrice,taxable), quantity);
    }
}
