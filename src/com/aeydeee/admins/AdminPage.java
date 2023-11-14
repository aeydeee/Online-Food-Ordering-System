package com.aeydeee.admins;

import com.aeydeee.database.MyConnection;
import com.aeydeee.loginRegister.Login;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Logger;
import javax.swing.JFrame;

public final class AdminPage extends javax.swing.JFrame {

    int position = 0;

    public AdminPage() {
        initComponents();
        // populate the jtable with products
        showUsersInTable();

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
        spinnerId.setValue(UsersArray.get(index).getId());
        txtFname.setText(UsersArray.get(index).getfName());
        txtLname.setText(UsersArray.get(index).getlName());
        cmbCategory.setSelectedItem(UsersArray.get(index).getCategory());
        txtUsername.setText(UsersArray.get(index).getUsername());
        txtEmail.setText(UsersArray.get(index).getEmail());
        txtBirthday.setDate(parseDate(UsersArray.get(index).getbDay()));
        txtBarangay.setText(UsersArray.get(index).getBrgy());
        txtHouseNo.setText(Integer.toString(UsersArray.get(index).getHouseNo()));
        txtCity.setText(UsersArray.get(index).getCity());
        txtZip.setText(String.valueOf(UsersArray.get(index).getZip()));
        txtImagePath.setText(UsersArray.get(index).getImagePath());

        displayImage(UsersArray.get(index).getImagePath(), lblImage, 'a');
    }

    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateStr);
        } catch (ParseException ex) {
            ex.printStackTrace(); // Handle the exception appropriately
            return null;
        }
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
        txtFname.setText("");
        txtUsername.setText("");
        cmbCategory.setSelectedIndex(0);
        txtEmail.setText("");
        txtImagePath.setText("");
        lblImage.setIcon(null);
    }

    ArrayList<UserAdmin> UsersArray = new ArrayList<>();

    public ArrayList<UserAdmin> getUsersList() {
        ArrayList<UserAdmin> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM `users`";

        Statement st;
        ResultSet rs;

        try {
            st = MyConnection.getConnection().createStatement();
            rs = st.executeQuery(selectQuery);
            UserAdmin userAdmin;

            while (rs.next()) {
                userAdmin = new UserAdmin(rs.getInt("user_id"), rs.getString("user_fname"),
                        rs.getString("user_lname"), rs.getString("category"), rs.getString("user_username"),
                        rs.getString("user_email"), rs.getString("user_birthdate"),
                        rs.getString("user_barangay"), rs.getInt("user_house_no"),
                        rs.getString("user_city"), rs.getInt("user_zip"),
                        rs.getString("image_path"));
                list.add(userAdmin);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        UsersArray = list;
        return list;

    }

    public void showUsersInTable() {
        ArrayList<UserAdmin> usersList = getUsersList();
        DefaultTableModel model = (DefaultTableModel) tblUsers.getModel();

        // clear jtable
        model.setRowCount(0);

        Object[] row = new Object[12];// 6 the number of columns

        for (int i = 0; i < usersList.size(); i++) {
            row[0] = usersList.get(i).getId();
            row[1] = usersList.get(i).getfName();
            row[2] = usersList.get(i).getlName();
            row[3] = usersList.get(i).getCategory();
            row[4] = usersList.get(i).getUsername();
            row[5] = usersList.get(i).getEmail();
            row[6] = usersList.get(i).getbDay();
            row[7] = usersList.get(i).getHouseNo();
            row[8] = usersList.get(i).getBrgy();
            row[9] = usersList.get(i).getCity();
            row[10] = usersList.get(i).getZip();
            row[11] = usersList.get(i).getImagePath();

            model.addRow(row);
        }

    }

    public boolean checkEmptyFields() {
        String name = txtFname.getText().trim();
        String quantity = txtUsername.getText().trim();
        String price = txtEmail.getText().trim();
        String imagePath = txtImagePath.getText().trim();

        return !(name.equals("") || quantity.equals("") || price.equals("") || imagePath.equals("")); // if one or more fields are empty return false else return true

    }

    private void selectSearchedItem(String search) {
        for (int i = 0; i < tblUsers.getRowCount(); i++) {
            Object value = tblUsers.getValueAt(i, 1); // Assuming the name is in the second column (index 1)
            if (value != null && value.toString().toLowerCase().contains(search.toLowerCase())) {
                tblUsers.setRowSelectionInterval(i, i);
                tblUsers.scrollRectToVisible(tblUsers.getCellRect(i, 0, true));
                break;
            }
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
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
        tblUsers = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        spinnerId = new javax.swing.JSpinner();
        cmbCategory = new javax.swing.JComboBox<>();
        txtFname = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
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
        jLabel8 = new javax.swing.JLabel();
        txtLname = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtHouseNo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtBarangay = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCity = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtZip = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtBirthday = new com.toedter.calendar.JDateChooser();
        txtPassword = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnManageProducts = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelContainer.setBackground(new java.awt.Color(0, 102, 102));
        panelContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));
        panelContainer.setEnabled(false);

        lblMinimize.setFont(new java.awt.Font("Arial Black", 1, 60)); // NOI18N
        lblMinimize.setForeground(new java.awt.Color(204, 255, 204));
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

        tblUsers.setBackground(new java.awt.Color(0, 204, 204));
        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "First Name", "Last Name", "Category", "Username", "Email", "Birthday", "House No.", "Barangay", "City", "Zip", "Image"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsers.setRowHeight(53);
        tblUsers.setSelectionBackground(new java.awt.Color(0, 0, 0));
        tblUsers.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsers);

        jLabel1.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SEARCH:");

        jLabel2.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("First Name:");

        jLabel3.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Category:");

        jLabel4.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Username:");

        jLabel5.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Email:");

        jLabel6.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Image:");

        spinnerId.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        spinnerId.setEnabled(false);
        spinnerId.setFocusable(false);

        cmbCategory.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "customer", "admin" }));

        txtFname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFnameActionPerformed(evt);
            }
        });

        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsernameKeyTyped(evt);
            }
        });

        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmailKeyTyped(evt);
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

        btnSearch.setBackground(new java.awt.Color(0, 204, 204));
        btnSearch.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        btnSearch.setText("SEARCH");
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnBrowse.setBackground(new java.awt.Color(0, 204, 204));
        btnBrowse.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
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

        jLabel7.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ID:");

        jLabel8.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Last Name:");

        jLabel9.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Birthday:");

        jLabel10.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("House No.:");

        txtHouseNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHouseNoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHouseNoKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Barangay:");

        txtBarangay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBarangayKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBarangayKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("City:");

        txtCity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCityKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCityKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Zip:");

        txtZip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtZipKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtZipKeyTyped(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(204, 255, 204));
        jLabel14.setFont(new java.awt.Font("Constantia", 1, 48)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Admin Page");
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel14.setOpaque(true);

        txtBirthday.setFont(new java.awt.Font("Segoe Script", 1, 14)); // NOI18N

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPasswordKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Password:");

        btnManageProducts.setBackground(new java.awt.Color(153, 0, 0));
        btnManageProducts.setFont(new java.awt.Font("Consolas", 1, 36)); // NOI18N
        btnManageProducts.setForeground(new java.awt.Color(255, 255, 255));
        btnManageProducts.setText("CLICK HERE TO MANAGE PRODUCTS");
        btnManageProducts.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnManageProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageProductsActionPerformed(evt);
            }
        });

        btnLogout.setBackground(new java.awt.Color(0, 0, 204));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("LOGOUT");
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelContainerLayout = new javax.swing.GroupLayout(panelContainer);
        panelContainer.setLayout(panelContainerLayout);
        panelContainerLayout.setHorizontalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContainerLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelContainerLayout.createSequentialGroup()
                                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel15))
                                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelContainerLayout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelContainerLayout.createSequentialGroup()
                                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSearch))
                                            .addComponent(spinnerId, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(93, 93, 93))
                                    .addGroup(panelContainerLayout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtLname, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(panelContainerLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtBirthday, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelContainerLayout.createSequentialGroup()
                                        .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(227, 227, 227))
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContainerLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel12))
                                    .addGroup(panelContainerLayout.createSequentialGroup()
                                        .addGap(105, 105, 105)
                                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtHouseNo, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtBarangay, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContainerLayout.createSequentialGroup()
                                                .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel13)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtZip, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panelContainerLayout.createSequentialGroup()
                                            .addGap(35, 35, 35)
                                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panelContainerLayout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                    .addGroup(panelContainerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContainerLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lblMinimize)
                        .addGap(18, 18, 18)
                        .addComponent(lblClose))
                    .addGroup(panelContainerLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(panelContainerLayout.createSequentialGroup()
                                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnManageProducts, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelContainerLayout.createSequentialGroup()
                                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        panelContainerLayout.setVerticalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addComponent(btnManageProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spinnerId, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLname, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtEmail)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHouseNo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBarangay, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtZip, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelContainerLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69))
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
            .addComponent(panelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 806, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1674, 806));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizeMouseClicked

        this.setState(ICONIFIED);
    }//GEN-LAST:event_lblMinimizeMouseClicked

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked

        int closeBtn = JOptionPane.showConfirmDialog(null, "Sure u wanna close the app?");
        if (closeBtn == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_lblCloseMouseClicked

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed

        if (Integer.parseInt(spinnerId.getValue().toString()) > 0) {
            Integer userId = Integer.valueOf(spinnerId.getValue().toString());

            String deleteQuery = "DELETE FROM `users` WHERE `user_id`=?";

            try {
                PreparedStatement ps = MyConnection.getConnection().prepareStatement(deleteQuery);
                ps.setInt(1, userId);

                // Show a confirmation box before deleting the user
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Remove User", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (ps.executeUpdate() > 0) {
                        showUsersInTable();
                        JOptionPane.showMessageDialog(null, "User Deleted Successfully", "Remove User", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("User Deleted");
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "User Not Deleted, Make Sure The ID is Valid", "Remove User", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "User Not Deleted, Make Sure The ID is Valid", "Remove User", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed

        if (checkEmptyFields()) {
            String search = txtSearch.getText().trim();
            String selectQuery = "SELECT * FROM `users` WHERE `user_fname` LIKE '%" + search + "%' OR `user_lname` LIKE '%" + search + "%' OR `user_username` LIKE '%" + search + "%'";

            try {
                Statement st = MyConnection.getConnection().createStatement();
                ResultSet rs = st.executeQuery(selectQuery);

                if (rs.next()) {
                    // Update the fields with the search results
                    spinnerId.setValue(rs.getInt("user_id"));
                    txtFname.setText(rs.getString("user_fname"));
                    txtLname.setText(rs.getString("user_lname"));
                    cmbCategory.setSelectedItem(rs.getString("category"));
                    txtUsername.setText(rs.getString("user_username"));
                    txtEmail.setText(rs.getString("user_email"));
                    txtPassword.setText(""); // Don't display hashed password in the UI
                    txtBirthday.setDate(rs.getDate("user_birthdate"));
                    txtHouseNo.setText(String.valueOf(rs.getInt("user_house_no")));
                    txtBarangay.setText(rs.getString("user_barangay"));
                    txtCity.setText(rs.getString("user_city"));
                    txtZip.setText(String.valueOf(rs.getInt("user_zip")));
                    txtImagePath.setText(rs.getString("image_path"));
                    displayImage(rs.getString("image_path"), lblImage, 'a');

                    // Select the searched item in the table
                    selectSearchedItem(search);
                } else {
                    JOptionPane.showMessageDialog(null, "No User With This Name");
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error connecting to the database.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a search term.");
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // Insert query: INSERT INTO `products`(`name`, `category`, `quantity`, `price`, `image`) VALUES (?,?,?,?,?)

        if (checkEmptyFields()) {
            String fName = txtFname.getText();
            String lName = txtLname.getText();
            String category = cmbCategory.getSelectedItem().toString();
            String username = txtUsername.getText();
            String email = txtEmail.getText();
            String pass = txtPassword.getText();
            Date birthday = txtBirthday.getDate();
            int houseNo = Integer.parseInt(txtHouseNo.getText());
            String barangay = txtBarangay.getText();
            String city = txtCity.getText();
            int zip = Integer.parseInt(txtZip.getText());
            String img = txtImagePath.getText();

            String insertQuery = "INSERT INTO `users`(`user_fname`, `user_lname`, `category`, `user_username`, `user_email`, `user_password`, `user_birthdate`, `user_house_no`, `user_barangay`, `user_city`, `user_zip`, `image_path`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            try {
                // Check if password is empty
                if (pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a password.", "Add User", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if username already exists
                String checkUsernameQuery = "SELECT * FROM `users` WHERE `user_username` = ?";
                PreparedStatement psCheckUsername = MyConnection.getConnection().prepareStatement(checkUsernameQuery);
                psCheckUsername.setString(1, username);
                ResultSet rs = psCheckUsername.executeQuery();

                if (rs.next()) { // Username already exists
                    JOptionPane.showMessageDialog(null, "Username already exists. Please choose another username.", "Add User", JOptionPane.ERROR_MESSAGE);
                } else { // Username does not exist
                    // Hash the password
                    String hashedPass = hashPassword(pass);

                    // Prepare and execute the insert query
                    PreparedStatement psInsert = MyConnection.getConnection().prepareStatement(insertQuery);
                    psInsert.setString(1, fName);
                    psInsert.setString(2, lName);
                    psInsert.setString(3, category);
                    psInsert.setString(4, username);
                    psInsert.setString(5, email);
                    psInsert.setString(6, hashedPass);

                    // Convert java.util.Date to java.sql.Date
                    java.util.Date utilDate = birthday;
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    psInsert.setDate(7, sqlDate);

                    psInsert.setInt(8, houseNo);
                    psInsert.setString(9, barangay);
                    psInsert.setString(10, city);
                    psInsert.setInt(11, zip);
                    psInsert.setString(12, img);

                    if (psInsert.executeUpdate() > 0) {
                        showUsersInTable();
                        JOptionPane.showMessageDialog(null, "New User Added Successfully", "Add Users", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("New User Added");
                    } else {
                        JOptionPane.showMessageDialog(null, "User Not Added", "Add User", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Add User", JOptionPane.ERROR_MESSAGE);
            System.out.println("One Or More Fields Are Empty");
        }


    }//GEN-LAST:event_btnAddActionPerformed

    private void tblUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsersMouseClicked
        txtPassword.setText("");
        int index = tblUsers.getSelectedRow();
        showUserData(index);

    }//GEN-LAST:event_tblUsersMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int saveEdit = JOptionPane.showConfirmDialog(null, "Save Edit?");
        if (checkEmptyFields()) {
            Integer userId = Integer.valueOf(spinnerId.getValue().toString());
            String fName = txtFname.getText();
            String lName = txtLname.getText();
            String category = cmbCategory.getSelectedItem().toString();
            String username = txtUsername.getText();
            String email = txtEmail.getText();
            String pass = txtPassword.getText();
            Date birthday = txtBirthday.getDate();
            int houseNo = Integer.parseInt(txtHouseNo.getText());
            String barangay = txtBarangay.getText();
            String city = txtCity.getText();
            int zip = Integer.parseInt(txtZip.getText());
            String img = txtImagePath.getText();

            String updateQuery = "UPDATE `users` SET `user_fname`=?, `user_lname`=?, `category`=?, `user_username`=?, `user_email`=?, `user_password`=?, `user_birthdate`=?, `user_house_no`=?, `user_barangay`=?, `user_city`=?, `user_zip`=?, `image_path`=? WHERE `user_id`=?";

            if (saveEdit == JOptionPane.YES_OPTION) {
                try {
                    // Check if password is empty
                    if (pass.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter a password.", "Edit User", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Hash the password
                    String hashedPass = hashPassword(pass);

                    // Prepare and execute the update query
                    PreparedStatement psUpdate = MyConnection.getConnection().prepareStatement(updateQuery);
                    psUpdate.setString(1, fName);
                    psUpdate.setString(2, lName);
                    psUpdate.setString(3, category);
                    psUpdate.setString(4, username);
                    psUpdate.setString(5, email);
                    psUpdate.setString(6, hashedPass);

                    // Convert java.util.Date to java.sql.Date
                    java.util.Date utilDate = birthday;
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    psUpdate.setDate(7, sqlDate);

                    psUpdate.setInt(8, houseNo);
                    psUpdate.setString(9, barangay);
                    psUpdate.setString(10, city);
                    psUpdate.setInt(11, zip);
                    psUpdate.setString(12, img);
                    psUpdate.setInt(13, userId);

                    if (psUpdate.executeUpdate() > 0) {
                        showUsersInTable();
                        JOptionPane.showMessageDialog(null, "User Updated Successfully", "Edit User", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("User Updated");
                    } else {
                        JOptionPane.showMessageDialog(null, "User Not Updated", "Edit User", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Edit User", JOptionPane.ERROR_MESSAGE);
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
        tblUsers.setRowSelectionInterval(position, position);

    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed

        position--;

        if (position < 0) {
            position = 0;
        }

        showUserData(position);
        tblUsers.setRowSelectionInterval(position, position);

    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed

        position++;
        if (position > UsersArray.size() - 1) {
            position = UsersArray.size() - 1;
        }
        showUserData(position);
        tblUsers.setRowSelectionInterval(position, position);

    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed

        position = getUsersList().size() - 1;
        showUserData(position);
        tblUsers.setRowSelectionInterval(position, position);

    }//GEN-LAST:event_btnLastActionPerformed

    private void txtUsernameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyTyped

    }//GEN-LAST:event_txtUsernameKeyTyped

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyTyped

    }//GEN-LAST:event_txtEmailKeyTyped

    private void txtEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyReleased


    }//GEN-LAST:event_txtEmailKeyReleased

    private void txtHouseNoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHouseNoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHouseNoKeyReleased

    private void txtHouseNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHouseNoKeyTyped
        // allow only numbers
        // or you can user jspinner
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtHouseNoKeyTyped

    private void txtBarangayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBarangayKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBarangayKeyReleased

    private void txtBarangayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBarangayKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBarangayKeyTyped

    private void txtCityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCityKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCityKeyReleased

    private void txtCityKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCityKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCityKeyTyped

    private void txtZipKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtZipKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtZipKeyReleased

    private void txtZipKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtZipKeyTyped
        // allow only numbers
        // or you can user jspinner
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtZipKeyTyped

    private void txtFnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFnameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void txtPasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordKeyTyped

    private void btnManageProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageProductsActionPerformed
        int manageBtn = JOptionPane.showConfirmDialog(null, "Manage products?");
        if (manageBtn == JOptionPane.YES_OPTION) {
            ManageProductsForm manageProductsForm = new ManageProductsForm();
            manageProductsForm.setVisible(true);
            manageProductsForm.pack();
            this.dispose();
        }

    }//GEN-LAST:event_btnManageProductsActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int logoutBtn = JOptionPane.showConfirmDialog(null, "Logout?");
        if (logoutBtn == JOptionPane.YES_OPTION) {
            Login login = new Login();
            login.setVisible(true);
            login.pack();
            login.setLocationRelativeTo(null);
            login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.dispose();
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

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
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnManageProducts;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSearch;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JPanel panelContainer;
    private javax.swing.JSpinner spinnerId;
    private javax.swing.JTable tblUsers;
    private javax.swing.JTextField txtBarangay;
    private com.toedter.calendar.JDateChooser txtBirthday;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFname;
    private javax.swing.JTextField txtHouseNo;
    private javax.swing.JTextField txtImagePath;
    private javax.swing.JTextField txtLname;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtZip;
    // End of variables declaration//GEN-END:variables
}
