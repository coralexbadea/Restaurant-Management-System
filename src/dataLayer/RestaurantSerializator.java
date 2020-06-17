package dataLayer;

import businessLayer.Restaurant;

import java.io.*;

/**
 * @class RestaurantSerializator is used to serialize and deserialize the Restaurant Object
 * used in the program.
 */
public class RestaurantSerializator implements Serializable{
    String fileName;

    public RestaurantSerializator(String fileName) {
        this.fileName = fileName;
    }

    /**
    /**
     * serializeRestaurant method - used to serialize the Restaurant Object
     * used in the program
     * @param restaurant
     */
    public void serializeRestaurant(Restaurant restaurant){

        String restaurantFile = fileName;
        try {


            FileOutputStream file = new FileOutputStream(restaurantFile);

            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(restaurant);
            System.out.print(fileName);
            out.close();
            file.close();

            System.out.println("Object has been serialized");

        }
        catch(IOException ex)
        {
            System.out.println("IOException is caught:" + ex.getLocalizedMessage() + ex.getStackTrace());
        }
    }

    /**
     * deserializeRetaurant method - used to deserialize the Restaurant Object
     * used in the program from the RestaurantFile.ser file
     * @return model - the Restaurant Object ment to be the model in the MVC design
     */
    public Restaurant deserializeRestaurant(){

        Restaurant model = null;
        FileInputStream file;
        ObjectInputStream in;
        // Deserialization
        try
        {
            // Reading the object from a file

            String restaurantFile = fileName;
            file = new FileInputStream(restaurantFile);
             in = new ObjectInputStream(file);

            // Method for deserialization of object
            model = (Restaurant)in.readObject();
            in.close();
            file.close();


        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }

        return model;
    }

}
