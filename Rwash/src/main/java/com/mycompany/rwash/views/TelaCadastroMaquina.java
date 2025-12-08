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
    private JTextField txtEficiencia; // Mapeado para turbidez no seu código original
    private RoundedButton btnCadastrar;
    private JButton btnPular; // Botão "Prosseguir" (texto)

    public TelaCadastroMaquina() {
        initUI();
        setExtendedState(MAXIMIZED_BOTH);
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
            // Cor preta com transparência (Alpha 100)
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

    // 4. Estilo para Botão de Texto (Link)
    private void styleLinkButton(JButton b) {
        b.setFont(new Font("Arial", Font.PLAIN, 14));
        b.setForeground(new Color(200, 200, 255)); // Azulado claro
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Efeito simples de hover (muda cor)
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setForeground(Color.WHITE); }
            @Override public void mouseExited(MouseEvent e) { b.setForeground(new Color(200, 200, 255)); }
        });
    }

    // ==========================================
    //            CONSTRUÇÃO DA TELA
    // ==========================================

    private void initUI() {
        setTitle("R-Wash | Cadastro de Máquina");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fundo Gradiente Principal
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout()); // Centraliza o cartão
        setContentPane(mainPanel);

        // --- HEADER SIMPLES (Topo) ---
        // Adicionando um título fixo no topo para manter a identidade
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setOpaque(false);
        JLabel title = new JLabel("R-Wash");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);
        
        // Posiciona o Header no topo absoluto (usando BorderLayout no ContentPane seria uma opção, 
        // mas aqui vamos usar GBC para posicionar o header no topo e o form no centro)
        GridBagConstraints gbcHead = new GridBagConstraints();
        gbcHead.gridx = 0; gbcHead.gridy = 0;
        gbcHead.weightx = 1.0; gbcHead.anchor = GridBagConstraints.NORTHWEST;
        gbcHead.insets = new Insets(20, 30, 0, 0);
        mainPanel.add(headerPanel, gbcHead);


        // --- CARTÃO DO FORMULÁRIO (Centro) ---
        TranslucentPanel formCard = new TranslucentPanel();
        formCard.setOpaque(false);
        formCard.setLayout(new GridBagLayout());
        // Tamanho fixo do cartão para ficar elegante
        formCard.setPreferredSize(new Dimension(450, 580)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // 1. Título do Form
        JLabel lblTitulo = new JLabel("Cadastrar Máquina");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 20, 30, 20); // Margem maior no topo
        formCard.add(lblTitulo, gbc);

        // 2. Campo: Modelo
        gbc.gridy++;
        gbc.insets = new Insets(5, 20, 0, 20); // Reset margem
        formCard.add(criarLabel("Modelo da Máquina"), gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 20, 15, 20);
        txtModelo = criarTextField();
        formCard.add(txtModelo, gbc);

        // 3. Campo: Capacidade
        gbc.gridy++;
        gbc.insets = new Insets(5, 20, 0, 20);
        formCard.add(criarLabel("Capacidade de Carga (kg)"), gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 20, 15, 20);
        txtCapacidade = criarTextField();
        formCard.add(txtCapacidade, gbc);

        // 4. Campo: Eficiência/Turbidez
        gbc.gridy++;
        gbc.insets = new Insets(5, 20, 0, 20);
        formCard.add(criarLabel("Eficiência Energética"), gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 20, 25, 20); // Mais espaço antes do botão
        txtEficiencia = criarTextField();
        formCard.add(txtEficiencia, gbc);

        // 5. Botão CADASTRAR
        btnCadastrar = new RoundedButton("SALVAR MÁQUINA");
        btnCadastrar.setPreferredSize(new Dimension(0, 50)); // Altura 50
        btnCadastrar.addActionListener(e -> acaoCadastrar());
        
        gbc.gridy++;
        gbc.insets = new Insets(10, 20, 10, 20);
        formCard.add(btnCadastrar, gbc);

        // 6. Link "Já tem máquina? Prosseguir"
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

        gbc.gridy++;
        gbc.insets = new Insets(0, 20, 30, 20); // Margem fim
        formCard.add(linkPanel, gbc);

        // Adiciona o Cartão ao Painel Principal (Centralizado)
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0; gbcMain.gridy = 1; // Abaixo do header
        gbcMain.weightx = 1.0; gbcMain.weighty = 1.0; // Ocupa espaço para centralizar
        gbcMain.anchor = GridBagConstraints.CENTER;
        mainPanel.add(formCard, gbcMain);

        pack();
    }

    // --- Helpers para criar Inputs Bonitos ---

    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(new Color(220, 220, 220));
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        return lbl;
    }

    private JTextField criarTextField() {
        JTextField txt = new JTextField();
        txt.setOpaque(false); // Transparente
        txt.setForeground(Color.WHITE);
        txt.setFont(new Font("Arial", Font.PLAIN, 16));
        txt.setCaretColor(Color.WHITE);
        // Borda apenas embaixo (estilo Material Design)
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(153, 50, 255)), // Linha roxa
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding interno
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
        novaMaquina.setTurbidezMaquina(eficiencia); // Mapeado conforme seu original
        
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
        this.setVisible(false);
        TelaCadastroEndereço janela = new TelaCadastroEndereço();
        janela.setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new TelaCadastroMaquina().setVisible(true));
    }
}