package eas.com.view;

import eas.com.exception.QuickMartException;
import eas.com.model.ItemQuantity;
import eas.com.model.ShoppingCart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
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
     * Print the invoice of the transaction
     *
     * @param cash paid by the customer
     * @return path of the file where was generated the invoice
     * @throws QuickMartException different errors found
     */
    public String printInvoice(float cash) throws QuickMartException {
        String fileName = "transaction_" + this.shoppingCart.getTransaction() + "_" + this.shoppingCart.getLocalDate().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + ".txt";

        File file = new File("QuickMartInvoices" + File.separator + fileName);
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdir())
                throw new QuickMartException("Error to create directory QuickMartInvoices (inside the user home directory) for saving the invoices ");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(this.generateCompleteData(cash, ""));
        } catch (IOException e) {
            throw new QuickMartException("Error to print invoice with transaction: " + this.shoppingCart.getTransaction());
        }

        return file.getAbsolutePath();
    }

    /**
     * Print the complete data of Shopping Cart to standard Output
     *
     * @param cash   paid by the customer
     * @param header of the view
     */
    public void printCompleteDataToStandardOutput(float cash, String header) {
        System.out.println(this.generateCompleteData(cash, header));
    }

    /**
     * Print the basic data of Shopping Cart to standard Output
     */
    public void printBasicDataToStandardOutput() {
        System.out.println(this.generateBasicData());
    }


    /**
     * Generate the basic data of the shopping cart
     */
    private String generateBasicData() {
        return this.shoppingCart.getTypeCustomer()
                + System.lineSeparator()
                + this.shoppingCart.getLocalDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
                + System.lineSeparator()
                + "TRANSACTION: " + this.shoppingCart.getTransaction();
    }

    /**
     * generate the complete data of the Shopping Cart
     */
    private String generateCompleteData(float cash, String header) {
        String padding = String.format("%20s", "");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        StringBuilder stringBuilder = new StringBuilder(500);
        stringBuilder.append(header).append(System.lineSeparator());
        stringBuilder.append(this.generateBasicData()).append(System.lineSeparator());
        stringBuilder.append("ITEM")
                .append(padding)
                .append("QUANTITY")
                .append(padding)
                .append("UNIT PRICE")
                .append(padding)
                .append("TOTAL")
                .append(System.lineSeparator());

        int totalItem = 0;
        float subTotalPrice = 0;
        float totalTax = 0;
        float savedMoney = 0;

        for (Map.Entry<String, ItemQuantity> entry : this.shoppingCart.getBoughtItemMap().entrySet()) {
            stringBuilder.append(entry.getValue().toStringFormatShoppingCart(this.shoppingCart.isMemberCustomer())).append(System.lineSeparator());
            totalItem += entry.getValue().getQuantity();
            subTotalPrice += entry.getValue().getTotalPrice(this.shoppingCart.isMemberCustomer());
            totalTax += entry.getValue().getTotalTax(this.shoppingCart.isMemberCustomer());
            savedMoney += entry.getValue().getSavedMoney(this.shoppingCart.isMemberCustomer());
        }


        stringBuilder.append("******************************").append(System.lineSeparator());
        stringBuilder.append("TOTAL NUMBER OF ITEMS SOLD: ").append(totalItem).append(System.lineSeparator());
        stringBuilder.append("SUB-TOTAL: $").append(Float.valueOf(decimalFormat.format(subTotalPrice))).append(System.lineSeparator());
        stringBuilder.append("TAX (6.5%): $").append(Float.valueOf(decimalFormat.format(totalTax))).append(System.lineSeparator());
        stringBuilder.append("TOTAL : $").append(Float.valueOf(decimalFormat.format((subTotalPrice + totalTax)))).append(System.lineSeparator());

        if (cash != 0) {
            stringBuilder.append("CASH : $").append(cash).append(System.lineSeparator());
            stringBuilder.append("CHANGE : $").append(Float.valueOf(decimalFormat.format(cash - (subTotalPrice + totalTax)))).append(System.lineSeparator());
        }

        if (this.shoppingCart.isMemberCustomer())
            stringBuilder.append("YOU SAVED : $").append(Float.valueOf(decimalFormat.format(savedMoney))).append("!");

        return stringBuilder.toString();
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
