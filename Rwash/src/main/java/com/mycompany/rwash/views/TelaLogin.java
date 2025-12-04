package com.mycompany.rwash.views;

import com.mycompany.rwash.Model.Usuario;
import com.mycompany.rwash.DAO.UsuarioDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class TelaLogin extends JFrame {

    Usuario objAlterar = null;

    private javax.swing.JButton btnEsquecerSenha;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnMudarTelaCadastrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField txtEmailCliente;
    private javax.swing.JPasswordField txtSenhaCliente;

    public TelaLogin() {
        initCustomUI();
        setTitle("R-Wash - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    private void initCustomUI() {

        // Painel superior 
        jPanel4 = new GradientPanel();
        jPanel4.setPreferredSize(new Dimension(0, 150));
        jPanel4.setLayout(new BorderLayout());

        jLabel1 = new JLabel("CONECTE-SE", SwingConstants.CENTER);
        jLabel1.setFont(new Font("Arial", Font.BOLD, 48));
        jLabel1.setForeground(new Color(153, 50, 255));

        jPanel4.add(jLabel1, BorderLayout.CENTER);

        // Painel gradiente
        jPanel3 = new JPanel(new GridLayout(1, 1));
        jPanel1 = new GradientPanel();
        jPanel1.setLayout(new GridBagLayout());

        // Painel branco 
        jPanel2 = new JPanel();
        jPanel2.setPreferredSize(new Dimension(380, 420));
        jPanel2.setBackground(Color.WHITE);
        jPanel2.setLayout(null);

        // Email
        jLabel3 = new JLabel("Email");
        jLabel3.setFont(new Font("Arial", Font.PLAIN, 16));
        jLabel3.setBounds(30, 40, 200, 22);

        txtEmailCliente = new JTextField();
        txtEmailCliente.setBounds(30, 65, 300, 28);
        txtEmailCliente.setBorder(null);

        jSeparator1 = new JSeparator();
        jSeparator1.setBounds(30, 93, 300, 1);

        // Senha
        jLabel4 = new JLabel("Senha");
        jLabel4.setFont(new Font("Arial", Font.PLAIN, 16));
        jLabel4.setBounds(30, 125, 200, 22);

        txtSenhaCliente = new JPasswordField();
        txtSenhaCliente.setBounds(30, 150, 300, 28);
        txtSenhaCliente.setBorder(null);

        jSeparator3 = new JSeparator();
        jSeparator3.setBounds(30, 178, 300, 1);

        // Esqueceu senha
        btnEsquecerSenha = new JButton("Esqueceu a senha?");
        btnEsquecerSenha.setBounds(90, 190, 200, 30);
        btnEsquecerSenha.setHorizontalAlignment(SwingConstants.CENTER);
        btnEsquecerSenha.setBorder(null);
        btnEsquecerSenha.setContentAreaFilled(false);
        btnEsquecerSenha.setForeground(new Color(120, 120, 120));
        btnEsquecerSenha.setFocusPainted(false);

        // Não tem conta?
        jLabel2 = new JLabel("Não tem uma conta?");
        jLabel2.setFont(new Font("Arial", Font.PLAIN, 14));
        jLabel2.setBounds(75, 250, 150, 25);

        // Botão cadastrar
        btnMudarTelaCadastrar = new JButton("Cadastrar");
        btnMudarTelaCadastrar.setBorder(null);
        btnMudarTelaCadastrar.setContentAreaFilled(false);
        btnMudarTelaCadastrar.setForeground(new Color(153, 50, 255));
        btnMudarTelaCadastrar.setBounds(220, 250, 120, 25);
        btnMudarTelaCadastrar.addActionListener((e) -> abrirCadastro());

        // Botão LOGIN 
        btnLogin = new RoundedButton("LOGIN");
        btnLogin.setBounds(75, 290, 230, 50);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 18));
        btnLogin.addActionListener((e) -> btnLoginActionPerformed(null));

        // Adiciona no painel branco
        jPanel2.add(jLabel3);
        jPanel2.add(txtEmailCliente);
        jPanel2.add(jSeparator1);

        jPanel2.add(jLabel4);
        jPanel2.add(txtSenhaCliente);
        jPanel2.add(jSeparator3);

        jPanel2.add(btnEsquecerSenha);

        jPanel2.add(jLabel2);
        jPanel2.add(btnMudarTelaCadastrar);

        jPanel2.add(btnLogin);

        jPanel1.add(jPanel2);
        jPanel3.add(jPanel1);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jPanel4, BorderLayout.NORTH);
        getContentPane().add(jPanel3, BorderLayout.CENTER);
    }

    // -------------------------------
    //   BACK-END 
    // -------------------------------
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {

       String email = txtEmailCliente.getText();
    String senha = new String(txtSenhaCliente.getPassword()); 

    // Autenticar no DAO
    int idClienteLogado = UsuarioDAO.autenticar(email, senha);

    if (idClienteLogado > 0) {
        JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
        this.dispose(); // Fecha a tela de login

        // Verifica se o cliente já fez uma compra
        boolean jaComprou = UsuarioDAO.clienteJaComprou(idClienteLogado);

        if (jaComprou) {
        // Cliente ainda não comprou, vai para a tela de compra
        DashBoardCliente dashboard = new DashBoardCliente(idClienteLogado);
        dashboard.setVisible(true);
        
    } else {
        
        // Cliente já comprou → vai para o painel do cliente
        PainelCliente painel = new PainelCliente(idClienteLogado);
        painel.setVisible(true);
    }

    } else {
        JOptionPane.showMessageDialog(rootPane, 
            "Falha no Login. Email ou Senha inválidos.", 
            "Erro de Autenticação", 
            JOptionPane.ERROR_MESSAGE);
    
}
    }

    private void abrirCadastro() {
        setVisible(false);
        new TelaCadastro().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}
