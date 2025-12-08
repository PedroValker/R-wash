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
        initUI();
        setExtendedState(MAXIMIZED_BOTH);
        System.out.println("Tela Endereço aberta — Cliente: " + UsuarioDAO.idClienteLogado);
    }

    // ==========================================
    //       CLASSES DE DESIGN E ESTILO
    // ==========================================

    // 1. Painel Gradiente
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

    // 4. Estilo para Botão de Texto (Link)
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
        setTitle("R-Wash | Cadastro de Endereço");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel Principal
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

        // --- CARTÃO DE ENDEREÇO ---
        TranslucentPanel addressCard = new TranslucentPanel();
        addressCard.setOpaque(false);
        addressCard.setLayout(new GridBagLayout());
        addressCard.setPreferredSize(new Dimension(500, 580)); // Tamanho confortável

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
// 1. Título (FIXADO NO TOPO)
        JLabel lblTitulo = new JLabel("Endereço de Entrega");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridy = 0;
        gbc.weighty = 0.0; // Não deixa o título ocupar espaço elástico vertical
        gbc.anchor = GridBagConstraints.PAGE_START; // <--- OBRIGA A FICAR NO TOPO
        
        // (Topo: 20, Esq: 30, BAIXO: 60, Dir: 30)
        // O 60 ali embaixo é o que "empurra" o formulário para longe do título
        gbc.insets = new Insets(20, 30, 60, 30); 
        
        addressCard.add(lblTitulo, gbc);

        // --- PREPARAÇÃO PARA OS CAMPOS (CENTRALIZADOS) ---
        
        // Resetamos as configurações para que os campos não fiquem colados no topo
        gbc.anchor = GridBagConstraints.CENTER; // Volta a centralizar os campos
        gbc.weighty = 0.0;
        
        // 2. CEP
        gbc.gridy++;
        // Margens normais para os campos
        gbc.insets = new Insets(0, 30, 0, 30); 
        addressCard.add(criarLabel("CEP"), gbc);
        gbc.gridy++;
        gbc.insets = new Insets(0, 30, 15, 30);
        txtCEP = criarTextField();
        addressCard.add(txtCEP, gbc);

        // 3. Bairro
        gbc.gridy++;
        gbc.insets = new Insets(5, 30, 0, 30);
        addressCard.add(criarLabel("Bairro"), gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 30, 15, 30);
        txtBairro = criarTextField();
        addressCard.add(txtBairro, gbc);

        // 4. Rua
        gbc.gridy++;
        gbc.insets = new Insets(5, 30, 0, 30);
        addressCard.add(criarLabel("Rua / Logradouro"), gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 30, 30, 30);
        txtRua = criarTextField();
        addressCard.add(txtRua, gbc);

        // 5. Botão CADASTRAR/FINALIZAR
        btnFinalizar = new RoundedButton("CONCLUIR PEDIDO");
        btnFinalizar.setPreferredSize(new Dimension(0, 50));
        btnFinalizar.addActionListener(e -> acaoCadastrarEndereco());
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 30, 20, 30);
        addressCard.add(btnFinalizar, gbc);

        // 6. Link "Já tem endereço?"
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linkPanel.setOpaque(false);
        
        JLabel lblJaTem = new JLabel("Já tem endereço cadastrado?");
        lblJaTem.setForeground(Color.GRAY);
        lblJaTem.setFont(new Font("Arial", Font.PLAIN, 12));
        
        btnPular = new JButton("Pular / Finalizar");
        styleLinkButton(btnPular);
        btnPular.addActionListener(e -> acaoPular());

        linkPanel.add(lblJaTem);
        linkPanel.add(btnPular);

        gbc.gridy++;
        gbc.insets = new Insets(0, 30, 30, 30);
        addressCard.add(linkPanel, gbc);

        // Adiciona card ao centro
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0; gbcMain.gridy = 1;
        gbcMain.weightx = 1.0; gbcMain.weighty = 1.0;
        gbcMain.anchor = GridBagConstraints.CENTER;
        mainPanel.add(addressCard, gbcMain);

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
        // Borda roxa inferior
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(153, 50, 255)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
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
        
        // Associa ao cliente logado
        novoEnd.setCliente_idCliente(UsuarioDAO.idClienteLogado);

        // Salva
        boolean sucesso = EnderecoDAO.salvarEndereco(novoEnd);

        if (sucesso) {
            // ATUALIZA STATUSCOMPRA = 1 (Marca que o cliente finalizou o pedido)
            EnderecoDAO.atualizarStatusCompra(UsuarioDAO.idClienteLogado);

            JOptionPane.showMessageDialog(this, "Pedido finalizado com sucesso!\nVocê será redirecionado para o Dashboard.");
            
            irParaDashboard();
            
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar endereço.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoPular() {
        // Se pular, assume que já tem endereço e finaliza
        EnderecoDAO.atualizarStatusCompra(UsuarioDAO.idClienteLogado);
        irParaDashboard();
    }

    private void irParaDashboard() {
        this.dispose();
        // Abre o Dashboard novo e bonito que criamos
        new DashBoardCliente().setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new TelaCadastroEndereço().setVisible(true));
    }
}