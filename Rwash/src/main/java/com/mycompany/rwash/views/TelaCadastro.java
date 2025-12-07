package com.mycompany.rwash.views;

import com.mycompany.rwash.DAO.UsuarioDAO;
import com.mycompany.rwash.Model.Usuario;

import java.awt.*;
import javax.swing.*;

public class TelaCadastro extends JFrame {

    Usuario objAlterar = null;

    // Mantendo os nomes originais
    private javax.swing.JButton btnCadastro;
    private javax.swing.JButton btnMudarParaTelaLogin;

    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelSenha;
    private javax.swing.JLabel jLabelCPF;

    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;

    private javax.swing.JSeparator jSeparatorNome;
    private javax.swing.JSeparator jSeparatorEmail;
    private javax.swing.JSeparator jSeparatorSenha;
    private javax.swing.JSeparator jSeparatorCPF;

    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextField txtEmailCliente;
    private javax.swing.JPasswordField txtSenhaCliente;
    private javax.swing.JTextField txtCpfCliente;

    public TelaCadastro() {
        initCustomUI();
        setTitle("R-Wash - Cadastro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    private void initCustomUI() {

        // Painel superior — TÍTULO (IGUAL AO LOGIN)
        jPanel4 = new GradientPanel();
        jPanel4.setPreferredSize(new Dimension(0, 150));
        jPanel4.setLayout(new BorderLayout());

        jLabel1 = new JLabel("CADASTRE-SE", SwingConstants.CENTER);
        jLabel1.setFont(new Font("Arial", Font.BOLD, 48));
        jLabel1.setForeground(new Color(153, 50, 255));

        jPanel4.add(jLabel1, BorderLayout.CENTER);

        // Painel central com gradiente
        jPanel3 = new JPanel(new GridLayout(1, 1));
        jPanel1 = new GradientPanel();
        jPanel1.setLayout(new GridBagLayout());

        // Painel BRANCO (card) — EXATAMENTE COMO NO LOGIN
        jPanel2 = new JPanel();
        jPanel2.setPreferredSize(new Dimension(380, 480));
        jPanel2.setBackground(Color.WHITE);
        jPanel2.setLayout(null);

        // -------- CAMPOS DO CADASTRO (seguindo o login) ---------

        // Nome
        jLabelNome = new JLabel("Nome");
        jLabelNome.setFont(new Font("Arial", Font.PLAIN, 16));
        jLabelNome.setBounds(30, 30, 200, 22);

        txtNomeCliente = new JTextField();
        txtNomeCliente.setBounds(30, 55, 300, 28);
        txtNomeCliente.setBorder(null);

        jSeparatorNome = new JSeparator();
        jSeparatorNome.setBounds(30, 83, 300, 1);

        // Email
        jLabelEmail = new JLabel("Email");
        jLabelEmail.setFont(new Font("Arial", Font.PLAIN, 16));
        jLabelEmail.setBounds(30, 110, 200, 22);

        txtEmailCliente = new JTextField();
        txtEmailCliente.setBounds(30, 135, 300, 28);
        txtEmailCliente.setBorder(null);

        jSeparatorEmail = new JSeparator();
        jSeparatorEmail.setBounds(30, 163, 300, 1);

        // Senha
        jLabelSenha = new JLabel("Senha");
        jLabelSenha.setFont(new Font("Arial", Font.PLAIN, 16));
        jLabelSenha.setBounds(30, 190, 200, 22);

        txtSenhaCliente = new JPasswordField();
        txtSenhaCliente.setBounds(30, 215, 300, 28);
        txtSenhaCliente.setBorder(null);

        jSeparatorSenha = new JSeparator();
        jSeparatorSenha.setBounds(30, 243, 300, 1);

        // CPF
        jLabelCPF = new JLabel("CPF");
        jLabelCPF.setFont(new Font("Arial", Font.PLAIN, 16));
        jLabelCPF.setBounds(30, 270, 200, 22);

        txtCpfCliente = new JTextField();
        txtCpfCliente.setBounds(30, 295, 300, 28);
        txtCpfCliente.setBorder(null);

        jSeparatorCPF = new JSeparator();
        jSeparatorCPF.setBounds(30, 323, 300, 1);

        // Texto “Já tem conta?”
        jLabel2 = new JLabel("Já tem uma conta?");
        jLabel2.setFont(new Font("Arial", Font.PLAIN, 14));
        jLabel2.setBounds(85, 340, 150, 25);

        // Botão LOGIN
        btnMudarParaTelaLogin = new JButton("Login");
        btnMudarParaTelaLogin.setBorder(null);
        btnMudarParaTelaLogin.setContentAreaFilled(false);
        btnMudarParaTelaLogin.setForeground(new Color(153, 50, 255));
        btnMudarParaTelaLogin.setBounds(220, 340, 120, 25);
        btnMudarParaTelaLogin.addActionListener((e) -> abrirLogin());

        // Botão CADASTRO (arredondado, estilo login)
        btnCadastro = new RoundedButton("CADASTRAR");
        btnCadastro.setBounds(75, 380, 230, 50);
        btnCadastro.setFont(new Font("Arial", Font.BOLD, 18));
        btnCadastro.addActionListener((e) -> registrar());

        // Adiciona tudo ao card branco
        jPanel2.add(jLabelNome);
        jPanel2.add(txtNomeCliente);
        jPanel2.add(jSeparatorNome);

        jPanel2.add(jLabelEmail);
        jPanel2.add(txtEmailCliente);
        jPanel2.add(jSeparatorEmail);

        jPanel2.add(jLabelSenha);
        jPanel2.add(txtSenhaCliente);
        jPanel2.add(jSeparatorSenha);

        jPanel2.add(jLabelCPF);
        jPanel2.add(txtCpfCliente);
        jPanel2.add(jSeparatorCPF);

        jPanel2.add(jLabel2);
        jPanel2.add(btnMudarParaTelaLogin);
        jPanel2.add(btnCadastro);

        // Adiciona card
        jPanel1.add(jPanel2);
        jPanel3.add(jPanel1);

        // Adiciona à janela
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jPanel4, BorderLayout.NORTH);
        getContentPane().add(jPanel3, BorderLayout.CENTER);
    }

    // -------------------------------
    //   BACK-END
    // -------------------------------
    
    private boolean validarEmail(String email) {
    // Regex básica válida para emails comuns
    String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    return email.matches(regex);
  }
    
    private boolean validarCPF(String cpf) {
        // Remove caracteres especiais caso o usuário digite com máscara
        cpf = cpf.replaceAll("[^0-9]", "");

        return cpf.length() == 11 && cpf.matches("\\d{11}");
    }


   private void registrar() {

    String nome = txtNomeCliente.getText().trim();
    String email = txtEmailCliente.getText().trim();
    String senha = new String(txtSenhaCliente.getPassword()).trim();
    String cpf = txtCpfCliente.getText().trim();

    if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validação do e-mail
    if (!validarEmail(email)) {
        JOptionPane.showMessageDialog(this, "E-mail inválido! Digite um e-mail válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validação do CPF
    if (!validarCPF(cpf)) {
        JOptionPane.showMessageDialog(this, "CPF inválido! Digite um CPF com 11 números.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Usuario novo = new Usuario();
    novo.setNomeCliente(nome);
    novo.setEmailCliente(email);
    novo.setSenhaCliente(senha);
    novo.setCpfCliente(cpf);

    boolean sucesso = UsuarioDAO.salvar(novo);

    if (sucesso) {
        JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
        this.dispose();
        new TelaLogin().setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Erro ao cadastrar!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}


    private void abrirLogin() {
        setVisible(false);
        new TelaLogin().setVisible(true);
    }
}
