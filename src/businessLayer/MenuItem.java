package businessLayer;

import java.io.Serializable;
/**
    @class MenuItem abstract class - this abstract class is used to derive the products
    that will be part of the menu
 */
public abstract class MenuItem implements Serializable {
    /**
        computePrice abstract method - used to compute the price of the
        menu item
     */
    abstract public float computePrice();
    /**
        getItemName abstract method
        @returns the name of the menu item
     */
    abstract public String getItemName();
}
