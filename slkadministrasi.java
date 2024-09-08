/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tugas_akhir;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.event.*;

public class slkadministrasi extends javax.swing.JFrame {

    private Connection con;
    private Statement stm;
    private ResultSet rs;
    private DefaultTableModel tableModel;

    public slkadministrasi() {
        initComponents();
        openDatabase();
        initializeTable();
        fillKabKoComboBox();
        loadData();
        setLocationRelativeTo(null);
    }

    private void openDatabase() {
        try {
            koneksiMysql kon = new koneksiMysql("localhost", "root", "", "papbo");
            con = kon.getConnection();
            System.out.println("Successfully connected to database");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to connect to database: " + e.getMessage());
        }
    }

    private void initializeTable() {
        String[] columns = {"ID", "NIK", "Nama Lengkap", "Usia", "IPK", "Alamat", "Kab/Kota", "Kecamatan", "Kelurahan", "Pendidikan", "Telephone", "Email", "sukes", "skck"};
        tableModel = new DefaultTableModel(columns, 0);
        tblPeserta.setModel(tableModel);
    }

    private void fillKabKoComboBox() {
        try {
            stm = con.createStatement();
            rs = stm.executeQuery("SELECT id, kota FROM kota ORDER BY kota");

            cmdKabKo.removeAllItems();
            cmdKabKo.addItem("Semua Kab/Kota");

            while (rs.next()) {
                cmdKabKo.addItem(rs.getString("kota"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving Kab/Kota data: " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            stm = con.createStatement();
            rs = stm.executeQuery("SELECT * FROM peserta INNER JOIN administrasi ON peserta.kd_pes=administrasi.kd_pes");

            tableModel.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("kd_pes"),
                    rs.getString("nik"),
                    rs.getString("nama"),
                    rs.getInt("usia"),
                    rs.getFloat("ipk"),
                    rs.getString("alamat"),
                    getNama(rs.getInt("id_kab"), "kota", "kota"),
                    getNama(rs.getInt("id_kec"), "kecamatan", "kec"),
                    getNama(rs.getInt("id_kel"), "kelurahan", "kel"),
                    rs.getString("pendidikan"),
                    rs.getString("tlp"),
                    rs.getString("email"),
                    rs.getString("sukes"),
                    rs.getString("skck")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }

    private String getNama(int id, String table, String column) {
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT " + column + " FROM " + table + " WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error getting name: " + e.getMessage());
        }
        return "";
    }

    private void searchData() {
        try {
            double ipk = Double.parseDouble(txt.getText());
            String kabKota = (String) cmdKabKo.getSelectedItem();
            String sukes = (String) txtSukes.getSelectedItem();
            String skck = (String) txtSKCK.getSelectedItem();

            StringBuilder query = new StringBuilder("SELECT * FROM peserta WHERE ipk >= ?");

            if (!"Semua Kab/Kota".equals(kabKota)) {
                query.append(" AND id_kab = (SELECT id FROM kota WHERE kota = ?)");
            }

            if (!"Semua".equals(sukes)) {
                query.append(" AND sukes = ?");
            }

            if (!"Semua".equals(skck)) {
                query.append(" AND skck = ?");
            }

            PreparedStatement pstmt = con.prepareStatement(query.toString());
            int paramIndex = 1;
            pstmt.setDouble(paramIndex++, ipk);

            if (!"Semua Kab/Kota".equals(kabKota)) {
                pstmt.setString(paramIndex++, kabKota);
            }

            if (!"Semua".equals(sukes)) {
                pstmt.setString(paramIndex++, sukes);
            }

            if (!"Semua".equals(skck)) {
                pstmt.setString(paramIndex++, skck);
            }

            System.out.println("Executing query: " + pstmt);

            rs = pstmt.executeQuery();
            tableModel.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("kd_pes"),
                    rs.getString("nik"),
                    rs.getString("nama"),
                    rs.getInt("usia"),
                    rs.getFloat("ipk"),
                    rs.getString("alamat"),
                    getNama(rs.getInt("id_kab"), "kota", "kota"),
                    getNama(rs.getInt("id_kec"), "kecamatan", "kec"),
                    getNama(rs.getInt("id_kel"), "kelurahan", "kel"),
                    rs.getString("pendidikan"),
                    rs.getString("tlp"),
                    rs.getString("email"),
                    rs.getString("sukes"),
                    rs.getString("skck")
                };
                tableModel.addRow(row);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid IPK.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error searching data: " + e.getMessage());
        }
    }

    private void simpanData() {
        String deleteQuery = "DELETE FROM administrasi";
        try (PreparedStatement deleteStmt = con.prepareStatement(deleteQuery)) {
            deleteStmt.executeUpdate();
            System.out.println("All data deleted from administrasi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String insertQuery = "INSERT INTO administrasi (kd_pes, status) VALUES (?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(insertQuery)) {
            int rowCount = tableModel.getRowCount();

            for (int i = 0; i < rowCount; i++) {
                int idPeserta = (int) tableModel.getValueAt(i, 0);
                String status = "Lolos";

                stmt.setInt(1, idPeserta);
                stmt.setString(2, status);
                stmt.addBatch();
            }

            int[] result = stmt.executeBatch();
            System.out.println("Batch insert completed. Rows inserted: " + result.length);

        } catch (SQLException e) {
            e.printStackTrace();
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

        jLabel7 = new javax.swing.JLabel();
        cmdKeluar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cmdKabKo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPeserta = new javax.swing.JTable();
        btnsimpan = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtSukes = new javax.swing.JComboBox<>();
        txtSKCK = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("SELEKSI ADMINISTRASI");

        cmdKeluar.setBackground(new java.awt.Color(204, 0, 0));
        cmdKeluar.setForeground(new java.awt.Color(255, 255, 255));
        cmdKeluar.setText("Keluar");
        cmdKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeluarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel1.setText("IPK");

        btnCari.setBackground(new java.awt.Color(255, 255, 0));
        btnCari.setForeground(new java.awt.Color(0, 0, 0));
        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel4.setText("Kab/Kota");

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

        tblPeserta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NIK", "Nama", "Alamat", "Kota", "Kecamatan", "Kelurahan", "Usia", "IPK", "Pend.Terakhir", "Tel", "Email"
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

        btnsimpan.setBackground(new java.awt.Color(0, 0, 204));
        btnsimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnsimpan.setText("Simpan");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });

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
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(89, 89, 89)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmdKabKo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnCari)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnsimpan)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSukes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSKCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(195, 195, 195))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmdKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdKabKo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtSukes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtSKCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCari)
                    .addComponent(btnsimpan))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdKeluar)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKeluarActionPerformed
        // TODO add your handling code here:
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing connection: " + e.getMessage());
        }
        new frmmenu().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cmdKeluarActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        searchData();
    }//GEN-LAST:event_btnCariActionPerformed

    private void cmdKabKoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmdKabKoItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cmdKabKoItemStateChanged

    private void cmdKabKoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKabKoActionPerformed
        // TODO add your handling code here:
        String selectedKode = (String) cmdKabKo.getSelectedItem();
    }//GEN-LAST:event_cmdKabKoActionPerformed

    private void tblPesertaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblPesertaAncestorAdded
        // TODO add your handling code here:

    }//GEN-LAST:event_tblPesertaAncestorAdded

    private void tblPesertaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesertaMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblPesertaMouseClicked

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        // TODO add your handling code here:
        simpanData();
        loadData();
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void txtSukesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSukesActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtSukesActionPerformed

    private void txtSKCKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSKCKActionPerformed
        // TODO add your handling code here:

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
            java.util.logging.Logger.getLogger(slkadministrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(slkadministrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(slkadministrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(slkadministrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new slkadministrasi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JComboBox<String> cmdKabKo;
    private javax.swing.JButton cmdKeluar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPeserta;
    private javax.swing.JTextField txt;
    private javax.swing.JComboBox<String> txtSKCK;
    private javax.swing.JComboBox<String> txtSukes;
    // End of variables declaration//GEN-END:variables
}
