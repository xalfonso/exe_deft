package eas.com.view;

import eas.com.exception.QuickMartException;
import eas.com.model.Inventory;
import eas.com.model.Item;
import eas.com.model.ItemQuantity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * View for the Inventory
 * <p>
 * Created by eduardo on 12/12/2016.
 */
public class InventoryView {

    /**
     * Represent the model
     */
    private Inventory inventory;


    public InventoryView(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * print the Inventory data to standard Output
     */
    public void printToStandardOutput() {
        System.out.println("<item>: <quantity>, <regular price>, <member price>, <tax status>");
        this.inventory.getInventoryItemMap()
                .forEach((k, v)
                        -> System.out.println(k + ": " + v.getQuantity() + ", $" + v.getItem().getUnitRegularPrice() + ", $" + v.getItem().getUnitMemberPrice() + ", " + v.getItem().getTaxableString()));
    }


    /**
     * print the Inventory temporal data to standard Output
     *
     * @param itemQuantities to updateView
     */
    public void printTemporalDataToStandardOutput(List<ItemQuantity> itemQuantities) {
        System.out.println("<item>: <quantity>, <regular price>, <member price>, <tax status>");
        itemQuantities
                .forEach(v -> System.out.println(v.getItem().getName() + ": " + v.getQuantity() + ", $" + v.getItem().getUnitRegularPrice() + ", $" + v.getItem().getUnitMemberPrice() + ", " + v.getItem().getTaxableString()));
    }

    /**
     * Load the file inventory
     *
     * @param fileInventory to read
     * @return List<ItemQuantity>
     * @throws IOException  is happens any error in read line from the file
     * @throws QuickMartException different errors found
     */
    public List<ItemQuantity> readDataFromFile(String fileInventory) throws IOException, QuickMartException {
        List<ItemQuantity> items = new ArrayList<>();

        try (BufferedReader fileReaderInventory = new BufferedReader(new FileReader(fileInventory))) {

            String line;
            int linePos = 1;
            while ((line = fileReaderInventory.readLine()) != null) {
                ItemQuantity itemQuantity = this.parseDataLine(line, linePos);
                items.add(itemQuantity);
                linePos++;
            }
        } catch (FileNotFoundException e) {
            throw new QuickMartException("The file: " + fileInventory + " for loading the inventory do not exist");

        }

        return items;
    }

    /**
     * Parse the data of each item defined in the file of the inventory
     *
     * @param line    of the file
     * @param linePos pos os the line in the file
     * @return ItemQuantity
     * @throws QuickMartException different errors found in the line parsing
     */
    private ItemQuantity parseDataLine(String line, int linePos) throws QuickMartException {
        String[] nameAndDetail = line.split(":");
        if (nameAndDetail.length == 1)
            throw new QuickMartException("In the line: " + linePos + " is not defined correctly the item, missing the symbol (:) {Data Line: " + line + "}");

        if (nameAndDetail[1].isEmpty())
            throw new QuickMartException("In the line: " + linePos + " the data (quantity, prices, tax) of the item is empty {Data Line: " + line + "}");


        /*Item Name*/
        if (nameAndDetail[0].isEmpty())
            throw new QuickMartException("In the line: " + linePos + " the name of the item is empty {Data Line: " + line + "}");

        String itemName = nameAndDetail[0].trim();


        String[] partsDetail = nameAndDetail[1].split(",");

        if (partsDetail.length < 4)
            throw new QuickMartException("In the line: " + linePos + " is not defined correctly the data (quantity, prices, tax) of the item, missing the symbol (,) in some position {Data Line: " + line + "}");


        if (partsDetail.length > 4)
            throw new QuickMartException("In the line: " + linePos + " is not defined correctly the data (quantity, prices, tax) of the item, too many symbols (,) {Data Line: " + line + "}");


        /*Quantity*/
        if (partsDetail[0].isEmpty())
            throw new QuickMartException("In the line: " + linePos + " the quantity of the item is empty {Data Line: " + line + "}");

        int quantity;
        try {
            quantity = Integer.valueOf(partsDetail[0].trim());
        } catch (NumberFormatException e) {
            throw new QuickMartException("In the line: " + linePos + " the quantity of the item is wrong {Data Line: " + line + "}");
        }

        /*regular price*/
        if (partsDetail[1].isEmpty())
            throw new QuickMartException("In the line: " + linePos + " the regular price of the item is empty {Data Line: " + line + "}");

        float regularPrice;
        try {
            regularPrice = Float.valueOf(partsDetail[1].trim().replace("$", ""));
        } catch (NumberFormatException e) {
            throw new QuickMartException("In the line: " + linePos + " the regular price of the item is wrong {Data Line: " + line + "}");
        }

        /*member price*/
        if (partsDetail[2].isEmpty())
            throw new QuickMartException("In the line: " + linePos + " the member price of the item is empty {Data Line: " + line + "}");

        float memberPrice;
        try {
            memberPrice = Float.valueOf(partsDetail[2].trim().replace("$", ""));
        } catch (NumberFormatException e) {
            throw new QuickMartException("In the line: " + linePos + " the member price of the item is wrong {Data Line: " + line + "}");
        }

        /*taxable*/
        if (partsDetail[3].isEmpty())
            throw new QuickMartException("In the line: " + linePos + " the taxable value of the item is empty {Data Line: " + line + "}");

        boolean taxable;
        String taxString = partsDetail[3].trim();
        if (taxString.equalsIgnoreCase("Tax-Exempt")) {
            taxable = false;
        } else if (taxString.equalsIgnoreCase("Taxable")) {
            taxable = true;
        } else {
            throw new QuickMartException("In the line: " + linePos + " the taxable value of the item is wrong. Must be [Tax-Exempt or Taxable] {Data Line: " + line + "}");
        }

        return new ItemQuantity(new Item(itemName, regularPrice, memberPrice, taxable), quantity);
    }


}
