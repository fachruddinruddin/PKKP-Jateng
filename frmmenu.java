/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tugas_akhir;

public class frmmenu extends javax.swing.JFrame {

    public frmmenu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnpeserta = new javax.swing.JMenuItem();
        mnuser = new javax.swing.JMenuItem();
        mnkota = new javax.swing.JMenuItem();
        mnkecamatan = new javax.swing.JMenuItem();
        mnkelurahan = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnSelAdmin = new javax.swing.JMenuItem();
        mnSelPen = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mnLPeserta = new javax.swing.JMenuItem();
        mnLSAdmin = new javax.swing.JMenuItem();
        mnLSNil = new javax.swing.JMenuItem();
        mnPesLolos = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        bckup = new javax.swing.JMenuItem();
        mnkeluar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 0));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 153, 153));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("                                             @Muhammad Fachruddin");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tugas_akhir/22222.jpg"))); // NOI18N

        jMenu1.setText("Data Master");

        mnpeserta.setText("Pendataan Peserta");
        mnpeserta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnpesertaActionPerformed(evt);
            }
        });
        jMenu1.add(mnpeserta);

        mnuser.setText("Data User");
        mnuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuserActionPerformed(evt);
            }
        });
        jMenu1.add(mnuser);

        mnkota.setText("Kabupaten/Kota");
        mnkota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnkotaActionPerformed(evt);
            }
        });
        jMenu1.add(mnkota);

        mnkecamatan.setText("Kecamatan");
        mnkecamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnkecamatanActionPerformed(evt);
            }
        });
        jMenu1.add(mnkecamatan);

        mnkelurahan.setText("Kelurahan");
        mnkelurahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnkelurahanActionPerformed(evt);
            }
        });
        jMenu1.add(mnkelurahan);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Transaksi");

        mnSelAdmin.setText("Seleksi Administrasi");
        mnSelAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSelAdminActionPerformed(evt);
            }
        });
        jMenu2.add(mnSelAdmin);

        mnSelPen.setText("Seleksi Penilaian");
        mnSelPen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSelPenActionPerformed(evt);
            }
        });
        jMenu2.add(mnSelPen);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Laporan");

        mnLPeserta.setText("Laporan Peserta");
        mnLPeserta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLPesertaActionPerformed(evt);
            }
        });
        jMenu3.add(mnLPeserta);

        mnLSAdmin.setText("Laporan Seleksi Administrasi");
        mnLSAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLSAdminActionPerformed(evt);
            }
        });
        jMenu3.add(mnLSAdmin);

        mnLSNil.setText("Laporan Seleksi Nilai");
        mnLSNil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLSNilActionPerformed(evt);
            }
        });
        jMenu3.add(mnLSNil);

        mnPesLolos.setText("Peserta Lolos");
        mnPesLolos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnPesLolosActionPerformed(evt);
            }
        });
        jMenu3.add(mnPesLolos);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Utility");
        jMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu4ActionPerformed(evt);
            }
        });

        bckup.setText("Back Up Data");
        bckup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bckupActionPerformed(evt);
            }
        });
        jMenu4.add(bckup);

        mnkeluar.setText("Keluar");
        mnkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnkeluarActionPerformed(evt);
            }
        });
        jMenu4.add(mnkeluar);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnpesertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnpesertaActionPerformed
        // TODO add your handling code here:
        new frmdatapeserta().setVisible(true);
        
    }//GEN-LAST:event_mnpesertaActionPerformed

    private void mnSelPenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSelPenActionPerformed
        // TODO add your handling code here:
        new slkpenilaian().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mnSelPenActionPerformed

    private void mnPesLolosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnPesLolosActionPerformed
        // TODO add your handling code here:
        new laplolos().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mnPesLolosActionPerformed

    private void jMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu4ActionPerformed

    private void mnkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnkeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_mnkeluarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void mnuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuserActionPerformed
        // TODO add your handling code here:
        new frmuser().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mnuserActionPerformed

    private void mnkotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnkotaActionPerformed
        // TODO add your handling code here:
        new frmkota().setVisible(true);
       
    }//GEN-LAST:event_mnkotaActionPerformed

    private void mnkecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnkecamatanActionPerformed
        // TODO add your handling code here:
        new frmkecamatan().setVisible(true);
        
    }//GEN-LAST:event_mnkecamatanActionPerformed

    private void mnkelurahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnkelurahanActionPerformed
        // TODO add your handling code here:
        new frmkelurahan().setVisible(true);
        
    }//GEN-LAST:event_mnkelurahanActionPerformed

    private void mnSelAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSelAdminActionPerformed
        // TODO add your handling code here:
        new slkadministrasi().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mnSelAdminActionPerformed

    private void mnLPesertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLPesertaActionPerformed
        // TODO add your handling code here:
        new lappeserta().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mnLPesertaActionPerformed

    private void mnLSAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLSAdminActionPerformed
        // TODO add your handling code here:
        new lapadministrasi().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mnLSAdminActionPerformed

    private void mnLSNilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLSNilActionPerformed
        // TODO add your handling code here:
        new lapseleksinilai().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mnLSNilActionPerformed

    private void bckupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bckupActionPerformed
        // TODO add your handling code here:
        new BackUpDatabase().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_bckupActionPerformed

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
            java.util.logging.Logger.getLogger(frmmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmmenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem bckup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem mnLPeserta;
    private javax.swing.JMenuItem mnLSAdmin;
    private javax.swing.JMenuItem mnLSNil;
    private javax.swing.JMenuItem mnPesLolos;
    private javax.swing.JMenuItem mnSelAdmin;
    private javax.swing.JMenuItem mnSelPen;
    private javax.swing.JMenuItem mnkecamatan;
    private javax.swing.JMenuItem mnkeluar;
    private javax.swing.JMenuItem mnkelurahan;
    private javax.swing.JMenuItem mnkota;
    private javax.swing.JMenuItem mnpeserta;
    private javax.swing.JMenuItem mnuser;
    // End of variables declaration//GEN-END:variables
}
