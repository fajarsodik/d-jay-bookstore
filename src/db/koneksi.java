/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author CLIENT 6
 */
public class koneksi {
    private static Connection koneksi;
    
    public static Connection getkoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost/toko-djay";
                String user = "root";
                String pass = "";
                
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                
                koneksi = (Connection) DriverManager.getConnection(url, user, pass);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "EROR" + e.getMessage());
            }
        }
        
        return koneksi;
    }
}
