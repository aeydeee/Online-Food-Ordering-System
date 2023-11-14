package com.aeydeee.loginRegister;

import com.aeydeee.database.MyConnection;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Register extends javax.swing.JFrame {

    public Register() {
        initComponents();
        this.setLocationRelativeTo(null);
        txtImagePath.setPreferredSize(new Dimension(64, 39));
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

        background = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblMinimize = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        btnRegister = new javax.swing.JButton();
        lblLogin = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        txtLastName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtRetypePassword = new javax.swing.JPasswordField();
        birthdate = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtHouseNo = new javax.swing.JTextField();
        txtBrgy = new javax.swing.JTextField();
        txtCity = new javax.swing.JTextField();
        txtZip = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtImagePath = new javax.swing.JTextField();
        btnBrowse = new javax.swing.JButton();
        lblImage = new javax.swing.JLabel();
        cmbCategory = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        background.setBackground(new java.awt.Color(255, 245, 0));
        background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 10));

        jPanel1.setBackground(new java.awt.Color(25, 146, 69));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(31, 26, 23));
        jLabel1.setText("Please Register");

        lblMinimize.setFont(new java.awt.Font("Arial Black", 1, 60)); // NOI18N
        lblMinimize.setText("-");
        lblMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinimizeMouseClicked(evt);
            }
        });

        lblClose.setFont(new java.awt.Font("Arial Black", 1, 60)); // NOI18N
        lblClose.setText("x");
        lblClose.setAlignmentY(0.0F);
        lblClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClose.setIconTextGap(0);
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMinimize)
                .addGap(18, 18, 18)
                .addComponent(lblClose)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Abbey Medium Extended", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 69, 34));
        jLabel2.setText("First Name:");

        jLabel3.setFont(new java.awt.Font("Abbey Medium Extended", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 69, 34));
        jLabel3.setText("Last Name:");

        txtFirstName.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        txtFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFirstNameActionPerformed(evt);
            }
        });

        btnRegister.setBackground(new java.awt.Color(248, 34, 44));
        btnRegister.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        btnRegister.setText("REGISTER");
        btnRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegister.setMargin(new java.awt.Insets(10, 14, 3, 14));
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        lblLogin.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        lblLogin.setForeground(new java.awt.Color(71, 66, 8));
        lblLogin.setText("Click here to Login");
        lblLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoginMouseClicked(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(248, 34, 44));
        btnCancel.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        btnCancel.setText("CANCEL");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.setMargin(new java.awt.Insets(10, 14, 3, 14));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        txtLastName.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        txtLastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLastNameActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Abbey Medium Extended", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 69, 34));
        jLabel7.setText("Username:");

        txtUsername.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Abbey Medium Extended", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 69, 34));
        jLabel8.setText("Email:");

        txtEmail.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Abbey Medium Extended", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 69, 34));
        jLabel9.setText("Password:");

        jLabel10.setFont(new java.awt.Font("Abbey Medium Extended", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 69, 34));
        jLabel10.setText("Birthdate:");

        jLabel11.setFont(new java.awt.Font("Abbey Medium Extended", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 69, 34));
        jLabel11.setText("Retype Pass:");

        txtPassword.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        txtPassword.setPreferredSize(new java.awt.Dimension(90, 38));

        txtRetypePassword.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        txtRetypePassword.setPreferredSize(new java.awt.Dimension(90, 38));

        birthdate.setFocusable(false);
        birthdate.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        birthdate.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                birthdateComponentShown(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Abbey Medium Extended", 1, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 69, 34));
        jLabel12.setText("House No.:");

        jLabel13.setFont(new java.awt.Font("Abbey Medium Extended", 1, 36)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 69, 34));
        jLabel13.setText("Barangay:");

        jLabel14.setFont(new java.awt.Font("Abbey Medium Extended", 1, 36)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 69, 34));
        jLabel14.setText("City/Municipality:");

        jLabel15.setFont(new java.awt.Font("Abbey Medium Extended", 1, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 69, 34));
        jLabel15.setText("Zip Code:");

        txtHouseNo.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        txtHouseNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHouseNoActionPerformed(evt);
            }
        });
        txtHouseNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHouseNoKeyTyped(evt);
            }
        });

        txtBrgy.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        txtBrgy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBrgyActionPerformed(evt);
            }
        });

        txtCity.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        txtCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCityActionPerformed(evt);
            }
        });

        txtZip.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        txtZip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtZipActionPerformed(evt);
            }
        });
        txtZip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtZipKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Abbey Medium Extended", 1, 36)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 69, 34));
        jLabel16.setText("Profile Picture:");

        txtImagePath.setEnabled(false);
        txtImagePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImagePathActionPerformed(evt);
            }
        });

        btnBrowse.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnBrowse.setText("BROWSE");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        lblImage.setBackground(new java.awt.Color(204, 204, 204));
        lblImage.setOpaque(true);
        lblImage.setPreferredSize(new java.awt.Dimension(41, 41));

        cmbCategory.setBackground(new java.awt.Color(0, 255, 102));
        cmbCategory.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "customer" }));
        cmbCategory.setEnabled(false);
        cmbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel7)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12)
                                .addComponent(jLabel13)
                                .addComponent(jLabel14)
                                .addComponent(jLabel15)
                                .addComponent(jLabel16))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(0, 170, Short.MAX_VALUE)
                                .addComponent(lblLogin)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                .addGap(5, 5, 5)
                                .addComponent(btnRegister))
                            .addComponent(txtFirstName)
                            .addComponent(txtLastName)
                            .addComponent(txtUsername)
                            .addComponent(txtEmail)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtRetypePassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(birthdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtHouseNo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtBrgy, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCity, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtZip, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(txtImagePath)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel11))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtRetypePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(birthdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtHouseNo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtBrgy, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtZip, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegister)
                    .addComponent(btnCancel)
                    .addComponent(lblLogin))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFirstNameActionPerformed

    private void txtLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLastNameActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed

        String fname = txtFirstName.getText();
        String lname = txtLastName.getText();
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String pass = String.valueOf(txtPassword.getPassword());
        String rePass = String.valueOf(txtRetypePassword.getPassword());
        String bdate = null;
        String houseNo = txtHouseNo.getText();
        String brgy = txtBrgy.getText();
        String city = txtCity.getText();
        String zip = txtZip.getText();
        String imagePath = txtImagePath.getText().trim();
        String category = cmbCategory.getSelectedItem().toString();

        if (username.isEmpty() || fname.isEmpty() || lname.isEmpty() || brgy.isEmpty() || city.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please make sure to fill-up all");
        } else if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Add a Password");
        } else if (!rePass.equals(pass)) {
            JOptionPane.showMessageDialog(null, "Retype the password again");
        } else if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email address.");
        } else if (checkUsername(username)) {
            JOptionPane.showMessageDialog(null, "This Username already exist.");
        } else {
            if (birthdate.getDate() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                bdate = dateFormat.format(birthdate.getDate());
            }
            PreparedStatement ps;
            String query = "INSERT INTO `users`(`user_fname`, `user_lname`, `user_username`, `user_email`, `user_password`, `user_birthdate`, `user_house_no`, `user_barangay`, `user_city`, `user_zip`, `image_path`, `category`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            try {
                ps = MyConnection.getConnection().prepareStatement(query);

                ps.setString(1, fname);
                ps.setString(2, lname);
                ps.setString(3, username);
                ps.setString(4, email);
                String hashedPass = hashPassword(pass); // Hash the password
                ps.setString(5, hashedPass);

                if (bdate != null) {
                    ps.setString(6, bdate);
                } else {
                    ps.setNull(6, 0);
                }

                ps.setString(7, houseNo);
                ps.setString(8, brgy);
                ps.setString(9, city);
                ps.setString(10, zip);
                ps.setString(11, imagePath);
                ps.setString(12, category);

                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "New User Added");
                }
            } catch (SQLException e) {
                if (e.getMessage().contains("No value specified for parameter")) {
                    JOptionPane.showMessageDialog(null, "Please make sure to fill-up all");
                } else if (e.getMessage().contains("Data truncation: Incorrect integer value")) {
                    JOptionPane.showMessageDialog(null, "House No. & Zip Code must be a number");
                }
            }
        }
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void birthdateComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_birthdateComponentShown

    }//GEN-LAST:event_birthdateComponentShown

    private void lblLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginMouseClicked
        Login login = new Login();
        login.setVisible(true);
        login.pack();
        login.setLocationRelativeTo(null);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_lblLoginMouseClicked

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        int closeBtn = JOptionPane.showConfirmDialog(null, "Sure u wanna close the app?");
        if (closeBtn == JOptionPane.YES_OPTION) {
            System.exit(0);
        }

    }//GEN-LAST:event_lblCloseMouseClicked

    private void lblMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizeMouseClicked

        this.setState(ICONIFIED);

    }//GEN-LAST:event_lblMinimizeMouseClicked

    private void txtHouseNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHouseNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHouseNoActionPerformed

    private void txtBrgyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBrgyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBrgyActionPerformed

    private void txtCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCityActionPerformed

    private void txtZipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtZipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtZipActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        int cancelBtn = JOptionPane.showConfirmDialog(null, "Cancel Register?");
        if (cancelBtn == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtImagePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImagePathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImagePathActionPerformed

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

    private void txtHouseNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHouseNoKeyTyped

        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }

    }//GEN-LAST:event_txtHouseNoKeyTyped

    private void txtZipKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtZipKeyTyped

        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }

    }//GEN-LAST:event_txtZipKeyTyped

    private void cmbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCategoryActionPerformed

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean checkUsername(String username) {
        PreparedStatement ps;
        ResultSet rs;
        boolean checkUser = false;
        String query = "SELECT * FROM `users` WHERE `user_username` = ?";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);

            ps.setString(1, username);

            rs = ps.executeQuery();

            if (rs.next()) {
                checkUser = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkUser;
    }

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
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Register().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private com.toedter.calendar.JDateChooser birthdate;
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnRegister;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JTextField txtBrgy;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtHouseNo;
    private javax.swing.JTextField txtImagePath;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtRetypePassword;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtZip;
    // End of variables declaration//GEN-END:variables
}
