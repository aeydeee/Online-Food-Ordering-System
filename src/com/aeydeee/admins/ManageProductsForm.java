package com.aeydeee.admins;

import com.aeydeee.customers.Menu;
import com.aeydeee.database.MyConnection;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Logger;

public final class ManageProductsForm extends javax.swing.JFrame {

    int position = 0;

    public ManageProductsForm() {
        initComponents();
        // populate the jtable with products
        showProductsInTable();

        txtImagePath.setPreferredSize(new Dimension(64, 22));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                // This code will be executed when the window is opened
                // Put your code here to trigger the action
                btnFirst.doClick(); // This will simulate a click on the panel
            }
        });
    }

    // create a function to display product data by index
    public void showUserData(int index) {
        spinnerId.setValue(menuArray.get(index).getId());
        txtName.setText(menuArray.get(index).getName());
        cmbCategory.setSelectedItem(menuArray.get(index).getCategory());
        txtQuantity.setText(menuArray.get(index).getQuantity().toString());
        txtPrice.setText(menuArray.get(index).getPrice().toString());
        txtImagePath.setText(menuArray.get(index).getImagePath());
        displayImage(menuArray.get(index).getImagePath(), lblImage, 'a');
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

    // create a function to clear fields
    public void clearFields() {
        spinnerId.setValue(0);
        txtName.setText("");
        txtQuantity.setText("");
        cmbCategory.setSelectedIndex(0);
        txtPrice.setText("");
        txtImagePath.setText("");
        lblImage.setIcon(null);
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
        DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();

        // clear jtable
        model.setRowCount(0);

        Object[] row = new Object[6];// 6 the number of columns

        for (int i = 0; i < productsList.size(); i++) {
            row[0] = productsList.get(i).getId();
            row[1] = productsList.get(i).getName();
            row[2] = productsList.get(i).getCategory();
            row[3] = productsList.get(i).getQuantity();
            row[4] = productsList.get(i).getPrice();
            row[5] = productsList.get(i).getImagePath();

            model.addRow(row);
        }

    }

    public boolean checkEmptyFields() {
        String name = txtName.getText().trim();
        String quantity = txtQuantity.getText().trim();
        String price = txtPrice.getText().trim();
        String imagePath = txtImagePath.getText().trim();

        return !(name.equals("") || quantity.equals("") || price.equals("") || imagePath.equals("")); // if one or more fields are empty return false else return true

    }

    private void selectSearchedItem(String search) {
        for (int i = 0; i < tblProducts.getRowCount(); i++) {
            Object value = tblProducts.getValueAt(i, 1); // Assuming the name is in the second column (index 1)
            if (value != null && value.toString().toLowerCase().contains(search.toLowerCase())) {
                tblProducts.setRowSelectionInterval(i, i);
                tblProducts.scrollRectToVisible(tblProducts.getCellRect(i, 0, true));
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panelContainer = new javax.swing.JPanel();
        lblMinimize = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        spinnerId = new javax.swing.JSpinner();
        cmbCategory = new javax.swing.JComboBox<>();
        txtName = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        lblImage = new javax.swing.JLabel();
        btnRemove = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnBrowse = new javax.swing.JButton();
        txtImagePath = new javax.swing.JTextField();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelContainer.setBackground(new java.awt.Color(255, 153, 153));
        panelContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        lblMinimize.setFont(new java.awt.Font("Arial Black", 1, 60)); // NOI18N
        lblMinimize.setForeground(new java.awt.Color(51, 102, 255));
        lblMinimize.setText("-");
        lblMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinimizeMouseClicked(evt);
            }
        });

        lblClose.setFont(new java.awt.Font("Arial Black", 1, 60)); // NOI18N
        lblClose.setForeground(new java.awt.Color(204, 51, 0));
        lblClose.setText("x");
        lblClose.setAlignmentY(0.0F);
        lblClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClose.setIconTextGap(0);
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
        });

        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Category", "Quantity", "Price", "Image"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProducts.setRowHeight(53);
        tblProducts.setSelectionBackground(new java.awt.Color(0, 153, 102));
        tblProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProducts);

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        jLabel1.setText("SEARCH:");

        jLabel2.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        jLabel2.setText("Name:");

        jLabel3.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        jLabel3.setText("Category:");

        jLabel4.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        jLabel4.setText("Quantity:");

        jLabel5.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        jLabel5.setText("Price:");

        jLabel6.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        jLabel6.setText("Image:");

        spinnerId.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        spinnerId.setEnabled(false);
        spinnerId.setFocusable(false);

        cmbCategory.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menu", "Drinks" }));

        txtName.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N

        txtQuantity.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        txtQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantityActionPerformed(evt);
            }
        });
        txtQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtQuantityKeyTyped(evt);
            }
        });

        txtPrice.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        txtPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPriceKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPriceKeyTyped(evt);
            }
        });

        lblImage.setBackground(new java.awt.Color(153, 153, 153));
        lblImage.setOpaque(true);

        btnRemove.setBackground(new java.awt.Color(51, 51, 51));
        btnRemove.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnRemove.setForeground(new java.awt.Color(255, 255, 255));
        btnRemove.setText("REMOVE");
        btnRemove.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(51, 51, 51));
        btnAdd.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("ADD");
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(51, 51, 51));
        btnEdit.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("EDIT");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnSearch.setBackground(new java.awt.Color(153, 51, 0));
        btnSearch.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("SEARCH");
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnBrowse.setBackground(new java.awt.Color(153, 51, 0));
        btnBrowse.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnBrowse.setForeground(new java.awt.Color(255, 255, 255));
        btnBrowse.setText("BROWSE");
        btnBrowse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        txtImagePath.setEnabled(false);

        btnFirst.setBackground(new java.awt.Color(204, 204, 204));
        btnFirst.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnFirst.setForeground(new java.awt.Color(255, 255, 255));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicons/first.png"))); // NOI18N
        btnFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(204, 204, 204));
        btnPrev.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnPrev.setForeground(new java.awt.Color(255, 255, 255));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicons/prev.png"))); // NOI18N
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(204, 204, 204));
        btnNext.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnNext.setForeground(new java.awt.Color(255, 255, 255));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicons/next.png"))); // NOI18N
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(204, 204, 204));
        btnLast.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnLast.setForeground(new java.awt.Color(255, 255, 255));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicons/last.png"))); // NOI18N
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        txtSearch.setText("Search using name");
        txtSearch.setToolTipText("");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        jLabel7.setText("ID:");

        javax.swing.GroupLayout panelContainerLayout = new javax.swing.GroupLayout(panelContainer);
        panelContainer.setLayout(panelContainerLayout);
        panelContainerLayout.setHorizontalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContainerLayout.createSequentialGroup()
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQuantity)
                            .addComponent(txtPrice, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtName)
                            .addComponent(cmbCategory, 0, 378, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContainerLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnBrowse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContainerLayout.createSequentialGroup()
                                .addComponent(txtSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelContainerLayout.createSequentialGroup()
                                .addComponent(spinnerId, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 765, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(panelContainerLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(104, 104, 104))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMinimize)
                .addGap(18, 18, 18)
                .addComponent(lblClose)
                .addContainerGap())
        );
        panelContainerLayout.setVerticalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContainerLayout.createSequentialGroup()
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spinnerId, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtQuantity)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPrice)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelContainerLayout.createSequentialGroup()
                                .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelContainerLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
        );

        txtSearch.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1334, 703));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizeMouseClicked

        this.setState(ICONIFIED);
    }//GEN-LAST:event_lblMinimizeMouseClicked

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked

        AdminPage adminPage = new AdminPage();
        adminPage.setVisible(true);
        adminPage.pack();
        this.dispose();
    }//GEN-LAST:event_lblCloseMouseClicked

    private void txtQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantityActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed

        if (Integer.valueOf(spinnerId.getValue().toString()) > 0) {
            Integer id = Integer.valueOf(spinnerId.getValue().toString());

            String deleteQuery = "DELETE FROM `menus` WHERE `id`=?";

            try {

                PreparedStatement ps = MyConnection.getConnection().prepareStatement(deleteQuery);
                ps.setInt(1, id);

                // show a confirmation box before deleting the product
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product ?", "Remove Product", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (ps.executeUpdate() > 0) {
                        showProductsInTable();
                        JOptionPane.showMessageDialog(null, "Product Deleted Successfully", "Remove Product", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("Product Deleted");
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Product Not Deleted, Make Sure The ID is Valid", "Remove Product", JOptionPane.ERROR_MESSAGE);
                        //System.out.println("Some Error Message Here");  
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(ManageProductsForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Product Not Deleted, Make Sure The ID is Valid", "Remove Product", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed

        if (checkEmptyFields()) {
            String search = txtSearch.getText().trim();
            String selectQuery = "SELECT * FROM `menus` WHERE `name` LIKE '%" + search + "%'";

            try {
                Statement st = MyConnection.getConnection().createStatement();
                ResultSet rs = st.executeQuery(selectQuery);

                if (rs.next()) {
                    // Update the fields with the search results
                    txtName.setText(rs.getString("name"));
                    cmbCategory.setSelectedItem(rs.getString("category"));
                    txtQuantity.setText(rs.getString("quantity"));
                    txtPrice.setText(String.valueOf(rs.getDouble("price")));
                    txtImagePath.setText(rs.getString("image"));
                    displayImage(rs.getString("image"), lblImage, 'a');

                    // Select the searched item in the table
                    selectSearchedItem(search);
                } else {
                    JOptionPane.showMessageDialog(null, "No Product With This Name");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManageProductsForm.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error connecting to the database.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a search term.");
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // Insert query: INSERT INTO `products`(`name`, `category`, `quantity`, `price`, `image`) VALUES (?,?,?,?,?)

        if (checkEmptyFields()) {
            String name = txtName.getText();
            String category = cmbCategory.getSelectedItem().toString();
            Integer quantity = Integer.valueOf(txtQuantity.getText());
            Double price = Double.valueOf(txtPrice.getText());
            String img = txtImagePath.getText();

            String insertQuery = "INSERT INTO `menus`(`name`, `category`, `quantity`, `price`, `image`) VALUES (?,?,?,?,?)";

            try {

                PreparedStatement ps = MyConnection.getConnection().prepareStatement(insertQuery);
                ps.setString(1, name);
                ps.setString(2, category);
                ps.setInt(3, quantity);
                ps.setDouble(4, price);
                ps.setString(5, img);

                if (ps.executeUpdate() > 0) {
                    showProductsInTable();
                    JOptionPane.showMessageDialog(null, "New Product Added Successfully", "Add Product", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("New Product Added");
                } else {
                    JOptionPane.showMessageDialog(null, "Product Not Added", "Add Product", JOptionPane.ERROR_MESSAGE);

                }

            } catch (SQLException ex) {
                Logger.getLogger(ManageProductsForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Add Product", JOptionPane.ERROR_MESSAGE);
            System.out.println("One Or More Fields Are Empty");
        }


    }//GEN-LAST:event_btnAddActionPerformed

    private void tblProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductsMouseClicked

        int index = tblProducts.getSelectedRow();
        showUserData(index);

    }//GEN-LAST:event_tblProductsMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int saveEdit = JOptionPane.showConfirmDialog(null, "Save Edit?");
        if (checkEmptyFields()) {
            Integer id = Integer.valueOf(spinnerId.getValue().toString());
            String name = txtName.getText();
            String category = cmbCategory.getSelectedItem().toString();
            Integer quantity = Integer.valueOf(txtQuantity.getText());
            Double price = Double.valueOf(txtPrice.getText());
            String img = txtImagePath.getText();

            String updateQuery = "UPDATE `menus` SET `name`=?,`category`=?,`quantity`=?,`price`=?,`image`=? WHERE `id`=?";
            if (saveEdit == JOptionPane.YES_OPTION) {
                try {

                    PreparedStatement ps = MyConnection.getConnection().prepareStatement(updateQuery);
                    ps.setString(1, name);
                    ps.setString(2, category);
                    ps.setInt(3, quantity);
                    ps.setDouble(4, price);
                    ps.setString(5, img);
                    ps.setInt(6, id);

                    if (ps.executeUpdate() > 0) {
                        showProductsInTable();
                        JOptionPane.showMessageDialog(null, "Product Updated", "Edit Product", JOptionPane.INFORMATION_MESSAGE);
                        //System.out.println("Menu Updated");
                    } else {
                        JOptionPane.showMessageDialog(null, "Product Not Updated", "Edit Product", JOptionPane.ERROR_MESSAGE);
                        //System.out.println("Some Error Message Here");  
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ManageProductsForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Edit Product", JOptionPane.ERROR_MESSAGE);
            System.out.println("One Or More Fields Are Empty");
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed

        JFileChooser filechooser = new JFileChooser();
        filechooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        FileNameExtensionFilter filter = new FileNameExtensionFilter("*images", ".png", "jpg", ".jpeg");
        filechooser.addChoosableFileFilter(filter);

        if (filechooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedImage = filechooser.getSelectedFile();
            String image = selectedImage.getAbsolutePath();
            displayImage(image, lblImage, 'a');
            txtImagePath.setText(image);
            System.out.println(image);
        } else {
            System.out.println("no file selected");
        }

    }//GEN-LAST:event_btnBrowseActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed

        position = 0;
        showUserData(position);
        tblProducts.setRowSelectionInterval(position, position);

    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed

        position--;

        if (position < 0) {
            position = 0;
        }

        showUserData(position);
        tblProducts.setRowSelectionInterval(position, position);

    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed

        position++;
        if (position > menuArray.size() - 1) {
            position = menuArray.size() - 1;
        }
        showUserData(position);
        tblProducts.setRowSelectionInterval(position, position);

    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed

        position = getMenuList().size() - 1;
        showUserData(position);
        tblProducts.setRowSelectionInterval(position, position);

    }//GEN-LAST:event_btnLastActionPerformed

    private void txtQuantityKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantityKeyTyped
        // allow only numbers
        // or you can user jspinner
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtQuantityKeyTyped

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyTyped

    }//GEN-LAST:event_txtPriceKeyTyped

    private void txtPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyReleased

        try {
            Double.valueOf(txtPrice.getText());
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            txtPrice.setText("");
        }

    }//GEN-LAST:event_txtPriceKeyReleased

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManageProductsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageProductsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageProductsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageProductsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageProductsForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSearch;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JPanel panelContainer;
    private javax.swing.JSpinner spinnerId;
    private javax.swing.JTable tblProducts;
    private javax.swing.JTextField txtImagePath;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
