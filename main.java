/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugas_akhir;
import java.sql.Connection;
/**
 *
 * @author LENOVO
 */
public class main {
     public static void main(String args[]) {
        koneksiMysql kon = new koneksiMysql("papbo");
        Connection c = kon.getConnection();
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmlogin().setVisible(true);
            }
        });
    }
}
