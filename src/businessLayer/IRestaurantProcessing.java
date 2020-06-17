package businessLayer;

import java.util.ArrayList;

/**
 * @inv wellFormed() == true;
 */
public interface IRestaurantProcessing {
    /**
     * @pre wellFormed() == true;
     * @param menuItem
     * @pre menuItem != null
     * @post getMenuSize() == getMenuSize()@pre + 1
     * @post wellFormed() == true;
     */
    void createMenuItem(MenuItem menuItem);
    /**
     * @pre wellFormed() == true;
     * @param menuItems
     * @pre menuItem != null
     * @post getMenuSize() == getMenuSize()@pre - menuItem.size()
     * @post wellFormed() == true;
     */
    void deleteMenuItem(ArrayList<MenuItem> menuItems);
    /**
     * @param order
     * @param items
     * @pre wellFormed() == true;
     * @pre order != null
     * @pre items != null
     * @post getOrdersSize() == getOrdersSize()@pre + 1
     * @post wellFormed() == true;
     */
    void insertOrder(Order order, ArrayList<MenuItem> items);
    /**
     * @pre wellFormed() == true;
     * @param doneOrders
     * @pre doneOrders != null
     * @post getOrdersSize() = getOrdersSize()@pre - menuItem.size()
     * @post wellFormed() == true;
     */
    void deleteOrders(ArrayList<Order> doneOrders);
    boolean wellFormed();
    int getMenuSize();
    int getOrdersSize();
}
