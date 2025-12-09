package com.mycompany.rwash.views;

import com.mycompany.rwash.DAO.UsuarioDAO;
import com.mycompany.rwash.Model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.MaskFormatter;

public class TelaCadastro extends JFrame {

    // Componentes
    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JTextField txtCPF;
    
    private RoundedButton btnCadastrar;
    private JButton btnVoltarLogin;

    public TelaCadastro() {
        setUndecorated(true);
        initUI();
        setTitle("R-Wash | Criar Conta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    // ==========================================
    //       CLASSES DE DESIGN E ESTILO
    // ==========================================

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

    class TranslucentPanel extends JPanel {
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0, 0, 0, 100)); 
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    public static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 18)); // Fonte maior no botão
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(153, 50, 255)); 
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    private void styleLinkButton(JButton b) {
        b.setFont(new Font("Arial", Font.PLAIN, 14)); // Fonte maior no link
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
    private JFormattedTextField criarCampoCPF() {
    try {
        MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
        cpfMask.setPlaceholderCharacter('_');

        JFormattedTextField txt = new JFormattedTextField(cpfMask);
        txt.setOpaque(false);
        txt.setForeground(Color.WHITE);
        txt.setFont(new Font("Arial", Font.PLAIN, 18));
        txt.setCaretColor(Color.WHITE);
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(153, 50, 255)),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));

        return txt;
    } catch (Exception e) {
        e.printStackTrace();
        return new JFormattedTextField();
    }
}
    // ==========================================
    //            CONSTRUÇÃO DA TELA
    // ==========================================

    private void initUI() {
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout()); 
        setContentPane(mainPanel);

        // Header Personalizado
        HeaderPersonalizado headerPanel = new HeaderPersonalizado(this, "R-Wash");
        GridBagConstraints gbcHead = new GridBagConstraints();
        gbcHead.gridx = 0; gbcHead.gridy = 0;
        gbcHead.weightx = 1.0; 
        gbcHead.fill = GridBagConstraints.HORIZONTAL;
        gbcHead.anchor = GridBagConstraints.NORTH;
        mainPanel.add(headerPanel, gbcHead);

        // --- CARD DE CADASTRO ---
        TranslucentPanel signupCard = new TranslucentPanel();
        signupCard.setOpaque(false);
        // MUDANÇA: BorderLayout para fixar Título no Topo e Campos no Centro
        signupCard.setLayout(new BorderLayout()); 
        signupCard.setPreferredSize(new Dimension(400, 500)); 

        // 1. TÍTULO (Fixo no Topo)
        JLabel lblTitulo = new JLabel("Criar Conta");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(new EmptyBorder(35, 0, 10, 0)); // Margem superior interna
        signupCard.add(lblTitulo, BorderLayout.NORTH);

        // 2. FORMULÁRIO (Centralizado no resto do espaço)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30); // Margens laterais 30px
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Nome
        formPanel.add(criarLabel("Nome Completo"), gbc);
        gbc.gridy++;
        txtNome = criarTextField();
        formPanel.add(txtNome, gbc);

        // Email
        gbc.gridy++;
        formPanel.add(criarLabel("Email"), gbc);
        gbc.gridy++;
        txtEmail = criarTextField();
        formPanel.add(txtEmail, gbc);

        // CPF
       // CPF com máscara
        gbc.gridy++;
        formPanel.add(criarLabel("CPF"), gbc);
        gbc.gridy++;
        txtCPF = criarCampoCPF();
        formPanel.add(txtCPF, gbc);

        // Senha
        gbc.gridy++;
        formPanel.add(criarLabel("Senha"), gbc);
        gbc.gridy++;
        gbc.insets = new Insets(5, 30, 25, 30); // Mais espaço após senha
        txtSenha = criarPasswordField();
        formPanel.add(txtSenha, gbc);

        // Botão CADASTRAR
        gbc.gridy++;
        gbc.insets = new Insets(0, 30, 15, 30);
        btnCadastrar = new RoundedButton("REGISTRAR");
        btnCadastrar.setPreferredSize(new Dimension(0, 45));
        btnCadastrar.addActionListener(e -> acaoCadastrar());
        formPanel.add(btnCadastrar, gbc);

        // Voltar para Login
        gbc.gridy++;
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginPanel.setOpaque(false);
        JLabel lblTemConta = new JLabel("Já tem conta?");
        lblTemConta.setForeground(Color.GRAY);
        lblTemConta.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btnVoltarLogin = new JButton("Fazer Login");
        styleLinkButton(btnVoltarLogin);
        btnVoltarLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltarLogin.setForeground(new Color(153, 50, 255));
        btnVoltarLogin.addActionListener(e -> abrirLogin());

        loginPanel.add(lblTemConta);
        loginPanel.add(btnVoltarLogin);
        formPanel.add(loginPanel, gbc);

        // Adiciona o painel de formulário ao Centro do Card
        signupCard.add(formPanel, BorderLayout.CENTER);

        // Adiciona card ao painel principal (Centralizado na tela)
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0; gbcMain.gridy = 1;
        gbcMain.weightx = 1.0; gbcMain.weighty = 1.0;
        gbcMain.anchor = GridBagConstraints.CENTER;
        mainPanel.add(signupCard, gbcMain);

        pack();
    }

    // --- Helpers de Input (FONTES AUMENTADAS AQUI) ---
    
    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(new Color(220, 220, 220));
        // Aumentado para 16
        lbl.setFont(new Font("Arial", Font.PLAIN, 16)); 
        return lbl;
    }

    private JTextField criarTextField() {
        JTextField txt = new JTextField();
        txt.setOpaque(false);
        txt.setForeground(Color.WHITE);
        // Aumentado para 18
        txt.setFont(new Font("Arial", Font.PLAIN, 18)); 
        txt.setCaretColor(Color.WHITE);
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(153, 50, 255)),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        return txt;
    }

    private JPasswordField criarPasswordField() {
        JPasswordField txt = new JPasswordField();
        txt.setOpaque(false);
        txt.setForeground(Color.WHITE);
        // Aumentado para 18
        txt.setFont(new Font("Arial", Font.PLAIN, 18)); 
        txt.setCaretColor(Color.WHITE);
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(153, 50, 255)),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
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
        String cpf = txtCPF.getText().replace(".", "").replace("-", "").trim();


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
    }

    private void abrirLogin() {
        // Transição suave
        Transicao.trocar(this, new TelaLogin());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastro().setVisible(true));
    }
}