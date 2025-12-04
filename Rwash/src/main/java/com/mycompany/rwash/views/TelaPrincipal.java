
package com.mycompany.rwash.views;

import com.mycompany.rwash.Model.Usuario;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class TelaPrincipal extends JFrame {

    private RoundedButton btnMudarTelaLogin;
    private RoundedButton btnMudarTelaCadastro;
    private RoundedButton btnSaibaMais;
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5;
    private GradientPanel headerPanel, mainPanel;

    Usuario object = null;

    public TelaPrincipal() {
        initCustomComponents();
        setTitle("R-Wash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    private void initCustomComponents() {

headerPanel = new GradientPanel();
headerPanel.setLayout(new BorderLayout());
headerPanel.setPreferredSize(new Dimension(0, 90));

// (R-Wash)
JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
headerLeft.setOpaque(false);

jLabel5 = new JLabel("R-Wash");
jLabel5.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 32));
jLabel5.setForeground(java.awt.Color.WHITE);

headerLeft.add(jLabel5);

// Login + Cadastro
JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
headerRight.setOpaque(false);

btnMudarTelaLogin = new RoundedButton("LOGIN");
btnMudarTelaLogin.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18));
btnMudarTelaLogin.setPreferredSize(new Dimension(140, 40));
btnMudarTelaLogin.addActionListener((ActionEvent e) -> abrirLogin());
btnMudarTelaCadastro = new RoundedButton("CADASTRO");
btnMudarTelaCadastro.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18));
btnMudarTelaCadastro.setPreferredSize(new Dimension(160, 40));
btnMudarTelaCadastro.addActionListener((ActionEvent e) -> abrirCadastro());

headerRight.add(btnMudarTelaLogin);
headerRight.add(btnMudarTelaCadastro);

headerPanel.add(headerLeft, BorderLayout.WEST);
headerPanel.add(headerRight, BorderLayout.EAST);

// Separador
headerPanel.add(new JSeparator(), BorderLayout.SOUTH);

        mainPanel = new GradientPanel();
        mainPanel.setLayout(null);

        jLabel1 = new JLabel("Água limpa,");
        jLabel1.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 56));
        jLabel1.setForeground(java.awt.Color.WHITE);
        jLabel1.setBounds(80, 120, 800, 70);

        jLabel2 = new JLabel("futuro");
        jLabel2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 56));
        jLabel2.setForeground(new java.awt.Color(153, 50, 255));
        jLabel2.setBounds(80, 200, 800, 70);

        jLabel3 = new JLabel("sustentável");
        jLabel3.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 56));
        jLabel3.setForeground(java.awt.Color.WHITE);
        jLabel3.setBounds(80, 280, 900, 70);

        btnSaibaMais = new RoundedButton("SAIBA MAIS");
        btnSaibaMais.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        btnSaibaMais.setBounds(80, 370, 220, 55);
        btnSaibaMais.addActionListener((ActionEvent e) -> abrirPainelClienteNaoLogado());

        jLabel4 = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("/lavadora-de-roupas-maquina-de-lavar-roupas-maquina-com-porta-frontal-frigelar-blog-da-frigelar.jpg"));
        jLabel4.setIcon(icon);
        jLabel4.setBounds(800, 100, 420, 390);

        mainPanel.add(jLabel1);
        mainPanel.add(jLabel2);
        mainPanel.add(jLabel3);
        mainPanel.add(btnSaibaMais);
        mainPanel.add(jLabel4);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(headerPanel, BorderLayout.PAGE_START);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    private void abrirLogin() {
        this.setVisible(false);
        new TelaLogin().setVisible(true);
    }

    private void abrirCadastro() {
        this.setVisible(false);
        new TelaCadastroAntigo().setVisible(true);
    }

    private void abrirPainelClienteNaoLogado() {
        this.setVisible(false);
        new PainelClienteNaoLogado().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal t = new TelaPrincipal();
            t.setVisible(true);
        });
    }
}
