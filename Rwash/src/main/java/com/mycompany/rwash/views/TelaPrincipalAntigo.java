/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.rwash.views;

import com.mycompany.rwash.Model.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;




/**
 *
 * @author aluno
 */
public class TelaPrincipalAntigo extends javax.swing.JFrame {
 
      Usuario object = null; 
    
      
    public TelaPrincipalAntigo() {
        initComponents();
            this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    aplicarEstiloModerno(); 
}


private void aplicarEstiloModerno() {
    
    GradientPanel gradient = new GradientPanel();
    gradient.setLayout(new java.awt.BorderLayout());

   
    try {
        java.awt.Container old = getContentPane();
        
        old.remove(jPanel2);
        old.remove(jPanel1);
    } catch (Exception ex) {
        
    }

  
    gradient.add(jPanel2, java.awt.BorderLayout.PAGE_START);
    gradient.add(jPanel1, java.awt.BorderLayout.CENTER);
   
    setContentPane(gradient);

    jPanel2.setOpaque(false);
    jPanel1.setOpaque(false);

  
    jLabel1.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 60));
    jLabel2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 60));
    jLabel3.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 60));
    jLabel5.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 36));

    jLabel1.setForeground(java.awt.Color.WHITE);
    jLabel3.setForeground(java.awt.Color.WHITE);
    jLabel5.setForeground(java.awt.Color.WHITE);

    
    substituirBotaoComAtualizacaoDeReferencia(btnMudarTelaLogin, "LOGIN");
    substituirBotaoComAtualizacaoDeReferencia(btnMudarTelaCadastro, "CADASTRO");
    substituirBotaoComAtualizacaoDeReferencia(btnSaibaMais, "SAIBA MAIS");

  
    revalidate();
    repaint();
}


private void substituirBotaoComAtualizacaoDeReferencia(javax.swing.JButton original, String text) {
    if (original == null) return;

    RoundedButton novo = new RoundedButton(text);

    
    novo.setPreferredSize(original.getPreferredSize());
    novo.setMinimumSize(original.getMinimumSize());
    novo.setMaximumSize(original.getMaximumSize());
    novo.setToolTipText(original.getToolTipText());
    novo.setIcon(original.getIcon());
    novo.setEnabled(original.isEnabled());
    novo.setVisible(original.isVisible());
    novo.setCursor(original.getCursor());

  
    java.awt.event.ActionListener[] listeners = original.getActionListeners();
    for (java.awt.event.ActionListener al : listeners) {
        novo.addActionListener(al);
        original.removeActionListener(al); 
    }

   
    java.awt.event.MouseListener[] mls = original.getMouseListeners();
    for (java.awt.event.MouseListener ml : mls) {
        novo.addMouseListener(ml);
    }

    
    java.awt.Container parent = original.getParent();
    if (parent != null) {
        int index = -1;
        java.awt.Component[] comps = parent.getComponents();
        for (int i = 0; i < comps.length; i++) {
            if (comps[i] == original) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            java.awt.LayoutManager lm = parent.getLayout();
            parent.remove(original);

            
            parent.add(novo, index);
            parent.revalidate();
            parent.repaint();
        } else {
          
            parent.add(novo);
        }
    }

   
    if (original == btnMudarTelaLogin) {
        btnMudarTelaLogin = novo;
    } else if (original == btnMudarTelaCadastro) {
        btnMudarTelaCadastro = novo;
    } else if (original == btnSaibaMais) {
        btnSaibaMais = novo;
    }
}
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        jPanel2 = new JPanel();
        btnMudarTelaLogin = new JButton();
        btnMudarTelaCadastro = new JButton();
        jLabel5 = new JLabel();
        jSeparator1 = new JSeparator();
        jSeparator2 = new JSeparator();
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        btnSaibaMais = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new Color(23, 21, 56));

        btnMudarTelaLogin.setBackground(new Color(23, 21, 56));
        btnMudarTelaLogin.setFont(new Font("Arial", 0, 24)); // NOI18N
        btnMudarTelaLogin.setForeground(new Color(153, 50, 255));
        btnMudarTelaLogin.setIcon(new ImageIcon(getClass().getResource("/5340287_man_people_person_user_users_icon.png"))); // NOI18N
        btnMudarTelaLogin.setText("LOGIN");
        btnMudarTelaLogin.setBorder(null);
        btnMudarTelaLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnMudarTelaLoginActionPerformed(evt);
            }
        });

        btnMudarTelaCadastro.setBackground(new Color(23, 21, 56));
        btnMudarTelaCadastro.setFont(new Font("Arial", 0, 24)); // NOI18N
        btnMudarTelaCadastro.setForeground(new Color(153, 50, 255));
        btnMudarTelaCadastro.setIcon(new ImageIcon(getClass().getResource("/5340287_man_people_person_user_users_icon.png"))); // NOI18N
        btnMudarTelaCadastro.setText("CADASTRO");
        btnMudarTelaCadastro.setBorder(null);
        btnMudarTelaCadastro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnMudarTelaCadastroActionPerformed(evt);
            }
        });

        jLabel5.setFont(new Font("Arial", 0, 36)); // NOI18N
        jLabel5.setForeground(new Color(255, 255, 255));
        jLabel5.setText("R-Wash");

        jSeparator1.setBackground(new Color(255, 255, 255));
        jSeparator1.setForeground(new Color(255, 255, 255));

        jSeparator2.setBackground(new Color(255, 255, 255));
        jSeparator2.setForeground(new Color(255, 255, 255));
        jSeparator2.setOrientation(SwingConstants.VERTICAL);

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel5)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 1003, Short.MAX_VALUE)
                .addComponent(btnMudarTelaLogin)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnMudarTelaCadastro)
                .addGap(25, 25, 25))
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 341, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMudarTelaLogin))
                    .addComponent(btnMudarTelaCadastro)
                    .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(SwingConstants.VERTICAL, new Component[] {btnMudarTelaCadastro, btnMudarTelaLogin});

        getContentPane().add(jPanel2, BorderLayout.PAGE_START);

        jPanel1.setBackground(new Color(23, 21, 56));
        jPanel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jPanel1.setAlignmentY(0.0F);
        jPanel1.setLayout(new GridBagLayout());

        jLabel1.setFont(new Font("Arial", 0, 60)); // NOI18N
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setText("Água limpa,");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(178, 163, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new Font("Arial", 0, 60)); // NOI18N
        jLabel2.setForeground(new Color(153, 50, 255));
        jLabel2.setText("futuro ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 144;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(0, 163, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new Font("Arial", 0, 60)); // NOI18N
        jLabel3.setForeground(new Color(255, 255, 255));
        jLabel3.setText("sustentável");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(0, 163, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel4.setIcon(new ImageIcon(getClass().getResource("/lavadora-de-roupas-maquina-de-lavar-roupas-maquina-com-porta-frontal-frigelar-blog-da-frigelar.jpg"))); // NOI18N
        jLabel4.setHorizontalTextPosition(SwingConstants.CENTER);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.ipadx = -200;
        gridBagConstraints.ipady = -117;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(178, 417, 183, 133);
        jPanel1.add(jLabel4, gridBagConstraints);

        btnSaibaMais.setBackground(new Color(153, 50, 255));
        btnSaibaMais.setFont(new Font("Arial", 0, 18)); // NOI18N
        btnSaibaMais.setForeground(new Color(255, 255, 255));
        btnSaibaMais.setText("SAIBA MAIS");
        btnSaibaMais.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSaibaMaisActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 26;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(18, 197, 183, 0);
        jPanel1.add(btnSaibaMais, gridBagConstraints);

        getContentPane().add(jPanel1, BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMudarTelaLoginActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnMudarTelaLoginActionPerformed
   setVisible(false);
   TelaLoginAntigo janela = new TelaLoginAntigo();
   janela.setVisible(true);
   
    }//GEN-LAST:event_btnMudarTelaLoginActionPerformed

    private void btnMudarTelaCadastroActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnMudarTelaCadastroActionPerformed
   setVisible(false);
   TelaCadastroAntigo janela = new TelaCadastroAntigo();
   janela.setVisible(true);    }//GEN-LAST:event_btnMudarTelaCadastroActionPerformed

    private void btnSaibaMaisActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSaibaMaisActionPerformed
        setVisible(false);
        PainelCliente janela = new PainelCliente();
    }//GEN-LAST:event_btnSaibaMaisActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipalAntigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipalAntigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipalAntigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipalAntigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipalAntigo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnMudarTelaCadastro;
    private JButton btnMudarTelaLogin;
    private JButton btnSaibaMais;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
