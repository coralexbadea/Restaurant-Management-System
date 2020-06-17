package businessLayer;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
/**
    @class CompositeProduct  - This class is used to represent an product that
    has in composition other products
 */
public class CompositeProduct extends MenuItem implements Serializable {
    private String name;
    private ArrayList<MenuItem> productComponents ;
    public CompositeProduct(String name,ArrayList<MenuItem> productComponents){
        this.productComponents =  productComponents;
        this.name = name;
    }

    /**
        computePrice method - used to add the prices of the composite products
        @return price - the price of the composite product
     */
    public float computePrice() {
        float price = 0;
        Iterator productIterator = productComponents.iterator();
        while(productIterator.hasNext()){
            MenuItem product = (MenuItem) productIterator.next();
            price += product.computePrice();
        }
        return price;
    }


    /**
        @return name -the name of the product
     */
    public String getItemName() {
        return name;
    }
}
