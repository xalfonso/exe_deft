package eas.com.view;

import eas.com.exception.QuickMartException;
import eas.com.model.Inventory;
import eas.com.model.InventoryItem;

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

    private InventoryItem parseDataFile(String line, int linePos) throws QuickMartException {
        String[] parts = line.split(":");
        if (parts[0].length() == line.length())
            throw new QuickMartException("In the line: " + linePos + " is not defined correctly the name of the item, missing the symbol (:) ");

        if (parts[0].isEmpty())
            throw new QuickMartException("In the line: " + linePos + " the name of the item is empty");

        if (parts[1].isEmpty())
            throw new QuickMartException("In the line: " + linePos + " the data (quantity, prices, tax) of the item is empty");


        /*I must continue parse*/

        return null;
    }
}
