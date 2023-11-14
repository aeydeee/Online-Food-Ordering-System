/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aeydeee.database;

import java.sql.DriverManager;

/**
 *
 * @author aeyde
 */
public class MyConnection {

    public static java.sql.Connection getConnection() {
        java.sql.Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/food_ordering_system", "root", "");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return con;
    }
}
