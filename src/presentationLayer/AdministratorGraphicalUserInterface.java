package presentationLayer;

import businessLayer.*;
import businessLayer.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.Serializable;
import java.util.ArrayList;


public class AdministratorGraphicalUserInterface implements  Observer, Serializable {

    ArrayList<MenuItem> menu;
    JPanel content1;
    JPanel content2;
    JPanel content3;
    JComboBox chooseList1[];
    JComboBox chooseList2[];
    JComboBox chooseList3[];
    JLabel label1[];
    JLabel label2[];
    JLabel label3[];
    JButton deleteButton;
    JComboBox chooseListEdit;
    JButton editButton;
    int oldMenuSize = 0;
    DefaultTableModel myModel;
    JTable tableMenu;
    JTextField nameTextCreate;
    JTextField priceTextCreate;
    JButton createButton;
    JTextField nameTextEdit;
    JTextField priceTextEdit;
    JFrame frame;
    public AdministratorGraphicalUserInterface(){
        menu = new ArrayList<>();

        init();
    }
    private void populateContent1(){
        label1 = new JLabel[menu.size()];
        String[] chooseStrings = { "Not Added", "Added" };
        chooseList1 =  new JComboBox[menu.size()];

        int i = 0;
        for(MenuItem item : menu){
            label1[i] = new JLabel(item.getItemName());
            content1.add(label1[i]);
            chooseList1[i] = new JComboBox(chooseStrings);
            content1.add(chooseList1[i]);
            i++;
        }
    }

    private void populateContent2(){

        chooseList2 =  new JComboBox[menu.size()];

        String[] chooseStrings = { "Not Deleted", "Deleted" };
        chooseList2 =  new JComboBox[menu.size()];
        label2 = new JLabel[menu.size()];
        int i = 0;
        for(MenuItem item : menu){
            label2[i] = new JLabel(item.getItemName());
            content2.add(label2[i]);
            chooseList2[i] = new JComboBox(chooseStrings);
            content2.add(chooseList2[i]);
            i++;
        }
    }


    private void populateContent3(){
        String[] chooseStringsEdit = new String[menu.size()];
        int i = 0;
        for(MenuItem item: menu){
            chooseStringsEdit[i] = item.getItemName();
            i++;
        }
        chooseListEdit =  new JComboBox(chooseStringsEdit);
        content3.add(chooseListEdit);
        chooseList3 =  new JComboBox[menu.size()];

        String[] chooseStrings = { "Don't Use", "Use" };
        chooseList3 =  new JComboBox[menu.size()];
        label3 = new JLabel[menu.size()];
        i = 0;
        for(MenuItem item : menu){
            label3[i] = new JLabel(item.getItemName());
            content3.add(label3[i]);
            chooseList3[i] = new JComboBox(chooseStrings);
            content3.add(chooseList3[i]);
            i++;
        }
    }


    public void removeContent1(int oldMenuSize){
        for(int i = 0; i < oldMenuSize; i++){
            content1.remove(label1[i]);
            content1.remove(chooseList1[i]);
        }
    }

    public void addComponentToPane1(Container pane) {
        CardLayout card=new CardLayout();

        content1 = new JPanel();
        content1.setLayout(new BoxLayout(content1, BoxLayout.PAGE_AXIS));
        JPanel name = new JPanel();
        name.setLayout(new BoxLayout(name, BoxLayout.LINE_AXIS));
        content1.setLayout(new BoxLayout(content1, BoxLayout.PAGE_AXIS));
        JPanel price = new JPanel();
        price.setLayout(new BoxLayout(price, BoxLayout.LINE_AXIS));

        name.add(new JLabel("Name:"));
        nameTextCreate = new JTextField();
        priceTextCreate = new JTextField();
        name.add(nameTextCreate);
        price.add(new JLabel("Price"));
        price.add(priceTextCreate);
        content1.setLayout(new BoxLayout(content1, BoxLayout.PAGE_AXIS));
        content1.add(Box.createRigidArea(new Dimension(0,30)));
        content1.add(name);
        content1.add(Box.createRigidArea(new Dimension(0,30)));
        content1.add(price);
        populateContent1();
        content1.add(Box.createRigidArea(new Dimension(0,30)));
        JScrollPane scrollPane = new JScrollPane(content1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        createButton = new JButton("Create");
        content1.add(createButton);
        content1.add(Box.createRigidArea(new Dimension(0,30)));
        pane.setLayout(card);
        pane.add(scrollPane);

    }
    public void removeContent2(int oldMenuSize){
        for(int i = 0; i < oldMenuSize; i++){
            content2.remove(label2[i]);
            content2.remove(chooseList2[i]);
        }
    }
    public void addComponentToPane2(Container pane) {
        CardLayout card=new CardLayout();

        content2 = new JPanel();
        content2.setLayout(new BoxLayout(content2, BoxLayout.PAGE_AXIS));
        content2.add(Box.createRigidArea(new Dimension(0,10)));
        deleteButton = new JButton("Delete");
        content2.add(deleteButton);
        content2.add(Box.createRigidArea(new Dimension(0,10)));

        populateContent2();

        JScrollPane scrollPane = new JScrollPane(content2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        pane.setLayout(card);
        pane.add(scrollPane);

    }

    public void addComponentToPane3(Container pane) {

        CardLayout card=new CardLayout();
        content3 = new JPanel();
        JPanel name = new JPanel();
        name.setLayout(new BoxLayout(name, BoxLayout.LINE_AXIS));
        content3.setLayout(new BoxLayout(content3, BoxLayout.PAGE_AXIS));
        JPanel price = new JPanel();
        price.setLayout(new BoxLayout(price, BoxLayout.LINE_AXIS));

        content3.add(Box.createRigidArea(new Dimension(0,10)));
        editButton = new JButton("Edit");
        content3.add(editButton);
        content3.add(Box.createRigidArea(new Dimension(0,10)));
        name.add(new JLabel("Name:"));
        nameTextEdit = new JTextField();
        priceTextEdit = new JTextField();
        name.add(nameTextEdit);
        price.add(new JLabel("Price"));
        price.add(priceTextEdit);
        content3.add(name);
        content3.add(price);
        populateContent3();


        JScrollPane scrollPane = new JScrollPane(content3, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setLayout(card);
        pane.add(scrollPane);


    }

    public void removeContent3(int oldMenuSize){
        for(int i = 0; i < oldMenuSize; i++){
            content3.remove(label3[i]);
            content3.remove(chooseList3[i]);
        }

        content3.remove(chooseListEdit);
    }

    private void init() {
        frame=new JFrame("Admin View");
        JTabbedPane tabbedPane = new JTabbedPane();
        final String CREATE = "Create Item";
        final String DELETE = "Delete Item";
        final String EDIT = "Edit Item";
        final String VIEW = "View Menu";

        //Create the "cards".
        JPanel card1 = new JPanel() ;
        addComponentToPane1(card1);
        JPanel card2 = new JPanel();
        addComponentToPane2(card2);
        JPanel card3 = new JPanel();
        addComponentToPane3(card3);
        tabbedPane.addTab(CREATE, card1);
        tabbedPane.addTab(DELETE, card2);
        tabbedPane.addTab(EDIT, card3);
        myModel = new DefaultTableModel();

        String[] columnNames = {"OrderID",
                "Table", "Total Price"};
        Object[][] data = new Object[menu.size()][2];
        myModel.setColumnIdentifiers(columnNames);

        int i = 0;


        for (MenuItem item : menu) {
            data[i][0] = item.getItemName();
            data[i][1] = item.computePrice();

            myModel.addRow(data[i]);
            i++;
        }

        tableMenu = new JTable(myModel);

        tabbedPane.addTab(VIEW, tableMenu);

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
        Object[][] data = new Object[menu.size()][2];
        int i = 0;

        for (MenuItem item : menu) {
            data[i][0] = item.getItemName();
            data[i][1] = item.computePrice();

            myModel.addRow(data[i]);
            i++;
        }

        tableMenu.revalidate();
    }

    public String getNameCreate(){
        String name = nameTextCreate.getText();
        return name;
    }

    public String getPriceCreate(){
        String price = priceTextCreate.getText();
        return price;
    }



    public String getNameEdit(){
        String name = nameTextEdit.getText();
        return name;
    }

    public String getPriceEdit(){
        String price = priceTextEdit.getText();
        return price;
    }

    public ArrayList<MenuItem> getComponents(){
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        for(int i = 0 ; i< menu.size(); i++){
            if(chooseList1[i].getSelectedItem().equals("Added")){
                menuItems.add(menu.get(i));
            }
        }
        return menuItems;
    }
    public ArrayList<MenuItem> getItemDelete(){
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        for(int i = 0 ; i< menu.size(); i++){
            if(chooseList2[i].getSelectedItem().equals("Deleted")){
                menuItems.add(menu.get(i));
            }
        }
        return menuItems;
    }
    public ArrayList<MenuItem> getItemEdit(){
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menu.get(chooseListEdit.getSelectedIndex()));
        return menuItems;
    }


    @Override
    public void update(Object arg[]) {
        removeContent1(oldMenuSize);
        removeContent2(oldMenuSize);
        removeContent3(oldMenuSize);
        menu = (ArrayList<MenuItem>) arg[0];
        refreshTable();
        tableMenu.revalidate();
        populateContent1();
        content1.revalidate();
        content1.repaint();
        populateContent2();
        content2.revalidate();
        content2.repaint();
        populateContent3();
        content3.revalidate();
        content3.repaint();
        oldMenuSize = menu.size();
    }
    public void addCreateItemListener(ActionListener listenerForCreateButton){
        createButton.addActionListener(listenerForCreateButton);
    }

    public void addDeleteItemListener(ActionListener listenerForDeleteButton){
        deleteButton.addActionListener(listenerForDeleteButton);
    }
    public void addEditItemListener(ActionListener listenerForDeleteButton){
        editButton.addActionListener(listenerForDeleteButton);
    }
    public void addExitListener(WindowAdapter listenerForCreateButton){
        frame.addWindowListener(listenerForCreateButton);
    }


}
