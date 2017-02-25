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
public class formBarang extends javax.swing.JInternalFrame {

    String hakakses = session.getAkses();
    /**
     * Creates new form formBarang
     */
    public formBarang() {
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
        String query = "SELECT * FROM buku";
        Statement statement;
        Connection connection;
        try {
            connection = koneksi.getkoneksi();
            statement = (Statement) connection.createStatement();
            ResultSet hasil = statement.executeQuery(query);
            while (hasil.next()) {                
                int tb_kode = hasil.getInt("id_buku");
                String tb_judul = hasil.getString("judul");
                int tb_noisbn = hasil.getInt("noisbn");
                String tb_penulis = hasil.getString("penulis");
                String tb_penerbit = hasil.getString("penerbit");
                String tb_tahun = hasil.getString("tahun");
                int stok = hasil.getInt("stok");
                int harga_pokok = hasil.getInt("harga_pokok");
                int harga_jual = hasil.getInt("harga_jual");
                int ppn = hasil.getInt("ppn");
                int diskon = hasil.getInt("diskon");
                Object[] data = new Object[] {tb_kode, tb_judul, tb_noisbn, tb_penulis, tb_penerbit, tb_tahun, stok, harga_pokok, harga_jual, ppn, diskon};
                tableModel.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Eror : " + e.getMessage());
        }
    }
    
    private void cari(String cariData) {
        if (cariData.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else {
            if (cmbcari.getSelectedItem().equals("Kode Buku")) {
                String query = "SELECT * FROM buku WHERE id_buku = ?";
                PreparedStatement statement;
                Connection connection;
                try {
                    connection = koneksi.getkoneksi();
                    statement = (PreparedStatement) connection.prepareStatement(query);
                    statement.setString(1, cariData);
                    ResultSet hasil = statement.executeQuery();
                    if (hasil.next()) {                        
                        txtKode.setText(hasil.getString("id_buku"));
                        txtNoIsbn.setText(hasil.getString("noisbn"));;
                        txtPenerbit.setText(hasil.getString("penerbit"));
                        txtPenulis.setText(hasil.getString("penulis"));
                        txtJudul.setText(hasil.getString("judul"));
                        txtTahun.setText(hasil.getString("tahun"));
                        txtStok.setText(hasil.getString("stok"));
                        txtHargaPokok.setText(hasil.getString("harga_pokok"));
                        txtHargaJual.setText(hasil.getString("harga_jual"));
                        txtPpn.setText(hasil.getString("ppn"));
                        txtDiskon.setText(hasil.getString("diskon"));
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Data tidak ada");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Eror di : " + e.getMessage());
                }
            }
            else if (cmbcari.getSelectedItem().equals("Judul Buku")) {
                String query = "SELECT * FROM buku WHERE judul LIKE ?";
                PreparedStatement statement;
                Connection connection;
                try {
                    connection = koneksi.getkoneksi();
                    statement = (PreparedStatement) connection.prepareStatement(query);
                    statement.setString(1, cariData);
                    ResultSet hasil = statement.executeQuery();
                    if (hasil.next()) {                        
                        txtKode.setText(hasil.getString("id_buku"));
                        txtNoIsbn.setText(hasil.getString("noisbn"));;
                        txtPenerbit.setText(hasil.getString("penerbit"));
                        txtPenulis.setText(hasil.getString("penulis"));
                        txtJudul.setText(hasil.getString("judul"));
                        txtTahun.setText(hasil.getString("tahun"));
                        txtStok.setText(hasil.getString("stok"));
                        txtHargaPokok.setText(hasil.getString("harga_pokok"));
                        txtHargaJual.setText(hasil.getString("harga_jual"));
                        txtPpn.setText(hasil.getString("ppn"));
                        txtDiskon.setText(hasil.getString("diskon"));
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Data tidak ada");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Eror di : " + e.getMessage());
                }
            }
        }
    }
    
    private void resetTable() {
        DefaultTableModel tableModel;
        tableModel = (DefaultTableModel) tableData.getModel();
        int isiTabel = tableModel.getRowCount();
        for (int i = 0; i < isiTabel; i++) {
            tableModel.removeRow(0);
        }
    }
    
    private void urutkanTable() {
        if (cmbUrut.getSelectedItem().equals("A - Z")) {
            DefaultTableModel tableModel;
            tableModel = (DefaultTableModel) tableData.getModel();
            String query = "SELECT * FROM buku ORDER BY judul ASC";
            Statement statement;
            Connection connection;
            try {
                connection = koneksi.getkoneksi();
                statement = (Statement) connection.createStatement();
                ResultSet hasil = statement.executeQuery(query);
                while (hasil.next()) {                    
                    int tb_kode = hasil.getInt("id_buku");
                   String tb_judul = hasil.getString("judul");
                    int tb_noisbn = hasil.getInt("noisbn");
                    String tb_penulis = hasil.getString("penulis");
                    String tb_penerbit = hasil.getString("penerbit");
                    String tb_tahun = hasil.getString("tahun");
                    int stok = hasil.getInt("stok");
                    int harga_pokok = hasil.getInt("harga_pokok");
                    int harga_jual = hasil.getInt("harga_jual");
                    int ppn = hasil.getInt("ppn");
                    int diskon = hasil.getInt("diskon");
                    Object[] data = new Object[] {tb_kode, tb_judul, tb_noisbn, tb_penulis, tb_penerbit, tb_tahun, stok, harga_pokok, harga_jual, ppn, diskon};
                    tableModel.addRow(data);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Eror di" + e.getMessage());
            }
        }
        else if (cmbUrut.getSelectedItem().equals("Z - A")) {
            DefaultTableModel tableModel;
            tableModel = (DefaultTableModel) tableData.getModel();
            String query = "SELECT * FROM buku ORDER BY judul DESC";
            Statement statement;
            Connection connection;
            try {
                connection = koneksi.getkoneksi();
                statement = (Statement) connection.createStatement();
                ResultSet hasil = statement.executeQuery(query);
                while (hasil.next()) {                    
                    int tb_kode = hasil.getInt("id_buku");
                String tb_judul = hasil.getString("judul");
                int tb_noisbn = hasil.getInt("noisbn");
                String tb_penulis = hasil.getString("penulis");
                String tb_penerbit = hasil.getString("penerbit");
                String tb_tahun = hasil.getString("tahun");
                int stok = hasil.getInt("stok");
                int harga_pokok = hasil.getInt("harga_pokok");
                int harga_jual = hasil.getInt("harga_jual");
                int ppn = hasil.getInt("ppn");
                int diskon = hasil.getInt("diskon");
                Object[] data = new Object[] {tb_kode, tb_judul, tb_noisbn, tb_penulis, tb_penerbit, tb_tahun, stok, harga_pokok, harga_jual, ppn, diskon};
                tableModel.addRow(data);
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Eror di" + e.getMessage());
            }
        }
    }
    
    private void tambah () {
        txtKode.setText("");
        txtJudul.setText("");
        txtNoIsbn.setText("");
        txtPenulis.setText("");
        txtPenerbit.setText("");
        txtTahun.setText("");
        txtStok.setText("");
        txtHargaPokok.setText("");
        txtHargaJual.setText("");
        txtPpn.setText("");
        txtDiskon.setText("");
        txtCari.setText("");
            if (hakakses.equals("Admin")) {
                hakAkses(true);
            }
            else if (hakakses.equals("Kasir")) {
                hakAkses(true);
            }
    }
    
    private void simpan(String judul, 
            String noisbn, String penulis, 
            String penerbit, String tahun, String stok,
            String harga_pokok, String harga_jual, String ppn) {
        String query = "INSERT INTO buku (judul, noisbn, penulis,"
                + "penerbit, tahun, stok, harga_pokok, harga_jual, ppn, diskon) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement;
        Connection connection;
        try {
            connection = koneksi.getkoneksi();
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, judul);
            statement.setString(2, noisbn);
            statement.setString(3, penulis);
            statement.setString(4, penerbit);
            statement.setString(5, tahun);
            statement.setString(6, stok);
            statement.setString(7, harga_pokok);
            statement.setString(8, harga_jual);
            statement.setString(9, ppn);
            statement.setString(10, txtDiskon.getText());
            int hasil = statement.executeUpdate();
            if (hasil == 1) {
                JOptionPane.showMessageDialog(this, "Data Tersimpan");
                tambah();
                resetTable();
                fillDataToTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Eror di : " + e.getMessage());
        }
    }
    
    private void ubah(String kode, String judul, 
            String noisbn, String penulis, 
            String penerbit, String tahun, String stok,
            String harga_pokok, String harga_jual, String ppn) {
        String query = "UPDATE buku SET "
                + " judul = ?, noisbn = ?,"
                + "penulis = ?, penerbit = ?,"
                + "tahun = ?, stok = ?,"
                + "harga_pokok = ?, harga_jual = ?,"
                + "ppn = ?, diskon = ? "
                + " WHERE id_buku = ?";
        PreparedStatement statement;
        Connection connection;
        try {
            connection = koneksi.getkoneksi();
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, judul);
            statement.setString(2, noisbn);
            statement.setString(3, penulis);
            statement.setString(4, penerbit);
            statement.setString(5, tahun);
            statement.setString(6, stok);
            statement.setString(7, harga_pokok);
            statement.setString(8, harga_jual);
            statement.setString(9, ppn);
            statement.setString(10, txtDiskon.getText());
            statement.setString(11, kode);
            int hasil = statement.executeUpdate();
            if (hasil == 1) {
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate");
                tambah();
                if (hakakses.equals("Admin")) {
                hakAkses(true);
                }
                else if (hakakses.equals("Kasir")) {
                    hakAkses(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Eror Di : " + e.getMessage());
        }
    }
    
    private void hapus () {
        String kode = txtKode.getText();
        String query = "DELETE FROM buku WHERE id_buku = ?";
        Connection connection;
        PreparedStatement statement;
        try {
            connection = koneksi.getkoneksi();
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, kode);
            int hasil = statement.executeUpdate();
            if (hasil == 1) {
                JOptionPane.showMessageDialog(this, "Data telah dihapus");
                tambah();
                resetTable();
                fillDataToTable();
                if (hakakses.equals("Admin")) {
                hakAkses(true);
            }
            else if (hakakses.equals("Kasir")) {
                hakAkses(true);
            }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Eror Di : " + e.getMessage());
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
        cmbcari = new javax.swing.JComboBox();
        txtCari = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtJudul = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNoIsbn = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPenulis = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPenerbit = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTahun = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtStok = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtHargaPokok = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtHargaJual = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPpn = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDiskon = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbUrut = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        btnHapus1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Master Data Barang");

        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Buku", "Judul Buku", "No ISBN", "Penulis", "Penerbit", "Tahun", "Stok", "Harga Pokok", "Harga Jual", "PPn", "Diskon"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
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
            tableData.getColumnModel().getColumn(5).setResizable(false);
            tableData.getColumnModel().getColumn(6).setResizable(false);
            tableData.getColumnModel().getColumn(7).setResizable(false);
            tableData.getColumnModel().getColumn(8).setResizable(false);
            tableData.getColumnModel().getColumn(9).setResizable(false);
            tableData.getColumnModel().getColumn(10).setResizable(false);
        }

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setText("Cari Buku");

        cmbcari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kode Buku", "Judul Buku" }));

        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCari)
                    .addComponent(cmbcari, javax.swing.GroupLayout.Alignment.LEADING, 0, 226, Short.MAX_VALUE)
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
                .addComponent(cmbcari, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCari, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
        );

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

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(240, 240, 240));
        jLabel3.setText("Kode Buku");

        txtKode.setEditable(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(240, 240, 240));
        jLabel4.setText("Judul Buku");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(240, 240, 240));
        jLabel5.setText("No ISBN");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(240, 240, 240));
        jLabel6.setText("Penulis");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(240, 240, 240));
        jLabel7.setText("Penerbit");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(240, 240, 240));
        jLabel8.setText("Tahun");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(240, 240, 240));
        jLabel9.setText("Stok");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(240, 240, 240));
        jLabel10.setText("Harga Pokok");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(240, 240, 240));
        jLabel11.setText("Harga Jual");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(240, 240, 240));
        jLabel12.setText("PPn");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(240, 240, 240));
        jLabel13.setText("Diskon");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNoIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPenulis, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtHargaPokok, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPpn, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtHargaPokok, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNoIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPenulis, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPpn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(240, 240, 240));
        jLabel2.setText("Urutkan Berdasarkan");

        cmbUrut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A - Z", "Z - A" }));

        jButton1.setText("Urutkan Tabel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbUrut, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 62, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(cmbUrut, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnHapus1.setText("Refresh Data");
        btnHapus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapus1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnUbah, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnHapus1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       resetTable();
       urutkanTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        tambah();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        
        String judul = txtJudul.getText();
        String noisbn = txtNoIsbn.getText();
        String penulis = txtPenulis.getText();
        String penerbit = txtPenerbit.getText();
        String tahun = txtTahun.getText();
        String stok = txtStok.getText();
        String harga_pokok = txtHargaPokok.getText();
        String harga_jual = txtHargaJual.getText();
        String ppn = txtPpn.getText();
        
        if (judul.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else if (noisbn.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else if (penulis.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else if (penerbit.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else if (tahun.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else if (stok.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else if (harga_pokok.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else if (harga_jual.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else if (ppn.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data");
        }
        else {
            simpan(judul, noisbn, penulis, penerbit, tahun, stok, harga_pokok, harga_jual, ppn);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        String kode = txtKode.getText();
        String judul = txtJudul.getText();
        String noisbn = txtNoIsbn.getText();
        String penulis = txtPenulis.getText();
        String penerbit = txtPenerbit.getText();
        String tahun = txtTahun.getText();
        String stok = txtStok.getText();
        String harga_pokok = txtHargaPokok.getText();
        String harga_jual = txtHargaJual.getText();
        String ppn = txtPpn.getText();
        ubah(kode, judul, noisbn, penulis, penerbit, tahun, stok, harga_pokok, harga_jual, ppn);
        resetTable();
        fillDataToTable();
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapus ();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnHapus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapus1ActionPerformed
        resetTable();
        fillDataToTable();
    }//GEN-LAST:event_btnHapus1ActionPerformed

    private void tableDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDataMouseClicked
        try {
            int row = tableData.rowAtPoint(evt.getPoint());
            
            String tb_kode = tableData.getValueAt(row, 0).toString();
            String tb_judul = tableData.getValueAt(row, 1).toString();
            String tb_noisbn = tableData.getValueAt(row, 2).toString();
            String tb_penulis = tableData.getValueAt(row, 3).toString();
            String tb_penerbit = tableData.getValueAt(row, 4).toString();
            String tb_tahun = tableData.getValueAt(row, 5).toString();
            String tb_stok = tableData.getValueAt(row, 6).toString();
            String tb_pokok = tableData.getValueAt(row, 7).toString();
            String tb_jual = tableData.getValueAt(row, 8).toString();
            String tb_ppn = tableData.getValueAt(row, 9).toString();
            String tb_diskon = tableData.getValueAt(row, 10).toString();
            
            txtKode.setText(String.valueOf(tb_kode));
            txtJudul.setText(String.valueOf(tb_judul));
            txtNoIsbn.setText(String.valueOf(tb_noisbn));
            txtPenulis.setText(String.valueOf(tb_penulis));
            txtPenerbit.setText(String.valueOf(tb_penerbit));
            txtTahun.setText(String.valueOf(tb_tahun));
            txtStok.setText(String.valueOf(tb_stok));
            txtHargaPokok.setText(String.valueOf(tb_pokok));
            txtHargaJual.setText(String.valueOf(tb_jual));
            txtPpn.setText(String.valueOf(tb_ppn));
            txtDiskon.setText(String.valueOf(tb_diskon));
            
            if (hakakses.equals("Admin")) {
                hakAkses(false);
            }
            else if (hakakses.equals("Kasir")) {
                hakAkses(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Eror : " + e.getMessage());
        }
    }//GEN-LAST:event_tableDataMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnHapus1;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox cmbUrut;
    private javax.swing.JComboBox cmbcari;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableData;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtDiskon;
    private javax.swing.JTextField txtHargaJual;
    private javax.swing.JTextField txtHargaPokok;
    private javax.swing.JTextField txtJudul;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNoIsbn;
    private javax.swing.JTextField txtPenerbit;
    private javax.swing.JTextField txtPenulis;
    private javax.swing.JTextField txtPpn;
    private javax.swing.JTextField txtStok;
    private javax.swing.JTextField txtTahun;
    // End of variables declaration//GEN-END:variables
}
