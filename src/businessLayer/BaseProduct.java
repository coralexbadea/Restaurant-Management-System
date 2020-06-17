package businessLayer;

import java.io.Serializable;

/**
    @class BaseProduct  -  This class is used to represent a basic product
    (one with only name and price - no other products in composition)
 */
public class BaseProduct extends MenuItem implements Serializable {
    private String name;
    private float price;

    public BaseProduct(String name, float price) {
        this.name = name;
        this.price = price;
    }
    /**
        @return name - returns the name of the product
     */
    public String getItemName() {
        return name;
    }

    /**
        @return price - the price of the product
     */
    public float computePrice() {
        return price;
    }
}
