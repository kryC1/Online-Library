/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package all_about_gui;

import Objects.MyConnection;
import Objects.Book;
import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author red_k
 */
public class BookPanel extends javax.swing.JFrame {

    String title = MemberPanel.titleForBookPanel;
    ArrayList<Book> book = Book.bookList(title, 2);
    String author = book.get(0).getAuthor();
    String publisher = book.get(0).getPublisher();
    String category = "";
    String path = "C:\\Users\\red_k\\Documents\\NetBeansProjects\\xxx\\src\\images\\";
    int ID = book.get(0).getID();
    
    
    public BookPanel() {
        initComponents();
        float[] compArray;
        this.setBackground(new Color(0,0,0,0));
      //  this.setBackground(new Color/*.HSBtoRGB*/(0,0,0,0));
        iconPanel.setIcon(new ImageIcon(path + ID + ".jpg"));
        titleT.setText(title);
        authorT.setText(author);
        publisherT.setText(publisher);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        iconPanel = new javax.swing.JLabel();
        titleT = new javax.swing.JTextField();
        authorT = new javax.swing.JTextField();
        publisherT = new javax.swing.JTextField();
        closeButton = new javax.swing.JButton();
        urlButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 51, 51));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        iconPanel.setBackground(new java.awt.Color(204, 204, 204));
        iconPanel.setOpaque(true);

        titleT.setEditable(false);

        authorT.setEditable(false);
        authorT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorTActionPerformed(evt);
            }
        });

        publisherT.setEditable(false);
        publisherT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                publisherTActionPerformed(evt);
            }
        });

        closeButton.setText("CLOSE");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        urlButton.setText("BUY AND MORE INFORMATION!");
        urlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(publisherT, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                            .addComponent(authorT, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                            .addComponent(titleT, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                            .addComponent(urlButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(titleT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(authorT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(publisherT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(urlButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(closeButton)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void authorTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authorTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_authorTActionPerformed

    private void publisherTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_publisherTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_publisherTActionPerformed

    private void urlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlButtonActionPerformed
        try {
            Desktop d = Desktop.getDesktop();
            try {
                d.browse(new URI(MyConnection.bookPage(ID)));
            } catch (IOException ex) {
                Logger.getLogger(BookPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(BookPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_urlButtonActionPerformed

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
            java.util.logging.Logger.getLogger(BookPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField authorT;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel iconPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField publisherT;
    private javax.swing.JTextField titleT;
    private javax.swing.JButton urlButton;
    // End of variables declaration//GEN-END:variables
}
