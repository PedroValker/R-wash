package com.mycompany.rwash.views;

import com.mycompany.rwash.DAO.UsuarioDAO;
import com.mycompany.rwash.Model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class TelaCadastro extends JFrame {

    // Componentes
    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JTextField txtCPF;
    
    private RoundedButton btnCadastrar;
    private JButton btnVoltarLogin;

    public TelaCadastro() {
        initUI();
        setTitle("R-Wash | Criar Conta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    // ==========================================
    //       CLASSES DE DESIGN E ESTILO
    // ==========================================

    // 1. Painel Gradiente (Fundo)
    class GradientPanel extends JPanel {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            int w = getWidth(), h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, new Color(23, 21, 56), w, h, new Color(60, 0, 120));
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);
        }
    }

<<<<<<< HEAD
    // 2. Painel Translúcido (Card)
    class TranslucentPanel extends JPanel {
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Preto com transparência (Alpha 100)
            g2.setColor(new Color(0, 0, 0, 100)); 
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    // 3. Botão Principal Arredondado
    public static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 18));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(153, 50, 255)); // Roxo Neon
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    // 4. Estilo para Botão Link
    private void styleLinkButton(JButton b) {
        b.setFont(new Font("Arial", Font.PLAIN, 14));
        b.setForeground(new Color(200, 200, 255));
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setForeground(Color.WHITE); }
            @Override public void mouseExited(MouseEvent e) { b.setForeground(new Color(200, 200, 255)); }
        });
    }

    // ==========================================
    //            CONSTRUÇÃO DA TELA
    // ==========================================

    private void initUI() {
        // Painel Principal Gradiente
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout()); 
        setContentPane(mainPanel);

        // --- HEADER ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setOpaque(false);
        JLabel title = new JLabel("R-Wash");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);
        
        GridBagConstraints gbcHead = new GridBagConstraints();
        gbcHead.gridx = 0; gbcHead.gridy = 0;
        gbcHead.weightx = 1.0; gbcHead.anchor = GridBagConstraints.NORTHWEST;
        gbcHead.insets = new Insets(20, 30, 0, 0);
        mainPanel.add(headerPanel, gbcHead);

        // --- CARD DE CADASTRO ---
        TranslucentPanel signupCard = new TranslucentPanel();
        signupCard.setOpaque(false);
        signupCard.setLayout(new GridBagLayout());
        
        // --- AQUI QUE AUMENTA O TAMANHO ---
        // Largura aumentada para 550 (era 450). Altura mantida 650.
        signupCard.setPreferredSize(new Dimension(550, 650)); 

        GridBagConstraints gbc = new GridBagConstraints();
        // Margens internas (laterais 40px para aproveitar a largura nova)
        gbc.insets = new Insets(8, 40, 8, 40); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // 1. Título "Crie sua conta"
        JLabel lblTitulo = new JLabel("Criar Conta");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 40, 20, 40);
        signupCard.add(lblTitulo, gbc);

        // 2. Nome
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 0, 40);
        signupCard.add(criarLabel("Nome Completo"), gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 10, 40);
        txtNome = criarTextField();
        signupCard.add(txtNome, gbc);

        // 3. Email
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 0, 40);
        signupCard.add(criarLabel("Email"), gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 10, 40);
        txtEmail = criarTextField();
        signupCard.add(txtEmail, gbc);

        // 4. CPF
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 0, 40);
        signupCard.add(criarLabel("CPF"), gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 10, 40);
        txtCPF = criarTextField();
        signupCard.add(txtCPF, gbc);

        // 5. Senha
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 0, 40);
        signupCard.add(criarLabel("Senha"), gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 25, 40);
        txtSenha = criarPasswordField();
        signupCard.add(txtSenha, gbc);

        // 6. Botão CADASTRAR
        btnCadastrar = new RoundedButton("REGISTRAR");
        btnCadastrar.setPreferredSize(new Dimension(0, 50));
        btnCadastrar.addActionListener(e -> acaoCadastrar());
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 20, 40);
        signupCard.add(btnCadastrar, gbc);

        // 7. Voltar para Login
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginPanel.setOpaque(false);
        
        JLabel lblTemConta = new JLabel("Já tem uma conta?");
        lblTemConta.setForeground(Color.GRAY);
        lblTemConta.setFont(new Font("Arial", Font.PLAIN, 13));
        
        btnVoltarLogin = new JButton("Fazer Login");
        styleLinkButton(btnVoltarLogin);
        btnVoltarLogin.setFont(new Font("Arial", Font.BOLD, 13));
        btnVoltarLogin.setForeground(new Color(153, 50, 255));
        btnVoltarLogin.addActionListener(e -> abrirLogin());

        loginPanel.add(lblTemConta);
        loginPanel.add(btnVoltarLogin);

        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 30, 40);
        signupCard.add(loginPanel, gbc);

        // Adiciona card ao centro
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0; gbcMain.gridy = 1;
        gbcMain.weightx = 1.0; gbcMain.weighty = 1.0;
        gbcMain.anchor = GridBagConstraints.CENTER;
        mainPanel.add(signupCard, gbcMain);

        pack();
    }

    // --- Helpers de Input ---
    
    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(new Color(220, 220, 220));
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        return lbl;
    }

    private JTextField criarTextField() {
        JTextField txt = new JTextField();
        txt.setOpaque(false);
        txt.setForeground(Color.WHITE);
        txt.setFont(new Font("Arial", Font.PLAIN, 16));
        txt.setCaretColor(Color.WHITE);
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(153, 50, 255)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return txt;
    }

    private JPasswordField criarPasswordField() {
        JPasswordField txt = new JPasswordField();
        txt.setOpaque(false);
        txt.setForeground(Color.WHITE);
        txt.setFont(new Font("Arial", Font.PLAIN, 16));
        txt.setCaretColor(Color.WHITE);
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(153, 50, 255)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return txt;
    }

    // ==========================================
    //            LÓGICA DE NEGÓCIO
    // ==========================================

    private void acaoCadastrar() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();
        String cpf = txtCPF.getText().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario novo = new Usuario();
        novo.setNomeCliente(nome);
        novo.setEmailCliente(email);
        novo.setSenhaCliente(senha);
        novo.setCpfCliente(cpf);

        boolean sucesso = UsuarioDAO.salvar(novo);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso! Faça login.");
            abrirLogin();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar. Verifique os dados ou tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
=======
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
>>>>>>> 91edeca91deeac40dcc0d492cc7678a17b36aa9e
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastro().setVisible(true));
    }
}