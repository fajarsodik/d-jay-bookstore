/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import db.koneksi;
import db.session;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CLIENT 6
 */
public class formDistributor extends javax.swing.JInternalFrame {

    String hakakses = session.getAkses();
    /**
     * Creates new form formDistributor
     */
    public formDistributor() {
        initComponents();
        fillDataToTable();
        hakAkses(true);
    }
    
    private void hakAkses(boolean akses) {
        if (hakakses.equals("Admin")) {
            btnSimpan.setEnabled(akses);
            btnUbah.setEnabled(!akses);
            btnHapus.setEnabled(!akses);
        }
        else if(hakakses.equals("Kasir")) {
            btnTambah.setEnabled(akses);
            btnSimpan.setEnabled(!akses);
            btnUbah.setEnabled(!akses);
            btnHapus.setEnabled(!akses);
        }
    }

    private void fillDataToTable() {
        DefaultTableModel tableModel;
        tableModel = (DefaultTableModel) tableData.getModel();
        String query = "SELECT * FROM distributor";
        Statement statement;
        Connection connection;
        try {
            connection = koneksi.getkoneksi();
            statement = (Statement) connection.createStatement();
            ResultSet hasil = statement.executeQuery(query);
            while (hasil.next()) {
                String tb_kode = hasil.getString("id_distributor");
                String tb_nama = hasil.getString("nama_distributor");
                String tb_alamat = hasil.getString("alamat");
                String tb_telepon = hasil.getString("telepon");
                Object[] data = new Object[] {tb_kode, tb_nama, tb_alamat, tb_telepon};
                tableModel.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "EROR"  + e.getMessage());
        }
    }

    private void cari(String cariData) {
        if (cmbUrut.getSelectedItem().equals("Kode Distributor")) {
            String query = "SELECT * FROM distributor WHERE id_distributor = ?";
            Connection connection;
            PreparedStatement statement;
            try {
                connection = koneksi.getkoneksi();
                statement = (PreparedStatement) connection.prepareStatement(query);
                statement.setString(1, cariData);
                ResultSet hasil = statement.executeQuery();
                if (hasil.next()) {
                    txtKode.setText(hasil.getString("id_distributor"));
                    txtNama.setText(hasil.getString("nama_distributor"));
                    txtAlamat.setText(hasil.getString("alamat"));;
                    txtTelepon.setText(hasil.getString("telepon"));
                }
                else {
                    JOptionPane.showMessageDialog(this, "Data tak ada");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "EROR JUL" + e.getMessage());
            }
        }
        else if(cmbUrut.getSelectedItem().equals("Nama Distributor")) {
            String query = "SELECT * FROM distributor WHERE nama_distributor LIKE ?";
            Connection connection;
            PreparedStatement statement;
            try {
                connection = koneksi.getkoneksi();
                statement = (PreparedStatement) connection.prepareStatement(query);
                statement.setString(1, cariData);
                ResultSet hasil = statement.executeQuery();
                if (hasil.next()) {
                    txtKode.setText(hasil.getString("id_distributor"));
                    txtNama.setText(hasil.getString("nama_distributor"));
                    txtAlamat.setText(hasil.getString("alamat"));;
                    txtTelepon.setText(hasil.getString("telepon"));
                }
                else {
                    JOptionPane.showMessageDialog(this, "Data tak ada");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "EROR JUL" + e.getMessage());
            }
        }
    }

    private void tambah() {
        txtKode.setText("");
        txtAlamat.setText("");
        txtCari.setText("");
        txtTelepon.setText("");
        txtNama.setText("");
   }
    
    private void simpan (String nama, String alamat, String telepon) {
        String query = "INSERT INTO distributor (nama_distributor, alamat, telepon) "
                + " VALUES (?, ?, ?)";
        PreparedStatement statement;
        Connection connection;
        try {
            connection = koneksi.getkoneksi();
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, nama);
            statement.setString(2, alamat);
            statement.setString(3, telepon);
            int hasil = statement.executeUpdate();
            if (hasil == 1) {
                JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
                tambah();
                refreshDataTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"EROR DI SINI : " + e.getMessage());
        }
    }
    
    private void refreshDataTable() {
        DefaultTableModel tableModel;
        tableModel = (DefaultTableModel) tableData.getModel();
        int isiTabel = tableModel.getRowCount();
        for (int i = 0; i < isiTabel; i++) {
            tableModel.removeRow(0);
        }
        fillDataToTable();
    }
    
    private void ubah() {
        String query = "UPDATE distributor SET "
                + " nama_distributor = ?, "
                + "alamat = ?, "
                + "telepon = ? "
                + " WHERE id_distributor = ?";
        Connection connection;
        PreparedStatement statement;
        try {
            connection = koneksi.getkoneksi();
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, txtNama.getText());
            statement.setString(2, txtAlamat.getText());
            statement.setString(3, txtTelepon.getText());
            statement.setString(4, txtKode.getText());
            int hasil = statement.executeUpdate();
            if (hasil == 1) {
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate");
                tambah();
                refreshDataTable();
            }
            else {
                JOptionPane.showMessageDialog(this, "Ntah apa salahku");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"FAK" + e.getMessage());
        }
    }
    
    private void hapus() {
        String query = "DELETE FROM distributor WHERE id_distributor = ?";
        Connection connection;
        PreparedStatement statement;
        try {
            connection = koneksi.getkoneksi();
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, txtKode.getText());
            int hasil = statement.executeUpdate();
            if (hasil == 1) {
                JOptionPane.showMessageDialog(this, "Data Berhasil dihapus");
                tambah();
                refreshDataTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"EROR DI SINI : " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableData = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        cmbUrut = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        txtTelepon = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Master Data Distributor");

        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Distributor", "Nama Distributor", "Alamat", "Telepon"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableData);
        if (tableData.getColumnModel().getColumnCount() > 0) {
            tableData.getColumnModel().getColumn(0).setResizable(false);
            tableData.getColumnModel().getColumn(1).setResizable(false);
            tableData.getColumnModel().getColumn(2).setResizable(false);
            tableData.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setText("Cari Data");

        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariKeyPressed(evt);
            }
        });

        cmbUrut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kode Distributor", "Nama Distributor" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbUrut, 0, 176, Short.MAX_VALUE)
                    .addComponent(txtCari, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cmbUrut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(0, 153, 204));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(240, 240, 240));
        jLabel2.setText("Kode Distributor");

        txtKode.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(240, 240, 240));
        jLabel3.setText("Nama Distributor");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(240, 240, 240));
        jLabel4.setText("Alamat");

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane2.setViewportView(txtAlamat);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(240, 240, 240));
        jLabel5.setText("No Telepon");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                    .addComponent(txtKode)
                    .addComponent(txtNama)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTelepon))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(txtTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnRefresh.setText("Refresh Data");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnHapus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTambah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(73, 73, 73))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyPressed
        String cariData = txtCari.getText();
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            cari(cariData);
            if (hakakses.equals("Admin")) {
                hakAkses(false);
            }
            else if (hakakses.equals("Kasir")) {
                hakAkses(true);
            }
        }
    }//GEN-LAST:event_txtCariKeyPressed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        tambah();
        if (hakakses.equals("Admin")) {
            hakAkses(true);
        }
        else if(hakakses.equals("Kasir")) {
            hakAkses(true);
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        String nama = txtNama.getText();
        String alamat = txtAlamat.getText();
        String telepon = txtTelepon.getText();
        
        if (nama.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Harap Lengkapi data");
        }
        else if(alamat.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Harap Lengkapi data");
        }
        else if(telepon.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Harap Lengkapi data");
        }
        else {
            simpan(nama, alamat, telepon);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
       refreshDataTable();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        ubah();
        if (hakakses.equals("Admin")) {
            hakAkses(true);
        }
        else if(hakakses.equals("Kasir")) {
            hakAkses(true);
        }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void tableDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDataMouseClicked
        try {
            int row = tableData.rowAtPoint(evt.getPoint());
            
            String tb_kode = tableData.getValueAt(row, 0).toString();
            String tb_nama = tableData.getValueAt(row, 1).toString();
            String tb_alamat = tableData.getValueAt(row, 2).toString();
            String tb_telepon = tableData.getValueAt(row, 3).toString();
            
            txtKode.setText(String.valueOf(tb_kode));
            txtNama.setText(String.valueOf(tb_nama));
            txtAlamat.setText(String.valueOf(tb_alamat));
            txtTelepon.setText(String.valueOf(tb_telepon));
            
            if (hakakses.equals("Admin")) {
                hakAkses(false);
            }
            else if (hakakses.equals("Kasir")) {
                hakAkses(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tableDataMouseClicked

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapus();
        if (hakakses.equals("Admin")) {
            hakAkses(true);
        }
        else if(hakakses.equals("Kasir")) {
            hakAkses(true);
        }
    }//GEN-LAST:event_btnHapusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox cmbUrut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tableData;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtTelepon;
    // End of variables declaration//GEN-END:variables
}
