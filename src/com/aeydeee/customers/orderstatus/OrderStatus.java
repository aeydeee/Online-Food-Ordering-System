
package com.aeydeee.customers.orderstatus;

import com.aeydeee.database.MyConnection;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;


public class OrderStatus extends javax.swing.JFrame {
    private int userId;

    public OrderStatus(int userId) {
        initComponents();
        this.userId = userId;
        showProductsInTable();
    }
    
    ArrayList<Order> menuArray = new ArrayList<>();

    public ArrayList<Order> getOrdersList(int userId) {
    ArrayList<Order> list = new ArrayList<>();
    String selectQuery = "SELECT * FROM `orders` WHERE user_id = ? ORDER BY date_ordered ASC";

    PreparedStatement preparedStatement;
    ResultSet rs;

    try {
        preparedStatement = MyConnection.getConnection().prepareStatement(selectQuery);
        preparedStatement.setInt(1, userId);
        rs = preparedStatement.executeQuery();

        Order order;

        while (rs.next()) {
            order = new Order(
             rs.getInt("id"),
                rs.getString("item_name"),
                rs.getInt("quantity"),
                rs.getInt("total_price"),
                rs.getString("date_ordered"),
                rs.getInt("order_status")
            );
            list.add(order);
        }

    } catch (SQLException ex) {
        Logger.getLogger(OrderStatus.class.getName()).log(Level.SEVERE, null, ex);
    }

    menuArray = list;
    return list;
}


    public void showProductsInTable() {
        ArrayList<Order> productsList = getOrdersList(userId);
        DefaultTableModel model = (DefaultTableModel) tblOrders.getModel();

        // clear jtable
        model.setRowCount(0);

        Object[] row = new Object[5];// 6 the number of columns

        for (int i = 0; i < productsList.size(); i++) {
            row[0] = productsList.get(i).getName();
            row[1] = productsList.get(i).getQuantity();
            row[2] = productsList.get(i).getTotalPrice();
            row[3] = productsList.get(i).getDateOrdered();
            row[4] = productsList.get(i).getOrderStatus();

            model.addRow(row);
        }
    }
    
   private void updateOrderStatus() {
    int[] selectedRows = tblOrders.getSelectedRows();

    if (selectedRows.length > 0) {
        for (int selectedRow : selectedRows) {
            int orderId = menuArray.get(selectedRow).getId();
            int currentStatus = menuArray.get(selectedRow).getOrderStatus();
            int newStatus = (currentStatus == 0) ? 1 : 0;

            // Update the order status in the database
            String updateQuery = "UPDATE orders SET order_status = ? WHERE id = ?";
            try {
                PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(updateQuery);
                preparedStatement.setInt(1, newStatus);
                preparedStatement.setInt(2, orderId);
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(OrderStatus.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Update the order status in the ArrayList
            menuArray.get(selectedRow).setOrderStatus(newStatus);
        }

        // Refresh the table to reflect the changes
        showProductsInTable();
    }
}






    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrders = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnDelivered = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        tblOrders.setBackground(new java.awt.Color(255, 153, 204));
        tblOrders.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "item_name", "quantity", "total_price", "date_ordered", "order_status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrders.setRowHeight(40);
        tblOrders.setSelectionBackground(new java.awt.Color(102, 0, 204));
        tblOrders.setSelectionForeground(new java.awt.Color(204, 204, 255));
        tblOrders.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblOrders);

        jPanel2.setBackground(new java.awt.Color(0, 0, 204));

        jLabel1.setFont(new java.awt.Font("SF Willamette", 0, 60)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ORDER STATUS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel2.setText("0 = PENDING / DELIVERING");

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel3.setText("1 = FINISHED / DELIVERED");

        btnDelivered.setBackground(new java.awt.Color(255, 204, 204));
        btnDelivered.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        btnDelivered.setText("CHANGE STATUS");
        btnDelivered.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeliveredActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDelivered)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnDelivered, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeliveredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeliveredActionPerformed
        updateOrderStatus();
    }//GEN-LAST:event_btnDeliveredActionPerformed

   
    public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details, see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(OrderStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(OrderStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(OrderStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(OrderStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            // Assuming you have the user ID available (replace 123 with the actual user ID)
            int userId = 123;
            new OrderStatus(userId).setVisible(true);
        }
    });
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelivered;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblOrders;
    // End of variables declaration//GEN-END:variables
}
