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
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mochamad Fajar Sodik
 */
public class formPasok extends javax.swing.JInternalFrame {

    String aksesUser = session.getAkses();
    /**
     * Creates new form formPasok
     */
    public formPasok() {
        initComponents();
        fillDataToTable();
        hakAkses(true);
    }
    
    private void hakAkses(boolean akses) {
        if (aksesUser.equals("Full")) {
            btnSimpan.setEnabled(akses);
            btnHapus.setEnabled(!akses);
        }
        else if (aksesUser.equals("Kasir")) {
            btnHapus.setEnabled(!akses);
            btnSimpan.setEnabled(!akses);
            btnTambah.setEnabled(!akses);
        }
    }
    
    private void fillDataToTable() {
        DefaultTableModel tableModel;
        tableModel = (DefaultTableModel) tableData.getModel();
        String query = "SELECT * FROM pasok";
        Connection connection;
        Statement statement;
        try {
            connection = koneksi.getKoneksi();
            statement = (Statement) connection.createStatement();
            ResultSet hasil = statement.executeQuery(query);
            while (hasil.next()) {                
                String kode_pasok = hasil.getString("id_pasok");
                String kode_distributor = hasil.getString("id_distributor");
                String kode_buku = hasil.getString("id_buku");
                String jumlah = hasil.getString("jumlah");
                String tanggal = hasil.getString("tanggal");
                Object[] data = new Object[] {kode_pasok, kode_distributor, kode_buku, jumlah, tanggal};
                tableModel.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Eror di : " + e.getMessage());
        }
    }
    
    private void tambah() {
        txtKode.setText("");
        txtBuku.setText("");
        txtDistributor.setText("");
        txtJumlah.setText("");
        dtTanggal.setDate(null);
    }
    
    private void simpan() {
        String query = "INSERT INTO pasok ("
                + "id_distributor, id_buku, jumlah, tanggal) "
                + "VALUES (?, ?, ?, ?)";
        PreparedStatement statement;
        Connection connection;
        java.sql.Date tgl = new java.sql.Date(dtTanggal.getDate().getTime());
        try {
            connection = koneksi.getKoneksi();
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, txtDistributor.getText());
            statement.setString(2, txtBuku.getText());
            statement.setString(3, txtJumlah.getText());
            statement.setDate(4, tgl);
            
            int hasil = statement.executeUpdate();
            if (hasil == 1) {
                JOptionPane.showMessageDialog(this, "Data telah disimpan");
                resetTable();
                fillDataToTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "EROR Saat Input");
        }
    }
    
    private void resetTable() {
        DefaultTableModel tableModel;
        tableModel = (DefaultTableModel) tableData.getModel();
        int jumlahBaris = tableData.getRowCount();
        for (int i = 0; i < jumlahBaris; i++) {
            tableModel.removeRow(0);
        }
    }
    
    private void cari(String kode) {
        String query = "SELECT * FROM pasok WHERE id_pasok = ?";
        Connection connection;
        PreparedStatement statement;
        try {
            connection = koneksi.getKoneksi();
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, kode);
            ResultSet hasil = statement.executeQuery();
            if (hasil.next()) {
                txtKode.setText(hasil.getString("id_pasok"));
                txtBuku.setText(hasil.getString("id_buku"));
                txtDistributor.setText(hasil.getString("id_distributor"));
                txtJumlah.setText(hasil.getString("jumlah"));
                dtTanggal.setDate(hasil.getDate("tanggal"));
                if (aksesUser.equals("Full")) {
                    hakAkses(false);
                }
                else if(aksesUser.equals("Kasir")) {
                    hakAkses(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private void hapus() {
        String kode = txtKode.getText();
        String query = "DELETE FROM pasok WHERE id_pasok = ?";
        PreparedStatement statement;
        Connection connection;
        try {
            connection = koneksi.getKoneksi();
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, kode);
            int hasil = statement.executeUpdate();
            if (hasil == 1) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                resetTable();
                fillDataToTable();
                tambah();
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
        txtKode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDistributor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtBuku = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        dtTanggal = new com.toedter.calendar.JDateChooser();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnCari = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Master Data Barang Masuk");

        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Pasok", "Kode Distributor", "Kode Buku", "Jumlah", "Tanggal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
            tableData.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel1.setText("Kode Pasok");

        txtKode.setEditable(false);

        jLabel2.setText("Kode Distributor");

        jLabel3.setText("Kode Buku");

        jLabel4.setText("Jumlah");

        jLabel5.setText("Tanggal");

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

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(txtDistributor, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(dtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dtTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtDistributor, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        tambah();
        if (aksesUser.equals("Full")) {
            hakAkses(true);
        }
        else if (aksesUser.equals("Kasir")) {
            hakAkses(true);
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        try {
            String pesan = "Masukan kode barang masuk yang ingin anda cari.";
            String kode = JOptionPane.showInputDialog(this, pesan);
            if (!kode.trim().equals("")) {
                cari(kode);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapus();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tableDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDataMouseClicked

    }//GEN-LAST:event_tableDataMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private com.toedter.calendar.JDateChooser dtTanggal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableData;
    private javax.swing.JTextField txtBuku;
    private javax.swing.JTextField txtDistributor;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKode;
    // End of variables declaration//GEN-END:variables
}
