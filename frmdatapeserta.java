/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tugas_akhir;

import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class frmdatapeserta extends javax.swing.JFrame {

    Connection Con;
    ResultSet RsBrg;
    Statement stm;
    Boolean edit = false;
    int iKab = 0;
    int iKec = 0;
    int id = 0;
    private String sukes;
    private String skck;
    private Object[][] dataTable = null;
    private final String[] header
            = {"Kd_pes", "NIK", "Nama", "Alamat", "Kota", "Kecamatan", "Kelurahan", "Usia", "IPK", "Pendidikan", "Telepon", "Email", "sukes", "skck"};

    public frmdatapeserta() {
        initComponents();
        open_db();
        baca_data();
        aktif(false);
        setTombol(true);
        detail("kota", "kota");
        detail("kecamatan", "kec");
        detail("kelurahan", "kel");
        setLocationRelativeTo(null);
        edit = false;
    }

    public void setField() {

        int row = tblPeserta.getSelectedRow();
        if (row != -1) {

            id = Integer.parseInt(tblPeserta.getValueAt(row, 0).toString());
            txtNIK.setText(tblPeserta.getValueAt(row, 1).toString());
            txtNama.setText(tblPeserta.getValueAt(row, 2).toString());
            txtAlamat.setText(tblPeserta.getValueAt(row, 3).toString());
            cmdKabKo.setSelectedItem(tblPeserta.getValueAt(row, 4).toString());
            cmdKec.setSelectedItem(tblPeserta.getValueAt(row, 5).toString());
            cmdKel.setSelectedItem(tblPeserta.getValueAt(row, 6).toString());
            txtUsia.setText(tblPeserta.getValueAt(row, 7).toString());
            txtIpk.setText(tblPeserta.getValueAt(row, 8).toString());
            txtPendidikan.setText(tblPeserta.getValueAt(row, 9).toString());
            txtTel.setText(tblPeserta.getValueAt(row, 10).toString());
            txtEmail.setText(tblPeserta.getValueAt(row, 11).toString());
            txtSukes.setSelectedItem(tblPeserta.getValueAt(row, 12).toString());
            txtSKCK.setSelectedItem(tblPeserta.getValueAt(row, 13).toString());
        }
    }

    public void open_db() {
        try {
            koneksiMysql kon = new koneksiMysql("localhost", "root", "", "papbo");
            Con = kon.getConnection();
            System.out.println("Berhasil ");
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }

    public void baca_data() {
        try {
            stm = Con.createStatement();
            RsBrg = stm.executeQuery("select * from peserta");

            ResultSetMetaData meta = RsBrg.getMetaData();
            int col = meta.getColumnCount();
            int baris = 0;
            while (RsBrg.next()) {
                baris = RsBrg.getRow();
            }

            dataTable = new Object[baris][col];
            int x = 0;
            RsBrg.beforeFirst();
            while (RsBrg.next()) {
                dataTable[x][0] = RsBrg.getInt("kd_pes");
                dataTable[x][1] = RsBrg.getString("nik");
                dataTable[x][2] = RsBrg.getString("nama");
                dataTable[x][3] = RsBrg.getString("alamat");
                dataTable[x][4] = getNm(RsBrg.getInt("id_kab"), "kota", "kota");
                dataTable[x][5] = getNm(RsBrg.getInt("id_kec"), "kecamatan", "kec");
                dataTable[x][6] = getNm(RsBrg.getInt("id_kel"), "kelurahan", "kel");
                dataTable[x][7] = RsBrg.getInt("usia");
                dataTable[x][8] = RsBrg.getFloat("ipk");
                dataTable[x][9] = RsBrg.getString("pendidikan");
                dataTable[x][10] = RsBrg.getString("tlp");
                dataTable[x][11] = RsBrg.getString("email");
                dataTable[x][12] = RsBrg.getString("sukes");
                dataTable[x][13] = RsBrg.getString("skck");

                x++;
            }
            tblPeserta.setModel(new DefaultTableModel(dataTable, header));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void kosong() {
        txtNIK.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        txtUsia.setText("");
        txtIpk.setText("");
        txtPendidikan.setText("");
        txtTel.setText("");
        txtEmail.setText("");
    }

    private void aktif(boolean x) {
        txtNIK.setEditable(x);
        txtNama.setEditable(x);
        txtAlamat.setEditable(x);
        cmdKabKo.setEnabled(x);
        cmdKec.setEnabled(x);
        cmdKel.setEnabled(x);
        txtUsia.setEditable(x);
        txtIpk.setEditable(x);
        txtPendidikan.setEditable(x);
        txtTel.setEditable(x);
        txtEmail.setEditable(x);
        txtSukes.setEditable(x);
        txtSKCK.setEditable(x);
    }

    private void setTombol(boolean t) {
        cmdTambah.setEnabled(t);
        cmdEdit.setEnabled(t);
        cmdHapus.setEnabled(t);
        cmdSimpan.setEnabled(!t);
        cmdBatal.setEnabled(!t);
        cmdKeluar.setEnabled(t);
    }

    private void detail(String table, String nm) {
        try (ResultSet rs = stm.executeQuery("SELECT " + nm + " FROM " + table)) {

            if (null != table) {
                switch (table) {
                    case "kota" -> {
                        while (rs.next()) {
                            cmdKabKo.addItem(rs.getString(nm));
                        }
                    }
                    case "kecamatan" -> {
                        while (rs.next()) {
                            cmdKec.addItem(rs.getString(nm));
                        }
                    }
                    case "kelurahan" -> {
                        while (rs.next()) {
                            cmdKel.addItem(rs.getString(nm));
                        }
                    }
                    default -> {
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading : " + e.getMessage());
        }
    }

    private void detailKabKo() {
        String sql = "SELECT kec FROM kecamatan WHERE id_kab=" + iKab;
        cmdKec.removeAllItems();
        try (ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                cmdKec.addItem(rs.getString("kec"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading : " + e.getMessage());
        }
    }

    private void detailKec() {
        String sql = "SELECT kel FROM kelurahan WHERE id_kec=" + iKec;
        cmdKel.removeAllItems();
        try (ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                cmdKel.addItem(rs.getString("kel"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading : " + e.getMessage());
        }
    }

    private String getNm(int id, String table, String nm) {
        String nama = "";
        try {
            stm = Con.createStatement();
            ResultSet sql = stm.executeQuery("select " + nm + " as nama from " + table + " where id=" + id);
            if (sql.next()) {
                nama = sql.getString("nama");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return nama;
    }

    private int getId(String nama, String table, String idT, String n) {
        int id = 0;
        try {
            stm = Con.createStatement();
            ResultSet sql = stm.executeQuery("select " + idT + " as id from " + table + " where " + n + "='" + nama + "'");
            if (sql.next()) {
                id = sql.getInt("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }

    private int getIdKel(String nama, int n) {
        int id = 0;
        try {
            stm = Con.createStatement();
            ResultSet sql = stm.executeQuery("SELECT id FROM `kelurahan` WHERE `kel`='" + nama + "' AND `id_kec` =" + n);
            if (sql.next()) {
                id = sql.getInt("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtEmail = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPeserta = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmdTambah = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        cmdSimpan = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNIK = new javax.swing.JTextField();
        cmdEdit = new javax.swing.JButton();
        cmdHapus = new javax.swing.JButton();
        cmdBatal = new javax.swing.JButton();
        txtTel = new javax.swing.JTextField();
        cmdKeluar = new javax.swing.JButton();
        txtUsia = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtPendidikan = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtIpk = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cmdKabKo = new javax.swing.JComboBox<>();
        cmdKec = new javax.swing.JComboBox<>();
        cmdKel = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtSukes = new javax.swing.JComboBox<>();
        txtSKCK = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 255, 204));

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        tblPeserta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblPeserta.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tblPesertaAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tblPeserta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPesertaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPeserta);

        jLabel11.setText("Nama Lengkap");

        jLabel5.setText("Telepon");

        cmdTambah.setBackground(new java.awt.Color(102, 255, 0));
        cmdTambah.setForeground(new java.awt.Color(0, 0, 0));
        cmdTambah.setText("Tambah");
        cmdTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTambahActionPerformed(evt);
            }
        });

        jLabel6.setText("Email");

        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });

        cmdSimpan.setBackground(new java.awt.Color(0, 0, 255));
        cmdSimpan.setForeground(new java.awt.Color(255, 255, 255));
        cmdSimpan.setText("Simpan");
        cmdSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSimpanActionPerformed(evt);
            }
        });

        jLabel13.setText("NIK");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("DATA PESERTA");

        txtNIK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNIKActionPerformed(evt);
            }
        });

        cmdEdit.setText("Edit");
        cmdEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditActionPerformed(evt);
            }
        });

        cmdHapus.setText("Hapus");
        cmdHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdHapusActionPerformed(evt);
            }
        });

        cmdBatal.setText("Batal");
        cmdBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBatalActionPerformed(evt);
            }
        });

        txtTel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelActionPerformed(evt);
            }
        });

        cmdKeluar.setBackground(new java.awt.Color(255, 0, 0));
        cmdKeluar.setForeground(new java.awt.Color(255, 255, 255));
        cmdKeluar.setText("Keluar");
        cmdKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeluarActionPerformed(evt);
            }
        });

        txtUsia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsiaActionPerformed(evt);
            }
        });

        jLabel15.setText("Usia");

        jLabel16.setText("Pendidikan Terakhir");

        txtPendidikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPendidikanActionPerformed(evt);
            }
        });

        jLabel17.setText("IPK");

        txtIpk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIpkActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel8.setText("Kelurahan");

        jLabel9.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel9.setText("Kecamatan");

        cmdKabKo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kab/Kota" }));
        cmdKabKo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmdKabKoItemStateChanged(evt);
            }
        });
        cmdKabKo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKabKoActionPerformed(evt);
            }
        });

        cmdKec.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kecamatan" }));
        cmdKec.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmdKecItemStateChanged(evt);
            }
        });

        cmdKel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kelurahan" }));

        jLabel3.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel3.setText("Alamat");

        jLabel4.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel4.setText("Kab/Kota");

        jLabel18.setText("Surat Kesehatan");

        jLabel19.setText("SKCK");

        txtSukes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Pilih -", "Ada", "Tidak ada" }));
        txtSukes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSukesActionPerformed(evt);
            }
        });

        txtSKCK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Pilih -", "Ada ", "Tidak ada" }));
        txtSKCK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSKCKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(501, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(107, 107, 107)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmdKec, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmdKabKo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cmdTambah)
                                        .addGap(18, 18, 18)
                                        .addComponent(cmdSimpan))
                                    .addComponent(cmdKel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNIK, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19))
                                .addGap(110, 110, 110))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(269, 269, 269))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtIpk, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPendidikan, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtUsia, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSukes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSKCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(16, 16, 16))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(314, Short.MAX_VALUE)
                .addComponent(cmdEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdHapus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdBatal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdKeluar)
                .addGap(47, 47, 47))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNIK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15)
                    .addComponent(txtUsia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txtIpk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdKabKo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdKec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPendidikan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdKel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdTambah)
                            .addComponent(cmdSimpan))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdBatal)
                            .addComponent(cmdEdit)
                            .addComponent(cmdHapus)
                            .addComponent(cmdKeluar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtSukes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtSKCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void tblPesertaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblPesertaAncestorAdded
        // TODO add your handling code here:
        setField();
    }//GEN-LAST:event_tblPesertaAncestorAdded

    private void cmdTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTambahActionPerformed
        // TODO add your handling code here:
        aktif(true);
        setTombol(false);
        kosong();
        edit = false;
    }//GEN-LAST:event_cmdTambahActionPerformed

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void cmdSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSimpanActionPerformed
        // TODO add your handling code here:
        String tNIK = txtNIK.getText();
        String tNama = txtNama.getText();
        String tAlamat = txtAlamat.getText();
        String KabKo = (String) cmdKabKo.getSelectedItem();
        int tKab = getId(KabKo, "kota", "id", "kota");
        String Kec = (String) cmdKec.getSelectedItem();
        int tKec = getId(Kec, "kecamatan", "id", "kec");
        String Kel = (String) cmdKel.getSelectedItem();
        int tKel = getId(Kel, "kelurahan", "id", "kel");
        int tUsia = Integer.parseInt(txtUsia.getText());
        double tIPK = Double.parseDouble(txtIpk.getText());
        String tPend = txtPendidikan.getText();
        String tTelp = txtTel.getText();
        String tEmail = txtEmail.getText();
        String sukes = (String) txtSukes.getSelectedItem();
        String skck = (String) txtSKCK.getSelectedItem();
        try {
            if (edit) {
                stm.executeUpdate("UPDATE peserta SET nik='" + tNIK + "', nama='" + tNama + "', alamat='" + tAlamat
                        + "', id_kab='" + tKab + "', id_kec='" + tKec + "', id_kel='" + tKel + "', usia='" + tUsia
                        + "', ipk='" + tIPK + "', pendidikan='" + tPend + "', tlp='" + tTelp + "', email='" + tEmail
                        + "', sukes='" + sukes + "', skck='" + skck + "' WHERE kd_pes=" + id);
            } else {
                String query = "INSERT INTO peserta (nik, nama, alamat, id_kab, id_kec, id_kel, usia, ipk, pendidikan, tlp, email, sukes, skck) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
                try (PreparedStatement stmt = Con.prepareStatement(query)) {
                    stmt.setString(1, tNIK);
                    stmt.setString(2, tNama);
                    stmt.setString(3, tAlamat);
                    stmt.setInt(4, tKab);
                    stmt.setInt(5, tKec);
                    stmt.setInt(6, tKel);
                    stmt.setInt(7, tUsia);
                    stmt.setDouble(8, tIPK);
                    stmt.setString(9, tPend);
                    stmt.setString(10, tTelp);
                    stmt.setString(11, tEmail);
                    stmt.setString(12, sukes);
                    stmt.setString(13, skck);
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
//                     System.out.print("INSERT into peserta (nik, nama_lengkap, usia, ipk, alamat, id_kabko, id_kec, id_kel, telp, email) VALUES('"+ tNIK + "', '"+tNama+"', '"+tUsia+"' , '" + tIPK + "', '" + tAlamat + "', '" + tIdKab + "', '" + tKec + "', '" + tKel + "', '" + tTelp + "', '" + tEmail + "')");
            }
            tblPeserta.setModel(new DefaultTableModel(dataTable, header));
            baca_data();
            aktif(false);
            setTombol(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_cmdSimpanActionPerformed

    private void txtNIKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNIKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNIKActionPerformed

    private void cmdEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditActionPerformed
        // TODO add your handling code here:
        edit = true;
        aktif(true);
        setTombol(false);
        txtNIK.setEditable(false);
    }//GEN-LAST:event_cmdEditActionPerformed

    private void cmdHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdHapusActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "delete from peserta where kd_pes=" + id;
            stm.executeUpdate(sql);
            baca_data();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_cmdHapusActionPerformed

    private void cmdBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBatalActionPerformed
        // TODO add your handling code here:
        aktif(false);
        setTombol(true);
    }//GEN-LAST:event_cmdBatalActionPerformed

    private void txtTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelActionPerformed

    private void cmdKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKeluarActionPerformed
        // TODO add your handling code here:
        try {
            if (Con != null) {
                Con.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing connection: " + e.getMessage());
        }
        new frmmenu().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_cmdKeluarActionPerformed

    private void txtUsiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsiaActionPerformed

    private void txtPendidikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPendidikanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPendidikanActionPerformed

    private void txtIpkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIpkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIpkActionPerformed

    private void cmdKabKoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmdKabKoItemStateChanged
        // TODO add your handling code here:
        iKab = getId((String) cmdKabKo.getSelectedItem(), "kota", "id", "kota");
        detailKabKo();
    }//GEN-LAST:event_cmdKabKoItemStateChanged

    private void cmdKecItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmdKecItemStateChanged
        // TODO add your handling code here:
        iKec = getId((String) cmdKec.getSelectedItem(), "kecamatan", "id", "kec");
        detailKec();
    }//GEN-LAST:event_cmdKecItemStateChanged

    private void tblPesertaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesertaMouseClicked
        // TODO add your handling code here:
        setField();
    }//GEN-LAST:event_tblPesertaMouseClicked

    private void cmdKabKoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKabKoActionPerformed
        // TODO add your handling code here:
        String selectedKode = (String) cmdKabKo.getSelectedItem();
    }//GEN-LAST:event_cmdKabKoActionPerformed

    private void txtSukesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSukesActionPerformed
        // TODO add your handling code here:
        JComboBox cSatuan = (javax.swing.JComboBox) evt.getSource();
       
        sukes = (String) cSatuan.getSelectedItem();
    }//GEN-LAST:event_txtSukesActionPerformed

    private void txtSKCKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSKCKActionPerformed
        // TODO add your handling code here:
        JComboBox cSatuan = (javax.swing.JComboBox) evt.getSource();
    
        skck = (String) cSatuan.getSelectedItem();
    }//GEN-LAST:event_txtSKCKActionPerformed

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
            java.util.logging.Logger.getLogger(frmdatapeserta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmdatapeserta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmdatapeserta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmdatapeserta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmdatapeserta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdBatal;
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdHapus;
    private javax.swing.JComboBox<String> cmdKabKo;
    private javax.swing.JComboBox<String> cmdKec;
    private javax.swing.JComboBox<String> cmdKel;
    private javax.swing.JButton cmdKeluar;
    private javax.swing.JButton cmdSimpan;
    private javax.swing.JButton cmdTambah;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPeserta;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIpk;
    private javax.swing.JTextField txtNIK;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtPendidikan;
    private javax.swing.JComboBox<String> txtSKCK;
    private javax.swing.JComboBox<String> txtSukes;
    private javax.swing.JTextField txtTel;
    private javax.swing.JTextField txtUsia;
    // End of variables declaration//GEN-END:variables
}
