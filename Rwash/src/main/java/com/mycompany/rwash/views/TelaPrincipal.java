package com.mycompany.rwash.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TelaPrincipal extends JFrame {

    // Variáveis para arrastar a janela
    private int pX, pY;

    // Elementos da UI
    private JButton btnHeaderLogin;
    private JButton btnHeaderCadastro;
    private JButton btnClose; // Botão X
    private RoundedButton btnSaibaMais;
    private JLabel imgLabel;

    public TelaPrincipal() {
        // 1. Remove a borda padrão do Windows
        setUndecorated(true); 
        
        initUI();
        
        // Configurações da Janela
        setTitle("R-Wash | Bem-vindo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    // ==========================================
    //       CLASSES DE DESIGN E ESTILO
    // ==========================================

    // 1. Botão Arredondado (Principal)
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
            g2.setColor(new Color(153, 50, 255)); // Roxo Neon
            g2.fillRoundRect(0,0,getWidth(),getHeight(),28,28);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    // 2. Painel Gradiente
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

    // 3. Sublinhado Animado (Para botões do Header)
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
            if (timer!=null && timer.isRunning()) timer.stop();
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

    // Método auxiliar para criar o botão com sublinhado
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

    // ==========================================
    //            CONSTRUÇÃO DA TELA
    // ==========================================

    private void initUI() {
        GradientPanel main = new GradientPanel();
        main.setLayout(new BorderLayout(0,0));
        setContentPane(main);

        // --- HEADER PERSONALIZADO (Com Login, Cadastro e Fechar) ---
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(14,22,6,22));

        // Título (Esquerda)
        JLabel tituloLabel = new JLabel("R-Wash");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 32));
        tituloLabel.setForeground(Color.WHITE);
        header.add(tituloLabel, BorderLayout.WEST);

        // Botões (Direita)
        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        rightButtons.setOpaque(false);

        // 1. Login
        btnHeaderLogin = new JButton("Login");
        styleHeaderButton(btnHeaderLogin);
        btnHeaderLogin.addActionListener(e -> abrirLogin());

        // 2. Cadastro
        btnHeaderCadastro = new JButton("Cadastro");
        styleHeaderButton(btnHeaderCadastro);
        btnHeaderCadastro.addActionListener(e -> abrirCadastro());
        
        // 3. Botão Fechar (X)
        btnClose = new JButton("X");
        btnClose.setFont(new Font("Arial", Font.BOLD, 20));
        btnClose.setForeground(Color.WHITE);
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnClose.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btnClose.setForeground(new Color(255, 80, 80)); } // Fica vermelho
            @Override public void mouseExited(MouseEvent e) { btnClose.setForeground(Color.WHITE); }
        });
        btnClose.addActionListener(e -> System.exit(0)); // Fecha o app

        // Adiciona os botões ao painel direito
        rightButtons.add(wrapHeaderButton(btnHeaderLogin));
        rightButtons.add(wrapHeaderButton(btnHeaderCadastro));
        
        // Separador pequeno antes do X
        JSeparator sepVertical = new JSeparator(SwingConstants.VERTICAL);
        sepVertical.setPreferredSize(new Dimension(2, 25));
        sepVertical.setForeground(new Color(255,255,255,50));
        rightButtons.add(Box.createHorizontalStrut(10));
        rightButtons.add(sepVertical);
        rightButtons.add(Box.createHorizontalStrut(10));
        rightButtons.add(btnClose);

        header.add(rightButtons, BorderLayout.EAST);

        // LÓGICA PARA ARRASTAR A JANELA (Drag & Drop)
        header.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                pX = me.getX();
                pY = me.getY();
            }
        });
        header.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {
                setLocation(getLocation().x + me.getX() - pX,
                            getLocation().y + me.getY() - pY);
            }
        });

        // Separador Inferior do Header
        JSeparator headerLine = new JSeparator();
        headerLine.setForeground(new Color(255,255,255,60));
        JPanel headerWrap = new JPanel(new BorderLayout());
        headerWrap.setOpaque(false);
        headerWrap.add(header, BorderLayout.CENTER);
        headerWrap.add(headerLine, BorderLayout.SOUTH);
        main.add(headerWrap, BorderLayout.NORTH);

        // --- CONTEÚDO CENTRAL (GridBagLayout) ---
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // >>> LADO ESQUERDO: Texto e Botão <<<
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);
        leftPanel.setBorder(new EmptyBorder(0, 100, 0, 24)); 

        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0; gbcLeft.gridy = 0;
        gbcLeft.anchor = GridBagConstraints.WEST;
        gbcLeft.fill = GridBagConstraints.HORIZONTAL;

        String html =
            "<html><div style='width:550px; font-family:Arial; line-height:1.2; text-align: left;'>"
            + "<span style='font-size:56px; font-weight:bold; color:#ffffff;'>Água limpa,</span><br>"
            + "<span style='font-size:56px; font-weight:bold; color:#9932FF;'>futuro</span><br>"
            + "<span style='font-size:56px; font-weight:bold; color:#ffffff;'>sustentável</span>"
            + "</div></html>";

        JLabel textBlock = new JLabel(html);
        leftPanel.add(textBlock, gbcLeft);

        // Botão Saiba Mais
        btnSaibaMais = new RoundedButton("SAIBA MAIS");
        Dimension btnDim = new Dimension(280, 70);
        btnSaibaMais.setPreferredSize(btnDim);
        btnSaibaMais.setMinimumSize(btnDim);
        btnSaibaMais.setMaximumSize(btnDim);
        btnSaibaMais.addActionListener(e -> abrirPainelClienteNaoLogado());

        JPanel ctaHolder = new JPanel(new GridBagLayout());
        ctaHolder.setOpaque(false);
        ctaHolder.setBorder(new EmptyBorder(40, 0, 0, 0)); 

        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.anchor = GridBagConstraints.WEST;
        gbcBtn.fill = GridBagConstraints.NONE;
        ctaHolder.add(btnSaibaMais, gbcBtn);
        
        gbcLeft.gridy = 1;
        leftPanel.add(ctaHolder, gbcLeft);

        // Add Left to Main Grid
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.6; gbc.weighty = 1.0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        center.add(leftPanel, gbc);

        // >>> LADO DIREITO: Imagem <<<
        imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        java.net.URL imgUrl = getClass().getResource("/lavadora_dark.jpg");
        if (imgUrl == null) {
            imgUrl = getClass().getResource("/lavadora-de-roupas-maquina-de-lavar-roupas-maquina-com-porta-frontal-frigelar-blog-da-frigelar.jpg");
        }
        
        final ImageIcon original;
        if (imgUrl != null) original = new ImageIcon(imgUrl);
        else original = new ImageIcon();

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

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 60));
        
        GridBagConstraints gbcImg = new GridBagConstraints();
        gbcImg.anchor = GridBagConstraints.CENTER;
        rightPanel.add(imgLabel, gbcImg);

        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 0.4; gbc.weighty = 1.0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 20, 0, 0);
        center.add(rightPanel, gbc);

        main.add(center, BorderLayout.CENTER);
        pack();
    }

    // --- TRANSIÇÕES SUAVES ---

    private void abrirLogin() {
        // Usa a classe Transicao para efeito Fade In/Out
        Transicao.trocar(this, new TelaLogin());
    }

    private void abrirCadastro() {
        Transicao.trocar(this, new TelaCadastro());
    }

    private void abrirPainelClienteNaoLogado() {
        Transicao.trocar(this, new PainelClienteNaoLogado());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}