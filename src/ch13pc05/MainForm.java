package ch13pc05;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.*;

/**
 *
 * @author Frank
 */
public class MainForm extends JFrame {
    //fields
    private final int width = 200;
    private final int height = 350;
    
    //components of the menu
    private JMenuBar menuBar;
    private JMenu fileMenu, cartMenu;
    private JMenuItem getItem, exitItem, addItem, removeItem, clearItem, checkOutItem;
    
    //components for the JFrame
    private JPanel productPanel, cartPanel, calculationPanel;
    private JLabel lblSubtotal, lblSalesTax, lblTotal, lblBlank;
    private JTextField txtSubtotal, txtSalesTax, txtTotal;
    private DefaultListModel productListModel, cartListModel;
    private JList productList, cartList;
    private JButton btnAdd, btnRemove, btnClear, btnCheckOut;
    private JScrollPane scrollProduct, scrollCart;
    
    //objects needed for the Form
    private ShoppingCart cart;
    private ArrayList<Product> products;
    
    //constructor
    public MainForm() {
        //set the Title
        this.setTitle("Book Seller");
        
        //set default close operations, minimum size, and resizability
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(width, height));
        this.setResizable(true);
        this.setLayout(new BorderLayout());
        
        //instantiate objects
        cart = new ShoppingCart();
        
        
        //build components
        buildMenuBar();
        buildProductPanel();
        buildCartPanel();
        buildCalculationPanel();
        
        this.add(productPanel, BorderLayout.NORTH);
        this.add(cartPanel);
        this.add(calculationPanel, BorderLayout.SOUTH);
        
        //pack and display the window
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    //methods
    private void buildMenuBar() {
        //create the menubar
        menuBar = new JMenuBar();
        
        //create the menus
        buildFileMenu();
        buildCartMenu();
                
        //add menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(cartMenu);
                
        //set the window's menu bar
        setJMenuBar(menuBar);
        
    }
    
    private void buildFileMenu() {
        //create menu items
        getItem = new JMenuItem("Get New List");
        getItem.setMnemonic(KeyEvent.VK_G);
        getItem.addActionListener(new getListener());
        
        exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.addActionListener(new exitListener());
        
        //create a JMenu object for the File Menu
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        //add items to the menu
        fileMenu.add(getItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
    }
    
    private void buildCartMenu() {
        //create menu items
        addItem = new JMenuItem("Add Item");
        addItem.setMnemonic(KeyEvent.VK_A);
        addItem.addActionListener(new addListener());
        
        removeItem = new JMenuItem("Remove Item");
        removeItem.setMnemonic(KeyEvent.VK_R);
        removeItem.addActionListener(new removeListener());
        
        clearItem = new JMenuItem("Clear Items");
        clearItem.setMnemonic(KeyEvent.VK_C);
        clearItem.addActionListener(new clearListener());
        
        checkOutItem = new JMenuItem("Check Out");
        checkOutItem.setMnemonic(KeyEvent.VK_O);
        checkOutItem.addActionListener(new checkOutListener());
        
        //create a JMenu object for the File Menu
        cartMenu = new JMenu("Shopping Cart");
        cartMenu.setMnemonic(KeyEvent.VK_S);
        
        //add items to the menu
        //addItem, removeItem, clearItem, checkOutItem
        cartMenu.add(addItem);
        cartMenu.add(removeItem);
        cartMenu.add(clearItem);
        cartMenu.addSeparator();
        cartMenu.add(checkOutItem);
    }
    
    private void buildProductPanel() {
        productPanel = new JPanel(new BorderLayout());
        productPanel.setBorder(BorderFactory.createTitledBorder("Select from the following:"));
        
        scrollProduct = new JScrollPane();
        scrollProduct.setMinimumSize(new Dimension(100, 500));
        
        //get items from text file
        File file = new File("BookPrices.txt");
        
        try {
            //instantiate the FileImporter class
            FileImporter fileImport = new FileImporter(file);

            //instantiate a list of Product from the File Importer
            products = new ArrayList<>(fileImport.getProducts());

            productListModel = new DefaultListModel();
            //read the products into productList
            for (Product prod : products) {
                productListModel.addElement(prod.getName());
            }
            productList = new JList(productListModel);
            productList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            productList.setVisibleRowCount(5);
        }
        catch (FileNotFoundException|NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        }
                    
        //add the list to the scroll pane
        scrollProduct.setViewportView(productList);
        
        btnAdd = new JButton("Add to Shopping Cart");
        btnAdd.addActionListener(new addListener());
        
        //add to the panel
        productPanel.add(scrollProduct, BorderLayout.NORTH);
        productPanel.add(btnAdd, BorderLayout.SOUTH);
    }
    
    private void buildCartPanel() {
        cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Your Shopping Cart"));
        
        scrollCart = new JScrollPane();
        scrollCart.setMinimumSize(new Dimension(100, 500));
        
        cartListModel = new DefaultListModel();
        cartListModel.add(0, "...is empty...");
        cartList = new JList(cartListModel);
        cartList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        cartList.setVisibleRowCount(5);
        scrollCart.setViewportView(cartList);
        
        btnRemove = new JButton("Remove from Shopping Cart");
        btnRemove.addActionListener(new removeListener());
        
        btnClear = new JButton("Clear the Shopping Cart");
        btnClear.addActionListener(new clearListener());
        
        cartPanel.add(scrollCart, BorderLayout.NORTH);
        cartPanel.add(btnRemove);
        cartPanel.add(btnClear, BorderLayout.SOUTH);
    }
    
    private void buildCalculationPanel() {
        calculationPanel = new JPanel(new GridLayout(4, 2));
        
        //add labels and textfields
        lblSubtotal = new JLabel("Subtotal: ");
        lblSalesTax = new JLabel("Sales Tax (6%): ");
        lblTotal = new JLabel("Total Price: ");
        lblBlank = new JLabel();
        
        txtSubtotal = new JTextField(15);
        txtSubtotal.setEditable(false);
        txtSubtotal.setText("0.00");
        txtSalesTax = new JTextField(15);
        txtSalesTax.setEditable(false);
        txtSalesTax.setText("0.00");
        txtTotal = new JTextField(15);
        txtTotal.setEditable(false);
        txtTotal.setText("0.00");
        
        btnCheckOut = new JButton("Check Out");
        btnCheckOut.addActionListener(new checkOutListener());
        
        calculationPanel.add(lblSubtotal);
        calculationPanel.add(txtSubtotal);
        calculationPanel.add(lblBlank);
        calculationPanel.add(btnCheckOut);
        calculationPanel.add(lblSalesTax);
        calculationPanel.add(txtSalesTax);
        calculationPanel.add(lblTotal);
        calculationPanel.add(txtTotal);
    }
    
    private void cartRefresh(){
        //add items to the cart panel
        cartListModel.clear();
        //cartPanel.removeAll();
        
        if (cart.getCart().isEmpty()) {
            cartListModel.add(0, "...is empty...");
        }
        else {
            for (Product prod : cart.getCart()) {
                cartListModel.addElement(prod.getName());
            }
        }
        
        cartList.setModel(cartListModel);
        cartList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        cartList.setVisibleRowCount(5);
        
        //revalidate and repaint the cartPanel
        cartPanel.revalidate();
        cartPanel.repaint();

        txtSubtotal.setText("$" + Double.toString(cart.getSubtotal()));
    }
    
    private void updateTotals(){
        txtSubtotal.setText("$" + Double.toString(cart.getSubtotal()));
        txtSalesTax.setText("$" + Double.toString(cart.getSalesTax()));
        txtTotal.setText("$" + Double.toString(cart.getTotalPrice()));
    }
    
    //inner classes
    /**
     * private inner class that handles the event generated when the user
     * selects Get New List from the File Menu
     */
    private class getListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //clear everything, then import the new file
            
            
            //instantiate file chooser
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    
                    //instantiate the FileImporter class
                    FileImporter fileImport = new FileImporter(file);
                    
                    //clear the list
                    productListModel.clear();
                    
                    //instantiate a list of Product from the File Importer
                    products = new ArrayList<>(fileImport.getProducts());
                    
                    //read the products into productList
                    for (Product prod : products) {
                        productListModel.addElement(prod.getName());
                    }
                    productList = new JList(productListModel);
                    
                    productPanel.add(productList, BorderLayout.NORTH);
                    productPanel.revalidate();
                    productPanel.repaint();
                }
                catch (FileNotFoundException|NoSuchElementException ex) {
                    System.out.println(ex.getMessage());
                }
                
            }
        }
    }
    
    /**
     * private inner class that handles the event generated when the user
     * selects Exit from the File Menu
     */
    private class exitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    /**
     * private inner class that handles the event generated when the user
     * adds an item to the shopping cart
     */
    private class addListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //get selected indeces of all the selected items
            int[] selectedIndex = productList.getSelectedIndices();
            Object[] titles = new Object[selectedIndex.length];
            
            //get all of the selected items based on the indeces
            for (int i = 0; i < selectedIndex.length; i++) {
                titles[i] = productList.getModel().getElementAt(selectedIndex[i]);
            }
            
            //get items and add them to the cart
            for (Product prod : products) {
                //get name and price from products
                for (int i = 0; i < selectedIndex.length; i++) {
                    if (titles[i] == prod.getName()){
                        //add to the cart
                        String itemName = prod.getName();
                        double itemPrice = prod.getPrice();
                        Product item = new Product(itemName, itemPrice);
                        cart.addItem(item);
                    }
                }
            }
            cartRefresh();
        }
    }
    
    /**
     * private inner class that handles the event generated when the user
     * removes an item from the shopping cart
     */
    private class removeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //get selected indeces for all the selected items
            int[] selectedIndex = cartList.getSelectedIndices();
            
            for (int i = 0; i < selectedIndex.length; i++) {
                cart.removeItem(selectedIndex[i]);
            }
            
            cartRefresh();
        }
    }
    
    /**
     * private inner class that handles the event generated when the user
     * clears the shopping cart
     */
    private class clearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //clears the shopping cart
            cart.clearCart();
            
            //clear the cartList
            cartListModel.clear();
            cartListModel.add(0, "...is empty...");
            cartList = new JList(cartListModel);
            cartPanel.add(cartList);
            cartPanel.revalidate();
            cartPanel.repaint();
            
            updateTotals();
        }
    }
    
    /**
     * private inner class that handles the event generated when the user
     * selects to check out from the shopping cart
     */
    private class checkOutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateTotals();
        }
    }
}
