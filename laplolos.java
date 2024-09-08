/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tugas_akhir;

import java.awt.Font;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.text.MessageFormat;
import javax.swing.SwingWorker;

public class laplolos extends javax.swing.JFrame {

    Connection Con;
    ResultSet RsBrg;
    Statement stm;
    int kuotaI = 0;
    DefaultTableModel tableModel = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "NIK", "Nama Lengkap", "Usia", "IPK", "Alamat", "Kab/Kota", "Kecamatan", "Kelurahan", "Telephone", "Email", "Nilai Tertulis", "Nilai Wawancara", "Nilai Akhir", "Rangking"
            });

    public laplolos() {
        initComponents();
        open_db();
        getKuota();
        baca_data(kuotaI);
        setVisible(true);
        setLocationRelativeTo(null);
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

    private void getKuota() {
        try {
            stm = Con.createStatement();
            RsBrg = stm.executeQuery("SELECT jumlah_kuota as kuota FROM kuota ORDER BY id DESC LIMIT 0;");
            if (RsBrg.next()) {
                kuotaI = RsBrg.getInt("kuota");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void baca_data(int kuota) {
        try {
            stm = Con.createStatement();
            String query = "SELECT * FROM peserta p INNER JOIN administrasi s ON p.kd_pes=s.kd_pes INNER JOIN penilaian t ON p.kd_pes=t.kd_pes INNER JOIN hasil_akhir h ON p.kd_pes=h.kd_pes ORDER BY \n"
                    + "h.nilai_total DESC, t.n_tulis DESC, p.ipk DESC LIMIT " + kuota;
            RsBrg = stm.executeQuery(query);

            tableModel.setRowCount(0);
            int x = 1;
            while (RsBrg.next()) {
                Object[] row = new Object[15];
                row[0] = RsBrg.getInt("kd_pes");
                row[1] = RsBrg.getString("nik");
                row[2] = RsBrg.getString("nama");
                row[3] = RsBrg.getInt("usia");
                row[4] = RsBrg.getFloat("ipk");
                row[5] = RsBrg.getString("alamat");
                row[6] = getNm(RsBrg.getInt("id_kab"), "kota", "kota");
                row[7] = getNm(RsBrg.getInt("id_kec"), "kecamatan", "kec");
                row[8] = getNm(RsBrg.getInt("id_kel"), "kelurahan", "kel");
                row[9] = RsBrg.getString("tlp");
                row[10] = RsBrg.getString("email");
                row[11] = RsBrg.getInt("n_tulis");
                row[12] = RsBrg.getString("n_wawancara");
                row[13] = RsBrg.getString("nilai_total");
                row[14] = x;
                x++;

                // Tambahkan baris ke DefaultTableModel
                tableModel.addRow(row);
            }

            // Atur model tabel dengan model yang baru
            tblPeserta.setModel(tableModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
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

    private void simpanData() {
        int tKuota = Integer.parseInt(txtK.getText());
        String query = "INSERT INTO kuota (jumlah_kuota) values (?)";
        try (PreparedStatement stmt = Con.prepareStatement(query)) {

            stmt.setInt(1, tKuota);
            stmt.addBatch();

            int[] result = stmt.executeBatch();
            System.out.println("Batch insert completed. Rows inserted: " + result.length);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Export PDF
    private MessageFormat createFormat(String source) {
        String text = source;
        if (text != null && text.length() > 0) {
            try {
                return new MessageFormat(text);
            } catch (IllegalArgumentException e) {
                error("Sorry");
            }
        }
        return null;
    }

    private void message(boolean error, String msg) {
        int type = (error ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(this, msg, "Printing", type);
    }

    private void error(String msg) {
        message(true, msg);
    }

    private class PrintingTask extends SwingWorker<Boolean, Object> {

        private final MessageFormat headerFormat;
        private final MessageFormat footerFormat;
        private final boolean interactive;

        public PrintingTask(MessageFormat header, MessageFormat footer, boolean interactive) {
            this.headerFormat = header;
            this.footerFormat = footer;
            this.interactive = interactive;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            return text.print(headerFormat, footerFormat, true, null, null, interactive);
        }

        @Override
        protected void done() {
            try {
                boolean completed = get();
                if (completed) {
                    JOptionPane.showMessageDialog(laplolos.this,
                            "Printing completed successfully", "Print", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(laplolos.this,
                            "Printing canceled", "Print", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(laplolos.this,
                        "Error printing: " + e.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
            }
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

        cmdKeluar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnCetak = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtK = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPeserta = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextArea();
        cmdCetak = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cmdKeluar.setBackground(new java.awt.Color(204, 0, 0));
        cmdKeluar.setForeground(new java.awt.Color(255, 255, 255));
        cmdKeluar.setText("Keluar");
        cmdKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeluarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("LAPORAN LOLOS SELEKSI");

        btnCetak.setText("Cetak");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel3.setText("Input Jumlah Kuota");

        btnSimpan.setBackground(new java.awt.Color(0, 0, 204));
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        tblPeserta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPeserta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPesertaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPeserta);

        text.setColumns(20);
        text.setRows(5);
        jScrollPane2.setViewportView(text);

        cmdCetak.setText("Cetak PDF");
        cmdCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(331, 331, 331)
                .addComponent(jLabel1)
                .addGap(0, 335, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtK, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnCetak)
                                .addGap(18, 18, 18)
                                .addComponent(cmdCetak)
                                .addGap(18, 18, 18)
                                .addComponent(cmdKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSimpan)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCetak)
                    .addComponent(cmdCetak)
                    .addComponent(cmdKeluar))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // TODO add your handling code here:
        try {
            ExportToExcel ex = new ExportToExcel(tblPeserta, new File("Data_Lolos_Seleksi.xls"));
            //exportToExcel(tblBrg, new File("DataBarang.xls"));
            JOptionPane.showMessageDialog(null, "Sukses Export data.....");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnCetakActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        simpanData();
        int tk = Integer.parseInt(txtK.getText());
        baca_data(tk);
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tblPesertaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesertaMouseClicked
        // TODO add your handling code here:
        //        setField();
    }//GEN-LAST:event_tblPesertaMouseClicked

    private void cmdCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCetakActionPerformed
        // TODO add your handling code here:
        StringBuilder report = new StringBuilder();
        
        
        java.util.Date currentDate = new java.util.Date();
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = dateFormat.format(currentDate);
        
        report.append("Tanggal: ").append(formattedDate).append("\n");
        report.append("Laporan Peserta Lolos\n\n");
        report.append(String.format("%-18s %-18s %-5s %-5s %-18s %-10s %-15s %-12s %-17s %-8s %-8s %-8s %-8s\n",
                "NIK", "Nama", "Usia", "IPK", "Alamat", "Kota", "Kecamatan", "Kelurahan", "Email", "N.tulis", "N.Wwncr", "N.Akhr", "Rangking"));
        report.append("-".repeat(160)).append("\n");

        for (int i = 0; i < tblPeserta.getRowCount(); i++) {
            report.append(String.format("%-18s %-18s %-5s %-5s %-18s %-10s %-15s %-12s %-17s %-8s %-8s %-8s %-8s\n",
                    tblPeserta.getValueAt(i, 1),
                    tblPeserta.getValueAt(i, 2),
                    tblPeserta.getValueAt(i, 3),
                    tblPeserta.getValueAt(i, 4),
                    tblPeserta.getValueAt(i, 5),
                    tblPeserta.getValueAt(i, 6),
                    tblPeserta.getValueAt(i, 7),
                    tblPeserta.getValueAt(i, 8),
                    tblPeserta.getValueAt(i, 10),
                    tblPeserta.getValueAt(i, 11),
                    tblPeserta.getValueAt(i, 12),
                    tblPeserta.getValueAt(i, 13),
                    tblPeserta.getValueAt(i, 14)
            ));
        }

        text.setText(report.toString());
        text.setFont(new Font("Monospaced", Font.PLAIN, 11));

        MessageFormat header = new MessageFormat("Laporan Peserta Lolos - Halaman {0}");
        MessageFormat footer = new MessageFormat("Halaman {0}");

        PrintingTask task = new PrintingTask(header, footer, true);
        task.execute();
    }//GEN-LAST:event_cmdCetakActionPerformed

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
            java.util.logging.Logger.getLogger(laplolos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(laplolos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(laplolos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(laplolos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new laplolos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton cmdCetak;
    private javax.swing.JButton cmdKeluar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblPeserta;
    private javax.swing.JTextArea text;
    private javax.swing.JTextField txtK;
    // End of variables declaration//GEN-END:variables
}
