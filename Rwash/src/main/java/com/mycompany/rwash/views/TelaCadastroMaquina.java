package com.mycompany.rwash.views;

import com.mycompany.rwash.DAO.MaquinaDAO;
import com.mycompany.rwash.DAO.UsuarioDAO;
import com.mycompany.rwash.Model.Maquina;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class TelaCadastroMaquina extends JFrame {

    // Componentes do Formulário
    private JTextField txtModelo;
    private JTextField txtCapacidade;
    private JTextField txtEficiencia; 
    private RoundedButton btnCadastrar;
    private JButton btnPular; 

    public TelaCadastroMaquina() {
        // 1. Remove a borda padrão do Windows
        setUndecorated(true);
        
        initUI();
        setTitle("R-Wash | Cadastro de Máquina");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        System.out.println("Tela Cadastro Maquina aberta — Cliente: " + UsuarioDAO.idClienteLogado);
    }

    // ==========================================
    //       CLASSES DE DESIGN E ESTILO
    // ==========================================

    // 1. Painel Gradiente (Fundo da Tela)
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

    // 2. Painel Translúcido (Card do Formulário)
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

    // 3. Botão Principal Arredondado
    public static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setForeground(Color.WHITE);
            // Fonte aumentada para 18px Bold
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

    // 4. Estilo para Botão de Texto (Link)
    private void styleLinkButton(JButton b) {
        b.setFont(new Font("Arial", Font.PLAIN, 14)); // Fonte aumentada
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
        // Fundo Gradiente Principal
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout()); 
        setContentPane(mainPanel);

        // --- HEADER PERSONALIZADO ---
        HeaderPersonalizado headerPanel = new HeaderPersonalizado(this, "R-Wash");
        
        GridBagConstraints gbcHead = new GridBagConstraints();
        gbcHead.gridx = 0; gbcHead.gridy = 0;
        gbcHead.weightx = 1.0; 
        gbcHead.fill = GridBagConstraints.HORIZONTAL;
        gbcHead.anchor = GridBagConstraints.NORTH;
        mainPanel.add(headerPanel, gbcHead);

        // --- CARTÃO DO FORMULÁRIO (Centro) ---
        TranslucentPanel formCard = new TranslucentPanel();
        formCard.setOpaque(false);
        // MUDANÇA: BorderLayout para fixar Título no Topo
        formCard.setLayout(new BorderLayout());
        formCard.setPreferredSize(new Dimension(400, 500)); 

        // 1. Título do Form (Fixo no Topo)
        JLabel lblTitulo = new JLabel("Cadastrar Máquina");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28)); // Fonte grande
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(new EmptyBorder(35, 0, 10, 0)); // Espaço do topo
        formCard.add(lblTitulo, BorderLayout.NORTH);

        // 2. Painel Interno do Formulário (Centralizado)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30); // Margens laterais 30px
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Campo: Modelo
        formPanel.add(criarLabel("Modelo da Máquina"), gbc);
        gbc.gridy++;
        txtModelo = criarTextField();
        formPanel.add(txtModelo, gbc);

        // Campo: Capacidade
        gbc.gridy++;
        formPanel.add(criarLabel("Capacidade de Carga (kg)"), gbc);
        gbc.gridy++;
        txtCapacidade = criarTextField();
        formPanel.add(txtCapacidade, gbc);

        // Campo: Eficiência/Turbidez
        gbc.gridy++;
        formPanel.add(criarLabel("Eficiência Energética"), gbc);
        gbc.gridy++;
        gbc.insets = new Insets(5, 30, 25, 30); // Mais espaço antes do botão
        txtEficiencia = criarTextField();
        formPanel.add(txtEficiencia, gbc);

        // Botão CADASTRAR
        gbc.gridy++;
        gbc.insets = new Insets(0, 30, 15, 30);
        btnCadastrar = new RoundedButton("SALVAR MÁQUINA");
        btnCadastrar.setPreferredSize(new Dimension(0, 45)); 
        btnCadastrar.addActionListener(e -> acaoCadastrar());
        formPanel.add(btnCadastrar, gbc);

        // Link "Já tem máquina? Prosseguir"
        gbc.gridy++;
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linkPanel.setOpaque(false);
        
        JLabel lblJaTem = new JLabel("Já possui cadastro?");
        lblJaTem.setForeground(Color.GRAY);
        lblJaTem.setFont(new Font("Arial", Font.PLAIN, 12));
        
        btnPular = new JButton("Pular / Prosseguir");
        styleLinkButton(btnPular);
        btnPular.addActionListener(e -> acaoPular());

        linkPanel.add(lblJaTem);
        linkPanel.add(btnPular);
        formPanel.add(linkPanel, gbc);

        // Adiciona formulário ao centro do card
        formCard.add(formPanel, BorderLayout.CENTER);

        // Adiciona o Cartão ao Painel Principal (Centralizado)
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0; gbcMain.gridy = 1; 
        gbcMain.weightx = 1.0; gbcMain.weighty = 1.0;
        gbcMain.anchor = GridBagConstraints.CENTER;
        mainPanel.add(formCard, gbcMain);

        pack();
    }

    // --- Helpers para criar Inputs Bonitos (FONTES AUMENTADAS) ---

    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(new Color(220, 220, 220));
        // Aumentado para 16px
        lbl.setFont(new Font("Arial", Font.PLAIN, 16));
        return lbl;
    }

    private JTextField criarTextField() {
        JTextField txt = new JTextField();
        txt.setOpaque(false); // Transparente
        txt.setForeground(Color.WHITE);
        // Aumentado para 18px
        txt.setFont(new Font("Arial", Font.PLAIN, 18));
        txt.setCaretColor(Color.WHITE);
        // Borda apenas embaixo (estilo Material Design)
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(153, 50, 255)), // Linha roxa
            BorderFactory.createEmptyBorder(2, 5, 2, 5) // Padding interno
        ));
        return txt;
    }

    // ==========================================
    //            LÓGICA DE NEGÓCIO
    // ==========================================

    private void acaoCadastrar() {
        String modelo = txtModelo.getText().trim();
        String capacidade = txtCapacidade.getText().trim();
        String eficiencia = txtEficiencia.getText().trim();

        if (modelo.isEmpty() || capacidade.isEmpty() || eficiencia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cria objeto Máquina
        Maquina novaMaquina = new Maquina();
        novaMaquina.setModeloMaquina(modelo);
        novaMaquina.setCapacidadeMaquina(capacidade);
        novaMaquina.setTurbidezMaquina(eficiencia); 
        
        // Define o ID do cliente logado
        novaMaquina.setCliente_idCliente(UsuarioDAO.idClienteLogado);

        boolean sucesso = MaquinaDAO.salvar(novaMaquina);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Máquina cadastrada com sucesso!");
            irParaProximaTela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoPular() {
        // Lógica do botão "Prosseguir" (caso o cliente não queira cadastrar agora ou já tenha)
        irParaProximaTela();
    }

    private void irParaProximaTela() {
        // Transição suave para tela de endereço
        Transicao.trocar(this, new TelaCadastroEndereço());
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new TelaCadastroMaquina().setVisible(true));
    }
}