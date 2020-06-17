package businessLayer;


import java.io.Serializable;
import java.util.Date;

/**
    @class Order - this class is used to represent on order at a restaurant
    with specifil fields and methods.
 */
public class Order implements Serializable {

    private int orderID ; //unique ID for every Order object
    private Date date; //date when the Order Object was instantiated
    private int table; // the table where the order was taken
    private float price; // price of the oreder
    public Order(int ID, int table, float price){
        this.price = price;
        this.date = new Date();
        this.orderID = ID;
        this.table = table;
    }

    /**
        hashCode() method - overriding the hash code custom to the Order class
        @return result - the hashcode of the Object
     */
//    @Override
//    public int hashCode() {
//        int result = 17;
//        result = 31 * result + date.hashCode();
//        result = 31 * result + table;
//        result = 31 * result + orderID;
//        return result;
//
//
//    }

    public Object getOrderID() {
        return this.orderID;
    }

    public Object getOrderTable() {
        return this.table;
    }

    public Object getOrderTotalPrice() {
        return this.price;
    }

    public Object getOrderDate() {
        return this.date;
    }
}
