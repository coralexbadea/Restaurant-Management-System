package controller;

import businessLayer.*;
import businessLayer.MenuItem;
import dataLayer.RestaurantSerializator;
import presentationLayer.AdministratorGraphicalUserInterface;
import presentationLayer.ChefGraphicalUserInterface;
import presentationLayer.WaiterGraphicalUserInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

/**
 * @class Controller - A class used as controller in the Model View Controller
 * design. The model is represented by the a Restaurant type object and the controller
 * is managing the GUI classes from the presentationLayer package that receives
 * input from the user.
 */
public class Controller {

    private static String fileName;
    private int orderID = 0; //the ID of the last order taken
    private Restaurant theModel; //a restaurant Object used as the Model for MVC design
    private AdministratorGraphicalUserInterface adminView; //a GUI type Object that receiver
    // input and gives output destined for the administrator of the restaurant
    private ChefGraphicalUserInterface chefView;//a GUI type Object that receiver
    // input and gives output destined for the chef of the restaurant
    private WaiterGraphicalUserInterface waiterView;//a GUI type Object that receiver
    // input and gives output destined for a waiter in the restaurant
    private float price;// the price of the last order taken
    private ArrayList<MenuItem> orderedItems;// an array of MenuItems that represent the products ordered in the last taken order
    private Order order;

    public Controller(Restaurant theModel, AdministratorGraphicalUserInterface adminView,
                      ChefGraphicalUserInterface chefView, WaiterGraphicalUserInterface waiterView){
        this.theModel = theModel;
        this.adminView = adminView;
        this.chefView = chefView;
        this.waiterView = waiterView;
        waiterView.addCreateOrderListener(new CreateOrderListener());
        waiterView.addCreateBillListener(new CreateBillListener());
        adminView.addCreateItemListener(new CreateItemListener());
        adminView.addDeleteItemListener(new DeleteItemListener());
        adminView.addEditItemListener(new EditItemListener());
        chefView.addRefreshListener(new RefreshListener());
        waiterView.addExitListener(new ExitListener());
        adminView.addExitListener(new ExitListener());
        chefView.addExitListener(new ExitListener());
        theModel.register(waiterView);
        theModel.register(adminView);
        theModel.register(chefView);
        theModel.notifyObserver();
    }

    /**
     * main method - The main method of the program
     * @param args
     */
    public static void main(String[] args){
        fileName = args[0];

        //a RestaurantSerializator Object used to deserialize a Restaurant
        //Object from a file.
        RestaurantSerializator serializator = new RestaurantSerializator(fileName);
        Restaurant model = serializator.deserializeRestaurant();
        //if the model has not been successfully deserialized a new Restaurant Object
        //is created with default characteristics
        if(model == null){
            model = new Restaurant();
        }
        new Controller(model, new AdministratorGraphicalUserInterface(),
                new ChefGraphicalUserInterface(), new WaiterGraphicalUserInterface());


    }


    /**
     * @class CreateOrderListener used as a listener for creating an order
     */
    class CreateOrderListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String table = waiterView.getTable();
            int tableNo = 0;
            try{
                tableNo = Integer.parseInt(table);
                price = 0 ;
                orderedItems = waiterView.getOrderedItems();
                for(MenuItem item:orderedItems){

                    price = price + item.computePrice();

                }
                orderID = ++orderID;
                order = new Order(orderID, tableNo, price );

                theModel.insertOrder(order, orderedItems);
                theModel.notifyObserver();

            }
            catch(Exception ex){
                waiterView.displayError(ex.getMessage(), "Error reading the table");
            }


        }
    }

    /**
     * @class CreateBillListener used to create a Bill text file with the information
     * of the ordered products along with their prices and a total price
     */
    class CreateBillListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            billCreate();

        }
    }
    //this is the method used in the CreateBIllListener class for creating a bill
    public void billCreate(){
        try {
            File myObj = new File("Order"+order.getOrderID()+".txt");
            if (!myObj.createNewFile()) {
                waiterView.displayError("Bill already exists.","");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            waiterView.displayError("An Error occured wile preparing the Bill", "");
        }
        try {
            FileWriter myWriter = new FileWriter("Order"+orderID+".txt");
            for(MenuItem item: orderedItems) {
                myWriter.write(item.getItemName()) ;
                myWriter.write(System.getProperty( "line.separator" ));
                myWriter.write("..................."+item.computePrice());
                myWriter.write(System.getProperty( "line.separator" ));
            }
            myWriter.write("Total: "+ price);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * @class CreateItemListener used as a listener for creating a menu item at the
     * request of the user.
     */
    class CreateItemListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            MenuItem menuItem = new BaseProduct("",0);
            String name = adminView.getNameCreate();
            String itemPrice = adminView.getPriceCreate();
            ArrayList<MenuItem> componentItems = adminView.getComponents();
            float priceFloat = 0;
            try{
                if(!itemPrice.equals(""))
                    priceFloat = Float.parseFloat(itemPrice);


                if(componentItems.size() == 0){//bascic product
                    menuItem = new BaseProduct(name, priceFloat);
                }
                else{
                   menuItem = new CompositeProduct(name, componentItems);
                }

                theModel.createMenuItem(menuItem);
                theModel.notifyObserver();
            }
            catch(Exception ex){
                waiterView.displayError(ex.getMessage(), "Error reading the price");
            }

        }
    }

    /**
     * @class DeleteItemListener used as a listener for deleting a menu item at the
     * request of the user.
     */
    class DeleteItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<MenuItem> menuItems = adminView.getItemDelete();
            theModel.deleteMenuItem(menuItems);
            theModel.notifyObserver();
        }
    }

    /**
     * @class EditItemListener used as a listener for editing a menu item at the
     * request of the user.
     */
    class EditItemListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<MenuItem> menuItems = adminView.getItemEdit();
            theModel.deleteMenuItem(menuItems);

            MenuItem menuItem = new BaseProduct("",0);
            String name = adminView.getNameEdit();
            if(name.equals(""))
                name = adminView.getItemEdit().get(0).getItemName();
            String itemPrice = adminView.getPriceEdit();

            ArrayList<MenuItem> componentItems = adminView.getComponents();
            float priceFloat = 0;
            if(itemPrice.equals(""))
                priceFloat = adminView.getItemEdit().get(0).computePrice();
            try{
                if(!itemPrice.equals("")){
                    float newPriceFloat = Float.parseFloat(itemPrice);
                    priceFloat = newPriceFloat;
                }

                if(componentItems.size() == 0){//bascic product
                    menuItem = new BaseProduct(name, priceFloat);
                }
                else{
                    menuItem = new CompositeProduct(name, componentItems);
                }


            }
            catch(Exception ex){
                waiterView.displayError(ex.getMessage(), "Error reading the price");
            }
            theModel.createMenuItem(menuItem);
            theModel.notifyObserver();
        }
    }

    /**
     * @class RefreshListener used as a listener for refreshing the GUI list of the
     * orders in the chef graphical user interface.
     */
    class RefreshListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Order> orders = chefView.getChecked();
            theModel.deleteOrders(orders);
            theModel.notifyObserver();
        }
    }

    /**
     * @class ExitListener used as a listener when exiting from the program - it has
     * the purpose of serialization of the Restaurant Object used in the program
     */
    class ExitListener extends WindowAdapter {
        RestaurantSerializator serializator = new RestaurantSerializator(fileName);

        @Override
        public void windowClosing(WindowEvent e)
        {
            /**
             * the serializeRestaurant method from the RestaurantSerializator is used
             */

            e.getWindow().dispose();
            serializator.serializeRestaurant(theModel);
        }
    }




}
