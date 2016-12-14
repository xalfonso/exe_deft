package eas.com.view;

import eas.com.exception.QuickMartException;
import eas.com.model.BoughtItem;
import eas.com.model.ShoppingCart;

import java.text.DecimalFormat;
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
     * generate the complete data of the Shopping Cart
     */
    private String generateCompleteData(float cash) {
        String padding = String.format("%20s", "");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        StringBuilder stringBuilder = new StringBuilder(500);
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

        for (Map.Entry<String, BoughtItem> entry : this.shoppingCart.getBoughtItemMap().entrySet()) {
            //System.out.println(entry.getValue().toStringFormat(this.shoppingCart.isMemberCustomer()));
            stringBuilder.append(entry.getValue().toStringFormat(this.shoppingCart.isMemberCustomer())).append(System.lineSeparator());
            totalItem += entry.getValue().getQuantity();
            subTotalPrice += entry.getValue().getTotalPrice(this.shoppingCart.isMemberCustomer());
            totalTax += entry.getValue().getTotalTax(this.shoppingCart.isMemberCustomer());
            savedMoney += entry.getValue().getSavedMoney(this.shoppingCart.isMemberCustomer());
        }


        stringBuilder.append("******************************").append(System.lineSeparator());
        //System.out.println("******************************");
        stringBuilder.append("TOTAL NUMBER OF ITEMS SOLD: ").append(totalItem).append(System.lineSeparator());
        //System.out.println("TOTAL NUMBER OF ITEMS SOLD: " + totalItem);
        stringBuilder.append("SUB-TOTAL: $").append(Float.valueOf(decimalFormat.format(subTotalPrice))).append(System.lineSeparator());
        //System.out.println("SUB-TOTAL: $" + subTotalPrice);
        stringBuilder.append("TAX (6.5%): $").append( Float.valueOf(decimalFormat.format(totalTax))).append(System.lineSeparator());
        //System.out.println("TAX (6.5%): $" + totalTax);
        stringBuilder.append("TOTAL : $").append(Float.valueOf(decimalFormat.format((subTotalPrice + totalTax)))).append(System.lineSeparator());
        //System.out.println("TOTAL : $" + (subTotalPrice + totalTax));

        if (cash != 0) {
            stringBuilder.append("CASH : $").append(cash).append(System.lineSeparator());
            stringBuilder.append("CHANGE : $").append((cash - (subTotalPrice + totalTax))).append(System.lineSeparator());

            //System.out.println("CASH : $" + cash);
            //System.out.println("CHANGE : $" + (cash - (subTotalPrice + totalTax)));
        }

        if (this.shoppingCart.isMemberCustomer())
            stringBuilder.append("YOU SAVED : $").append(Float.valueOf(decimalFormat.format(savedMoney))).append("!");
        //System.out.println("YOU SAVED : $" + savedMoney + "!");

        return stringBuilder.toString();
    }

    public void printCompleteDataToStandardOutput(float cash) {
        System.out.println(this.generateCompleteData(cash));
    }

    public void printBasicDataToStandardOutput() {
        System.out.println(this.generateBasicData());
    }


    /**
     * Generate the basic data of the shopping cart
     */
    private String generateBasicData() {
        return this.shoppingCart.getTypeCustomer()
                + System.lineSeparator()
                + this.shoppingCart.getLocalDate()
                + System.lineSeparator()
                + "TRANSACTION: " + this.shoppingCart.getTransaction();

        /*System.out.println(this.shoppingCart.getTypeCustomer());
        System.out.println(this.shoppingCart.getLocalDate());
        System.out.println("TRANSACTION: " + this.shoppingCart.getTransaction());*/
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
