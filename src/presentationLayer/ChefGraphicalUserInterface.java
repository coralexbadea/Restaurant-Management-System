package presentationLayer;


import businessLayer.MenuItem;
import businessLayer.Observer;
import businessLayer.Order;
import businessLayer.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ChefGraphicalUserInterface implements Observer, Serializable {

    ArrayList<Order> orders;
    Map<Order, ArrayList<MenuItem>> ordersTaken;
    JPanel content;
    JLabel label[];
    ArrayList<MenuItem> items;
    JCheckBox checkBoxes[];
    JPanel subContent[];
    int oldOrderSize = 0;
    JFrame frame;
    JButton refreshButton;
    public ChefGraphicalUserInterface(){
        orders = new ArrayList<>();
        ordersTaken = new Map<>();
        init();
    }
    private void populateContent(){

        label = new JLabel[orders.size()];
        checkBoxes = new JCheckBox[orders.size()];
        items =  new ArrayList();
        subContent = new JPanel[orders.size()];
        for(int j = 0; j < orders.size(); j++) {
            checkBoxes[j] = new JCheckBox("Done");
            label[j] = new JLabel();
            label[j].setText(orders.get(j).getOrderID().toString()+": ");
            items = ordersTaken.get(orders.get(j));

            for(MenuItem item:items){
                label[j].setText(label[j].getText()+item.getItemName()+", ");
            }
            subContent[j] = new JPanel();
            subContent[j].setLayout(new BoxLayout(subContent[j], BoxLayout.LINE_AXIS));
            subContent[j].add(label[j]);
            subContent[j].add(checkBoxes[j]);

            content.add(subContent[j]);
        }


    }

    public void removeContent(int oldOrderSize){
        for(int i = 0; i < oldOrderSize; i++){
            content.remove(subContent[i]);
        }
    }

    public void init(){
        content = new JPanel();
        frame=new JFrame("Chef View");
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        refreshButton = new JButton("Refresh");

        content.add(Box.createRigidArea(new Dimension(0,10)));
        content.add(refreshButton);
        content.add(Box.createRigidArea(new Dimension(0,10)));
        populateContent();

        JScrollPane scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        frame.add(scrollPane);
        frame.setSize(400,550);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public ArrayList<Order> getChecked(){
        ArrayList<Order> doneOrders = new ArrayList<>();
        for(int i = 0; i < oldOrderSize; i++){
            if(checkBoxes[i].isSelected())
                doneOrders.add(orders.get(i));
        }
        return doneOrders;
    }
    public void addRefreshListener(ActionListener listenerForRefreshButton){
        refreshButton.addActionListener(listenerForRefreshButton);
    }
    public void addExitListener(WindowAdapter listenerExitButton){
        frame.addWindowListener(listenerExitButton);
    }




    @Override
    public void update(Object arg[]) {


        removeContent(oldOrderSize);
        ordersTaken = (Map<Order, ArrayList<MenuItem>>) arg[2];
        orders = (ArrayList<Order>) arg[1];
        populateContent();
        content.revalidate();
        content.repaint();
        oldOrderSize = orders.size();
    }

}
