package com.aeydeee.customers;

import com.aeydeee.admins.ManageProductsForm;
import com.aeydeee.customers.Menu;
import com.aeydeee.customers.orderstatus.OrderStatus;
import com.aeydeee.loginRegister.User;
import com.aeydeee.loginRegister.Login;
import com.aeydeee.database.MyConnection;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class MenuPage extends javax.swing.JFrame {

    private Receipt receipt;
    int position = 0;

    public String username;
    public ArrayList<User> UsersArray = new ArrayList<>();

    public MenuPage() {
        initComponents();
        tab1.setBackground(Color.white);
        performTab1Click();
        this.username = Login.setUsername;
        loadUserData();
        showProductsInTable();
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    private void performTab1Click() {
        jp1.setVisible(true);
        jp2.setVisible(false);
        jp3.setVisible(false);
        jp4.setVisible(false);

        tab1.setBackground(Color.white);
        tab2.setBackground(new Color(0, 204, 102));
        tab3.setBackground(new Color(0, 204, 102));
        tab4.setBackground(new Color(0, 204, 102));
    }

    public ArrayList<User> getUsersList() {
        ArrayList<User> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM `users`";

        Statement st;
        ResultSet rs;

        try {
            st = MyConnection.getConnection().createStatement();
            rs = st.executeQuery(selectQuery);
            User user;

            while (rs.next()) {
                user = new User(rs.getInt("user_id "), rs.getString("user_fname"),
                        rs.getString("user_lname"), rs.getString("user_username"),
                        rs.getString("user_email"), rs.getString("user_password"),
                        rs.getString("user_birthdate"), rs.getInt("user_house_no"),
                        rs.getString("user_barangay"), rs.getString("user_city"),
                        rs.getInt("user_zip"), rs.getString("image_path"));
                list.add(user);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MenuPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        UsersArray = list;
        return list;

    }

    public void showUserData(int index) {
        lblUsername.setText(UsersArray.get(index).getUsername());
        lblFirstName.setText(UsersArray.get(index).getfName());
        lblLastName.setText(UsersArray.get(index).getlName());
        lblEmail.setText(UsersArray.get(index).getEmail());
        lblHouseNo.setText(Integer.toString(UsersArray.get(index).getHouseNo()));
        lblBrgy.setText(UsersArray.get(index).getBrgy());
        lblCity.setText(UsersArray.get(index).getCity());
        lblZip.setText(Integer.toString(UsersArray.get(index).getZip()));
        lblBirthday.setText(UsersArray.get(index).getbDay());

        displayImage(UsersArray.get(index).getImagePath(), lblProfile, 'a');
    }

    public void displayImage(String imgPath, JLabel label, char rsc) {
        ImageIcon imgIco;
        // check if the image is from the project files
        // if the image is from the resource rsc = 'r'
        // if not you can put any character abcd 123467 .......
        if (rsc == 'r') {
            imgIco = new ImageIcon(getClass().getResource(imgPath));
        } else {
            imgIco = new ImageIcon(imgPath);
        }

        Image img = imgIco.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));

    }

    public User getUserDataByUsername(String username) {
        String selectQuery = "SELECT * FROM users WHERE user_username = ?";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = MyConnection.getConnection().prepareStatement(selectQuery);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("user_fname"),
                        resultSet.getString("user_lname"),
                        resultSet.getString("user_username"),
                        resultSet.getString("user_email"),
                        resultSet.getString("user_password"),
                        resultSet.getString("user_birthdate"),
                        resultSet.getInt("user_house_no"),
                        resultSet.getString("user_barangay"),
                        resultSet.getString("user_city"),
                        resultSet.getInt("user_zip"),
                        resultSet.getString("image_path")
                );
                return user;
            }

        } catch (SQLException ex) {
            Logger.getLogger(MenuPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null; // Return null if user with given username is not found
    }

    public void loadUserData() {
        User loggedInUser = getUserDataByUsername(username);
        if (loggedInUser != null) {
            UsersArray.add(loggedInUser);
            showUserData(0);
        } else {
            // Handle case where user with given username is not found
        }
    }

    ArrayList<Menu> menuArray = new ArrayList<>();

    public ArrayList<Menu> getMenuList() {
        ArrayList<Menu> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM `menus`";

        Statement st;
        ResultSet rs;

        try {
            st = MyConnection.getConnection().createStatement();
            rs = st.executeQuery(selectQuery);
            Menu menu;

            while (rs.next()) {
                menu = new Menu(rs.getInt("id"), rs.getString("name"),
                        rs.getString("category"), rs.getInt("quantity"),
                        rs.getDouble("price"), rs.getString("image"));
                list.add(menu);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ManageProductsForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        menuArray = list;
        return list;

    }

    public void showProductsInTable() {
        ArrayList<Menu> productsList = getMenuList();
        DefaultTableModel model = (DefaultTableModel) tblMenus.getModel();

        // clear jtable
        model.setRowCount(0);

        Object[] row = new Object[6];// 6 the number of columns

        for (int i = 0; i < productsList.size(); i++) {
            row[0] = productsList.get(i).getName();
            row[1] = productsList.get(i).getCategory();
            row[2] = productsList.get(i).getPrice();

            model.addRow(row);
        }

    }

    public Map<String, Double> getDishPrices() {
        Map<String, Double> dishPrices = new HashMap<>();
        String selectQuery = "SELECT name, price FROM menus";

        Statement st;
        ResultSet rs;

        try {
            st = MyConnection.getConnection().createStatement();
            rs = st.executeQuery(selectQuery);

            while (rs.next()) {
                String dishName = rs.getString("name");
                double price = rs.getDouble("price");

                dishPrices.put(dishName, price);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MenuPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dishPrices;
    }

    public void showProductData(int index) {
        displayImage(menuArray.get(index).getImagePath(), lblImage, 'a');
    }

    private void allowOnlyNumbers(KeyEvent evt) {
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }

    public void receipt_print() {

        try {
            receipt.txtReceipt.setText("     CHICKEN ROASTER FOOD DELIVERY SYSTEM \n");
            receipt.txtReceipt.setText(receipt.txtReceipt.getText() + "\tZone 1/ Topas Proper, \n");
            receipt.txtReceipt.setText(receipt.txtReceipt.getText() + "\tNabua, Camarines Sur, \n");
            receipt.txtReceipt.setText(receipt.txtReceipt.getText() + "\t+639814628851 \n");
            receipt.txtReceipt.setText(receipt.txtReceipt.getText() + "-------------------------------------------------------------------------------\n");
            receipt.txtReceipt.setText(receipt.txtReceipt.getText() + " Item \t \tQty \t Price \n");
            receipt.txtReceipt.setText(receipt.txtReceipt.getText() + "-------------------------------------------------------------------------------\n");

            DefaultTableModel df = (DefaultTableModel) receipt.tblPurchaseReceipt.getModel();
            for (int i = 0; i < receipt.tblPurchaseReceipt.getRowCount(); i++) {
                String name = df.getValueAt(i, 0).toString();
                String qt = df.getValueAt(i, 1).toString();
                String prc = df.getValueAt(i, 2).toString();

                // Use String.format for fixed-width columns
                String formattedLine = String.format("%-20s\t%-10s\t%s \n", name, qt, prc);
                receipt.txtReceipt.setText(receipt.txtReceipt.getText() + formattedLine);
            }

            // Convert the text from JTextFields to numeric values
            double totalPrice = extractNumericValue(txtTotalPrice.getText());
            double payment = extractNumericValue(txtPayment.getText());

            receipt.txtReceipt.setText(receipt.txtReceipt.getText() + "-------------------------------------------------------------------------------\n");
            receipt.txtReceipt.setText(receipt.txtReceipt.getText() + "SubTotal :\t" + txtTotalPrice.getText() + "\n");
            receipt.txtReceipt.setText(receipt.txtReceipt.getText() + "Cash :\t" + txtPayment.getText() + "\n");
            receipt.txtReceipt.setText(receipt.txtReceipt.getText() + "Balance :\t" + (payment - totalPrice) + "\n");
            receipt.txtReceipt.setText(receipt.txtReceipt.getText() + "=============================================\n");
            receipt.txtReceipt.setText(receipt.txtReceipt.getText() + "                             Thanks For Ordering...!" + "\n");
            receipt.txtReceipt.setText(receipt.txtReceipt.getText() + "-------------------------------------------------------------------------------\n");
            receipt.txtReceipt.setText(receipt.txtReceipt.getText() + "                             Software by Gypsophila" + "\n");

            receipt.txtReceipt.print();

        } catch (PrinterException ex) {

            Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private double extractNumericValue(String input) {
        // Remove non-numeric characters (except for '.' to allow decimals)
        String cleanedInput = input.replaceAll("[^\\d.]", "");

        // Parse the cleaned string into a double
        return Double.parseDouble(cleanedInput);

    }

    public void addTable(String Name, Double Price) {

        String Qty = JOptionPane.showInputDialog("Enter Qty");
        Double tqty = Double.valueOf(Qty);
        Double Tot_Price = Price * tqty;

        DecimalFormat df = new DecimalFormat("00.00");
        String d11 = df.format(Tot_Price);

        DefaultTableModel dt = (DefaultTableModel) receipt.tblPurchaseReceipt.getModel();

        Vector v = new Vector();
        v.add(Name);
        v.add(Qty);
        v.add(d11);
        dt.addRow(v);

        cart_cal();
    }

    public void cart_cal() {

        int numofrow = receipt.tblPurchaseReceipt.getRowCount();
        double total = 0;
        for (int i = 0; i < numofrow; i++) {
            double value = Double.parseDouble(receipt.tblPurchaseReceipt.getValueAt(i, 2).toString());
            total += value;

        }

        DecimalFormat df = new DecimalFormat("00.00");
        String d1 = df.format(total);
        txtTotalPrice.setText(d1);

    }

    private void addItemWithQuantity(JLabel label, JTextField quantityField, List<ItemWithQuantity> itemsWithQuantity) {
        String itemName = label.getText();
        String quantity = quantityField.getText().trim();

        if (!quantity.isEmpty()) {
            double itemPrice = getDishPrices().get(itemName);
            double itemQuantity = Double.parseDouble(quantity);

            itemsWithQuantity.add(new ItemWithQuantity(itemName, itemPrice, itemQuantity));
        }
    }

    public class ItemWithQuantity {

        private final String itemName;
        private final double itemPrice;
        private final double itemQuantity;

        public ItemWithQuantity(String itemName, double itemPrice, double itemQuantity) {
            this.itemName = itemName;
            this.itemPrice = itemPrice;
            this.itemQuantity = itemQuantity;
        }

        public String getItemName() {
            return itemName;
        }

        public double getItemPrice() {
            return itemPrice;
        }

        public double getItemQuantity() {
            return itemQuantity;
        }
    }

    public void setItemsWithQuantity(List<ItemWithQuantity> itemsWithQuantity) {
        DefaultTableModel dt = (DefaultTableModel) receipt.tblPurchaseReceipt.getModel();
        for (ItemWithQuantity item : itemsWithQuantity) {
            Vector v = new Vector();
            v.add(item.getItemName());
            v.add(item.getItemQuantity());
            v.add(item.getItemPrice() * item.getItemQuantity());
            dt.addRow(v);
        }

        cart_cal();
    }

    private String cleanNumericString(String input) {
        // Remove non-numeric characters (except for '.' to allow decimals)
        return input.replaceAll("[^\\d.]", "");
    }

   private void saveOrderToDatabase() {
    try {
        // Get the user_id of the currently logged-in user
        int userId = UsersArray.get(0).getId();

        // Insert a new order into the database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/food_ordering_system", "root", "")) {
            // Insert a new order into the database
            String insertQuery = "INSERT INTO orders (user_id, item_name, quantity, total_price, date_ordered, order_status) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                int orderStatus = 0;
                DefaultTableModel model = (DefaultTableModel) receipt.tblPurchaseReceipt.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    String itemName = model.getValueAt(i, 0).toString();
                    String quantity = model.getValueAt(i, 1).toString();
                    String totalPrice = model.getValueAt(i, 2).toString();
                    

                    preparedStatement.setInt(1, userId);
                    preparedStatement.setString(2, itemName);
                    preparedStatement.setString(3, quantity);
                    preparedStatement.setString(4, totalPrice);
                    preparedStatement.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
                    preparedStatement.setInt(6, orderStatus);

                    preparedStatement.executeUpdate();
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Handle the exception as needed (e.g., show an error message)
    }
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tab1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tab3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tab2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tab4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();
        lblMinimize = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jp1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblHouseNo = new javax.swing.JLabel();
        lblBirthday = new javax.swing.JLabel();
        lblBrgy = new javax.swing.JLabel();
        lblCity = new javax.swing.JLabel();
        lblZip = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblProfile = new javax.swing.JLabel();
        btnOrderStatus = new javax.swing.JButton();
        jp2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        lblBeerCanChicken = new javax.swing.JLabel();
        lblSpatchcockChicken = new javax.swing.JLabel();
        lblButterfliedChicken = new javax.swing.JLabel();
        lblLumpiaChicken = new javax.swing.JLabel();
        lblChickenKabobs = new javax.swing.JLabel();
        lblChickenAdobo = new javax.swing.JLabel();
        qtyBeerCan = new javax.swing.JTextField();
        qtySpatchcock = new javax.swing.JTextField();
        qtyKabobs = new javax.swing.JTextField();
        qtyLumpia = new javax.swing.JTextField();
        qtyAdobo = new javax.swing.JTextField();
        qtyButterflied = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        qtySting = new javax.swing.JTextField();
        lblMalunggayMilkTea = new javax.swing.JLabel();
        qtyGin = new javax.swing.JTextField();
        lblMineral = new javax.swing.JLabel();
        qtyMineral = new javax.swing.JTextField();
        lblRedhorse = new javax.swing.JLabel();
        qtyCobra = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        qtyRedhorse = new javax.swing.JTextField();
        qtyMalunggay = new javax.swing.JTextField();
        lblCobra = new javax.swing.JLabel();
        lblSting = new javax.swing.JLabel();
        lblGinRound = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txtCostMeal = new javax.swing.JTextField();
        txtCostDrinks = new javax.swing.JTextField();
        txtTotalCostItems = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        txtTax = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtTotalPrice = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        txtPayment = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        btnTotal = new javax.swing.JButton();
        btnConfirm = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMenus = new javax.swing.JTable();
        lblImage = new javax.swing.JLabel();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        jp3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        lblImageAdrian = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        lblImageAdrian3 = new javax.swing.JLabel();
        lblImageAdrian4 = new javax.swing.JLabel();
        jp4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel54 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(25, 146, 69));

        tab1.setBackground(new java.awt.Color(0, 204, 102));
        tab1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tab1.setName(""); // NOI18N
        tab1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab1MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SaloonExt Th", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aeydeee/icons/profile.png"))); // NOI18N
        jLabel1.setText("PROFILE");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout tab1Layout = new javax.swing.GroupLayout(tab1);
        tab1.setLayout(tab1Layout);
        tab1Layout.setHorizontalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab1Layout.createSequentialGroup()
                .addGap(0, 26, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        tab1Layout.setVerticalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(25, 25, 25))
        );

        tab3.setBackground(new java.awt.Color(0, 204, 102));
        tab3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tab3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab3MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SaloonExt Th", 1, 18)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aeydeee/icons/contact-book.png"))); // NOI18N
        jLabel3.setText("CONTACTS");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout tab3Layout = new javax.swing.GroupLayout(tab3);
        tab3.setLayout(tab3Layout);
        tab3Layout.setHorizontalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        tab3Layout.setVerticalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab3Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(25, 25, 25))
        );

        tab2.setBackground(new java.awt.Color(0, 204, 102));
        tab2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tab2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab2MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("SaloonExt Th", 1, 18)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aeydeee/icons/food.png"))); // NOI18N
        jLabel2.setText("FOODZ TO GO!");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout tab2Layout = new javax.swing.GroupLayout(tab2);
        tab2.setLayout(tab2Layout);
        tab2Layout.setHorizontalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        tab2Layout.setVerticalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab2Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(24, 24, 24))
        );

        tab4.setBackground(new java.awt.Color(0, 204, 102));
        tab4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tab4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab4MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SaloonExt Th", 1, 18)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aeydeee/icons/about.png"))); // NOI18N
        jLabel4.setText("ABOUT");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout tab4Layout = new javax.swing.GroupLayout(tab4);
        tab4.setLayout(tab4Layout);
        tab4Layout.setHorizontalGroup(
            tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        tab4Layout.setVerticalGroup(
            tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel4)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aeydeee/icons/LOGO.png"))); // NOI18N

        lblClose.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        lblClose.setText("x");
        lblClose.setAlignmentY(0.0F);
        lblClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClose.setIconTextGap(0);
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
        });

        lblMinimize.setFont(new java.awt.Font("Arial Black", 1, 60)); // NOI18N
        lblMinimize.setText("-");
        lblMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinimizeMouseClicked(evt);
            }
        });

        btnLogout.setBackground(new java.awt.Color(204, 255, 204));
        btnLogout.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btnLogout.setText("LOGOUT");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tab2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tab3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tab4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblClose)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMinimize)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(btnLogout)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tab2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tab4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLogout)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(1014, 600));
        jPanel2.setMinimumSize(new java.awt.Dimension(1014, 600));
        jPanel2.setLayout(new javax.swing.OverlayLayout(jPanel2));

        jp1.setBackground(new java.awt.Color(204, 255, 0));
        jp1.setMaximumSize(null);
        jp1.setPreferredSize(new java.awt.Dimension(1014, 535));

        jPanel3.setBackground(new java.awt.Color(0, 153, 0));

        jLabel6.setFont(new java.awt.Font("Segoe Print", 1, 36)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("WELCOME TO CHICKEN ROASTER FOOD DELIVERY!");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1016, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Consolas", 1, 60)); // NOI18N
        jLabel7.setText("Hello");

        jLabel8.setFont(new java.awt.Font("Consolas", 1, 33)); // NOI18N
        jLabel8.setText("Name:");

        jLabel10.setFont(new java.awt.Font("Consolas", 1, 33)); // NOI18N
        jLabel10.setText("Email:");

        jLabel11.setFont(new java.awt.Font("Consolas", 1, 33)); // NOI18N
        jLabel11.setText("Address:");

        lblFirstName.setFont(new java.awt.Font("Consolas", 1, 33)); // NOI18N
        lblFirstName.setText("firstName");

        lblLastName.setFont(new java.awt.Font("Consolas", 1, 33)); // NOI18N
        lblLastName.setText("lastName");

        jLabel14.setFont(new java.awt.Font("Consolas", 1, 33)); // NOI18N
        jLabel14.setText("Birthday:");

        lblUsername.setBackground(new java.awt.Color(0, 51, 102));
        lblUsername.setFont(new java.awt.Font("Consolas", 1, 60)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(0, 0, 153));
        lblUsername.setText("username");

        lblEmail.setFont(new java.awt.Font("Consolas", 1, 33)); // NOI18N
        lblEmail.setText("emailAddress");

        lblHouseNo.setFont(new java.awt.Font("Consolas", 1, 33)); // NOI18N
        lblHouseNo.setText("hawsNo.");

        lblBirthday.setFont(new java.awt.Font("Consolas", 1, 33)); // NOI18N
        lblBirthday.setText("birthday");

        lblBrgy.setFont(new java.awt.Font("Consolas", 1, 33)); // NOI18N
        lblBrgy.setText("Brgy.");

        lblCity.setFont(new java.awt.Font("Consolas", 1, 33)); // NOI18N
        lblCity.setText("City");

        lblZip.setFont(new java.awt.Font("Consolas", 1, 33)); // NOI18N
        lblZip.setText("Zip");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProfile, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProfile, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
        );

        btnOrderStatus.setBackground(new java.awt.Color(0, 102, 102));
        btnOrderStatus.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnOrderStatus.setForeground(new java.awt.Color(255, 255, 255));
        btnOrderStatus.setText("ORDER STATUS");
        btnOrderStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jp1Layout = new javax.swing.GroupLayout(jp1);
        jp1.setLayout(jp1Layout);
        jp1Layout.setHorizontalGroup(
            jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jp1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp1Layout.createSequentialGroup()
                        .addComponent(btnOrderStatus)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jp1Layout.createSequentialGroup()
                        .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jp1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblUsername))
                            .addGroup(jp1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(21, 21, 21)
                                .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jp1Layout.createSequentialGroup()
                                        .addComponent(lblHouseNo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblBrgy)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblCity)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblZip))
                                    .addComponent(lblBirthday)))
                            .addGroup(jp1Layout.createSequentialGroup()
                                .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(41, 41, 41)
                                .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jp1Layout.createSequentialGroup()
                                        .addComponent(lblFirstName)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblLastName))
                                    .addComponent(lblEmail))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jp1Layout.setVerticalGroup(
            jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(lblUsername))
                        .addGap(18, 18, 18)
                        .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lblFirstName)
                            .addComponent(lblLastName))
                        .addGap(18, 18, 18)
                        .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(lblEmail))
                        .addGap(18, 18, 18)
                        .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(lblHouseNo)
                            .addComponent(lblBrgy)
                            .addComponent(lblCity)
                            .addComponent(lblZip))
                        .addGap(18, 18, 18)
                        .addGroup(jp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(lblBirthday)))
                    .addGroup(jp1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(151, Short.MAX_VALUE))
        );

        jPanel2.add(jp1);

        jp2.setBackground(new java.awt.Color(255, 245, 0));
        jp2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jp2.setMaximumSize(null);

        jLabel9.setFont(new java.awt.Font("Segoe Print", 1, 36)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("CHICKEN ROASTER FOOD DELIVERY SYSTEM");

        jPanel5.setBackground(new java.awt.Color(255, 213, 0));
        jPanel5.setPreferredSize(new java.awt.Dimension(330, 65));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel23.setText("MENU");

        lblBeerCanChicken.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblBeerCanChicken.setText("Beer Can Chicken");

        lblSpatchcockChicken.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSpatchcockChicken.setText("Spatchcock Chicken");

        lblButterfliedChicken.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblButterfliedChicken.setText("Butterflied Chicken");

        lblLumpiaChicken.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLumpiaChicken.setText("Lumpia Chicken");

        lblChickenKabobs.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblChickenKabobs.setText("Chicken Kabobs");

        lblChickenAdobo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblChickenAdobo.setText("Chicken Adobo");

        qtyBeerCan.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        qtyBeerCan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtyBeerCan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtyBeerCanKeyTyped(evt);
            }
        });

        qtySpatchcock.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        qtySpatchcock.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtySpatchcock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtySpatchcockKeyTyped(evt);
            }
        });

        qtyKabobs.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        qtyKabobs.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtyKabobs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtyKabobsKeyTyped(evt);
            }
        });

        qtyLumpia.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        qtyLumpia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtyLumpia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtyLumpiaKeyTyped(evt);
            }
        });

        qtyAdobo.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        qtyAdobo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtyAdobo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtyAdoboKeyTyped(evt);
            }
        });

        qtyButterflied.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        qtyButterflied.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtyButterflied.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qtyButterfliedActionPerformed(evt);
            }
        });
        qtyButterflied.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtyButterfliedKeyTyped(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 102));
        jLabel24.setText("QUANTITY");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblBeerCanChicken)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(qtyBeerCan, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblButterfliedChicken)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(qtyButterflied, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblSpatchcockChicken)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(qtySpatchcock, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblChickenAdobo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(qtyAdobo, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblChickenKabobs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(qtyKabobs, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblLumpiaChicken)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(qtyLumpia, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addGap(22, 22, 22))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBeerCanChicken)
                    .addComponent(qtyBeerCan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSpatchcockChicken)
                    .addComponent(qtySpatchcock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblButterfliedChicken)
                    .addComponent(qtyButterflied, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLumpiaChicken)
                    .addComponent(qtyLumpia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChickenKabobs)
                    .addComponent(qtyKabobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChickenAdobo)
                    .addComponent(qtyAdobo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 213, 0));

        qtySting.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        qtySting.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtySting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qtyStingActionPerformed(evt);
            }
        });
        qtySting.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtyStingKeyTyped(evt);
            }
        });

        lblMalunggayMilkTea.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMalunggayMilkTea.setText("Malunggay Milk tea");

        qtyGin.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        qtyGin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtyGin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtyGinKeyTyped(evt);
            }
        });

        lblMineral.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMineral.setText("Mineral Water");

        qtyMineral.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        qtyMineral.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtyMineral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtyMineralKeyTyped(evt);
            }
        });

        lblRedhorse.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblRedhorse.setText("Redhorse");

        qtyCobra.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        qtyCobra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtyCobra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtyCobraKeyTyped(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel31.setText("DRINKS");

        qtyRedhorse.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        qtyRedhorse.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtyRedhorse.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtyRedhorseKeyTyped(evt);
            }
        });

        qtyMalunggay.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        qtyMalunggay.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtyMalunggay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtyMalunggayKeyTyped(evt);
            }
        });

        lblCobra.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCobra.setText("Cobra E-Drink");

        lblSting.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSting.setText("Sting E-Drink");

        lblGinRound.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblGinRound.setText("GIN Round");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 102));
        jLabel25.setText("QUANTITY");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblMalunggayMilkTea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(qtyMalunggay, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblSting)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(qtySting, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblCobra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(qtyCobra, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblGinRound)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(qtyGin, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblRedhorse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(qtyRedhorse, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblMineral)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(qtyMineral, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addGap(20, 20, 20))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMalunggayMilkTea)
                    .addComponent(qtyMalunggay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCobra)
                    .addComponent(qtyCobra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSting)
                    .addComponent(qtySting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMineral)
                    .addComponent(qtyMineral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRedhorse)
                    .addComponent(qtyRedhorse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGinRound)
                    .addComponent(qtyGin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel12.setBackground(new java.awt.Color(255, 213, 0));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel39.setText("Cost of Meal/s:");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel40.setText("Cost of Drink/s:");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel41.setText("Sub-Total:");

        txtCostMeal.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        txtCostMeal.setFocusable(false);
        txtCostMeal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostMealActionPerformed(evt);
            }
        });

        txtCostDrinks.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        txtCostDrinks.setFocusable(false);

        txtTotalCostItems.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        txtTotalCostItems.setForeground(new java.awt.Color(0, 0, 153));
        txtTotalCostItems.setFocusable(false);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41))
                .addGap(34, 34, 34)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTotalCostItems)
                    .addComponent(txtCostMeal)
                    .addComponent(txtCostDrinks))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCostMeal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txtCostDrinks, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtTotalCostItems, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtCostDrinks, txtCostMeal, txtTotalCostItems});

        jPanel13.setBackground(new java.awt.Color(255, 213, 0));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel42.setText("Tax:");

        txtTax.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        txtTax.setFocusable(false);

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel44.setText("Total:");

        txtTotalPrice.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        txtTotalPrice.setForeground(new java.awt.Color(204, 51, 0));
        txtTotalPrice.setFocusable(false);

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel45.setText("Enter Payment:");

        txtPayment.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        txtPayment.setForeground(new java.awt.Color(204, 51, 0));
        txtPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaymentActionPerformed(evt);
            }
        });
        txtPayment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPaymentKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPayment)
                    .addComponent(txtTax, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(txtTotalPrice))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTax))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                        .addGap(8, 8, 8))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel13Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtTax, txtTotalPrice});

        jPanel7.setBackground(new java.awt.Color(255, 245, 0));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        btnTotal.setBackground(new java.awt.Color(255, 255, 204));
        btnTotal.setFont(new java.awt.Font("Brownwood NF", 1, 14)); // NOI18N
        btnTotal.setText("TOTAL");
        btnTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalActionPerformed(evt);
            }
        });

        btnConfirm.setBackground(new java.awt.Color(255, 204, 204));
        btnConfirm.setFont(new java.awt.Font("Brownwood NF", 1, 14)); // NOI18N
        btnConfirm.setText("CONFIRM");
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(204, 255, 204));
        btnReset.setFont(new java.awt.Font("Brownwood NF", 1, 14)); // NOI18N
        btnReset.setText("RESET");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnExit.setBackground(new java.awt.Color(153, 204, 255));
        btnExit.setFont(new java.awt.Font("Brownwood NF", 1, 14)); // NOI18N
        btnExit.setText("EXIT");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConfirm)
                .addGap(18, 18, 18)
                .addComponent(btnReset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit)
                .addGap(53, 53, 53))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnConfirm, btnExit, btnReset, btnTotal});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblMenus.setBackground(new java.awt.Color(204, 255, 204));
        tblMenus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tblMenus.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Category", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMenus.setRowHeight(30);
        tblMenus.setSelectionBackground(new java.awt.Color(0, 102, 102));
        tblMenus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMenusMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMenus);

        lblImage.setBackground(new java.awt.Color(255, 255, 255));
        lblImage.setFont(new java.awt.Font("Segoe Script", 1, 12)); // NOI18N
        lblImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImage.setText("PRODUCT IMAGE HERE");
        lblImage.setOpaque(true);

        btnFirst.setBackground(new java.awt.Color(218, 218, 218));
        btnFirst.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnFirst.setForeground(new java.awt.Color(255, 255, 255));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicons/first.png"))); // NOI18N
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(218, 218, 218));
        btnPrev.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnPrev.setForeground(new java.awt.Color(255, 255, 255));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicons/prev.png"))); // NOI18N
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(218, 218, 218));
        btnNext.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnNext.setForeground(new java.awt.Color(255, 255, 255));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicons/next.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(218, 218, 218));
        btnLast.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnLast.setForeground(new java.awt.Color(255, 255, 255));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicons/last.png"))); // NOI18N
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Postmaster", 1, 24)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 51, 153));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("FooDs and Drinks List");

        javax.swing.GroupLayout jp2Layout = new javax.swing.GroupLayout(jp2);
        jp2.setLayout(jp2Layout);
        jp2Layout.setHorizontalGroup(
            jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 1014, Short.MAX_VALUE)
            .addGroup(jp2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp2Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jp2Layout.createSequentialGroup()
                        .addGroup(jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jp2Layout.createSequentialGroup()
                                .addGroup(jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jp2Layout.createSequentialGroup()
                                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(1, 1, 1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jp2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel5, jPanel6});

        jp2Layout.setVerticalGroup(
            jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp2Layout.createSequentialGroup()
                        .addGroup(jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jp2Layout.createSequentialGroup()
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addGroup(jp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 9, Short.MAX_VALUE))
                            .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jp2Layout.createSequentialGroup()
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );

        jPanel2.add(jp2);

        jp3.setBackground(new java.awt.Color(255, 153, 0));
        jp3.setPreferredSize(new java.awt.Dimension(1014, 551));

        jLabel12.setBackground(new java.awt.Color(0, 255, 102));
        jLabel12.setFont(new java.awt.Font("Segoe Print", 1, 48)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("CONTACT PAGE");
        jLabel12.setOpaque(true);

        lblImageAdrian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aeydeee/icons/paul.jpg"))); // NOI18N
        lblImageAdrian.setOpaque(true);

        jPanel8.setBackground(new java.awt.Color(204, 204, 255));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Name:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setText("Adrian Samuel Olleres");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setText("Position:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setText("Chief Executive Officer / CEO");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setText("Address:");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setText("Zone 1, Topas Proper, Nabua, Camarines Sur");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setText("Email:");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 51, 153));
        jLabel22.setText("gypsophila881@gmail.com");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setText("FB:");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 51, 153));
        jLabel27.setText("https://www.facebook.com/aeydeee");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel26)
                    .addComponent(jLabel17)
                    .addComponent(jLabel19)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20)
                    .addComponent(jLabel22)
                    .addComponent(jLabel16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(204, 255, 204));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setText("Name:");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel28.setText("John Paul Alfonso");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel29.setText("Position:");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel30.setText("Chief Financial Officer / CFO");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel32.setText("Address:");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel33.setText("Zone 4, Paloyon Proper, Nabua, Camarines Sur");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel34.setText("Email:");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 51, 153));
        jLabel35.setText("johnpaulaalfonso@gmail.com");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel36.setText("FB:");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 51, 153));
        jLabel37.setText("https://www.facebook.com/ampol.alfonso");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel36)
                    .addComponent(jLabel29)
                    .addComponent(jLabel32)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addComponent(jLabel30)
                    .addComponent(jLabel33)
                    .addComponent(jLabel35)
                    .addComponent(jLabel28))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 204, 204));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel38.setText("Name:");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel43.setText("Mary Grace Isidro");

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel46.setText("Position:");

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel47.setText("Chief Strategy Officer / CSO");

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel48.setText("Address:");

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel49.setText("Zone 7,Sagrada, Crossing Buhi Camarines Sur");

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel50.setText("Email:");

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 51, 153));
        jLabel51.setText("Isidromarygrace16@gmail.com");

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel52.setText("FB:");

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(0, 51, 153));
        jLabel53.setText("https://www.facebook.com/graxiaIsidro");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel38)
                    .addComponent(jLabel52)
                    .addComponent(jLabel46)
                    .addComponent(jLabel48)
                    .addComponent(jLabel50))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel53)
                    .addComponent(jLabel47)
                    .addComponent(jLabel49)
                    .addComponent(jLabel51)
                    .addComponent(jLabel43))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(jLabel51))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(jLabel53))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblImageAdrian3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aeydeee/icons/adrian.jpg"))); // NOI18N
        lblImageAdrian3.setOpaque(true);

        lblImageAdrian4.setOpaque(true);

        javax.swing.GroupLayout jp3Layout = new javax.swing.GroupLayout(jp3);
        jp3.setLayout(jp3Layout);
        jp3Layout.setHorizontalGroup(
            jp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jp3Layout.createSequentialGroup()
                .addGap(176, 176, 176)
                .addGroup(jp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblImageAdrian, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImageAdrian3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImageAdrian4, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jp3Layout.setVerticalGroup(
            jp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp3Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImageAdrian3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImageAdrian, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImageAdrian4, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jp3);

        jp4.setBackground(new java.awt.Color(0, 204, 204));
        jp4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jTextArea1.setBackground(new java.awt.Color(153, 255, 204));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("\nWelcome to CHICKEN ROASTER FOOD DELIVERY!, your one-stop shop for convenient, reliable, and delectable meals delivered right to your doorstep. Our passion for culinary excellence and commitment to customer satisfaction drive us to provide an exceptional dining experience that will tantalize your taste buds and leave you wanting more.\n\nAt CHICKEN ROASTER FOOD DELIVERY!, we believe that great food should be accessible to everyone, no matter how busy your schedule. That's why we've streamlined the ordering process, offering a user-friendly platform that makes it easy to browse our extensive menu, customize your order to your exact liking, and have it delivered fresh and hot to your home or office.\n\nOur team of culinary experts has carefully crafted a menu that features a wide variety of dishes to suit every palate. From succulent rotisserie chicken and savory side dishes to mouthwatering desserts, we have something to satisfy every craving. We source only the freshest ingredients and prepare each dish with meticulous attention to detail, ensuring that every bite is an explosion of flavor.\n\nWe're proud to have earned a reputation for providing exceptional service and consistently exceeding customer expectations. Our dedicated team is committed to going the extra mile to ensure your satisfaction, from promptly answering your inquiries to resolving any issues you may encounter.\n\nJoin the CHICKEN ROASTER FOOD DELIVERY! family and experience the joy of having delicious meals delivered to your doorstep. Explore our menu, create your perfect order, and savor the taste of culinary excellence. We look forward to being your go-to source for convenient, satisfying, and unforgettable dining experiences.");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setDisabledTextColor(new java.awt.Color(204, 255, 204));
        jTextArea1.setFocusable(false);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel54.setFont(new java.awt.Font("Segoe Print", 1, 60)); // NOI18N
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("ABOUT PAGE");

        javax.swing.GroupLayout jp4Layout = new javax.swing.GroupLayout(jp4);
        jp4.setLayout(jp4Layout);
        jp4Layout.setHorizontalGroup(
            jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, 1006, Short.MAX_VALUE)
        );
        jp4Layout.setVerticalGroup(
            jp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp4Layout.createSequentialGroup()
                .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(jp4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tab1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab1MouseClicked

        performTab1Click();

    }//GEN-LAST:event_tab1MouseClicked

    private void tab2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab2MouseClicked

        jp2.setVisible(true);
        jp1.setVisible(false);
        jp3.setVisible(false);
        jp4.setVisible(false);

        tab2.setBackground(Color.white);
        tab1.setBackground(new Color(0, 204, 102));
        tab3.setBackground(new Color(0, 204, 102));
        tab4.setBackground(new Color(0, 204, 102));

    }//GEN-LAST:event_tab2MouseClicked

    private void tab3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab3MouseClicked

        jp3.setVisible(true);
        jp2.setVisible(false);
        jp1.setVisible(false);
        jp4.setVisible(false);

        tab3.setBackground(Color.white);
        tab1.setBackground(new Color(0, 204, 102));
        tab2.setBackground(new Color(0, 204, 102));
        tab4.setBackground(new Color(0, 204, 102));
    }//GEN-LAST:event_tab3MouseClicked

    private void tab4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab4MouseClicked

        jp4.setVisible(true);
        jp2.setVisible(false);
        jp3.setVisible(false);
        jp1.setVisible(false);

        tab4.setBackground(Color.white);
        tab1.setBackground(new Color(0, 204, 102));
        tab2.setBackground(new Color(0, 204, 102));
        tab3.setBackground(new Color(0, 204, 102));
    }//GEN-LAST:event_tab4MouseClicked

    private void qtyButterfliedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qtyButterfliedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qtyButterfliedActionPerformed

    private void qtyStingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qtyStingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qtyStingActionPerformed

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked

        int closeBtn = JOptionPane.showConfirmDialog(null, "Sure u wanna close the app?");
        if (closeBtn == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_lblCloseMouseClicked

    private void lblMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizeMouseClicked

        this.setState(ICONIFIED);
    }//GEN-LAST:event_lblMinimizeMouseClicked

    private void btnTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalActionPerformed
        double totalCost;
        //       Menu Names
        String beerCan = lblBeerCanChicken.getText();
        String spatchcock = lblSpatchcockChicken.getText();
        String butterflied = lblButterfliedChicken.getText();
        String lumpia = lblLumpiaChicken.getText();
        String Kabobs = lblChickenKabobs.getText();
        String Adobo = lblChickenAdobo.getText();

//        Drink Names
        String malunggay = lblMalunggayMilkTea.getText();
        String cobra = lblCobra.getText();
        String sting = lblSting.getText();
        String mineral = lblMineral.getText();
        String redhorse = lblRedhorse.getText();
        String ginBilog = lblGinRound.getText();

        Map<String, Double> dishPrices = getDishPrices();
        //       Menu Prices
        double beerCanChicken = dishPrices.get(beerCan);
        double spatchcockChicken = dishPrices.get(spatchcock);
        double butterfliedChicken = dishPrices.get(butterflied);
        double lumpiaChicken = dishPrices.get(lumpia);
        double chickenKabobs = dishPrices.get(Kabobs);
        double chickenAdobo = dishPrices.get(Adobo);

        //      Drink Prices
        double malunggayMilkTea = dishPrices.get(malunggay);
        double cobraDrink = dishPrices.get(cobra);
        double stingDrink = dishPrices.get(sting);
        double mineralWater = dishPrices.get(mineral);
        double redhorseDrink = dishPrices.get(redhorse);
        double ginBilogDrink = dishPrices.get(ginBilog);

//       Quantity of Menus
        String beerCanQty = qtyBeerCan.getText().trim();
        String spatchcockQty = qtySpatchcock.getText().trim();
        String butterfliedQty = qtyButterflied.getText().trim();
        String lumpiaQty = qtyLumpia.getText().trim();
        String kabobsQty = qtyKabobs.getText().trim();
        String adoboQty = qtyAdobo.getText().trim();

//       Quantity of Drinks
        String malunggayQty = qtyMalunggay.getText().trim();
        String cobraQty = qtyCobra.getText().trim();
        String stingQty = qtySting.getText().trim();
        String mineralQty = qtyMineral.getText().trim();
        String redhorseQty = qtyRedhorse.getText().trim();
        String ginQty = qtyGin.getText().trim();

// Quantities of the Items
        double qtyPriceCanChicken = (beerCanQty.isEmpty()) ? 0 : Double.parseDouble(beerCanQty);
        double qtyPricespatchcock = (spatchcockQty.isEmpty()) ? 0 : Double.parseDouble(spatchcockQty);
        double qtyPriceButterflied = (butterfliedQty.isEmpty()) ? 0 : Double.parseDouble(butterfliedQty);
        double qtyPriceLumpia = (lumpiaQty.isEmpty()) ? 0 : Double.parseDouble(lumpiaQty);
        double qtyPriceKabobs = (kabobsQty.isEmpty()) ? 0 : Double.parseDouble(kabobsQty);
        double qtyPriceAdobo = (adoboQty.isEmpty()) ? 0 : Double.parseDouble(adoboQty);
        double qtyPriceMalunggay = (malunggayQty.isEmpty()) ? 0 : Double.parseDouble(malunggayQty);
        double qtyPriceCobra = (cobraQty.isEmpty()) ? 0 : Double.parseDouble(cobraQty);
        double qtyPriceSting = (stingQty.isEmpty()) ? 0 : Double.parseDouble(stingQty);
        double qtyPriceMineral = (mineralQty.isEmpty()) ? 0 : Double.parseDouble(mineralQty);
        double qtyPriceRedhorse = (redhorseQty.isEmpty()) ? 0 : Double.parseDouble(redhorseQty);
        double qtyPriceGin = (ginQty.isEmpty()) ? 0 : Double.parseDouble(ginQty);

        String php = "\u20B1";

        if (qtyPriceCanChicken == 0 && qtyPricespatchcock == 0 && qtyPriceButterflied == 0
                && qtyPriceLumpia == 0 && qtyPriceKabobs == 0 && qtyPriceAdobo == 0
                && qtyPriceMalunggay == 0 && qtyPriceCobra == 0 && qtyPriceSting == 0
                && qtyPriceMineral == 0 && qtyPriceRedhorse == 0 && qtyPriceGin == 0) {
            // Show an error message for no quantity input
            JOptionPane.showMessageDialog(this, "Please enter at least one quantity.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Continue with the rest of your calculations and updates
            totalCost = (beerCanChicken * qtyPriceCanChicken) + (spatchcockChicken * qtyPricespatchcock)
                    + (butterfliedChicken * qtyPriceButterflied) + (lumpiaChicken * qtyPriceLumpia)
                    + (chickenKabobs * qtyPriceKabobs) + (chickenAdobo * qtyPriceAdobo)
                    + (malunggayMilkTea * qtyPriceMalunggay) + (cobraDrink * qtyPriceCobra)
                    + (stingDrink * qtyPriceSting) + (mineralWater * qtyPriceMineral)
                    + (redhorseDrink * qtyPriceRedhorse) + (ginBilogDrink * qtyPriceGin);

            double mealsCost = (beerCanChicken * qtyPriceCanChicken) + (spatchcockChicken * qtyPricespatchcock)
                    + (butterfliedChicken * qtyPriceButterflied) + (lumpiaChicken * qtyPriceLumpia)
                    + (chickenKabobs * qtyPriceKabobs) + (chickenAdobo * qtyPriceAdobo);

            double drinksCost = (malunggayMilkTea * qtyPriceMalunggay) + (cobraDrink * qtyPriceCobra)
                    + (stingDrink * qtyPriceSting) + (mineralWater * qtyPriceMineral)
                    + (redhorseDrink * qtyPriceRedhorse) + (ginBilogDrink * qtyPriceGin);

            double totalItemCost = mealsCost + drinksCost;

            double tax = 0.10 * totalItemCost;
            String taxString = String.format("%.2f", tax);

            double totalPrice = totalCost + tax;

            txtCostMeal.setText(php + " " + Double.toString(mealsCost));
            txtCostDrinks.setText(php + " " + Double.toString(drinksCost));
            txtTotalCostItems.setText(php + " " + Double.toString(totalItemCost));
            txtTax.setText(php + " " + taxString);
            txtTotalPrice.setText(php + " " + Double.toString(totalPrice));
        }

    }//GEN-LAST:event_btnTotalActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed

        position = 0;
        showProductData(position);
        tblMenus.setRowSelectionInterval(position, position);
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed

        position--;

        if (position < 0) {
            position = 0;
        }

        showProductData(position);
        tblMenus.setRowSelectionInterval(position, position);
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed

        position++;
        if (position > menuArray.size() - 1) {
            position = menuArray.size() - 1;
        }
        showProductData(position);
        tblMenus.setRowSelectionInterval(position, position);
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed

        position = getMenuList().size() - 1;
        showProductData(position);
        tblMenus.setRowSelectionInterval(position, position);
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblMenusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMenusMouseClicked
        int index = tblMenus.getSelectedRow();
        showProductData(index);
    }//GEN-LAST:event_tblMenusMouseClicked

    private void txtCostMealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostMealActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostMealActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed

        qtyBeerCan.setText("");
        qtySpatchcock.setText("");
        qtyButterflied.setText("");
        qtyLumpia.setText("");
        qtyKabobs.setText("");
        qtyAdobo.setText("");

        qtyMalunggay.setText("");
        qtyCobra.setText("");
        qtySting.setText("");
        qtyMineral.setText("");
        qtyRedhorse.setText("");
        qtyGin.setText("");

        txtCostMeal.setText("");
        txtCostDrinks.setText("");
        txtTotalCostItems.setText("");
        txtTax.setText("");
        txtTotalPrice.setText("");

    }//GEN-LAST:event_btnResetActionPerformed

    private void qtyBeerCanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyBeerCanKeyTyped
        allowOnlyNumbers(evt);
    }//GEN-LAST:event_qtyBeerCanKeyTyped

    private void qtySpatchcockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtySpatchcockKeyTyped
        allowOnlyNumbers(evt);
    }//GEN-LAST:event_qtySpatchcockKeyTyped

    private void qtyButterfliedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyButterfliedKeyTyped
        allowOnlyNumbers(evt);
    }//GEN-LAST:event_qtyButterfliedKeyTyped

    private void qtyLumpiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyLumpiaKeyTyped
        allowOnlyNumbers(evt);
    }//GEN-LAST:event_qtyLumpiaKeyTyped

    private void qtyKabobsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyKabobsKeyTyped
        allowOnlyNumbers(evt);
    }//GEN-LAST:event_qtyKabobsKeyTyped

    private void qtyAdoboKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyAdoboKeyTyped
        allowOnlyNumbers(evt);
    }//GEN-LAST:event_qtyAdoboKeyTyped

    private void qtyMalunggayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyMalunggayKeyTyped
        allowOnlyNumbers(evt);
    }//GEN-LAST:event_qtyMalunggayKeyTyped

    private void qtyCobraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyCobraKeyTyped
        allowOnlyNumbers(evt);
    }//GEN-LAST:event_qtyCobraKeyTyped

    private void qtyStingKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyStingKeyTyped
        allowOnlyNumbers(evt);
    }//GEN-LAST:event_qtyStingKeyTyped

    private void qtyMineralKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyMineralKeyTyped
        allowOnlyNumbers(evt);
    }//GEN-LAST:event_qtyMineralKeyTyped

    private void qtyRedhorseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyRedhorseKeyTyped
        allowOnlyNumbers(evt);
    }//GEN-LAST:event_qtyRedhorseKeyTyped

    private void qtyGinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyGinKeyTyped
        allowOnlyNumbers(evt);
    }//GEN-LAST:event_qtyGinKeyTyped

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        int exitBtn = JOptionPane.showConfirmDialog(null, "Sure u wanna exit the app?");
        if (exitBtn == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
        try {
            if (txtPayment.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a payment amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if the payment amount is not entered
            } else if (txtTotalPrice.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please calculate the total amount before confirming", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if the total amount is not entered
            }

            // Clean and parse the payment and total price
            String cleanedPayment = cleanNumericString(txtPayment.getText());
            String cleanedTotalPrice = cleanNumericString(txtTotalPrice.getText());

            double payment = Double.parseDouble(cleanedPayment);
            double totalPrice = Double.parseDouble(cleanedTotalPrice);

            if (payment < totalPrice) {
                JOptionPane.showMessageDialog(null, "Please make sure your payment is right", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if the payment is less than the total amount
            }

            // Your existing code for printing the receipt
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid numeric input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Log the exception for debugging purposes
        }

        JLabel messageLabel = new JLabel("<html>This will process the payment and mark the order as pending delivery. Are you sure?</html>");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set your desired font size here

        int finalConfirm = JOptionPane.showConfirmDialog(null, messageLabel, "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (finalConfirm == JOptionPane.YES_OPTION) {
            // Collect items with quantity
            List<ItemWithQuantity> itemsWithQuantity = new ArrayList<>();

            addItemWithQuantity(lblBeerCanChicken, qtyBeerCan, itemsWithQuantity);
            addItemWithQuantity(lblSpatchcockChicken, qtySpatchcock, itemsWithQuantity);
            addItemWithQuantity(lblButterfliedChicken, qtyButterflied, itemsWithQuantity);
            addItemWithQuantity(lblLumpiaChicken, qtyLumpia, itemsWithQuantity);
            addItemWithQuantity(lblChickenKabobs, qtyKabobs, itemsWithQuantity);
            addItemWithQuantity(lblChickenAdobo, qtyAdobo, itemsWithQuantity);
            addItemWithQuantity(lblMalunggayMilkTea, qtyMalunggay, itemsWithQuantity);
            addItemWithQuantity(lblCobra, qtyCobra, itemsWithQuantity);
            addItemWithQuantity(lblSting, qtySting, itemsWithQuantity);
            addItemWithQuantity(lblMineral, qtyMineral, itemsWithQuantity);
            addItemWithQuantity(lblRedhorse, qtyRedhorse, itemsWithQuantity);
            addItemWithQuantity(lblGinRound, qtyGin, itemsWithQuantity);

            // Create an instance of Receipt
            Receipt receipt = new Receipt();
            // Set the Receipt instance for MenuPage
            setReceipt(receipt);
            // Set the items with quantity in the Receipt frame
            setItemsWithQuantity(itemsWithQuantity);
            // Set up and display the Receipt
            receipt.setVisible(true);
            receipt.pack();
            receipt.setLocationRelativeTo(null);
            saveOrderToDatabase();
            receipt_print();
        }


    }//GEN-LAST:event_btnConfirmActionPerformed

    private void txtPaymentKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaymentKeyTyped
        allowOnlyNumbers(evt);
    }//GEN-LAST:event_txtPaymentKeyTyped

    private void txtPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaymentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaymentActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int logoutBtn = JOptionPane.showConfirmDialog(null, "Sure u wanna logout?");
        if (logoutBtn == JOptionPane.YES_OPTION) {
            Login login = new Login();
            login.setVisible(true);
            login.pack();
            login.setLocationRelativeTo(null);
            login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.dispose();
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnOrderStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderStatusActionPerformed
        int userId = UsersArray.get(0).getId();

        int statusBtn = JOptionPane.showConfirmDialog(null, "See the order status?");
        if (statusBtn == JOptionPane.YES_OPTION) {
            // Assuming you have the user ID available (replace 123 with the actual user ID)

            OrderStatus status = new OrderStatus(userId);
            status.setVisible(true);
            status.pack();
            status.setLocationRelativeTo(null);
            // Note: You might not want to use setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
            // as it will close the entire application when you close the OrderStatus frame.
            // Instead, consider using setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
            // to only close the OrderStatus frame.
            status.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }//GEN-LAST:event_btnOrderStatusActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new MenuPage().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnOrderStatus;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel jp1;
    private javax.swing.JPanel jp2;
    private javax.swing.JPanel jp3;
    private javax.swing.JPanel jp4;
    private javax.swing.JLabel lblBeerCanChicken;
    private javax.swing.JLabel lblBirthday;
    private javax.swing.JLabel lblBrgy;
    private javax.swing.JLabel lblButterfliedChicken;
    private javax.swing.JLabel lblChickenAdobo;
    private javax.swing.JLabel lblChickenKabobs;
    private javax.swing.JLabel lblCity;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblCobra;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblGinRound;
    private javax.swing.JLabel lblHouseNo;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblImageAdrian;
    private javax.swing.JLabel lblImageAdrian3;
    private javax.swing.JLabel lblImageAdrian4;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblLumpiaChicken;
    private javax.swing.JLabel lblMalunggayMilkTea;
    private javax.swing.JLabel lblMineral;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JLabel lblProfile;
    private javax.swing.JLabel lblRedhorse;
    private javax.swing.JLabel lblSpatchcockChicken;
    private javax.swing.JLabel lblSting;
    public javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblZip;
    private javax.swing.JTextField qtyAdobo;
    private javax.swing.JTextField qtyBeerCan;
    private javax.swing.JTextField qtyButterflied;
    private javax.swing.JTextField qtyCobra;
    private javax.swing.JTextField qtyGin;
    private javax.swing.JTextField qtyKabobs;
    private javax.swing.JTextField qtyLumpia;
    private javax.swing.JTextField qtyMalunggay;
    private javax.swing.JTextField qtyMineral;
    private javax.swing.JTextField qtyRedhorse;
    private javax.swing.JTextField qtySpatchcock;
    private javax.swing.JTextField qtySting;
    private javax.swing.JPanel tab1;
    private javax.swing.JPanel tab2;
    private javax.swing.JPanel tab3;
    private javax.swing.JPanel tab4;
    private javax.swing.JTable tblMenus;
    private javax.swing.JTextField txtCostDrinks;
    private javax.swing.JTextField txtCostMeal;
    private javax.swing.JTextField txtPayment;
    private javax.swing.JTextField txtTax;
    private javax.swing.JTextField txtTotalCostItems;
    private javax.swing.JTextField txtTotalPrice;
    // End of variables declaration//GEN-END:variables
}
