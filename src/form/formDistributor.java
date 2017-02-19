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
 * @author Mochamad Fajar Sodik
 */
public class formDistributor extends javax.swing.JInternalFrame {

    String aksesUser = session.getAkses() ;
    /**
     * Creates new form formDistributor
     */
    public formDistributor() {
        initComponents();
        hakAkses(true);
        fillDataToTable();
    }
    
    private void cari(String cariData) {
        if (cmbCari.getSelectedItem().equals("Kode Distributor")) {
            String query = "SELECT * FROM distributor WHERE id_distributor = ?";
            PreparedStatement statement;
            Connection connection;
            try {
                connection = koneksi.getKoneksi();
                statement = (PreparedStatement) connection.prepareStatement(query);
                statement.setString(1, cariData);
                ResultSet hasil = statement.executeQuery();
                if (hasil.next()) {
                    txtKode.setText(hasil.getString("id_distributor"));
                    txtNama.setText(hasil.getString("nama_distributor"));
                    txtAlamat.setText(hasil.getString("alamat"));
                    txtTelepon.setText(hasil.getString("telepon"));
                    if (aksesUser.equals("Full")) {
                        hakAkses(false);
                    }
                    else if(aksesUser.equals("Kasir")) {
                        hakAkses(true);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, "Data Tidak Ditemukan");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
        else if(cmbCari.getSelectedItem().equals("Nama Distributor")) {
            String query = "SELECT * FROM distributor WHERE nama_distributor LIKE ?";
            PreparedStatement statement;
            Connection connection;
            try {
                connection = koneksi.getKoneksi();
                statement = (PreparedStatement) connection.prepareStatement(query);
                statement.setString(1, cariData);
                ResultSet hasil = statement.executeQuery();
                if (hasil.next()) {
                    txtKode.setText(hasil.getString("id_distributor"));
                    txtNama.setText(hasil.getString("nama_distributor"));
                    txtAlamat.setText(hasil.getString("alamat"));
                    txtTelepon.setText(hasil.getString("telepon"));
                    if (aksesUser.equals("Full")) {
                        hakAkses(false);
                    }
                    else if(aksesUser.equals("Kasir")) {
                        hakAkses(true);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, "Data Tidak Ditemukan");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }
    
    private void tambah() {
        txtCari.setText("");
        txtKode.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelepon.setText("");
        hakAkses(true);
    }
    
    private void hakAkses(boolean akses) {
        if (aksesUser.equals("Full")) {
          btnSimpan.setEnabled(akses);
          btnHapus.setEnabled(!akses);
          btnUbah.setEnabled(!akses);
        }
        else if(aksesUser.equals("Kasir")) {
          btnSimpan.setEnabled(!akses);
          btnHapus.setEnabled(!akses);
          btnUbah.setEnabled(!akses);
          btnTambah.setEnabled(!akses);  
        }
    }
    
    private void fillDataToTable() {
        DefaultTableModel tableModel;
        tableModel = (DefaultTableModel) tableData.getModel();
        String query = "SELECT * FROM distributor";
        Connection connection;
        Statement statement;
        try {
            connection = koneksi.getKoneksi();
            statement = (Statement) connection.createStatement();
            ResultSet hasil = statement.executeQuery(query);
            while (hasil.next()) {                
                String kode = hasil.getString("id_distributor");
                String nama = hasil.getString("nama_distributor");
                String alamat = hasil.getString("alamat");
                String telepon = hasil.getString("telepon");
                Object[] data = new Object[] {kode, nama, alamat, telepon};
                tableModel.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private void simpan() {
        String query = "INSERT INTO distributor (nama_distributor, alamat, telepon)"
                + " VALUES (?, ?, ?)";
        PreparedStatement statement;
        Connection connection;
        try {
            connection = koneksi.getKoneksi();
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, txtNama.getText());
            statement.setString(2, txtAlamat.getText());
            statement.setString(3, txtTelepon.getText());
            int hasil = statement.executeUpdate();
            if (hasil == 1) {
                JOptionPane.showMessageDialog(this, "Data Telah Disimpan");
                refreshDataTable();
                tambah();
            }
            else {
                JOptionPane.showMessageDialog(this, "Maaf Anda Kuyu :v");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private void refreshDataTable() {
        DefaultTableModel tableModel;
        tableModel = (DefaultTableModel) tableData.getModel();
        int jumlahBaris = tableData.getRowCount();
        for (int i = 0; i < jumlahBaris; i++) {
            tableModel.removeRow(0);
        }
        fillDataToTable();
    }
    
    private void ubah() {
        String query = "UPDATE distributor SET nama_distributor = ? , "
                + " alamat = ?,"
                + "telepon = ?"
                + " WHERE id_distributor = ?";
        PreparedStatement statement;
        Connection connection;
        try {
            connection = koneksi.getKoneksi();
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, txtNama.getText());
            statement.setString(2, txtAlamat.getText());
            statement.setString(3, txtTelepon.getText());
            statement.setString(4, txtKode.getText());
            
            int hasil = statement.executeUpdate();
            if (hasil == 1) {
                JOptionPane.showMessageDialog(this, "Data Berhasil Diupdate");
                refreshDataTable();
                tambah();
            }
            else {
                JOptionPane.showMessageDialog(this, "Maaf Anda Kuyu :v");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private void hapus() {
        String query = "DELETE FROM distributor WHERE id_distributor = ?";
        PreparedStatement statement;
        Connection connection;
        try {
            connection = koneksi.getKoneksi();
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, txtKode.getText());
            int hasil = statement.executeUpdate();
            if (hasil == 1) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                refreshDataTable();
                tambah();
            }
            else {
                JOptionPane.showMessageDialog(this, "Maaf Anda Kuyu :v");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
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
        jLabel1 = new javax.swing.JLabel();
        cmbCari = new javax.swing.JComboBox();
        txtCari = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTelepon = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Master Data Distributor");

        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Distributor", "Nama istributor", "Alamat", "Telepon"
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

        jLabel1.setText("Cari Data");

        cmbCari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kode Distributor", "Nama Distributor" }));

        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariKeyPressed(evt);
            }
        });

        jLabel2.setText("Kode Distributor");

        txtKode.setEditable(false);
        txtKode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeKeyPressed(evt);
            }
        });

        jLabel3.setText("Nama Distributor");

        txtNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaKeyPressed(evt);
            }
        });

        jLabel4.setText("Telepon");

        txtTelepon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTeleponKeyPressed(evt);
            }
        });

        jLabel5.setText("Alamat");

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane2.setViewportView(txtAlamat);

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(cmbCari, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(txtTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(165, 165, 165)
                                        .addComponent(jLabel5)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cmbCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyPressed
        String cariData = txtCari.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cari(cariData);
        }
    }//GEN-LAST:event_txtCariKeyPressed

    private void txtKodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeKeyPressed

    private void txtNamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaKeyPressed

    private void txtTeleponKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTeleponKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTeleponKeyPressed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        tambah();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        String nama = txtNama.getText();
        String alamat = txtAlamat.getText();
        String telepon = txtTelepon.getText();
        if(nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else if(alamat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else if(telepon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else {
            simpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        String nama = txtNama.getText();
        String alamat = txtAlamat.getText();
        String telepon = txtTelepon.getText();
        if(nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else if(alamat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else if(telepon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else {
            ubah();
        }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        
        String nama = txtNama.getText();
        String alamat = txtAlamat.getText();
        String telepon = txtTelepon.getText();
        if(nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else if(alamat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else if(telepon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else {
            hapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

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
            if (aksesUser.equals("Full")) {
                hakAkses(false);
            }
            else if (aksesUser.equals("Kasir")) {
                hakAkses(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tableDataMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox cmbCari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
