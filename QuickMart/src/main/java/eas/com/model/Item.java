package eas.com.model;

/**
 * Class for simulating the item of the market
 * Created by eduardo on 12/12/2016.
 */
public class Item {

    private String name;
    private float unitRegularPrice;
    private float unitMemberPrice;
    private boolean taxable;


    public Item(String name, float unitRegularPrice, float unitMemberPrice, boolean taxable) {
        this.name = name;
        this.unitRegularPrice = unitRegularPrice;
        this.unitMemberPrice = unitMemberPrice;
        this.taxable = taxable;
    }

    public float differenceMemberRegularPrice() {
        return this.unitRegularPrice - this.unitMemberPrice;
    }

    /**
     * @param isMemberCustomer if the customer is member customer
     * @return unit price depending on type of customer
     */
    public float getUnitPrice(boolean isMemberCustomer) {
        return isMemberCustomer ? this.unitMemberPrice : unitRegularPrice;
    }

    public float getUnitRegularPrice() {
        return unitRegularPrice;
    }

    public float getUnitMemberPrice() {
        return unitMemberPrice;
    }

    public String getTaxableString() {
        return this.taxable ? "Taxable" : "Tax-Exempt";
    }

    public String getName() {
        return name;
    }

    public boolean isTaxable() {
        return taxable;
    }

}
