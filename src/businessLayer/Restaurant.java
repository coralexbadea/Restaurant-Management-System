package businessLayer;


import java.io.Serializable;
import java.util.HashMap; // import the HashMap class


import java.util.ArrayList;

/**
 * @inv wellFormed() == true
 * @class Restaurant - The class that is used to represent a restaurant with
 * attributes and methods specific for working with the logic of orders and
 * menu with products.
 */
public class Restaurant  implements Subject, IRestaurantProcessing, Serializable {

    private ArrayList<MenuItem> menu; //the menu of the restaurant
    private ArrayList<Order> orders; // the orders in the restaurant
    private ArrayList<businessLayer.Observer> observers; // the array of observers that need to be notified when an update occurs
    private Map<Order, ArrayList<MenuItem>> ordersTaken; // a hashtable that has the set of keys represented by
    //the orders and the values represeted by the products that are ordered in that specific order

    public Restaurant(){
        menu = new ArrayList<>();
        orders = new ArrayList<>();
        ordersTaken = new Map<>();
        observers = new ArrayList<>();
        this.menu.add(new BaseProduct("item1", 399));
        this.menu.add(new BaseProduct("item2", 399));
    }

    /**
     * wellFormed() method - is an invariant method that is true every time
     * the menu and orders array are not null, as it they should be for every iteration
     * of the program .
     * @return a boolean value that must be true every time.
     */
    @Override
    public boolean wellFormed() {
        if(this.menu == null || this.orders == null || ordersTaken == null)
            return false;
        return true;
    }

    /**
     *
     * @return the size of the menu array
     */
    @Override
    public int getMenuSize() {
        return menu.size();
    }

    /**
     *
     * @return th size of the orders array
     */
    @Override
    public int getOrdersSize() {
        return orders.size();
    }

    /**
     * register method - method used to register the an Observer object the the set
     * of Observer objects that must be notified when an update occurs.
     * @param o - the Observator object to be added
     */
    @Override
    public void register(businessLayer.Observer o) {
        observers.add(o);
    }

    /**
     * unregiste method - method to delete an observer from the set of Observer
     * objects that are notified every time when an update occurs.
     * @param o - the object to deleted from the set of observers
     */
    @Override
    public void unregister(businessLayer.Observer o) {
        observers.remove(o);
    }

    /**
     * notifyObserver method - Used to notify all Observer objects whenever is needed
     * the Objects that are update are the following - the array of the menu items, array of the
     * orders, the hashtable ordersTaken. These Objects are updated in the Observer Objects
     * via the update method and a vector named args.
     */
    @Override
    public void notifyObserver() {
        Object args[] = new Object[3];
        args[0] = menu;
        args[1] = orders;
        args[2] = ordersTaken;

        for(Observer obs:observers){
            obs.update(args);
        }
    }

    /**
     * createMenuItem method - used to create a MenuItem Object to the menu array
     * @pre wellFormed() == true;
     * @param menuItem
     * @pre menuItem != null
     * @post getMenuSize() == getMenuSize()@pre + 1
     * @post wellFormed() == true;
     */
    @Override
    public void createMenuItem(MenuItem menuItem) {
        assert menuItem != null;
        assert wellFormed();
        int sizePre = getMenuSize();

        menu.add(menuItem);

        int sizePost = getMenuSize();
        assert sizePost == sizePre + 1;
    }

    /**
     * deleteMenuItem method - used to delete a MenuItem Object from a menu array
     * @pre wellFormed() == true;
     * @param menuItems
     * @pre menuItem != null
     * @post getMenuSize() = getMenuSize()@pre - menuItem.size()
     * @post wellFormed() == true;
     */
    @Override
    public void deleteMenuItem(ArrayList<MenuItem> menuItems) {
        assert wellFormed();
        assert menuItems != null;
        int sizePre = getMenuSize();
        for(MenuItem item: menuItems){
            menu.remove(item);
        }
        int sizePost = getMenuSize();
        assert wellFormed();
        assert sizePost == sizePre - menuItems.size();
    }

    /**
     * deleteOrder method - used to delete on Order object from the array of orders
     * @pre wellFormed() == true;
     * @param doneOrders
     * @pre doneOrders != null
     * @post getOrdersSize() = getOrdersSize()@pre - menuItem.size()
     * @post wellFormed() == true;
     */
    public void deleteOrders(ArrayList<Order> doneOrders){
        assert wellFormed();
        assert doneOrders != null;
        int sizePre = getOrdersSize();
        for(Order order: doneOrders){
            orders.remove(order);
            ordersTaken.remove(order);
        }
        int sizePost = getOrdersSize();
        assert wellFormed();
        assert sizePost == sizePre - doneOrders.size();
    }


    /**
     * insertOrder method - used to insert an Order object to the hash table ordersTaken along with the ordered items
     * @param order
     * @param items
     * @pre wellFormed() == true;
     * @pre order != null
     * @pre items != null
     * @post getOrdersSize() == getOrdersSize()@pre + 1
     * @post wellFormed() == true;
     */
    public void insertOrder(Order order, ArrayList<MenuItem> items){
        assert wellFormed();
        assert order != null && items != null;
        int sizePre = getOrdersSize();
        orders.add(order);
        ordersTaken.put(order, items);
        int sizePost = getOrdersSize();
        assert wellFormed();
        assert sizePost == sizePre + 1;

    }


}
