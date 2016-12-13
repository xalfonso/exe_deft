package eas.com.model;

/**
 * Created by eduardo on 12/12/2016.
 */
public class Item implements Cloneable {

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

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isTaxable() {
        return taxable;
    }

    public float diferenceMemberRegularPrice(){
        return this.unitRegularPrice - this.unitMemberPrice;
    }


    public Item setTaxable(boolean taxable) {
        this.taxable = taxable;
        return this;
    }

    /**
     * @param isMemberCustomer if the customer is member customer
     * @return unit price depending on type of customer
     */
    public float getUnitPrice(boolean isMemberCustomer){
        return isMemberCustomer ? this.unitMemberPrice : unitRegularPrice;
    }

    public float getUnitRegularPrice() {
        return unitRegularPrice;
    }

    public float getUnitMemberPrice() {
        return unitMemberPrice;
    }

    public String getTaxableString(){
        return this.taxable ? "Taxable" : "Tax-Exempt";
    }

    @Override
    protected Object clone()  {
        Object o = null;
        try{
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
