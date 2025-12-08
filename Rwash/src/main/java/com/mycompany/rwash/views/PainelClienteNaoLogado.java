package com.mycompany.rwash.views;

import com.mycompany.rwash.DAO.UsuarioDAO;
import com.mycompany.rwash.Model.Usuario;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * PainelClienteNaoLogado - Versão Final Completa
 */
public class PainelClienteNaoLogado extends JFrame {

    private int idCliente;
    private JLabel tituloLabel;
    private JButton btnTopoAdquirir;
    private JButton btnTopoLogin;
    private RoundedButton btnAdquirirPrincipal;
    private JLabel imgLabel;

    public PainelClienteNaoLogado() {
        initUI();
        setExtendedState(MAXIMIZED_BOTH);
    }

    public PainelClienteNaoLogado(int idCliente) {
        this.idCliente = idCliente;
        initUI();
        carregarDadosCliente();
        setExtendedState(MAXIMIZED_BOTH);
    }

    private void carregarDadosCliente() {
        Usuario u = UsuarioDAO.buscarPorId(idCliente);
        if (u != null) {
            tituloLabel.setText("R-Wash | " + u.getNomeCliente());
        }
    }

    // --- CLASSES INTERNAS (Animações e Estilos) ---

    // 1. Linha animada
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

    // 2. Botão Arredondado Principal
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

    // 3. Fundo Gradiente
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

    // --- CONSTRUÇÃO DA TELA ---

    private void initUI() {
        setTitle("R-Wash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GradientPanel main = new GradientPanel();
        main.setLayout(new BorderLayout(0,0));
        setContentPane(main);

        // HEADER (Topo)
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(14,22,6,22));

        tituloLabel = new JLabel("R-Wash");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 34));
        tituloLabel.setForeground(Color.WHITE);
        header.add(tituloLabel, BorderLayout.WEST);

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 22, 12));
        rightButtons.setOpaque(false);

        // Botão Topo: Adquira
        btnTopoAdquirir = new JButton("Adquira Agora");
        styleHeaderButton(btnTopoAdquirir);
        btnTopoAdquirir.setPreferredSize(new Dimension(160,36));
        // AÇÃO: Vai para Login
        btnTopoAdquirir.addActionListener(e -> {
            this.setVisible(false);
            new TelaLogin().setVisible(true);
        });

        // Botão Topo: Login
        btnTopoLogin = new JButton("Login");
        styleHeaderButton(btnTopoLogin);
        btnTopoLogin.setPreferredSize(new Dimension(110,36));
        // AÇÃO: Vai para Login
        btnTopoLogin.addActionListener(e -> {
            this.setVisible(false);
            new TelaLogin().setVisible(true);
        });

        rightButtons.add(wrapHeaderButton(btnTopoAdquirir));
        rightButtons.add(Box.createHorizontalStrut(10));
        rightButtons.add(wrapHeaderButton(btnTopoLogin));

        header.add(rightButtons, BorderLayout.EAST);

        // Linha separadora do header
        JSeparator headerLine = new JSeparator();
        headerLine.setForeground(new Color(255,255,255,60));
        JPanel headerWrap = new JPanel(new BorderLayout());
        headerWrap.setOpaque(false);
        headerWrap.add(header, BorderLayout.CENTER);
        headerWrap.add(headerLine, BorderLayout.SOUTH);
        main.add(headerWrap, BorderLayout.NORTH);

        // CENTER content (GridBagLayout para organizar Texto e Imagem)
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // ---------------- LEFT: Texto + Botão Principal ----------------
        // style='text-align: center;' para centralizar o texto conforme pedido
        String html =
            "<html><div style='width:600px; font-family:Arial; line-height:1.18; text-align: center;'>"
            + "<span style='font-size:30px; font-weight:bold; color:#9932FF;'>Transforme</span>"
            + "<span style='font-size:26px; color:#ffffff; margin-left:8px;'> sua rotina e economize água de maneira simples e eficiente.</span>"
            + "<br>"
            + "<span style='font-size:26px; color:#ffffff;'>Ao se cadastrar, você começa a fazer a diferença hoje mesmo, contribuindo para um planeta mais verde.</span>"
            + "<br>"
            + "<span style='font-size:26px; color:#ffffff;'>Não deixe para amanhã o que você pode mudar agora!</span>"
            + "</div></html>";

        JLabel textBlock = new JLabel(html);
        textBlock.setVerticalAlignment(SwingConstants.TOP);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        // Margens ajustadas para não colar na borda
        leftPanel.setBorder(new EmptyBorder(30, 48, 30, 10)); 
        leftPanel.add(textBlock, BorderLayout.NORTH);

        // Botão ADQUIRA — Principal
        btnAdquirirPrincipal = new RoundedButton("ADQUIRA AGORA");
        btnAdquirirPrincipal.setBackground(new Color(153, 50, 255));
        btnAdquirirPrincipal.setPreferredSize(new Dimension(220, 55));
        // AÇÃO: Vai para Login
        btnAdquirirPrincipal.addActionListener(e -> {
            this.setVisible(false);
            new TelaLogin().setVisible(true);
        });

        JPanel ctaHolder = new JPanel(new GridBagLayout());
        ctaHolder.setOpaque(false);
        ctaHolder.setBorder(new EmptyBorder(60, 0, 0, 0)); 
        ctaHolder.add(btnAdquirirPrincipal);

        leftPanel.add(ctaHolder, BorderLayout.SOUTH);

        // Configuração GBC do Painel Esquerdo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6; // Ocupa 60% da largura
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        center.add(leftPanel, gbc);

        // ---------------- RIGHT: Imagem MAIOR e SEGURA ----------------
        imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // 1. Tenta pegar o caminho da imagem de forma segura
        java.net.URL imgUrl = getClass().getResource("/lavadora-de-roupas-maquina-de-lavar-roupas-maquina-com-porta-frontal-frigelar-blog-da-frigelar.jpg");

        // 2. Verifica se achou a imagem
        final ImageIcon original;
        if (imgUrl != null) {
            original = new ImageIcon(imgUrl);
        } else {
            System.err.println("ERRO: Imagem não encontrada. Verifique o caminho.");
            original = new ImageIcon(); // Cria vazio para não travar
        }

        // Lógica de redimensionamento
        center.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (original.getIconWidth() <= 0) return; // Se imagem falhou, retorna

                int totalW = center.getWidth();
                if (totalW <= 0) return;

                int targetW = (int)(totalW * 0.35); // 35% da tela para a imagem
                targetW = Math.max(300, Math.min(targetW, 520));

                double ratio = (double) original.getIconHeight() / original.getIconWidth();
                int targetH = (int)(targetW * ratio);

                Image scaled = original.getImage().getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(scaled));
            }
        });

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 60));
        rightPanel.add(imgLabel, BorderLayout.NORTH);

        // Configuração GBC do Painel Direito
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.4; // Ocupa 40% da largura
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 20, 0, 0); // Margem de segurança à esquerda da imagem
        center.add(rightPanel, gbc);

        main.add(center, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void styleHeaderButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 20));
        b.setForeground(new Color(153,50,255));
        b.setContentAreaFilled(false);
        b.setBorder(new EmptyBorder(6,10,6,10));
        b.setBorderPainted(false);
        b.setFocusable(false);
        b.setOpaque(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PainelClienteNaoLogado().setVisible(true));
    }
}