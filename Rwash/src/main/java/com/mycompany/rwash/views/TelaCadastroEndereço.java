package com.mycompany.rwash.views;

import com.mycompany.rwash.DAO.EnderecoDAO;
import com.mycompany.rwash.DAO.UsuarioDAO;
import com.mycompany.rwash.Model.Endereco;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class TelaCadastroEndereço extends JFrame {

    // Componentes do Formulário
    private JTextField txtCEP;
    private JTextField txtBairro;
    private JTextField txtRua;
    
    private RoundedButton btnFinalizar;
    private JButton btnPular; 

    public TelaCadastroEndereço() {
        // 1. Remove a borda padrão do Windows
        setUndecorated(true);
        
        initUI();
        setTitle("R-Wash | Cadastro de Endereço");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        System.out.println("Tela Endereço aberta — Cliente: " + UsuarioDAO.idClienteLogado);
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
            setFont(new Font("Arial", Font.BOLD, 16));
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
        b.setFont(new Font("Arial", Font.PLAIN, 13));
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

        // --- CARTÃO DE ENDEREÇO ---
        TranslucentPanel addressCard = new TranslucentPanel();
        addressCard.setOpaque(false);
        // LAYOUT MISTO: BorderLayout para Título (Topo) e Campos (Centro)
        addressCard.setLayout(new BorderLayout());
        addressCard.setPreferredSize(new Dimension(400, 500)); 

        // 1. TÍTULO (Fixo no Topo)
        JLabel lblTitulo = new JLabel("Endereço");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(new EmptyBorder(35, 0, 10, 0)); // Espaço do topo
        addressCard.add(lblTitulo, BorderLayout.NORTH);

        // 2. FORMULÁRIO (Centralizado)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30); // Margens laterais
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // CEP
        formPanel.add(criarLabel("CEP"), gbc);
        gbc.gridy++;
        txtCEP = criarTextField();
        formPanel.add(txtCEP, gbc);

        // Bairro
        gbc.gridy++;
        formPanel.add(criarLabel("Bairro"), gbc);
        gbc.gridy++;
        txtBairro = criarTextField();
        formPanel.add(txtBairro, gbc);

        // Rua
        gbc.gridy++;
        formPanel.add(criarLabel("Rua / Logradouro"), gbc);
        gbc.gridy++;
        gbc.insets = new Insets(5, 30, 30, 30); // Espaço maior antes do botão
        txtRua = criarTextField();
        formPanel.add(txtRua, gbc);

        // Botão FINALIZAR
        gbc.gridy++;
        gbc.insets = new Insets(0, 30, 15, 30);
        btnFinalizar = new RoundedButton("CONCLUIR PEDIDO");
        btnFinalizar.setPreferredSize(new Dimension(0, 45));
        btnFinalizar.addActionListener(e -> acaoCadastrarEndereco());
        formPanel.add(btnFinalizar, gbc);

        // Link Pular
        gbc.gridy++;
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linkPanel.setOpaque(false);
        JLabel lblJaTem = new JLabel("Já tem endereço?");
        lblJaTem.setForeground(Color.GRAY);
        lblJaTem.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btnPular = new JButton("Pular / Finalizar");
        styleLinkButton(btnPular);
        btnPular.addActionListener(e -> acaoPular());

        linkPanel.add(lblJaTem);
        linkPanel.add(btnPular);
        formPanel.add(linkPanel, gbc);

        // Adiciona formulário ao centro do card
        addressCard.add(formPanel, BorderLayout.CENTER);

        // Adiciona card ao painel principal
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0; gbcMain.gridy = 1;
        gbcMain.weightx = 1.0; gbcMain.weighty = 1.0;
        gbcMain.anchor = GridBagConstraints.CENTER;
        mainPanel.add(addressCard, gbcMain);

        pack();
    }

    // --- Helpers com FONTE MAIOR ---
    
    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(new Color(220, 220, 220));
        lbl.setFont(new Font("Arial", Font.PLAIN, 16)); // Fonte 16
        return lbl;
    }

    private JTextField criarTextField() {
        JTextField txt = new JTextField();
        txt.setOpaque(false);
        txt.setForeground(Color.WHITE);
        txt.setFont(new Font("Arial", Font.PLAIN, 18)); // Fonte 18
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

    private void acaoCadastrarEndereco() {
        String cep = txtCEP.getText().trim();
        String bairro = txtBairro.getText().trim();
        String rua = txtRua.getText().trim();

        if (cep.isEmpty() || bairro.isEmpty() || rua.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cria objeto
        Endereco novoEnd = new Endereco();
        novoEnd.setCEP(cep);
        novoEnd.setBairro(bairro);
        novoEnd.setRua(rua);
        novoEnd.setCliente_idCliente(UsuarioDAO.idClienteLogado);

        // Salva
        boolean sucesso = EnderecoDAO.salvarEndereco(novoEnd);

        if (sucesso) {
            EnderecoDAO.atualizarStatusCompra(UsuarioDAO.idClienteLogado);
            JOptionPane.showMessageDialog(this, "Pedido finalizado com sucesso!\nVocê será redirecionado para o Dashboard.");
            irParaDashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar endereço.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoPular() {
        EnderecoDAO.atualizarStatusCompra(UsuarioDAO.idClienteLogado);
        irParaDashboard();
    }

    private void irParaDashboard() {
        // Transição suave para o Dashboard
        Transicao.trocar(this, new DashBoardCliente());
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new TelaCadastroEndereço().setVisible(true));
    }
}