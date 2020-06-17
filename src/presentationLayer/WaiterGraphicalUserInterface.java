package presentationLayer;

import businessLayer.MenuItem;
import businessLayer.Order;
import businessLayer.Observer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class WaiterGraphicalUserInterface implements Observer, Serializable {
    JButton createOrderButton;
    JButton createBillButton;
    ArrayList<MenuItem> menu;
    ArrayList<Order> orders;
    JTextField tableText;
    JComboBox chooseList[] ;
    JTable tableOrders;
    JLabel label[];
    JPanel content;
    int oldMenuSize;
    DefaultTableModel myModel;
    JFrame frame;
    public WaiterGraphicalUserInterface(){
        menu = new ArrayList<>();
        orders = new ArrayList<>();
        content = new JPanel();
        init();
    }

        private void init() {
        frame=new JFrame("Waiter View");
        JTabbedPane tabbedPane = new JTabbedPane();
        final String CREATE = "Create Order";
        final String VIEW = "View Orders";
        createOrderButton = new JButton("Order");
        createBillButton = new JButton("Bill");
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

        JPanel table = new JPanel();
        table.setLayout(new BoxLayout(table, BoxLayout.LINE_AXIS));
        table.add(new JLabel("Table:"));
        tableText = new JTextField();
        table.add(tableText);
        content.add(table);

        populateContent();

        createOrderButton.setPreferredSize(new Dimension(70, 50));
        createBillButton.setPreferredSize(new Dimension(70, 50));
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        buttons.add(createOrderButton);

        buttons.add(createBillButton);

        content.add(buttons);

        JScrollPane scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        myModel = new DefaultTableModel();

        String[] columnNames = {"OrderID",
            "Table", "Total Price"};
        Object[][] data = new Object[orders.size()][4];
        myModel.setColumnIdentifiers(columnNames);

        int i = 0;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        for (Order order : orders) {
            data[i][0] = order.getOrderID();
            data[i][1] = order.getOrderTable();
            data[i][2] = order.getOrderTotalPrice();
            data[i][3] = dateFormat.format(order.getOrderDate());
            myModel.addRow(data[i]);
            i++;
        }

        tableOrders = new JTable(myModel);

        tabbedPane.addTab(CREATE, scrollPane);

        tabbedPane.addTab(VIEW, tableOrders);

        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.setSize(400,550);

        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void refreshTable(){
        int c = myModel.getRowCount();
        for (int i=c-1; i>=0; i--)
        {
            myModel.removeRow(i);
        }
        Object[][] data = new Object[orders.size()][4];
        int i = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        for (Order order : orders) {
            data[i][0] = order.getOrderID();
            data[i][1] = order.getOrderTable();
            data[i][2] = order.getOrderTotalPrice();
            data[i][3] = dateFormat.format(order.getOrderDate());
            myModel.addRow(data[i]);
            i++;
        }
        tableOrders.revalidate();
    }
    private void populateContent(){

        label = new JLabel[this.menu.size()];
        chooseList =  new JComboBox[menu.size()];
        String[] chooseStrings = { "Unselected", "Selected" };
        for(int j = 0; j < menu.size(); j++) {
            chooseList[j] = new JComboBox(chooseStrings);
        }

        int i = 0;
        for(MenuItem item : this.menu){
            label[i] = new JLabel(item.getItemName());
            content.add(label[i]);
            content.add(chooseList[i]);

            i++;
        }


    }
    public void addCreateOrderListener(ActionListener listenerForCreateButton){
        createOrderButton.addActionListener(listenerForCreateButton);
    }
    public void addCreateBillListener(ActionListener listenerForCreateButton){
        createBillButton.addActionListener(listenerForCreateButton);
    }

    public void addExitListener(WindowAdapter listenerForCreateButton){
        frame.addWindowListener(listenerForCreateButton);
    }


    public String getTable(){
        return tableText.getText();
    }
    public void removeContent(int oldMenuSize){


        for(int i = 0 ; i < oldMenuSize; i++){
            content.remove(label[i]);
            content.remove(chooseList[i]);

        }
    }

    @Override
    public void update(Object arg[]) {

        removeContent(oldMenuSize);
        orders = (ArrayList<Order>) arg[1];

        this.menu = (ArrayList<MenuItem>) arg[0];
        refreshTable();
        tableOrders.revalidate();
        populateContent();
        content.revalidate();
        content.repaint();

        oldMenuSize = this.menu.size();

    }


    public ArrayList<MenuItem> getOrderedItems(){
        ArrayList<MenuItem> orderedItems = new ArrayList<>();
        int i = 0;
        for(JComboBox choice:chooseList){
            if(choice.getSelectedItem().equals("Selected")){
                orderedItems.add(this.menu.get(i));

            }
            i+=1;
        }
        return orderedItems;
    }

    public void displayError(String error, String debug){
        JOptionPane.showMessageDialog(new JFrame(), error, debug, JOptionPane.ERROR_MESSAGE);
    }




}
