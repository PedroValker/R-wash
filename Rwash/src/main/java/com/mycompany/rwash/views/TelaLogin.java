package com.mycompany.rwash.views;

import com.mycompany.rwash.DAO.UsuarioDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class TelaLogin extends JFrame {

    // Componentes
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private RoundedButton btnLogin;
    private JButton btnEsquecerSenha;
    private JButton btnCadastrar;

    public TelaLogin() {
        // 1. Remove borda do Windows
        setUndecorated(true);
        
        initUI();
        
        // Configurações da Janela
        setTitle("R-Wash | Login");
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

        // --- HEADER PERSONALIZADO (Com botão X e arraste) ---
        // Certifique-se de que a classe HeaderPersonalizado.java existe no pacote
        HeaderPersonalizado headerPanel = new HeaderPersonalizado(this, "R-Wash");
        
        GridBagConstraints gbcHead = new GridBagConstraints();
        gbcHead.gridx = 0; gbcHead.gridy = 0;
        gbcHead.weightx = 1.0; 
        gbcHead.fill = GridBagConstraints.HORIZONTAL;
        gbcHead.anchor = GridBagConstraints.NORTH;
        // Sem insets ou margens, pois o HeaderPersonalizado já cuida disso
        mainPanel.add(headerPanel, gbcHead);

        // --- CARD DE LOGIN ---
        TranslucentPanel loginCard = new TranslucentPanel();
        loginCard.setOpaque(false);
        loginCard.setLayout(new GridBagLayout());
        
        // --- TAMANHO PADRONIZADO (Igual ao Cadastro) ---
        loginCard.setPreferredSize(new Dimension(400, 500)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 40, 10, 40); // Margens laterais maiores (40) para o card largo
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // 1. Título "Login"
        JLabel lblTitulo = new JLabel("Login");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridy = 0;
        gbc.insets = new Insets(40, 40, 40, 40); // Mais espaço vertical no título
        loginCard.add(lblTitulo, gbc);

        // 2. Email
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 0, 40);
        loginCard.add(criarLabel("Email"), gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 25, 40); // Mais espaçamento entre campos
        txtEmail = criarTextField();
        loginCard.add(txtEmail, gbc);

        // 3. Senha
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 0, 40);
        loginCard.add(criarLabel("Senha"), gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 10, 40);
        txtSenha = criarPasswordField();
        loginCard.add(txtSenha, gbc);

        // 4. Esqueci Senha (Direita)
        JPanel forgotPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        forgotPanel.setOpaque(false);
        btnEsquecerSenha = new JButton("Esqueceu a senha?");
        styleLinkButton(btnEsquecerSenha);
        btnEsquecerSenha.setFont(new Font("Arial", Font.PLAIN, 13));
        forgotPanel.add(btnEsquecerSenha);

        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 40, 40); // Espaço antes do botão Entrar
        loginCard.add(forgotPanel, gbc);

        // 5. Botão LOGIN
        btnLogin = new RoundedButton("ENTRAR");
        btnLogin.setPreferredSize(new Dimension(0, 50));
        btnLogin.addActionListener(e -> acaoLogin());
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 30, 40);
        loginCard.add(btnLogin, gbc);

        // 6. Criar Conta
        JPanel signupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signupPanel.setOpaque(false);
        
        JLabel lblNaoTem = new JLabel("Não tem conta?");
        lblNaoTem.setForeground(Color.GRAY);
        lblNaoTem.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btnCadastrar = new JButton("Cadastre-se");
        styleLinkButton(btnCadastrar);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastrar.setForeground(new Color(153, 50, 255)); 
        btnCadastrar.addActionListener(e -> abrirCadastro());

        signupPanel.add(lblNaoTem);
        signupPanel.add(btnCadastrar);

        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 40, 40);
        loginCard.add(signupPanel, gbc);

        // Adiciona card ao centro
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0; gbcMain.gridy = 1;
        gbcMain.weightx = 1.0; gbcMain.weighty = 1.0;
        gbcMain.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginCard, gbcMain);

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

    private void acaoLogin() {
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());

        int idClienteLogado = UsuarioDAO.autenticar(email, senha);

        if (idClienteLogado > 0) {
            
            // --- TRANSIÇÃO SUAVE (Substituindo o dispose seco) ---
            
            boolean jaComprou = UsuarioDAO.clienteJaComprou(idClienteLogado);

            if (jaComprou) {
                // Vai para o Painel (que tem acesso ao Dashboard)
                Transicao.trocar(this, new PainelCliente(idClienteLogado));
            } else {
                // Vai para o Painel (que tem acesso à Compra)
                Transicao.trocar(this, new PainelCliente(idClienteLogado));
            }

        } else {
            JOptionPane.showMessageDialog(this, 
                "Email ou senha inválidos.", 
                "Falha no Login", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCadastro() {
        // --- TRANSIÇÃO SUAVE PARA CADASTRO ---
        Transicao.trocar(this, new TelaCadastro());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}