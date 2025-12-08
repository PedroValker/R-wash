package com.mycompany.rwash.views;

import com.mycompany.rwash.DAO.UsuarioDAO;
import com.mycompany.rwash.Model.Usuario;
import com.mycompany.rwash.Model.Usuario.Sessao;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * PainelCliente - Versão Centralizada Verticalmente
 */
public class PainelCliente extends JFrame {

    private int idCliente;
    private JLabel tituloLabel;
    
    // Elementos da UI
    private JButton btnHeaderAdquirir;
    private JButton btnHeaderLogout;
    private RoundedButton btnAcessarDashboard;
    private JLabel imgLabel;

    public PainelCliente() {
        this.idCliente = Sessao.idClienteLogado; 
        initUI();
        setExtendedState(MAXIMIZED_BOTH);
    }

    public PainelCliente(int idClienteLogado) {
        this.idCliente = idClienteLogado;
        initUI();
        carregarDadosCliente();
        setExtendedState(MAXIMIZED_BOTH);
    }

    private void carregarDadosCliente() {
        Usuario cliente = UsuarioDAO.buscarPorId(idCliente);
        if (cliente != null) {
            tituloLabel.setText("R-Wash | Olá, " + cliente.getNomeCliente());
        }
    }

    // ==========================================
    //       CLASSES DE DESIGN E ESTILO
    // ==========================================

    class AnimatedUnderline extends JComponent {
        private Color current = new Color(153, 50, 255);
        private final Color hover = Color.WHITE;
        private final Color normal = new Color(153, 50, 255);
        private Timer timer;

        public AnimatedUnderline(JButton target) {
            setOpaque(false);
            setPreferredSize(new Dimension(100, 3));
            target.addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { animateTo(hover); }
                @Override public void mouseExited(MouseEvent e)  { animateTo(normal); }
            });
        }
        private void animateTo(Color target) {
            if (timer != null && timer.isRunning()) timer.stop();
            timer = new Timer(16, ev -> {
                int r = step(current.getRed(), target.getRed());
                int g = step(current.getGreen(), target.getGreen());
                int b = step(current.getBlue(), target.getBlue());
                current = new Color(r,g,b);
                repaint();
                if (r==target.getRed() && g==target.getGreen() && b==target.getBlue()) timer.stop();
            });
            timer.start();
        }
        private int step(int c, int t) {
            if (c < t) return Math.min(c+12, t);
            if (c > t) return Math.max(c-12, t);
            return c;
        }
        @Override protected void paintComponent(Graphics g) {
            g.setColor(current);
            g.fillRect(0,0,getWidth(), getHeight());
        }
    }

    private JPanel wrapHeaderButton(final JButton btn) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.add(btn);
        wrapper.add(center, BorderLayout.CENTER);

        final AnimatedUnderline underline = new AnimatedUnderline(btn);
        wrapper.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) {
                underline.setPreferredSize(new Dimension(center.getWidth(), 3));
                underline.revalidate();
            }
        });
        wrapper.add(underline, BorderLayout.SOUTH);
        return wrapper;
    }

    private void styleHeaderButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 18));
        b.setForeground(new Color(153, 50, 255));
        b.setContentAreaFilled(false);
        b.setBorder(new EmptyBorder(6,10,6,10));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 20));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0,0,getWidth(),getHeight(),28,28);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    class GradientPanel extends JPanel {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            int w = getWidth(), h = getHeight();
            GradientPaint gp = new GradientPaint(0,0,new Color(23,21,56), w, h, new Color(60,0,120));
            g2.setPaint(gp);
            g2.fillRect(0,0,w,h);
        }
    }

    // ==========================================
    //            CONSTRUÇÃO DA TELA
    // ==========================================

    private void initUI() {
        setTitle("R-Wash | Área do Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GradientPanel main = new GradientPanel();
        main.setLayout(new BorderLayout(0,0));
        setContentPane(main);

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(14,22,6,22));

        tituloLabel = new JLabel("R-Wash");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 32));
        tituloLabel.setForeground(Color.WHITE);
        header.add(tituloLabel, BorderLayout.WEST);

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 22, 12));
        rightButtons.setOpaque(false);

        btnHeaderAdquirir = new JButton("Novo Pedido");
        styleHeaderButton(btnHeaderAdquirir);
        btnHeaderAdquirir.addActionListener(e -> acaoAdquirirProduto());

        btnHeaderLogout = new JButton("Logout");
        styleHeaderButton(btnHeaderLogout);
        btnHeaderLogout.addActionListener(e -> acaoLogout());

        rightButtons.add(wrapHeaderButton(btnHeaderAdquirir));
        rightButtons.add(Box.createHorizontalStrut(15));
        rightButtons.add(wrapHeaderButton(btnHeaderLogout));

        header.add(rightButtons, BorderLayout.EAST);

        JSeparator headerLine = new JSeparator();
        headerLine.setForeground(new Color(255,255,255,60));
        JPanel headerWrap = new JPanel(new BorderLayout());
        headerWrap.setOpaque(false);
        headerWrap.add(header, BorderLayout.CENTER);
        headerWrap.add(headerLine, BorderLayout.SOUTH);
        main.add(headerWrap, BorderLayout.NORTH);

        // --- CONTEÚDO CENTRAL ---
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // >>> LADO ESQUERDO: Texto e Botão <<<
        // MUDANÇA: Usando GridBagLayout interno para centralizar verticalmente o conteúdo
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);
        // Margem esquerda grande (100) para empurrar o conteúdo
        leftPanel.setBorder(new EmptyBorder(0, 100, 0, 24)); 

        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0;
        gbcLeft.gridy = 0;
        gbcLeft.anchor = GridBagConstraints.WEST; // Alinha tudo à esquerda
        gbcLeft.fill = GridBagConstraints.HORIZONTAL;
        
        // 1. TEXTO
        String html =
            "<html><div style='width:550px; font-family:Arial; line-height:1.3; text-align: center;'>"
            + "<span style='font-size:38px; font-weight:bold; color:#ffffff;'>Agradecemos o seu acesso!</span><br><br><br><br><br>"
            + "<span style='font-size:24px; color:#ffffff;'>Caso já tenha um produto, acesse o </span>"
            + "<span style='font-size:24px; font-weight:bold; color:#9932FF;'>Relatório/DashBoard</span>"
            + "<span style='font-size:24px; color:#ffffff;'> clicando abaixo.</span><br>"
            + "<span style='font-size:24px; color:#ffffff;'>Ou se não, adquira um novo produto no menu acima.</span>"
            + "</div></html>";

        JLabel textBlock = new JLabel(html);
        leftPanel.add(textBlock, gbcLeft);

        // 2. BOTÃO
        btnAcessarDashboard = new RoundedButton("ACESSAR DASHBOARD");
        btnAcessarDashboard.setBackground(new Color(153, 50, 255));
        btnAcessarDashboard.setFont(new Font("Arial", Font.BOLD, 22));
        
        Dimension btnDim = new Dimension(350, 65);
        btnAcessarDashboard.setPreferredSize(btnDim);
        btnAcessarDashboard.setMinimumSize(btnDim);
        btnAcessarDashboard.setMaximumSize(btnDim);
        btnAcessarDashboard.addActionListener(e -> acaoAcessarDashboard());

        // Painel para afastar o botão do texto (margin top)
        JPanel ctaHolder = new JPanel(new GridBagLayout());
        ctaHolder.setOpaque(false);
        ctaHolder.setBorder(new EmptyBorder(50, 0, 0, 0)); // Margem topo 50, esquerda 100

        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.anchor = GridBagConstraints.WEST;
        gbcBtn.fill = GridBagConstraints.NONE;
        ctaHolder.add(btnAcessarDashboard, gbcBtn);
        
        gbcLeft.gridy = 1; // Posição abaixo do texto
        gbcLeft.insets = new Insets(0, 0, 0, 0); // Sem insets extras aqui, usamos a borda do ctaHolder
        leftPanel.add(ctaHolder, gbcLeft);

        // Adiciona Lado Esquerdo na Grid Principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 1.0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // MUDANÇA: Preenche horizontal, mas altura ajusta ao centro
        gbc.anchor = GridBagConstraints.CENTER;   // MUDANÇA: Centraliza o bloco todo verticalmente
        center.add(leftPanel, gbc);

        // >>> LADO DIREITO: Imagem <<<
        imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        java.net.URL imgUrl = getClass().getResource("/lavadora_dark.jpg");
        if (imgUrl == null) {
            imgUrl = getClass().getResource("/lavadora-de-roupas-maquina-de-lavar-roupas-maquina-com-porta-frontal-frigelar-blog-da-frigelar.jpg");
        }

        final ImageIcon original;
        if (imgUrl != null) {
            original = new ImageIcon(imgUrl);
        } else {
            original = new ImageIcon();
        }

        center.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (original.getIconWidth() <= 0) return;
                int totalW = center.getWidth();
                if (totalW <= 0) return;

                int targetW = (int)(totalW * 0.35); 
                targetW = Math.max(300, Math.min(targetW, 550));

                double ratio = (double) original.getIconHeight() / original.getIconWidth();
                int targetH = (int)(targetW * ratio);

                Image scaled = original.getImage().getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(scaled));
            }
        });

        // MUDANÇA: GridBagLayout aqui também para centralizar a imagem verticalmente
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 60));
        
        GridBagConstraints gbcImg = new GridBagConstraints();
        gbcImg.anchor = GridBagConstraints.CENTER;
        rightPanel.add(imgLabel, gbcImg);

        // Adiciona Lado Direito na Grid Principal
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza verticalmente
        gbc.insets = new Insets(0, 20, 0, 0);
        center.add(rightPanel, gbc);

        main.add(center, BorderLayout.CENTER);
        pack();
    }

    // ==========================================
    //            LÓGICA DE NEGÓCIO
    // ==========================================

    private void acaoLogout() {
        Sessao.idClienteLogado = 0;
        dispose();
        new TelaLogin().setVisible(true);
    }

    private void acaoAdquirirProduto() {
        setVisible(false);
        new TelaCadastroMaquina().setVisible(true);
    }

    private void acaoAcessarDashboard() {
        boolean jaComprou = UsuarioDAO.clienteJaComprou(idCliente);

        if (jaComprou) {
            this.setVisible(false);
           DashBoardCliente dashboard = new DashBoardCliente(); 
            dashboard.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Acesso Negado!\n\n" +
                "Identificamos que você ainda não possui um produto R-Wash cadastrado.\n" +
                "Por favor, clique em 'Novo Pedido' para adquirir sua máquina.",
                "Produto Necessário", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new PainelCliente().setVisible(true));
    }
}