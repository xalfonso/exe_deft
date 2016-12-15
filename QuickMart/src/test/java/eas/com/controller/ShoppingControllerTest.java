package eas.com.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by eduardo on 12/14/2016.
 */
public class ShoppingControllerTest {

    @BeforeClass
    public static void setUp() throws Exception {
        String inventoryFileTest = "C:\\Users\\USER\\QuickMartInventory\\inventory.txt";
        InventoryController.getCurrentInventoryController().loadDataFromFile(inventoryFileTest);
        InventoryController.getCurrentInventoryController().fillInventory();

        ShoppingController.createNewShoppingControllerRegularCustomer();
    }

    @Test
    public void testDataInventory() throws Exception {
        Assert.assertEquals(9, InventoryController.getCurrentInventoryController().getInventory().getInventoryItemMap().get("Bread").getQuantity());
        Assert.assertEquals(false, InventoryController.getCurrentInventoryController().getInventory().getInventoryItemMap().get("Flour").getItem().isTaxable());
        Assert.assertEquals(4.6f, InventoryController.getCurrentInventoryController().getInventory().getInventoryItemMap().get("Egg").getItem().getUnitRegularPrice(),0);
    }

    @Test
    public void testAddItemShoppingCart() throws Exception {
        ShoppingController.getCurrentShoppingController().addItem("Egg, 16");
        ShoppingController.getCurrentShoppingController().addItem("Bread, 5");

        Assert.assertEquals(16, ShoppingController.getCurrentShoppingController().getShoppingCart().getBoughtItemMap().get("Egg").getQuantity());
        Assert.assertEquals(5, ShoppingController.getCurrentShoppingController().getShoppingCart().getBoughtItemMap().get("Bread").getQuantity());

        //In the inventory must be the other part 30 - 16 = 14 (Egg)
        Assert.assertEquals(14, InventoryController.getCurrentInventoryController().getInventory().getInventoryItemMap().get("Egg").getQuantity());

        //In the inventory must be the other part 9 - 5 = 4 (Bread)
        Assert.assertEquals(4, InventoryController.getCurrentInventoryController().getInventory().getInventoryItemMap().get("Bread").getQuantity());
    }

    @Test
    public void testRemoveItemShoppingCart() throws Exception{
        ShoppingController.getCurrentShoppingController().removeItem("Egg, 6");
        ShoppingController.getCurrentShoppingController().removeItem("Bread, 5");

        Assert.assertEquals(10, ShoppingController.getCurrentShoppingController().getShoppingCart().getBoughtItemMap().get("Egg").getQuantity());

        //In the shopping cart must be 0 of Bread, the the item is deleted : Null
        Assert.assertNull(ShoppingController.getCurrentShoppingController().getShoppingCart().getBoughtItemMap().get("Bread"));


        //In the inventory must be  20 (Egg)
        Assert.assertEquals(20, InventoryController.getCurrentInventoryController().getInventory().getInventoryItemMap().get("Egg").getQuantity());

        //In the inventory must be  9 (Egg)
        Assert.assertEquals(9, InventoryController.getCurrentInventoryController().getInventory().getInventoryItemMap().get("Bread").getQuantity());
    }



    @After
    public void end() throws Exception {

    }
}
