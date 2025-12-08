package com.mycompany.rwash.views;

import com.mycompany.rwash.Model.Usuario;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TelaPrincipal extends JFrame {

    // Botões do cabeçalho
    private JButton btnMudarTelaLogin;
    private JButton btnMudarTelaCadastro;

    // Botão Principal
    private RoundedButton btnSaibaMais;
    
    // Label da imagem para controle de redimensionamento
    private JLabel imgLabel;

    public TelaPrincipal() {
        initUI();
        // Configurações da Janela
        setTitle("R-Wash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    // --- CLASSES AUXILIARES (Idênticas ao PainelCliente) ---

    // 1. Botão Arredondado
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

    // 2. Painel Gradiente
    class GradientPanel extends JPanel {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            int w = getWidth(), h = getHeight();
            // Mesmas cores do PainelCliente
            GradientPaint gp = new GradientPaint(0,0,new Color(23,21,56), w, h, new Color(60,0,120));
            g2.setPaint(gp);
            g2.fillRect(0,0,w,h);
        }
    }

    // 3. Sublinhado Animado
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
        b.setFont(new Font("Arial", Font.BOLD, 20));
        // Alterado de Color.WHITE para a cor roxa da marca
        b.setForeground(new Color(153, 50, 255)); 
        b.setContentAreaFilled(false);
        b.setBorder(new EmptyBorder(6,10,6,10));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // --- CONSTRUÇÃO DA INTERFACE ---

    private void initUI() {
        GradientPanel main = new GradientPanel();
        main.setLayout(new BorderLayout(0,0));
        setContentPane(main);

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(14,22,6,22));

        JLabel tituloLabel = new JLabel("R-Wash");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 34));
        tituloLabel.setForeground(Color.WHITE);
        header.add(tituloLabel, BorderLayout.WEST);

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 22, 12));
        rightButtons.setOpaque(false);

        // Botão Login
        btnMudarTelaLogin = new JButton("Login");
        styleHeaderButton(btnMudarTelaLogin);
        btnMudarTelaLogin.addActionListener(e -> abrirLogin());

        // Botão Cadastro
        btnMudarTelaCadastro = new JButton("Cadastro");
        styleHeaderButton(btnMudarTelaCadastro);
        btnMudarTelaCadastro.addActionListener(e -> abrirCadastro());

        rightButtons.add(wrapHeaderButton(btnMudarTelaLogin));
        rightButtons.add(Box.createHorizontalStrut(10));
        rightButtons.add(wrapHeaderButton(btnMudarTelaCadastro));

        header.add(rightButtons, BorderLayout.EAST);

        // Separador do Header
        JSeparator headerLine = new JSeparator();
        headerLine.setForeground(new Color(255,255,255,60));
        JPanel headerWrap = new JPanel(new BorderLayout());
        headerWrap.setOpaque(false);
        headerWrap.add(header, BorderLayout.CENTER);
        headerWrap.add(headerLine, BorderLayout.SOUTH);
        main.add(headerWrap, BorderLayout.NORTH);

        // --- CENTER CONTENT (GridBagLayout) ---
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // 1. LEFT: Texto + Botão
        
        // Adaptando seu texto antigo para HTML para manter a mesma formatação visual do outro painel
        String html =
            "<html><div style='width:600px; font-family:Arial; line-height:1.2; text-align: left;'>"
            + "<span style='font-size:56px; font-weight:bold; color:#ffffff;'>Água limpa,</span><br>"
            + "<span style='font-size:56px; font-weight:bold; color:#9932FF;'>futuro</span><br>"
            + "<span style='font-size:56px; font-weight:bold; color:#ffffff;'>sustentável</span>"
            + "</div></html>";

        JLabel textBlock = new JLabel(html);
        textBlock.setVerticalAlignment(SwingConstants.TOP);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        leftPanel.setBorder(new EmptyBorder(30, 150, 30, 24));
        leftPanel.add(textBlock, BorderLayout.NORTH);

        // Botão Saiba Mais
        btnSaibaMais = new RoundedButton("SAIBA MAIS");
        btnSaibaMais.setBackground(new Color(153, 50, 255));
        btnSaibaMais.setPreferredSize(new Dimension(220, 55));
        btnSaibaMais.addActionListener(e -> abrirPainelClienteNaoLogado());

        JPanel ctaHolder = new JPanel(new GridBagLayout()); 
        ctaHolder.setOpaque(false);
        ctaHolder.setBorder(new EmptyBorder(35, 70, 0, 0));        
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.anchor = GridBagConstraints.WEST;
        gbcBtn.weightx = 1.0;
        ctaHolder.add(btnSaibaMais, gbcBtn);

        leftPanel.add(ctaHolder, BorderLayout.CENTER);

        // Configuração GBC do Painel Esquerdo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6; 
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        center.add(leftPanel, gbc);

        // 2. RIGHT: Imagem (Lógica idêntica ao PainelCliente)
        imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Tenta carregar a imagem
        java.net.URL imgUrl = getClass().getResource("/lavadora-de-roupas-maquina-de-lavar-roupas-maquina-com-porta-frontal-frigelar-blog-da-frigelar.jpg");
        final ImageIcon original;
        if (imgUrl != null) {
            original = new ImageIcon(imgUrl);
        } else {
            original = new ImageIcon(); // Vazio para evitar erro
        }

        // Lógica de redimensionamento dinâmico
        center.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (original.getIconWidth() <= 0) return;

                int totalW = center.getWidth();
                if (totalW <= 0) return;

                // MESMA PROPORÇÃO DO PAINEL CLIENTE (0.45 = 45% da tela)
                int targetW = (int)(totalW * 0.45); 
                targetW = Math.max(300, Math.min(targetW, 600)); // Limites min/max

                double ratio = (double) original.getIconHeight() / original.getIconWidth();
                int targetH = (int)(targetW * ratio);

                Image scaled = original.getImage().getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(scaled));
            }
        });

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 60)); // Margens idênticas
        rightPanel.add(imgLabel, BorderLayout.NORTH);

        // Configuração GBC do Painel Direito
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 20, 0, 0); // Separador
        center.add(rightPanel, gbc);

        main.add(center, BorderLayout.CENTER);

        pack();
    }

    // --- MÉTODOS DE NAVEGAÇÃO ---

    private void abrirLogin() {
        this.setVisible(false);
        new TelaLogin().setVisible(true);
    }

    private void abrirCadastro() {
        this.setVisible(false);
        new TelaCadastro().setVisible(true);
    }

    private void abrirPainelClienteNaoLogado() {
        this.setVisible(false);
        new PainelClienteNaoLogado().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}